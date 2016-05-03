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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import mblog.core.modules.persist.GenericRepository;
import mblog.core.modules.pojos.Paging;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

/**
 * 通用持久层基类
 * @author langhsu
 */
public class GenericRepositoryImpl implements GenericRepository {
    private static final long serialVersionUID = -1108549180370324076L;

    /**
     * 使用的SessionFactory
     */
    protected SessionFactory  sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 启用Filter
     * @param name filterName
     */
    protected void enableFilter(String name) {
        session().enableFilter(name);
    }

    /**
     * 获得当前上下文Session
     * @return Session
     */
    protected Session session() {
        return this.sessionFactory.getCurrentSession();
    }

    /**
     * 持久化实体对象
     * @param entity 实体对象
     */
    @Override
    public void save(Object entity) {
        Assert.notNull(entity, "entity不能为空");
        session().save(entity).hashCode();
    }

    /**
     * 删除持久化对象
     * 
     * @param entity 实体对象
     */
    @Override
    public void delete(Object entity) {
        Assert.notNull(entity, "entity不能为空");
        session().delete(entity);
    }

    /**
     * 修改持久化对象
     * 
     * @param entity 实体对象
     */
    @Override
    public void update(Object entity) {
        Assert.notNull(entity, "entity不能为空");
        session().update(entity);
    }

    /**
     * 添加/修改
     * 
     * @param entity 实体对象
     */
    @Override
    public void saveOrUpdate(Object entity) {
        Assert.notNull(entity, "entity不能为空");
        session().saveOrUpdate(entity);
    }

    /**
     * refresh持久化对象
     * 
     * @param entity 实体对象
     */
    @Override
    public void refresh(Object entity) {
        Assert.notNull(entity, "entity不能为空");
        session().refresh(entity);
    }

    /**
     * merge
     * 
     * @param entity 实体对象
     */
    @Override
    public Object merge(Object entity) {
        Assert.notNull(entity, "entity不能为空");
        return session().merge(entity);
    }

    /**
     * get 查询
     * 
     * @param clazz 实体类
     * @param <E> 实体类类型
     * @param id 主键
     * @return 实体对象
     */
    @SuppressWarnings("unchecked")
    public <E> E get(Class<E> clazz, Serializable id) {
        return (E) session().get(clazz, id);
    }

    /**
     * 根据主键删除
     * 
     * @param clazz 实体类
     * @param id 主键
     */
    protected void deleteById(Class<?> clazz, Serializable id) {
        Session s = session();
        Object obj = s.get(clazz, id);
        if (obj != null) {
            s.delete(obj);
        }
    }

    /** 
     * 根据查询HQL与参数列表创建Query对象. 
     * 与find()函数可进行更加灵活的操作. 
     * 
     * @param hql hql语句
     * @param values 数量可变的参数,按顺序绑定.
     * @return Query
     */
    public Query createQuery(final String hql, final Object... values) {
        Assert.hasText(hql, "hql不能为空");
        Query query = session().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 创建带缓存的HQL查询
     * @param hql hql语句
     * @return Query
     */
    protected Query createQuery(String hql) {
        return createQuery(hql, true, null);
    }

    /**
     * 创建HQL查询，通过传入参数设置是否可缓存
     * @param hql hql语句
     * @param cacheable 设置是否可缓存
     * @return Query
     */
    protected Query createQuery(String hql, boolean cacheable) {
        return createQuery(hql, cacheable, null);
    }

    /**
     * 创建HQL查询，通过传入参数设置是否可缓存
     * @param hql hql语句
     * @param cacheable 设置是否可缓存
     * @param cacheRegion 缓存保存区域
     * @return Query
     */
    protected Query createQuery(String hql, boolean cacheable, String cacheRegion) {
        Query q = session().createQuery(hql).setCacheable(true);
        if (cacheable && cacheRegion != null) {
            q.setCacheRegion(cacheRegion);
        }
        return q;
    }

    /**
     * 创建Native SQL查询
     * @param sql sql语句
     * @return SQLQuery
     */
    protected SQLQuery createSQLQuery(String sql) {
        return session().createSQLQuery(sql);
    }

    /**
     * 创建带缓存的QBC查询
     * @param clazz 查询的实体类型
     * @return Criteria
     */
    protected Criteria createCriteria(Class<?> clazz) {
        return session().createCriteria(clazz).setCacheable(true);
    }

    /**
     * 创建分页查询
     * @param paging 分页对象
     * @param clazz 返回实体对象类
     * @param <E> 实体类类型
     * @return PagingQuery
     */
    protected <E> PagingQuery<E> pagingQuery(Paging paging, Class<E> clazz) {
        return new PagingQuery<E>(paging, clazz, null);
    }

    /**
     * 创建分页查询
     * @param paging 分页对象
     * @param clazz 返回实体对象类
     * @param <E> 实体类类型
     * @param cacheRegion 缓存保存区域
     * @return PagingQuery
     */
    protected <E> PagingQuery<E> pagingQuery(Paging paging, Class<E> clazz, String cacheRegion) {
        return new PagingQuery<E>(paging, clazz, cacheRegion);
    }

    protected class PagingQuery<T> implements Serializable {
        private static final long serialVersionUID = 8307596869106859651L;
        String                    cacheRegion;
        Class<T>                  clazz;

        QueryFilter               filter           = new QueryFilter();
        LinkedList<Order>         orderBuffer      = new LinkedList<Order>();
        ResultTransformer         resultTransformer;
        Paging                    paging;

        public PagingQuery(Paging paging, Class<T> clazz, String cacheRegion) {
            this.clazz = clazz;
            this.cacheRegion = cacheRegion;
            this.paging = paging;
        }

        public PagingQuery<T> add(Order order) {
            orderBuffer.add(order);
            return this;
        }

        public PagingQuery<T> asc(String field) {
            add(Order.asc(field));
            return this;
        }

        public PagingQuery<T> desc(String field) {
            add(Order.desc(field));
            return this;
        }

        public PagingQuery<T> alias(String field, String alias) {
            filter.alias(field, alias);
            return this;
        }

        public PagingQuery<T> add(Criterion c) {
            filter.add(c);
            return this;
        }

        public PagingQuery<T> setResultTransformer(ResultTransformer resultTransformer) {
            this.resultTransformer = resultTransformer;
            return this;
        }

        protected Criteria criteria(boolean sortable) {
            Criteria c = createCriteria(clazz);

            filter.doFilter(c);

            if (cacheRegion != null) {
                c.setCacheRegion(cacheRegion);
            }

            if (sortable && !orderBuffer.isEmpty()) {

                for (Order order : orderBuffer) {
                    c.addOrder(order);
                }
            }
            return c;
        }

        /**
         * execute query
         * @return result
         */
        @SuppressWarnings("unchecked")
        public List<T> list() {
            boolean hasData = true;
            // count
            if (paging.isCount()) {
                Criteria ctr = criteria(false);
                int totalRows = ((Number) ctr.setProjection(Projections.rowCount()).uniqueResult())
                    .intValue();
                paging.setTotalCount(totalRows);
                if (totalRows <= paging.getFirstResult()) {
                    hasData = false;
                }
            }

            // query data
            List<T> results = Collections.emptyList();
            if (hasData) {
                Criteria ctr = criteria(true);
                ctr.setFirstResult(paging.getFirstResult()).setMaxResults(paging.getMaxResults());

                if (resultTransformer != null) {
                    ctr.setResultTransformer(resultTransformer);
                }
                results = ctr.list();
            }
            paging.setResults(results);
            return results;
        }
    }

    /**
     * PagingQuery filter
     */
    protected class QueryFilter {
        LinkedList<Criterion>         criterions = new LinkedList<Criterion>();
        LinkedHashMap<String, String> aliases    = new LinkedHashMap<String, String>();

        public QueryFilter add(Criterion c) {
            criterions.add(c);
            return this;
        }

        public QueryFilter alias(String field, String alias) {
            aliases.put(field, alias);
            return this;
        }

        public void doFilter(Criteria criteria) {
            if (!criterions.isEmpty()) {
                if (!aliases.isEmpty()) {
                    for (Entry<String, String> entry : aliases.entrySet()) {
                        criteria.createAlias(entry.getKey(), entry.getValue());
                    }
                }
                for (Criterion ct : criterions) {
                    criteria.add(ct);
                }
            }
        }
    }
}
