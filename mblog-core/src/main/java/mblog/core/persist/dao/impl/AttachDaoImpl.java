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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import mblog.core.data.Attach;
import mblog.core.modules.annotation.Repository;
import mblog.core.modules.persist.impl.BaseRepositoryImpl;
import mblog.core.persist.dao.AttachDao;
import mblog.core.persist.entity.AttachPO;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;

/**
 * @author langhsu
 *
 */
@Repository(entity = AttachPO.class)
public class AttachDaoImpl extends BaseRepositoryImpl<AttachPO> implements AttachDao {
    private static final long serialVersionUID = -3561107849267517664L;

    @Override
    public List<AttachPO> findByTarget(long toId) {
        return find(Restrictions.eq("toId", toId));
    }

    @Override
    public List<AttachPO> findByTarget(Set<Long> toIds) {
        return find(Restrictions.in("toId", toIds));
    }

    @Override
    public List<AttachPO> findByIds(Set<Long> ids) {
        return find(Restrictions.in("id", ids));
    }

    @Override
    public void batchAdd(final List<Attach> datas) {
        final String sql = "insert into mto_attachs(store,to_id,original,preview,width,height,status) values(?,?,?,?,?,?,?)";
        session().doWork(new Work() {

            @Override
            public void execute(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql);

                for (Attach d : datas) {
                    ps.setInt(1, d.getStore());
                    ps.setLong(2, d.getToId());
                    ps.setString(3, d.getOriginal());
                    ps.setString(4, d.getPreview());
                    ps.setInt(5, d.getWidth());
                    ps.setInt(6, d.getHeight());
                    ps.setInt(7, d.getStatus());

                    ps.addBatch();
                }
                ps.executeBatch();
            }
        });
    }

    @Override
    public void deleteByToId(long toId) {
        Query query = createQuery("delete from AttachPO where to_id = :toId");
        query.setLong("toId", toId);
        query.executeUpdate();
    }
}
