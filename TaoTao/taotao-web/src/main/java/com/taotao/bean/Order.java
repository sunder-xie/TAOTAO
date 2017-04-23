package com.taotao.bean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
	private String orderId;// id,rowKye:id+时间戳
	private String payment;// 实付金额
	private Integer paymentType; // 支付类型，1、在线支付，2、货到付款
	private String postFee;// 邮费
	/**
	 * 初始阶段：1、未付款、未发货；初始化所有数据 付款阶段：2、已付款；更改付款时间 发货阶段：3、已发货；更改发货时间、更新时间、物流名称、物流单号
	 * 成功阶段：4、已成功；更改更新时间，交易结束时间，买家留言，是否已评价 关闭阶段：5、关闭； 更改更新时间，交易关闭时间。
	 */
	private Integer status;// 状态:1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private Date paymentTime;// 付款时间
	private Date consignTime;// 发货时间
	private Date endTime;// 交易结束时间
	private Date closeTime;// 交易关闭时间
	private String shippingName;// 物流名称
	private String shippingCode;// 物流单号
	private Long userId;// 用户id
	private String buyerMessage;// 买家留言
	private String buyerNick;// 买家昵称
	private Integer buyerRate;// 买家是否已经评价
	private List<OrderItem> orderItems;// 商品详情
	private OrderShipping orderShipping; // 物流地址信息

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getPostFee() {
		return postFee;
	}
	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Date getConsignTime() {
		return consignTime;
	}
	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public String getShippingCode() {
		return shippingCode;
	}
	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBuyerMessage() {
		return buyerMessage;
	}
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}
	public String getBuyerNick() {
		return buyerNick;
	}
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	public Integer getBuyerRate() {
		return buyerRate;
	}
	public void setBuyerRate(Integer buyerRate) {
		this.buyerRate = buyerRate;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public OrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(OrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", payment=" + payment + ", paymentType=" + paymentType + ", postFee="
				+ postFee + ", status=" + status + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", paymentTime=" + paymentTime + ", consignTime=" + consignTime + ", endTime=" + endTime
				+ ", closeTime=" + closeTime + ", shippingName=" + shippingName + ", shippingCode=" + shippingCode
				+ ", userId=" + userId + ", buyerMessage=" + buyerMessage + ", buyerNick=" + buyerNick + ", buyerRate="
				+ buyerRate + ", orderItems=" + orderItems + ", orderShipping=" + orderShipping + "]";
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Order(String orderId, String payment, Integer paymentType, String postFee, Integer status, Date createTime,
			Date updateTime, Date paymentTime, Date consignTime, Date endTime, Date closeTime, String shippingName,
			String shippingCode, Long userId, String buyerMessage, String buyerNick, Integer buyerRate,
			List<OrderItem> orderItems, OrderShipping orderShipping) {
		super();
		this.orderId = orderId;
		this.payment = payment;
		this.paymentType = paymentType;
		this.postFee = postFee;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.paymentTime = paymentTime;
		this.consignTime = consignTime;
		this.endTime = endTime;
		this.closeTime = closeTime;
		this.shippingName = shippingName;
		this.shippingCode = shippingCode;
		this.userId = userId;
		this.buyerMessage = buyerMessage;
		this.buyerNick = buyerNick;
		this.buyerRate = buyerRate;
		this.orderItems = orderItems;
		this.orderShipping = orderShipping;
	}
}
