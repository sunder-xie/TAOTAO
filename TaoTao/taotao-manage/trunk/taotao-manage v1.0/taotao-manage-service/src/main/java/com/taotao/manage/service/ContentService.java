package com.taotao.manage.service;

import java.util.List;

import com.taotao.common.bean.EasyUIPage;
import com.taotao.manage.pojo.Content;

public interface ContentService extends BaseService<Content> {

	public EasyUIPage queryPageListByWhere(Long categoryId, Integer pageNum, Integer rows);

	public void delete(List<Object> ids);
	
	public int save(Content content);
}
