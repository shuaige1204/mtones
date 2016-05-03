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
import java.util.Set;

import mblog.core.modules.persist.BaseRepository;
import mblog.core.modules.pojos.Paging;
import mblog.core.persist.entity.UserPO;

/**
 * @author langhsu
 */
public interface UserDao extends BaseRepository<UserPO> {
    UserPO getByUsername(String username);

    UserPO getByEmail(String email);

    List<UserPO> getHotUserByfans(int maxResults);

    List<UserPO> paging(Paging paging, String key);

    List<UserPO> findByIds(Set<Long> ids);

    void identityPost(List<Long> userIds, boolean identity);

    void identityComment(List<Long> userIds, boolean identity);

    void identityFollow(List<Long> userIds, boolean identity);

    void identityFans(List<Long> userIds, boolean identity);

    void identityFavors(List<Long> userIds, boolean identity);

}
