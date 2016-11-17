package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sinitek.demo.dao.hibernate.GoodDaoImpl;
import com.sinitek.demo.domain.Good;

public class GoodTest {
	ApplicationContext context=new ClassPathXmlApplicationContext("spring-base.xml");
	GoodDaoImpl dao = (GoodDaoImpl) context.getBean("goodDao");
	@Test
	public void get(){		
		Good good=dao.getGood(1);
		System.out.println(good);
	}
	
	@Test
	public void save(){
		Date date = new Date();
		Good good  = new Good();
		good.setBarcode("1234");
		good.setGoodName("毛巾");
		good.setGoodLocation("常德");
		good.setGoodType("生活用品");
		good.setGoodImage("54.jpg");
		good.setBuyPrice(21.5);
		good.setSalePrice(30);
		good.setGoodCount(0);
		good.setGoodDate(date);
		good.setGoodId(12);
		dao.saveGood(good);
	}
	
	@Test
	public void modify(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		String dstr="2008-4-24";  
		Date date;
		try {
			date = sdf.parse(dstr);
			
			Good good  = new Good();
			good.setGoodId(4);
			good.setBarcode("12334546");
			good.setGoodName("毛巾");
			good.setGoodLocation("常德");
			good.setGoodType("生活用品");
			good.setGoodImage("54.jpg");
			good.setBuyPrice(21.5);
			good.setSalePrice(30);
			good.setGoodDate(date);
			dao.modifyGood(good);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}  
	}
	
	
	@Test
	public void query(){
		List<Good> data = dao.queryAllGood(1);
		for(Good good : data){
			System.out.println(good);
		}
	}
	
	@Test
	public void find(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		
		Map<String, Object> param = new HashMap<String, Object>();
		int page = 1;
		param.put("barcode", "%5%");
		param.put("goodName", "%%");
		try {
			param.put("dateFrom", sdf.parse("2016-01-11"));
			param.put("dateTo", sdf.parse("2016-10-11"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Good> data = dao.FindGood(page, param);
		for(Good good : data){
			System.out.println(good);
		}
	}
	
/*	@Test 
	public void tt(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		try {
			String dstr="2008-04-24";  
			Date date=sdf.parse(dstr);
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date date=new Date();  
		String str=sdf.format(date);  
		System.out.println(str);
	}*/
}
