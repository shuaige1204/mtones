/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.modules.persist.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import mblog.core.modules.annotation.Repository;
import mblog.core.modules.persist.BaseRepository;
import mblog.core.modules.pojos.Paging;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

/**
 * 持久层基类, 通过泛型指定实体类
 * 
 * 基类中的 entityClass 在使用的时候必须呗赋值, 详情见 {@link mtons.modules.annotation.Repository} 注解
 * 
 * @param <T> 实体类
 * @author langhsu
 */
public class BaseRepositoryImpl<T> extends GenericRepositoryImpl implements BaseRepository<T> {
    private static final long serialVersionUID = 3362415966471351147L;

    /**
     * 实体类型
     */
    protected Class<T>        entityClass;

    /**
     * 自动提取 entityClass
     * 
     * @throws Exception 出错
     */
    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() throws Exception {
        Repository repository = this.getClass().getAnnotation(Repository.class);

        Assert.notNull(repository, this.getClass() + " 必须要使用" + Repository.class + "注解!");
        Assert.notNull(repository.entity(), this.getClass() + " 的 @Repository注解的 entity 不能为空!");

        this.entityClass = (Class<T>) repository.entity();
    }

    /**
     * 通过主键删除对象
     * 
     * @param id 主键
     */
    @Override
    public void deleteById(Serializable id) {
        deleteById(entityClass, id);
    }

    /**
     * 根据ID列表删除
     * @param ids 主键集合
     */
    @Override
    public void deleteAll(Collection<Serializable> ids) {
        Assert.notNull(ids, "ids不能为空");
        for (Serializable id : ids) {
            delete(id);
        }
    }

    /**
     * 通过主键获取数据对象
     * 
     * @param id 主键
     * @return 持久对象
     */
    @Override
    public T get(Serializable id) {
        return get(entityClass, id);
    }

    /** 
     * 按Criteria查询对象列表. 
     *  
     * @param criterions 数量可变的Criterion. 
     * @return 持久队列集合
     */
    @SuppressWarnings("unchecked")
    protected List<T> find(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    /** 
     * 按属性查找对象列表, 匹配方式为相等.
     * 
     * @param propertyName 键
     * @param value 值
     * @return 持久队列集合
     */
    protected List<T> findBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }

    /** 
     * 按属性查找唯一对象, 匹配方式为相等. 
     * 
     * @param propertyName 键
     * @param value 值
     * @return 实体对象
     */
    @SuppressWarnings("unchecked")
    protected T findUniqueBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(criterion).uniqueResult();
    }

    /** 
     * 按HQL查询唯一对象. 
     * 
     * @param hql hql语句
     * @param values 数量可变的参数,按顺序绑定.
     * @return 实体对象 
     */
    @SuppressWarnings("unchecked")
    protected T findUnique(final String hql, final Object... values) {
        return (T) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按属性查询, 返回第一个对象
     * @param criterions 数量可变的Criterion. 
     * @return 结果集的第一个对象
     */
    protected T findFirst(final Criterion... criterions) {
        List<T> rets = find(criterions);
        if (rets != null && rets.size() > 0) {
            return rets.get(0);
        }
        return null;
    }

    /**
     * 按属性查询, 返回第一个对象
     * 
     * @param propertyName 键
     * @param value 值
     * @return 结果集的第一个对象
     */
    protected T findFirst(final String propertyName, final Object value) {
        List<T> rets = findBy(propertyName, value);
        if (rets != null && rets.size() > 0) {
            return rets.get(0);
        }
        return null;
    }

    /**
     * 按属性查询, 返回第一个对象
     * 
     * @param hql hql语句
     * @param values 数量可变的参数,按顺序绑定. 
     * @return 结果集的第一个对象
     */
    @SuppressWarnings("unchecked")
    protected T findFirst(final String hql, final Object... values) {
        List<T> rets = createQuery(hql, values).list();
        if (rets != null && rets.size() > 0) {
            return rets.get(0);
        }
        return null;
    }

    /** 
     * 执行HQL进行批量修改/删除操作. 
     * 
     * @param hql hql语句
     * @param values 数量可变的参数,按顺序绑定. 
     * @return 更新记录数. 
     */
    protected int batchExecute(final String hql, final Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 创建Criteria查询对象
     * @return Criteria
     */
    protected Criteria createCriteria() {
        return createCriteria(entityClass);
    }

    /** 
     * 根据Criterion条件创建Criteria. 
     * 与find()函数可进行更加灵活的操作. 
     *  
     * @param criterions 数量可变的Criterion.
     * @return Criteria
     */
    protected Criteria createCriteria(final Criterion... criterions) {
        Criteria criteria = session().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 创建PagingQuery查询对象
     * 
     * @param paging 分页对象
     * @return PagingQuery
     */
    protected PagingQuery<T> pagingQuery(Paging paging) {
        return pagingQuery(paging, entityClass, null);
    }

    /**
     * 创建PagingQuery查询对象
     * 
     * @param paging 分页对象
     * @param criterions 数量可变的Criterion.
     * @return PagingQuery
     */
    protected PagingQuery<T> pagingQuery(Paging paging, Criterion... criterions) {
        PagingQuery<T> q = pagingQuery(paging, entityClass, null);

        for (Criterion c : criterions) {
            q.add(c);
        }
        return q;
    }

    /**
     * 创建PagingQuery查询对象
     * 
     * @param paging 分页对象
     * @param cacheRegion 缓存保存区域
     * @return PagingQuery
     */
    protected PagingQuery<T> pagingQuery(Paging paging, String cacheRegion) {
        return pagingQuery(paging, entityClass, cacheRegion);
    }

    /**
     * 查询所有 (谨慎使用)
     * @return 集合
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> list() {
        return createCriteria().list();
    }

}
