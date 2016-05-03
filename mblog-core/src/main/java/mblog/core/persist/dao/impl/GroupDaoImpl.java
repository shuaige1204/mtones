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

import java.util.List;

import mblog.base.modules.lang.Const;
import mblog.core.modules.annotation.Repository;
import mblog.core.modules.persist.impl.BaseRepositoryImpl;
import mblog.core.persist.dao.GroupDao;
import mblog.core.persist.entity.GroupPO;

/**
 * @author langhsu
 *
 */
@Repository(entity = GroupPO.class)
public class GroupDaoImpl extends BaseRepositoryImpl<GroupPO> implements GroupDao {
    private static final long serialVersionUID = -3510165157507261158L;

    @Override
    public List<GroupPO> findAll() {
        return findBy("status", Const.STATUS_NORMAL);
    }

    @Override
    public GroupPO getByKey(String key) {
        return findUniqueBy("key", key);
    }

}
