package mblog.core.persist.dao;

import java.util.List;

import mblog.core.modules.persist.BaseRepository;
import mblog.core.modules.pojos.Paging;
import mblog.core.persist.entity.RolePO;

public interface RoleDao extends BaseRepository<RolePO> {

    List<RolePO> paging(Paging paging, String key);

}
