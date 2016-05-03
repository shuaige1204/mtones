/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.dao;

import java.util.List;

import mblog.core.modules.persist.BaseRepository;
import mblog.core.persist.entity.MenuPO;

/**
 * @author langhsu
 *
 */
public interface MenuDao extends BaseRepository<MenuPO> {
    List<MenuPO> findAll();
}
