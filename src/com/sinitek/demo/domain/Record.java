package com.sinitek.demo.domain;

/**
 * 记录表实体类
 * @author Administrator
 *
 */
public class Record {

	private int id;
	private int goodId;
	private int operatorType;
	private int amount;
	private double price;
	private String operatorDate;
	
	public Record() {

	}
	
	public Record(int goodId, int operatorType, int amount, double price,
			String operatorDate) {
		this.goodId = goodId;
		this.operatorType = operatorType;
		this.amount = amount;
		this.price = price;
		this.operatorDate = operatorDate;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getGoodId() {
		return goodId;
	}
	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}
	
	public int getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(int operatorType) {
		this.operatorType = operatorType;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public String getOperatorDate() {
		return operatorDate;
	}
	public void setOperatorDate(String operatorDate) {
		this.operatorDate = operatorDate;
	}
	
}
