/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.biz.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mblog.base.lang.EnumPrivacy;
import mblog.base.upload.FileRepo;
import mblog.core.biz.PostBiz;
import mblog.core.data.Attach;
import mblog.core.data.Post;
import mblog.core.event.FeedsEvent;
import mblog.core.modules.pojos.Paging;
import mblog.core.persist.service.AttachService;
import mblog.core.persist.service.FeedsService;
import mblog.core.persist.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author langhsu
 * 
 */
@Service
public class PostBizImpl implements PostBiz {
    @Autowired
    private PostService        postService;
    @Autowired
    private AttachService      attachService;
    @Autowired
    private FeedsService       feedsService;
    @Autowired
    private FileRepo           fileRepo;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 分页查询文章, 带缓存
     * - 缓存key规则: list_分组ID排序方式_页码_每页条数
     * @param paging
     * @param group
     * @param ord
     * @return
     */
    @Override
    public Paging paging(Paging paging, int group, String ord) {
        postService.paging(paging, group, ord, true);
        return paging;
    }

    @Override
    public Paging pagingByAuthorId(Paging paging, long uid, EnumPrivacy privacy) {
        postService.pagingByAuthorId(paging, uid, privacy);
        return paging;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Paging gallery(Paging paging, int group, String ord) {
        postService.paging(paging, group, ord, false);

        // 查询图片, 这里只加载文章的最后一张图片
        List<Post> results = (List<Post>) paging.getResults();
        Set<Long> imageIds = new HashSet<Long>();

        for (Post post : results) {
            if (post.getLastImageId() > 0) {
                imageIds.add(post.getLastImageId());
            }
        }

        if (!imageIds.isEmpty()) {
            Map<Long, Attach> ats = attachService.findByIds(imageIds);
            for (Post post : results) {
                if (post.getLastImageId() > 0) {
                    post.setAlbum(ats.get(post.getLastImageId()));
                }
            }
        }
        return paging;
    }

    @Override
    public Post getPost(long id) {
        return postService.get(id);
    }

    @Override
    public void post(Post post) {
        long id = postService.post(post);

        FeedsEvent event = new FeedsEvent("feedsEvent");
        event.setPostId(id);
        event.setAuthorId(post.getAuthorId());
        event.setPrivacy(post.getPrivacy());
        applicationContext.publishEvent(event);
    }

    @Override
    public void updateFeatured(long id, int featured) {
        postService.updateFeatured(id, featured);
    }

    @Override
    public List<Post> findRecents(int maxResults, long ignoreUserId) {
        return postService.findLatests(maxResults, ignoreUserId);
    }

    @Override
    public List<Post> findHots(int maxResults, long ignoreUserId) {
        return postService.findHots(maxResults, ignoreUserId);
    }

    @Override
    public void delete(long id, long authorId) {
        List<Attach> atts = attachService.findByTarget(id);
        postService.delete(id, authorId);

        // 时刻保持清洁, 物理删除图片
        if (!atts.isEmpty()) {

            for (Attach attach : atts) {
                fileRepo.deleteFile(attach.getPreview());
                fileRepo.deleteFile(attach.getOriginal());
                fileRepo.deleteFile(attach.getScreenshot());
            }
        }

        feedsService.deleteByTarget(id);
    }

    @Override
    public void delete(Collection<Long> ids) {
        for (Long id : ids) {
            List<Attach> atts = attachService.findByTarget(id);
            postService.delete(id);

            // 时刻保持清洁, 物理删除图片
            if (!atts.isEmpty()) {
                for (Attach attach : atts) {
                    fileRepo.deleteFile(attach.getPreview());
                    fileRepo.deleteFile(attach.getOriginal());
                    fileRepo.deleteFile(attach.getScreenshot());
                }
            }

            feedsService.deleteByTarget(id);
        }
    }

    @Override
    public void favor(long userId, long postId) {
        postService.favor(userId, postId);
    }

    @Override
    public void unfavor(long userId, long postId) {
        postService.unfavor(userId, postId);
    }

    /**
     * 更新文章：更新文章并清空缓存
     * @param p
     */
    @Override
    public void update(Post p) {
        postService.update(p);
    }

}
