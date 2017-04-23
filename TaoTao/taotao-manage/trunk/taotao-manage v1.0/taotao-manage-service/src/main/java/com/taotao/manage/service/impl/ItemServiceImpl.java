package com.taotao.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIPage;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemParamItemService;
import com.taotao.manage.service.ItemService;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private ItemParamItemService itemParamItemService;
	@Autowired
	private Mapper<Item> mapper;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	private void sendMsg(String itemId, String type) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemId", itemId);
			map.put("type", type);
			map.put("Date", System.currentTimeMillis());
			rabbitTemplate.convertAndSend("item." + type, MAPPER.writeValueAsString(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除商品 逻辑删除
	 */
	public Boolean delete(List<Object> ids) {
		Item item = new Item();
		item.setStatus(3);
		Example example = new Example(Item.class);
		Criteria criteria = example.createCriteria().andIn("id", ids);
		example.or(criteria);
		int updateByExampleSelective = this.updateByExampleSelective(item, example);
		// 更新商品时候需要更新缓存,通知前端系统刷新缓存
		// try {
		// for (Object id : ids) {
		// apiService.doPost(URL + id + ".html");
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		try {
			for (Object id : ids) {
				this.sendMsg(String.valueOf(id), "删除");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updateByExampleSelective == ids.size();
	}
	/**
	 * 1修改商品,
	 */
	public Boolean update(Item record, String desc, String itemParams) {//[[,]]
		// 更新商品时候,商品的创建时间不能更新,更新时间要更新,
		// 这些都在父类中已经处理,如果父类没有处理需要处理
		int itemNum = 0;
		int itemDescNum = 0;
		int itemParamNum = 0;
		Boolean flag = false;
		ItemDesc itemDesc = new ItemDesc();
		if (null != record) {
			itemNum = this.updateByPrimaryKeySelective(record);
		}
		if (StringUtils.isNotEmpty(desc)) {
			itemDesc.setItemId(record.getId());
			itemDesc.setItemDesc(desc);
			itemDescNum = itemDescService.updateByPrimaryKeySelective(itemDesc);
		}
		if (StringUtils.isNotEmpty(itemParams)) {
			itemParamNum = this.itemParamItemService.updateByItemId(record.getId(), itemParams);
		}
		// 通知前端系统刷新缓存
		try {
			// apiService.doPost(URL + record.getId() + ".html");
			this.sendMsg(String.valueOf(record.getId()), "更新");
		} catch (Exception e) {
			e.printStackTrace();
		}
		flag = itemNum == 1;
		if (StringUtils.isNotEmpty(desc)) {
			flag = flag && itemDescNum == 1;
		}
		if (StringUtils.isNotEmpty(itemParams)) {
			flag = flag && itemParamNum == 1;
		}
		return flag;
	}
	/**
	 * 1保存商品,需要在同一事物中保存,保存商品和描述在不同表中 2需要校验参数,需要注意id问题
	 */
	public Boolean save(Item record, String desc, String itemParams) {
		int itemNum = 0;
		int itemDescNum = 0;
		int itemParamNum = 0;
		Boolean flag = false;
		if (null != record) {
			record.setStatus(1);
			record.setId(null);
			// TODO 参数校验 有待完善
			itemNum = super.save(record);
		}
		if (StringUtils.isNotEmpty(desc)) {
			ItemDesc itemDesc = new ItemDesc();
			itemDesc.setItemDesc(desc);
			itemDesc.setItemId(record.getId());
			itemDescNum = itemDescService.save(itemDesc);
		}
		if (StringUtils.isNotEmpty(itemParams))
			itemParamNum = this.itemParamItemService.saveByItemId(record.getId(), itemParams);
		flag = itemNum == 1;
		if (StringUtils.isNotEmpty(desc)) {
			flag = flag && itemDescNum == 1;
		}
		if (StringUtils.isNotEmpty(itemParams)) {
			flag = flag && itemParamNum == 1;
		}
		// 发送商品添加消息
		try {
			this.sendMsg(String.valueOf(record.getId()), "新增");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 分页查询,最新上架的排序在最上面
	 */
	public EasyUIPage queryPageListByWhere(Integer pageNum, Integer rows) {
		EasyUIPage page = new EasyUIPage();
		PageHelper.startPage(pageNum, rows);
		Example example = new Example(Item.class);
		example.setOrderByClause("  updated  desc ");
		List<Item> list = this.mapper.selectByExample(example);
		PageInfo<Item> pageInfo = new PageInfo<Item>(list);
		page.setRows(pageInfo.getList());
		page.setTotal(pageInfo.getTotal());
		return page;
	}
	@Override
	public Item showDetail(Long itemId) {
		return super.queryById(itemId);
	}
}
