package com.model2.mvc.service.product;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;


public interface ProductDAO {
	
		public void insertProduct(Product product) throws Exception;
	
		public Product findProduct(int prodNo) throws Exception;
		
		public List<Product> getProductList(Search search)throws Exception;
			
	    public void updateProduct(Product product) throws Exception;
		
		public int getTotalCount(Search search) throws Exception;
		/*
		//�Խ��� Page ó���� ���� ��ü Row(totalCount) return
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
			System.out.println("totalcount ���ɴϱ�?"+totalCount);
			return totalCount;
		}
		*/
		
		/*
		//�Խ��� currentPage Row�� return
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
