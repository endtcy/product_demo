package com.sinitek.demo.dao;

import java.util.List;
import java.util.Map;

import com.sinitek.demo.domain.Good;

public interface GoodDao {
	
	int barFind(String barcode);
	
	/**
	 * 保存商品信息
	 * @param good
	 */
	void saveGood(Good good);
	
	/**
	 * 修改商品信息
	 * @param good
	 */
	void modifyGood(Good good);
	
	/**
	 * 根据id获取单条记录
	 * @param goodId
	 * @return
	 */
	Good getGood(Integer goodId);
	
	/**
	 * 查询全部记录
	 * @return
	 */
	List<Good> queryAllGood(int page);
	
	/**
	 * 根据名称、编码、日期查询信息
	 */
	List<Good> FindGood(int page,Map<String, Object> param);
	
	
	
	
}
