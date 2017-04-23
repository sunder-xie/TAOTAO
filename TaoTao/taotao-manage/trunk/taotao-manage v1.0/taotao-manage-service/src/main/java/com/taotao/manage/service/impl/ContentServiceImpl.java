package com.taotao.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIPage;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;

/**
 * 内容
 * @author xieshengrong
 */
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {
	@Autowired
	private ContentMapper mapper;

	/**
	 * 查询内容列表
	 */
	public EasyUIPage queryPageListByWhere(Long categoryId, Integer pageNum, Integer rows) {
		EasyUIPage page = new EasyUIPage();
		PageHelper.startPage(pageNum, rows);
		Example example = new Example(Content.class);
		example.setOrderByClause("  updated  desc ");
		Criteria criteria = example.createCriteria().andEqualTo("categoryId", categoryId);
		example.or(criteria);
		List<Content> list = this.mapper.selectByExample(example);
		PageInfo<Content> pageInfo = new PageInfo<Content>(list);
		page.setRows(pageInfo.getList());
		page.setTotal(pageInfo.getTotal());
		return page;
	}
	/**
	 * 删除
	 */
	public void delete(List<Object> ids) {
		super.deleteByIds(Content.class, "id", ids);
	}
	/**
	 * 保存
	 */
	public int save(Content content) {
		return super.save(content);
	}
}
