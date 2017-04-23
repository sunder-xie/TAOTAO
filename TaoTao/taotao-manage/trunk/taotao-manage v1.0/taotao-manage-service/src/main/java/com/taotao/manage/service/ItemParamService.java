package com.taotao.manage.service;

import org.springframework.stereotype.Service;

import com.taotao.common.bean.EasyUIPage;
import com.taotao.manage.pojo.ItemParam;
@Service
public interface ItemParamService extends BaseService<ItemParam> {

	public Boolean save(String paramData, Long cid);

	public EasyUIPage queryPageListByWhere(Integer pageNum, Integer rows);


}
