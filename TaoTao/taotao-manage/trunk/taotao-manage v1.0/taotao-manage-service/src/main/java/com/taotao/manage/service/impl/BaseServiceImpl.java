package com.taotao.manage.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;
import com.taotao.manage.service.BaseService;

/**
 * baseService 父类抽象类
 * @author xieshengrong
 */
public abstract class BaseServiceImpl<T extends BasePojo> implements BaseService<T> {
	/**
	 * spring 4.0新特性,可以实现通用接口注入
	 */
	@Autowired
	private Mapper<T> mapper;

	/**
	 * 查询总数
	 * @param record
	 * @return
	 */
	public int selectCount(T record) {
		return this.mapper.selectCount(record);
	}
	/**
	 * 条件查询总数
	 * @param example
	 * @return
	 */
	public int selectCountByExample(Object example) {
		return this.mapper.selectCountByExample(example);
	}
	/**
	 * 根据主键查询实体
	 * @param id
	 * @return 
	 * @return
	 */
	public T queryById(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}
	/**
	 * 查询全部
	 * @return
	 */
	public List<T> queryAll() {
		return this.mapper.select(null);
	}
	/**
	 * 根据实体,查询
	 * @param record
	 * @return
	 */
	public T queryOne(T record) {
		return this.mapper.selectOne(record);
	}
	/**
	 * 根据对象,查询集合
	 * @param record
	 * @return
	 */
	public List<T> queryListByWhere(T record) {
		return this.mapper.select(record);
	}
	/**
	 * 分页查询
	 * @param record
	 * @return
	 */
	public PageInfo<T> queryPageListByWhere(T record, Integer pageNum, Integer rows) {
		PageHelper.startPage(pageNum, rows);
		List<T> list = this.mapper.select(record);
		PageInfo<T> page = new PageInfo<T>(list);
		return page;
	}
	/**
	 * 保存对象,(全字段保存,无数据属性设置为null)
	 * @param record
	 * @return
	 */
	public int save(T record) {
		record.setCreated(new Timestamp(System.currentTimeMillis()));
		record.setUpdated(record.getCreated());
		return this.mapper.insert(record);
	}
	/**
	 * 保存对象,(局部字段保存,无数据属性不设置)
	 * @param record
	 * @return
	 */
	public int saveSelective(T record) {
		record.setCreated(new Timestamp(System.currentTimeMillis()));
		record.setUpdated(record.getCreated());
		return this.mapper.insertSelective(record);
	}
	/**
	 * 更新对象,(局部字段保存,无数据属性不设置)
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateByExampleSelective(T record, Object example) {
		record.setCreated(null);//更新不更新创建时间,
		record.setUpdated(new Timestamp(System.currentTimeMillis()));
		return this.mapper.updateByExampleSelective(record, example);
	}
	/**
	 * 更新对象,(全字段保存,无数据属性设置为null)
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateByExample(T record, Object example) {
		record.setCreated(null);//更新不更新创建时间,
		record.setUpdated(new Timestamp(System.currentTimeMillis()));
		return this.mapper.updateByExample(record, example);
	}
	/**
	 * 根据主键进行更新数据(全字段更新)
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKey(T record) {
		record.setCreated(null);//更新不更新创建时间,
		record.setUpdated(new Timestamp(System.currentTimeMillis()));
		return this.mapper.updateByPrimaryKey(record);
	}
	/**
	 * 根据主键进行更新数据(局部字段更新)
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKeySelective(T record) {
		record.setCreated(null);//更新不更新创建时间,
		record.setUpdated(new Timestamp(System.currentTimeMillis()));
		return this.mapper.updateByPrimaryKeySelective(record);
	}
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	public int deleteById(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}
	/**
	 * 批量删除
	 * @param record
	 * @return
	 */
	public int deleteByIds(Class<T> clazz, String property, List<Object> values) {
		Example example = new Example(clazz);
		example.setOrderByClause("  " + property + " asc ");
		example.createCriteria().andIn(property, values);
		return this.mapper.deleteByExample(example);
	}
	/**
	 * 条件删除
	 * @param record
	 * @return
	 */
	public int deleteByWhere(T record) {
		return this.mapper.delete(record);
	}
}
