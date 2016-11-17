package com.sinitek.demo.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sinitek.demo.dao.GoodDao;
import com.sinitek.demo.domain.Good;


public class GoodDaoImpl extends HibernateDaoSupport implements GoodDao {
	
	public int barFind(String barcode) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void saveGood(Good good) {
		
		super.getHibernateTemplate().save(good);

	}

	public void modifyGood(Good good) {
		//super.getHibernateTemplate().update(good);
		String hql = "update Good g set g.barcode = :barcode , g.goodName = :goodName ," +
		" g.goodLocation = :goodLocation , g.goodType = :goodType , g.goodImage = :goodImage," +
		"g.buyPrice = :buyPrice , g.salePrice = :salePrice ,g.goodDate = :goodDate where g.goodId = :goodId";
		Query query =this.getSession().createQuery(hql);
		query.setString("barcode", good.getBarcode());
		query.setString("goodName", good.getGoodName());
		query.setString("goodLocation", good.getGoodLocation());
		query.setString("goodType", good.getGoodType());
		query.setString("goodImage", good.getGoodImage());
		query.setDouble("buyPrice", good.getBuyPrice());
		query.setDouble("salePrice", good.getSalePrice());
		query.setDate("goodDate", good.getGoodDate());
		query.setInteger("goodId", good.getGoodId());
		query.executeUpdate();
	}

	public Good getGood(Integer goodId) {
		return (Good) super.getHibernateTemplate().load(Good.class, goodId);
	}

	@SuppressWarnings("unchecked")
	public List<Good> queryAllGood(int page) {
		String hql = "from Good";
		Query query = this.getSession().createQuery(hql);
		query.setFirstResult((page-1)*4);
		query.setMaxResults(4);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Good> FindGood(int page,Map<String, Object> param) {
		String hql = "from Good where barcode like :barcode and goodName like :goodName " +
				"and goodDate between :dateFrom and :dateTo";
		Query query = this.getSession().createQuery(hql);
		query.setProperties(param);
		query.setFirstResult((page-1)*4);
		query.setMaxResults(4);
		return query.list();
	}

	

}
