/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mblog.base.modules.lang.Const;
import mblog.core.data.Comment;
import mblog.core.data.Post;
import mblog.core.data.User;
import mblog.core.modules.pojos.Paging;
import mblog.core.persist.dao.CommentDao;
import mblog.core.persist.entity.CommentPO;
import mblog.core.persist.service.CommentService;
import mblog.core.persist.service.PostService;
import mblog.core.persist.service.UserEventService;
import mblog.core.persist.service.UserService;
import mblog.core.persist.utils.BeanMapUtils;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author langhsu
 *
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao       commentDao;
    @Autowired
    private UserService      userService;
    @Autowired
    private UserEventService userEventService;
    @Autowired
    private PostService      postService;

    @Override
    @Transactional(readOnly = true)
    public void paging4Admin(Paging paging, String key) {
        List<CommentPO> list = commentDao.paging(paging, key);
        List<Comment> rets = new ArrayList<Comment>();

        HashSet<Long> uids = new HashSet<Long>();

        for (CommentPO po : list) {
            uids.add(po.getAuthorId());
            rets.add(BeanMapUtils.copy(po));
        }

        buildUsers(rets, uids);

        paging.setResults(rets);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "commentsCaches", key = "'lth_' + #authorId + '_' + #paging.getPageNo() + '_' + #paging.getMaxResults()")
    public Paging paging4Home(Paging paging, long authorId) {
        List<CommentPO> list = commentDao.paging(paging, Const.ZERO, authorId, true);

        List<Comment> rets = new ArrayList<Comment>();
        Set<Long> parentIds = new HashSet<Long>();
        Set<Long> uids = new HashSet<Long>();
        Set<Long> postIds = new HashSet<Long>();

        for (CommentPO po : list) {
            Comment c = BeanMapUtils.copy(po);

            if (c.getPid() > 0) {
                parentIds.add(c.getPid());
            }
            uids.add(c.getAuthorId());
            postIds.add(c.getToId());

            rets.add(c);
        }

        // 加载父节点
        if (!parentIds.isEmpty()) {
            Map<Long, Comment> pm = findByIds(parentIds);
            for (Comment c : rets) {
                if (c.getPid() > 0) {
                    c.setParent(pm.get(c.getPid()));
                }
            }
        }

        buildUsers(rets, uids);
        buildPosts(rets, postIds);

        paging.setResults(rets);

        return paging;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "commentsCaches", key = "'lt_' + #toId + '_' + #paging.getPageNo() + '_' + #paging.getMaxResults()")
    public Paging paging(Paging paging, long toId) {
        List<CommentPO> list = commentDao.paging(paging, toId, Const.ZERO, true);

        List<Comment> rets = new ArrayList<Comment>();
        Set<Long> parentIds = new HashSet<Long>();
        Set<Long> uids = new HashSet<Long>();

        for (CommentPO po : list) {
            Comment c = BeanMapUtils.copy(po);

            if (c.getPid() > 0) {
                parentIds.add(c.getPid());
            }
            uids.add(c.getAuthorId());

            rets.add(c);
        }

        // 加载父节点
        if (!parentIds.isEmpty()) {
            Map<Long, Comment> pm = findByIds(parentIds);
            for (Comment c : rets) {
                if (c.getPid() > 0) {
                    c.setParent(pm.get(c.getPid()));
                }
            }
        }

        buildUsers(rets, uids);

        paging.setResults(rets);

        return paging;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, Comment> findByIds(Set<Long> ids) {
        List<CommentPO> list = commentDao.findByIds(ids);
        Map<Long, Comment> ret = new HashMap<Long, Comment>();
        Set<Long> uids = new HashSet<Long>();

        for (CommentPO po : list) {
            uids.add(po.getAuthorId());
            ret.put(po.getId(), BeanMapUtils.copy(po));
        }

        buildUsers(ret.values(), uids);
        return ret;
    }

    @Override
    @Transactional
    @CacheEvict(value = "commentsCaches", allEntries = true)
    public long post(Comment comment) {
        CommentPO po = new CommentPO();

        po.setAuthorId(comment.getAuthorId());
        po.setToId(comment.getToId());
        po.setContent(comment.getContent());
        po.setCreated(new Date());
        po.setPid(comment.getPid());
        commentDao.save(po);

        userEventService.identityComment(Collections.singletonList(comment.getAuthorId()),
            po.getId(), true);
        return po.getId();
    }

    @Override
    @Transactional
    @CacheEvict(value = "commentsCaches", allEntries = true)
    public void delete(List<Long> ids) {
        commentDao.deleteByIds(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = "commentsCaches", allEntries = true)
    public void delete(long id, long authorId) {
        CommentPO po = commentDao.get(id);
        if (po != null) {
            // 判断文章是否属于当前登录用户
            Assert.isTrue(po.getAuthorId() == authorId, "认证失败");
            commentDao.delete(po);
        }
    }

    @Transactional
    @Override
    public List<CommentPO> findByHql(String hql) {
        Query query = commentDao.createQuery(hql);
        return query.list();
    }

    private void buildUsers(Collection<Comment> posts, Set<Long> uids) {
        Map<Long, User> userMap = userService.findMapByIds(uids);
        for (Comment p : posts) {
            p.setAuthor(userMap.get(p.getAuthorId()));
        }
    }

    private void buildPosts(Collection<Comment> comments, Set<Long> postIds) {
        Map<Long, Post> postMap = postService.findSingleMapByIds(postIds);
        for (Comment p : comments) {
            p.setPost(postMap.get(p.getToId()));
        }
    }
}
