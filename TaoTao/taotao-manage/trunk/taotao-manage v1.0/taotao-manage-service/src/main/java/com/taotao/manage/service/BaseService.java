package com.taotao.manage.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

/**
 * baseService 父类抽象类
 * @author xieshengrong
 */
@Service
public interface BaseService<T extends Serializable> {
	/**
	 * 查询总数
	 * @param record
	 * @return
	 */
	public int selectCount(T record);
	/**
	 * 条件查询总数
	 * @param example
	 * @return
	 */
	public int selectCountByExample(Object example);
	/**
	 * 根据主键查询实体
	 * @param id
	 * @return
	 */
	public T queryById(Object id);
	/**
	 * 查询全部
	 * @return
	 */
	public List<T> queryAll();
	/**
	 * 根据实体,查询
	 * @param record
	 * @return
	 */
	public T queryOne(T record);
	/**
	 * 根据对象,查询集合
	 * @param record
	 * @return
	 */
	public List<T> queryListByWhere(T record);
	/**
	 * 分页查询
	 * @param record
	 * @return
	 */
	public PageInfo<T> queryPageListByWhere(T record, Integer pageNum, Integer rows);
	/**
	 * 保存对象,(全字段保存,无数据属性设置为null)
	 * @param record
	 * @return
	 */
	public int save(T record);
	/**
	 * 保存对象,(局部字段保存,无数据属性不设置)
	 * @param record
	 * @return
	 */
	public int saveSelective(T record);
	/**
	 * 更新对象,(局部字段保存,无数据属性不设置)
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateByExampleSelective(T record, Object example);
	/**
	 * 更新对象,(全字段保存,无数据属性设置为null)
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateByExample(T record, Object example);
	/**
	 * 根据主键进行更新数据(全字段更新)
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKey(T record);
	/**
	 * 根据主键进行更新数据(局部字段更新)
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKeySelective(T record);
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	public int deleteById(Object id);
	/**
	 * 批量删除
	 * @param record
	 * @return
	 */
	public int deleteByIds(Class<T> clazz, String property, List<Object> values);
	/**
	 * 条件删除
	 * @param record
	 * @return
	 */
	public int deleteByWhere(T record);
}
