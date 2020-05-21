package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDAO;
import com.model2.mvc.service.product.ProductService;




@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

	///Field
	@Autowired
	@Qualifier("productDAOImpl")
	private ProductDAO productDAO;
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	///Constructor
	public ProductServiceImpl() {
		System.out.println(this.getClass());
	}
	
	///Method
	public void insertProduct(Product product) throws Exception{
		productDAO.insertProduct(product);
	}
	
	public Product findProduct(int prodNo) throws Exception{
		return productDAO.findProduct(prodNo);
	}

	public Map<String,Object> getProductList(Search search) throws Exception{
		List<Product> list= productDAO.getProductList(search);
		int totalCount = productDAO.getTotalCount(search);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}

	public void updateProduct(Product product) throws Exception{
		productDAO.updateProduct(product);
	}
	
}//end class
