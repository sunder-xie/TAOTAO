package com.taotao.manage.service;

import com.taotao.manage.pojo.ContentCategory;

public interface ContentCategoryService extends BaseService<ContentCategory> {
	/**
	 * 添加商品分类
	 * @param parentId
	 * @param name
	 * @return
	 */
	public Boolean save(Long parentId, String name);
	/**
	 * 更新商品分类
	 * @param id
	 * @param name
	 * @return
	 */
	public Boolean update(Long id, String name);
	/**
	 * 根据节点删除
	 * @param parentId
	 * @param id
	 * @return
	 */
	public void delete(Long parentId, Long id);
}
