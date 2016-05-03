package mblog.core.persist.dao;

import java.util.List;

import mblog.core.modules.persist.BaseRepository;
import mblog.core.persist.entity.AuthMenuPO;

public interface AuthMenuDao extends BaseRepository<AuthMenuPO> {

    List<AuthMenuPO> findByParentId(Long parentId);

}
