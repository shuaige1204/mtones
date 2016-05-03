/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import mblog.base.modules.lang.Const;
import mblog.core.data.Feeds;
import mblog.core.modules.annotation.Repository;
import mblog.core.modules.persist.impl.BaseRepositoryImpl;
import mblog.core.modules.pojos.Paging;
import mblog.core.persist.dao.FeedsDao;
import mblog.core.persist.entity.FeedsPO;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

/**
 * @author langhsu
 *
 */
@Repository(entity = FeedsPO.class)
public class FeedsDaoImpl extends BaseRepositoryImpl<FeedsPO> implements FeedsDao {
    private static final long serialVersionUID = -3008484602494194917L;

    String                    pattern          = "INSERT INTO mto_feeds (own_id, type, post_id, author_id, created) SELECT user_id, {0}, {1}, {2}, now() FROM mto_follows WHERE follow_id = {3}";

    @Override
    public int batchAdd(Feeds feeds) {
        String sql = MessageFormat.format(pattern, feeds.getType(), feeds.getPostId(),
            feeds.getAuthorId(), feeds.getAuthorId());
        SQLQuery query = createSQLQuery(sql);
        return query.executeUpdate();
    }

    @Override
    public int deleteByAuthorId(long ownId, long authorId) {
        SQLQuery query = createSQLQuery("delete from mto_feeds where own_id = :ownId and author_id = :authorId");
        query.setLong("ownId", ownId);
        query.setLong("authorId", authorId);
        return query.executeUpdate();
    }

    @Override
    public List<FeedsPO> findUserFeeds(Paging paging, long ownId, long authorId, long ignoreId) {
        PagingQuery<FeedsPO> q = pagingQuery(paging);
        q.add(Restrictions.eq("ownId", ownId));

        if (authorId > Const.ZERO) {
            q.add(Restrictions.eq("authorId", authorId));
        }

        if (ignoreId > Const.ZERO) {
            q.add(Restrictions.ne("authorId", ignoreId));
        }

        q.desc("id");
        return q.list();
    }

    @Override
    public void deleteByTarget(long postId) {
        SQLQuery query = createSQLQuery("delete from mto_feeds where post_id = :postId");
        query.setLong("postId", postId);
        query.executeUpdate();
    }
}
