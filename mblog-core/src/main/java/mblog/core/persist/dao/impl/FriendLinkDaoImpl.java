package mblog.core.persist.dao.impl;

import java.util.List;

import mblog.core.modules.annotation.Repository;
import mblog.core.modules.persist.impl.BaseRepositoryImpl;
import mblog.core.persist.dao.FriendLinkDao;
import mblog.core.persist.entity.FriendLinkPO;

import org.hibernate.Query;

/**
 * @author Beldon
 */
@Repository(entity = FriendLinkPO.class)
public class FriendLinkDaoImpl extends BaseRepositoryImpl<FriendLinkPO> implements FriendLinkDao {
    private static final long serialVersionUID = 754755214307906383L;

    @Override
    @SuppressWarnings("unchecked")
    public List<FriendLinkPO> findAll() {
        Query query = createQuery("from FriendLinkPO am order by am.sort");
        return query.list();
    }
}
