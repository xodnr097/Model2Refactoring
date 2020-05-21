package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
															"classpath:config/context-aspect.xml",
																"classpath:config/context-mybatis.xml",
																"classpath:config/context-transaction.xml"})
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testInsertProduct() throws Exception {
		
		System.out.println("1");
		Product product = new Product();
		System.out.println("2");
		product.setProdName("testprodname1");
		System.out.println(product.getProdName());
		product.setProdDetail("testproddetail");
		System.out.println(product.getProdDetail());
		product.setManuDate("202005");
		System.out.println(product.getManuDate());
		product.setPrice(1000);
		System.out.println(product.getPrice());
		product.setFileName("testPriceOfFile");
		System.out.println(product.getFileName());

		productService.insertProduct(product);
		System.out.println("3");
		
		//==> console Ȯ��
		System.out.println(product);
		
		//==> API Ȯ��
	/*	Assert.assertEquals("testprodname", product.getProdName());
		Assert.assertEquals("testproddetail", product.getProdDetail());
		Assert.assertEquals("testmanudate", product.getManuDate());
		Assert.assertEquals(1000, product.getPrice());
		Assert.assertEquals("testPriceOfFile", product.getFileName()); */
	}
	
		//@Test
		public void testFindProduct() throws Exception{
			
			Product product = new Product();
			
			product = productService.findProduct(10012);
			
			System.out.println(product);
		}
		
		//@Test
		public void testUpdateProduct() throws Exception{
			
			Product product = productService.findProduct(10012);
			System.out.println(product);
			
			product.setProdName("����513");
			product.setProdDetail("����513");
			product.setPrice(514);
			
			System.out.println(product.getProdName());
			System.out.println(product.getProdDetail());
			System.out.println(product.getPrice());

			productService.updateProduct(product);
			
			System.out.println("�Ϸ�");
		}
		
		//@Test
		public void testGetProductListAll() throws Exception{
			
			System.out.println("productlist ����");
			Search search = new Search();
			search.setCurrentPage(1);
			System.out.println(search.getCurrentPage());
			search.setPageSize(3);
			System.out.println(search.getPageSize());
			Map<String,Object> map = productService.getProductList(search);
			System.out.println("����");
			List<Object> list = (List<Object>)map.get("list");
			Assert.assertEquals(3,  list.size());
			
			System.out.println(list);
			
			Integer totalCount = (Integer)map.get("totalCount");
			System.out.println(totalCount);
			
			System.out.println("============================================");
			
			search.setCurrentPage(1);
			search.setPageSize(3);
			search.setSearchCondition("0");
			search.setSearchKeyword("");
			map = productService.getProductList(search);
			
			list = (List<Object>)map.get("list");
			
			totalCount = (Integer)map.get("totalCount");
			System.out.println(totalCount);
		}
		
		@Test
		public void testGetProductListByProdNo() throws Exception{
			
			Search search = new Search();
			search.setCurrentPage(1);
			search.setPageSize(3);
			search.setSearchCondition("0");
			search.setSearchKeyword("10017");
			Map<String,Object> map = productService.getProductList(search);
			
			List<Object> list = (List<Object>)map.get("list");
			Assert.assertEquals(1, list.size());
			
			System.out.println(list);
		}
}