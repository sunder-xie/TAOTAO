package com.taotao.solr.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item extends BasePojo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Field("id")
	private Long id;
	@Field("title")
	private String title;
	@Field("sellPoint")
	private String sellPoint;
	@Field("price")
	private Long price;
	private Integer num;
	private String barcode;
	@Field("image")
	private String image;
	@Field("cid")
	private Long cid;
	@Field("status")
	private Integer status = 1; // 商品状态 1:新上架 2:下架 3:删除

	@Override
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", sellPoint=" + sellPoint + ", price=" + price + ", num=" + num
				+ ", barcode=" + barcode + ", image=" + image + ", cid=" + cid + ", status=" + status + ", created="
				+ created + ", updated=" + updated + "]";
	}

	private Date created;
	private Date updated;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint == null ? null : sellPoint.trim();
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode == null ? null : barcode.trim();
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image == null ? null : image.trim();
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String[] getImages() {
		return StringUtils.isNotEmpty(this.getImage()) ? StringUtils.split(getImage(), ",") : null;
	}
}
