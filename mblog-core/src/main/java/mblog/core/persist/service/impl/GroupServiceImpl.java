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
import java.util.List;

import mblog.core.data.Group;
import mblog.core.persist.dao.GroupDao;
import mblog.core.persist.entity.GroupPO;
import mblog.core.persist.service.GroupService;
import mblog.core.persist.utils.BeanMapUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author langhsu
 *
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupDao groupDao;

    @Override
    @Transactional(readOnly = true)
    public List<Group> findAll() {
        List<GroupPO> list = groupDao.findAll();
        List<Group> rets = new ArrayList<Group>();

        for (GroupPO po : list) {
            rets.add(BeanMapUtils.copy(po));
        }
        return rets;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "groupsCaches", key = "'g_' + #id")
    public Group getById(int id) {
        return BeanMapUtils.copy(groupDao.get(id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "groupsCaches", key = "'g_' + #key")
    public Group getByKey(String key) {
        return BeanMapUtils.copy(groupDao.getByKey(key));
    }

}
