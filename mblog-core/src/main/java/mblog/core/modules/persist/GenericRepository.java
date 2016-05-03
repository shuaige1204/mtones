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

import org.hibernate.Query;

/**
 * 通用数据访问接口, 本接口定义非泛型的通用的数据访问接口。
 */
public interface GenericRepository extends Serializable {
	/**
     * 保存数据对象，用于新建
     * @param entity 实体对象
     */
    void save(Object entity);

    /**
     * 删除数据对象
     * @param entity 实体对象
     */
    void delete(Object entity);

    /**
     * 更新数据对象，用于修改
     * @param entity 实体对象
     */
    void update(Object entity);

    /**
     * 创建或修改数据对象
     * @param entity 实体对象
     */
    void saveOrUpdate(Object entity);
    
    /**
     * 从数据库重新读取对象
     * @param entity 实体对象
     */
    void refresh(Object entity);
    
    /**
     * 如果存在则更新, 不存在则insert
     * 此处需要注意 merge会先执行get请求, 如果存在则将entity属性覆盖到持久实例, entity 本身依旧是游离态.
     * 
     * @param entity 持久实例
     * @return Object
     */
    Object merge(Object entity);
    
    /**
     * 创建Query查询
     * 
     * @param queryString sql语句
     * @param values 参数列表
     * @return Query
     */
	Query createQuery(final String queryString, final Object... values);
}
