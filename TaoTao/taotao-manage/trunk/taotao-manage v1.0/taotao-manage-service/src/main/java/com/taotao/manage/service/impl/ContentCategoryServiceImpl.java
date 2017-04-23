package com.taotao.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

/**
 * 内容类目
 * @author xieshengrong
 */
@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService {
	@Autowired
	private ContentCategoryMapper contentCategoryMapper;

	/**
	 * 添加内容分类
	 */
	public Boolean save(Long parentId, String name) {
		ContentCategory category = new ContentCategory();
		category.setId(null);
		category.setParentId(parentId);
		category.setName(name);
		category.setStatus(1);
		category.setSortOrder(1);
		category.setIsParent(false);
		int num = super.saveSelective(category);
		// 保存新节点时候,需要考虑新添加的节点是否有父节点,或者父节点的isParent 是否为true
		ContentCategory parentCategory = queryById(parentId);
		if (parentCategory != null && !parentCategory.getIsParent()) {
			parentCategory.setIsParent(true);
			updateByPrimaryKeySelective(parentCategory);
		}
		return num == 1;
	}
	/**
	 * 更新内容分类名称
	 */
	public Boolean update(Long id, String name) {
		ContentCategory category = new ContentCategory();
		category.setName(name);
		category.setId(id);
		int selective = super.updateByPrimaryKeySelective(category);
		return 1 == selective;
	}
	/**
	 * 删除内容分类
	 */
	public void delete(Long parentId, Long id) {
		// 删除当天的节点
		ContentCategory parent2 = super.queryById(id);
		super.deleteById(id);
		ContentCategory parent = super.queryById(parentId);
		if (parent != null && parent.getIsParent()) {
			List<ContentCategory> list = contentCategoryMapper.queryById(parent.getId());
			if (list == null || list.size() == 0) {
				parent.setIsParent(false);
				updateByPrimaryKeySelective(parent);
			}
		}
		if (parent2 != null && parent2.getIsParent()) {
			parentId = parent2.getId();
			List<ContentCategory> list2 = contentCategoryMapper.queryById(parentId);
			if (list2 != null && list2.size() > 0) {
				for (ContentCategory contentCategory : list2) {
					delete(parentId, contentCategory.getId());
				}
			}
		}
	}
}
