package mblog.core.persist.dao;

import java.util.List;
import java.util.Set;

import mblog.core.data.Attach;
import mblog.core.modules.persist.BaseRepository;
import mblog.core.persist.entity.AttachPO;

/**
 * @author langhsu
 *
 */
public interface AttachDao extends BaseRepository<AttachPO> {
    List<AttachPO> findByTarget(long toId);

    List<AttachPO> findByTarget(Set<Long> toIds);

    List<AttachPO> findByIds(Set<Long> ids);

    void batchAdd(List<Attach> datas);

    void deleteByToId(long toId);
}
