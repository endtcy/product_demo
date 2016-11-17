package com.sinitek.demo.domain;

import java.util.Date;

/**
 * 商品表实体类 
 * @author Administrator
 *
 */
public class Good {
	private int goodId;
	private String barcode;
	private String goodName;
	private String goodLocation;
	private String goodType;
	private String goodImage;
	private double buyPrice;
	private double salePrice;
	private int goodCount;
	private Date goodDate;
	
	public Good() {

	}
	
	public Good(String barcode, String goodName, String goodLocation,
			String goodType, String goodImage, double buyPrice,
			double salePrice, int goodCount, Date goodDate) {
		this.barcode = barcode;
		this.goodName = goodName;
		this.goodLocation = goodLocation;
		this.goodType = goodType;
		this.goodImage = goodImage;
		this.buyPrice = buyPrice;
		this.salePrice = salePrice;
		this.goodCount = goodCount;
		this.goodDate = goodDate;
	}

	public int getGoodId() {
		return goodId;
	}
	
	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	
	public String getGoodLocation() {
		return goodLocation;
	}
	public void setGoodLocation(String goodLocation) {
		this.goodLocation = goodLocation;
	}
	
	public String getGoodType() {
		return goodType;
	}
	public void setGoodType(String goodType) {
		this.goodType = goodType;
	}
	
	public String getGoodImage() {
		return goodImage;
	}
	public void setGoodImage(String goodImage) {
		this.goodImage = goodImage;
	}
	
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	
	public int getGoodCount() {
		return goodCount;
	}
	public void setGoodCount(int goodCount) {
		this.goodCount = goodCount;
	}
	
	public Date getGoodDate() {
		return goodDate;
	}

	public void setGoodDate(Date goodDate) {
		this.goodDate = goodDate;
	}

	@Override
	public String toString() {
		return "Good [goodId=" + goodId + ", barcode=" + barcode
				+ ", goodName=" + goodName + ", goodLocation=" + goodLocation
				+ ", goodType=" + goodType + ", goodImage=" + goodImage
				+ ", buyPrice=" + buyPrice + ", salePrice=" + salePrice
				+ ", goodCount=" + goodCount + ", goodDate=" + goodDate + "]";
	}


	
}
