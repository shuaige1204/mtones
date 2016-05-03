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

import java.util.Date;
import java.util.List;

import mblog.base.modules.lang.Const;
import mblog.core.modules.annotation.Repository;
import mblog.core.modules.persist.impl.BaseRepositoryImpl;
import mblog.core.persist.dao.LogDao;
import mblog.core.persist.entity.LogPO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author langhsu
 *
 */
@Repository(entity = LogPO.class)
public class LogDaoImpl extends BaseRepositoryImpl<LogPO> implements LogDao {
    private static final long serialVersionUID = 7340674448192398350L;

    @Override
    @SuppressWarnings("unchecked")
    public List<LogPO> findByDay(int logType, long userId, long targetId, String ip, Date day) {
        Criteria c = createCriteria();
        c.add(Restrictions.eq("created", day));
        c.add(Restrictions.eq("type", logType));
        c.add(Restrictions.eq("userId", userId));

        if (targetId > Const.IGNORE) {
            c.add(Restrictions.eq("targetId", targetId));
        }

        if (StringUtils.isNotBlank(ip)) {
            c.add(Restrictions.eq("ip", ip));
        }

        c.addOrder(Order.desc("id"));
        return c.list();
    }
}
