package com.sinitek.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sinitek.demo.dao.GoodDao;
import com.sinitek.demo.domain.Good;

/**
 * 商品
 * @author Administrator
 *
 */

@Transactional(propagation = Propagation.REQUIRED)
public class GoodService {
	/**
	 * 添加
	 * @param good
	 */
	public void saveGood(Good good){
		goodDao.saveGood(good);
	}
	
	/**
	 * 修改
	 * @param good
	 */
	public void modifyGood(Good good) {
		goodDao.modifyGood(good);
	}
	
	/**
	 * 获取单条信息
	 * @param goodId
	 * @return
	 */
	@Transactional(readOnly = true)
	public Good getGood(Integer goodId) {
		return goodDao.getGood(goodId);
	}
	
	/**
	 * 查询全部信息
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Good> queryAllGood(int page) {
		return goodDao.queryAllGood(page);
	}
	
	/**
	 * 根据条件查询
	 * @param str
	 * @param Fromdate
	 * @param ToDate
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Good> FindGood(int page,Map<String, Object> param) {
		return goodDao.FindGood(page, param);
	}
	
	private GoodDao goodDao;

	public GoodDao getGoodDao() {
		return goodDao;
	}

	public void setGoodDao(GoodDao goodDao) {
		this.goodDao = goodDao;
	}
}
