/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.modules.pojos;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页对象
 */
public class Paging implements Serializable {
	private static final long serialVersionUID = -6480389889447195271L;
	public static final int DEFAULT_RESULTS = 15;
	
    private int pageNo = 1; // 查询起始位置
    private int maxResults = DEFAULT_RESULTS; // 查询分页最大数据数量
    private List<?> results = Collections.EMPTY_LIST; // 当前查询页中的数据
    private int totalCount;  // 符合查询条件数据总数
    private boolean count = true; // 是否计算数据总数

    public Paging() {
    }
    
    /**
     * 只设置最大返回数据量，不需要分页，用于top列表
     * @param maxResults 最大返回条数
     */
    public Paging(int maxResults) {
        this.setPageNo(1);
        this.setMaxResults(maxResults);
    }
    
    public Paging(int pageNo, int maxResults) {
        this.setPageNo(pageNo);
        this.setMaxResults(maxResults);
    }

    /**
     * 最大结果集
     * 
     * @return 0：返回所有符合条件的数据；正数：最多返回的数据数量
     */
    public int getMaxResults() {
        return maxResults;
    }

    public List<?> getResults() {
        return results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getFirstResult() {
        return (pageNo - 1) * maxResults;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = (pageNo < 1 ? 1 : pageNo);
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = (maxResults < 0 ? 0 : maxResults);
    }

    public void setResults(List<?> results) {
        this.results = results;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        if (maxResults == 0) {
            return 1;
        }
        return (int) Math.ceil(totalCount * 1.0 / maxResults);
    }

    /**
     * 数据集大小
     * 
     * @return 数字
     */
    public int getSize() {
        return results.size();
    }
    
    /**
     * 上一个有效页码
     * @return 当前页的上一页的页码，如果当前页为第一页时返回1
     */
    public int getPrev(){
        return (pageNo > 1 ? (pageNo - 1) : 0);
    }
    
    /**
     * 下一个有效页码
     * @return 当前页的下一页的页码，如果当前页为最后一页时返回最后一页
     */
    public int getNext(){
        int pc = getPageCount();
        return (pageNo < pc ? (pageNo + 1) : pc);
    }

    public boolean isCount(){
        return count;
    }

    public void setCount(boolean count){
        this.count = count;
    }
    
}
