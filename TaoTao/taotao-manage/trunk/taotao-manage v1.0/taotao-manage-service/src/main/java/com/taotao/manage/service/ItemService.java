package com.taotao.manage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.common.bean.EasyUIPage;
import com.taotao.manage.pojo.Item;

/**
 * 商品
 * @author xieshengrong
 */
@Service
public interface ItemService extends BaseService<Item> {
	/**
	 * 保存item商品,及其商品描述,在同一事物中
	 * @param record
	 * @param desc
	 * @return Boolean
	 */
	public Boolean save(Item record, String desc, String itemParams);
	public EasyUIPage queryPageListByWhere(Integer pageNum, Integer rows);
	public Boolean update(Item record, String desc, String itemParams);
	public Boolean delete(List<Object> ids);
	public Item showDetail(Long itemId);
}
