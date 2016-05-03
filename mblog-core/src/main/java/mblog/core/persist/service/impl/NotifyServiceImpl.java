package mblog.core.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mblog.core.data.Notify;
import mblog.core.data.Post;
import mblog.core.data.User;
import mblog.core.modules.pojos.Paging;
import mblog.core.persist.dao.NotifyDao;
import mblog.core.persist.entity.NotifyPO;
import mblog.core.persist.service.NotifyService;
import mblog.core.persist.service.PostService;
import mblog.core.persist.service.UserService;
import mblog.core.persist.utils.BeanMapUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author langhsu on 2015/8/31.
 */
@Service
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private NotifyDao   notifyDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    @Transactional(readOnly = true)
    public void findByOwnId(Paging paging, long ownId) {
        List<NotifyPO> list = notifyDao.findByOwnId(paging, ownId);
        List<Notify> rets = new ArrayList<Notify>();

        Set<Long> postIds = new HashSet<Long>();
        Set<Long> fromUserIds = new HashSet<Long>();

        // 筛选
        for (NotifyPO po : list) {
            Notify no = BeanMapUtils.copy(po);

            rets.add(no);

            if (no.getPostId() > 0) {
                postIds.add(no.getPostId());
            }
            if (no.getFromId() > 0) {
                fromUserIds.add(no.getFromId());
            }
        }

        // 加载
        Map<Long, Post> posts = postService.findSingleMapByIds(postIds);
        Map<Long, User> fromUsers = userService.findMapByIds(fromUserIds);

        for (Notify n : rets) {
            if (n.getPostId() > 0) {
                n.setPost(posts.get(n.getPostId()));
            }
            if (n.getFromId() > 0) {
                n.setFrom(fromUsers.get(n.getFromId()));
            }
        }

        paging.setResults(rets);

    }

    @Override
    @Transactional
    public void send(Notify notify) {
        if (notify == null || notify.getOwnId() <= 0 || notify.getFromId() <= 0) {
            return;
        }

        NotifyPO po = new NotifyPO();
        BeanUtils.copyProperties(notify, po);
        po.setCreated(new Date());

        notifyDao.save(po);
    }

    @Override
    @Transactional(readOnly = true)
    public int unread4Me(long ownId) {
        return notifyDao.unread4Me(ownId);
    }

    @Override
    @Transactional
    public void readed4Me(long ownId) {
        notifyDao.readed4Me(ownId);
    }
}
