package com.taotao.manage.service;

import org.springframework.stereotype.Service;

import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.pojo.ItemCat;

/**
 * 商品类目
 * @author xieshengrong
 */
@Service
public interface ItemCatService  extends BaseService<ItemCat>{

	public ItemCatResult queryItemCatList();
	/**
	 * 根据父类id加载商品条目
	 * @param pid
	 * @return
	 */
	//public List<ItemCat> list(Long pid);
}
