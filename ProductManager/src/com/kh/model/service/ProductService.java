package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.ProductDao;
import com.kh.model.vo.Product;

public class ProductService {

	public int insertProduct(Product p) {
		int result = 0;
		
		Connection conn = JDBCTemplate.getConnection();
		
		result = new ProductDao().insertProduct(conn, p);
		
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	public ArrayList<Product> selectAllList() {
		Connection conn = JDBCTemplate.getConnection();	
		ArrayList<Product> list = new ProductDao().selectAll(conn);
		JDBCTemplate.close(conn);
		return list;
	}
	
	public ArrayList<Product> searchName(String name) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Product> list = new ProductDao().searchName(conn, name);
		
		JDBCTemplate.close(conn);
		
		return list;
	}
	
	public int deleteProduct(String id) {
		int result = 0;
		
		Connection conn = JDBCTemplate.getConnection();
		
		result = new ProductDao().deleteProduct(conn, id);
		
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	public int updateProduct(Product p) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new ProductDao().updateProduct(conn, p);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
}
