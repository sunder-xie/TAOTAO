package com.taotao.manage.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.ContentCategory;

public interface ContentCategoryMapper extends Mapper<ContentCategory> {

	public List<ContentCategory> queryById(Long parentId);
	
}
