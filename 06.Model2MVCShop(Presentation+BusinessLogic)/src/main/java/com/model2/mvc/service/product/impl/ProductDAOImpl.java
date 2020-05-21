package com.model2.mvc.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDAO;


@Repository("productDAOImpl")
public class ProductDAOImpl implements ProductDAO{

	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public ProductDAOImpl() {
		System.out.println("productDAO입니다");
		System.out.println(this.getClass());
	}
	
	///Method
	public void insertProduct(Product product) throws Exception	{
		sqlSession.insert("ProductMapper.insertProduct",product);
	}
	
	public Product findProduct(int prodNo) throws Exception{
		System.out.println("findproduct 실행한다/ProductDAOImpl");
		return sqlSession.selectOne("ProductMapper.findProduct", prodNo);
	}
	
	public void updateProduct(Product product) throws Exception{
		sqlSession.update("ProductMapper.updateProduct", product);
		System.out.println("updateproduct 실행한다/ProductDAOImpl");
	}
	
	public List<Product> getProductList(Search search) throws Exception{
		return sqlSession.selectList("ProductMapper.getProductList", search);
	}
	
	public int getTotalCount(Search search) throws Exception{
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}
	
		/*
		 {
		Connection con = DBUtil.getConnection();
		
		String sql = "insert into Product values (seq_product_prod_no.NEXTVAL,?,?,?,?,?,sysdate)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		//stmt.setInt(1, productVO.getProdNo());
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		//stmt.setDate(7, productVO.getRegDate());
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
	}
		*/
	
	/*
	public Product findProduct(int prodNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from PRODUCT where PROD_NO=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
		
		ResultSet rs = pStmt.executeQuery();
		
		Product product = null;
		while(rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_Detail"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			System.out.println("in productDAO"+product.getManuDate());
		}
		con.close();
		rs.close();
		pStmt.close();
		
		return product;
	}//find
		
		public Map<String,Object> getProductList(Search search)throws Exception{
			
			Map<String, Object>	map = new HashMap<String, Object>();
			
			Connection con = DBUtil.getConnection();
			
			// Original Query 구성
			String sql = "select * from PRODUCT";
			
			if (search.getSearchCondition() != null) {
				if( search.getSearchCondition().equals("0") && !search.getSearchKeyword().contentEquals("")) {
					sql += " WHERE prod_no LIKE'" + "%" + search.getSearchKeyword() + "%"
							+"'";
				}else if(search.getSearchCondition().equals("1") && !search.getSearchKeyword().contentEquals("")) {
					sql += " WHERE prod_name LIKE'" + "%" + search.getSearchKeyword() + "%"
							+ "'";
				}else if(search.getSearchCondition().equals("2") && !search.getSearchKeyword().contentEquals("")) {
					sql += " WHERE price LIKE'" + search.getSearchKeyword()
							+ "'";
				}
			}

			sql += " ORDER BY prod_no"; // 앞에 띄어쓰기로 공백!!
			
			System.out.println("ProductDAO::Original SQL::" + sql);
			
			//==> TotalCount GET
			int totalCount = this.getTotalCount(sql);
			System.out.println("ProductDAO :: totalCount ::" + totalCount);
			
			
			//==> CurrentPage 게시물만 받도록 Query 다시 구성
			sql = makeCurrentPageSql(sql, search);
			PreparedStatement pStmt =con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
	
			System.out.println(search);
			
			ArrayList<Product> list = new ArrayList<Product>() ;
			
			while(rs.next()) {
					Product product = new Product();
					product.setProdNo(rs.getInt("PROD_NO"));
					product.setProdName(rs.getString("PROD_NAME"));
					product.setProdDetail(rs.getString("PROD_DETAIL"));
					product.setManuDate(rs.getString("MANUFACTURE_DAY"));
					product.setFileName(rs.getString("IMAGE_FILE"));
					list.add(product);
				}//end for
			
			// ==> totalCount 정보 저장
			map.put("totalCount", new Integer(totalCount));
			// ==> currentPage 의 게시물 정보 갖는 List 저장
			System.out.println("list.size() : "+ list.size());
			map.put("list", list);
			System.out.println("map().size() :"+ map.size());
			System.out.println(map.get("totalCount"));
			
			rs.close();
			con.close();
			con.close();
	
			return map; 
		}

		public void updateProduct(Product product) throws Exception{
			
				Connection con = DBUtil.getConnection();
				
				String sql = "update PRODUCT set PROD_Name=?, PROD_DETAIL=?, MANUFACTURE_DAY=?, PRICE=?, IMAGE_FILE=? where PROD_NO=? ";
				
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, product.getProdName());
				stmt.setString(2, product.getProdDetail());
				stmt.setString(3, product.getManuDate());
				stmt.setInt(4, product.getPrice());
				stmt.setString(5, product.getFileName());
				stmt.setInt(6, product.getProdNo());
				stmt.executeUpdate();
				
				stmt.close();
				con.close();
		}
		
		//게시판 Page 처리를 위한 전체 Row(totalCount) return
		private int getTotalCount(String sql) throws Exception {
			
			sql = "SELECT COUNT(*)" +
					 "FROM (" +sql+") countTable";
			
			Connection con = DBUtil.getConnection();
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			int totalCount = 0;
			if(rs.next()) {
				totalCount = rs.getInt(1);
			}
			
			pStmt.close();
			con.close();
			rs.close();
			System.out.println("totalcount 나옵니까?"+totalCount);
			return totalCount;
		}
		
		//게시판 currentPage Row만 return
		private String makeCurrentPageSql(String sql, Search search) {
		System.out.println("0ProductDAO :: make SQL ::" + sql);
			sql = "SELECT * " +
					 "FROM(		SELECT inner_table.*,  ROWNUM AS row_seq " +
					 				"	FROM(		"+sql+"	)inner_table "+
					 				"   WHERE ROWNUM <= "+search.getCurrentPage()*search.getPageSize()+" ) " +
					 "WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) + " AND " +search.getCurrentPage()*search.getPageSize();			
		
		System.out.println("ProductDAO :: make SQL ::" + sql);
		
		return sql;
		
		}
		*/
}//end class
