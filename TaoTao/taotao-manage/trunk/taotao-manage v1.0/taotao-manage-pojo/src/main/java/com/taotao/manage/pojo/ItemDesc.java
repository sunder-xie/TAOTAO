package com.taotao.manage.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "tb_item_desc")
public class ItemDesc  extends BasePojo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "ItemDesc [itemId=" + itemId + ", created=" + created + ", updated=" + updated + ", itemDesc=" + itemDesc
				+ "]";
	}
	@Id
	private Long itemId;
	private Date created;
	private Date updated;
	private String itemDesc;

	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc == null ? null : itemDesc.trim();
	}
}
