package com.d3sq.search.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.core.common.jdbc.BaseDaoSupport;
import javax.core.common.jdbc.QueryRule;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.d3sq.common.constants.DataSourceConstant;
import com.d3sq.core.dao.datasource.DynamicDataSource;
import com.d3sq.core.dao.datasource.DynamicDataSourceEntry;
//import com.d3sq.core.plugin.queue.annotation.QueueTarget;
//import com.d3sq.core.plugin.queue.annotation.QueueTask;
//import com.d3sq.core.plugin.queue.model.QueueItem;
import com.d3sq.model.entity.Product;

@Repository("sProductDao")
public class ProductDao extends BaseDaoSupport<Product, Long> {
	
	private DynamicDataSourceEntry dynamicDataSourceEntry;

	@Override
	protected String getPKColumn() {
		return "id";
	}

	/**
	 * 配置动态数据源
	 * 
	 * @param dataSource
	 */
	@Resource(name = "dynamicDataSource")
	public void setDataSource(DataSource dataSource) {
		dynamicDataSourceEntry = ((DynamicDataSource) dataSource).getDataSourceEntry();
		super.setDataSourceReadOnly(dataSource);
		super.setDataSourceWrite(dataSource);
	}

	public List<Product> selectAll() {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		return super.getAll();
	}
	
	public List<Product> selectByPtype(Integer ptype) {
		dynamicDataSourceEntry.set(DataSourceConstant.DB_SHOPPING);
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual("ptype", ptype);
		return super.find(queryRule);
	}
}
