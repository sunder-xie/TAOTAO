package com.taotao.manage.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品类目
 * @author xieshengrong
 */
@Table(name = "tb_item_cat")
public class ItemCat extends BasePojo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "ItemCat [id=" + id + ", parentId=" + parentId + ", name=" + name + ", status=" + status + ", sortOrder="
				+ sortOrder + ", isParent=" + isParent + ", created=" + created + ", updated=" + updated + "]";
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long parentId;
	private String name;
	private Integer status;
	private Integer sortOrder;
	private Boolean isParent;
	private Date created;
	private Date updated;

	/**
	 * 页面easyUI显示扩展字段
	 * @return
	 */
	public String getText() {
		return this.getName();
	}
	/**
	 * 页面easyUI显示扩展字段
	 * @return
	 */
	public String getState() {
		return this.getIsParent() ? "closed" : "open";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
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
}
