package com.taotao.manage.service.impl;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIPage;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;

@Service
public class ItemParamServiceImpl extends BaseServiceImpl<ItemParam> implements ItemParamService {
	/**
	 * 保存方法
	 */
	@Override
	public Boolean save(String paramData, Long cid) {
		ItemParam itemParam = new ItemParam();
		// 防止安全问题
		itemParam.setId(null);
		itemParam.setParamData(paramData);
		itemParam.setItemCatId(cid);
		int saveNum = this.save(itemParam);
		return saveNum == 1;
	}
	/**
	 * 查询规格参数列表
	 */
	public EasyUIPage queryPageListByWhere(Integer pageNum, Integer rows) {
		ItemParam record = new ItemParam();
		PageInfo<ItemParam> pageInfo = this.queryPageListByWhere(record, pageNum, rows);
		return new EasyUIPage(pageInfo.getTotal(), pageInfo.getList());
	}
}
