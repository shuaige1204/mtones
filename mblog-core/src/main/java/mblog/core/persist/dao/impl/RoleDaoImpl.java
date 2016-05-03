package mblog.core.persist.dao.impl;

import java.util.List;

import mblog.core.modules.annotation.Repository;
import mblog.core.modules.persist.impl.BaseRepositoryImpl;
import mblog.core.modules.pojos.Paging;
import mblog.core.persist.dao.RoleDao;
import mblog.core.persist.entity.RolePO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

@Repository(entity = RolePO.class)
public class RoleDaoImpl extends BaseRepositoryImpl<RolePO> implements RoleDao {
    private static final long serialVersionUID = 1L;

    @Override
    public List<RolePO> paging(Paging paging, String key) {
        PagingQuery<RolePO> q = pagingQuery(paging);
        if (StringUtils.isNotBlank(key)) {
            q.add(Restrictions.like("name", key, MatchMode.ANYWHERE));
        }
        q.desc("id");
        return q.list();
    }
}
