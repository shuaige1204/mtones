package mblog.core.persist.dao;

import java.util.List;

import mblog.core.modules.persist.BaseRepository;
import mblog.core.persist.entity.FriendLinkPO;

/**
 * @author Beldon
 */
public interface FriendLinkDao extends BaseRepository<FriendLinkPO> {

    List<FriendLinkPO> findAll();
}
