/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.modules.persist;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 指定泛型的数据访问接口, 本接口定义指定泛型的通用的数据访问接口。
 * @param <T> 实体类
 */
public interface BaseRepository<T> extends GenericRepository {
    
    /**
     * 根据ID获取数据对象
     * @param id 主键
     * @return T 实体对象
     */
    T get(Serializable id);
    
    /**
     * 根据ID删除数据对象
     * @param id 主键
     */
    void deleteById(Serializable id);
    
    /**
     * 根据ID列表删除
     * @param ids 主键集合
     */
    void deleteAll(Collection<Serializable> ids);

    /**
     * 查询全部数据
     * @return 结果列表
     */
    List<T> list();
}
