package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Product;

public class ProductDao {
	Properties prop = new Properties();
	PreparedStatement pstat = null;
	ResultSet rset = null;
	
	
	public ProductDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int insertProduct(Connection conn, Product p) {
		int result = 0;
		try {
			
			String sql = prop.getProperty("insert");
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, p.getProductId());
			pstat.setString(2, p.getpName());
			pstat.setInt(3, p.getPrice());
			pstat.setString(4, p.getDescription());
			pstat.setInt(5, p.getStock());
			
			result = pstat.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstat);
		}
		return result;
	}

	public int updateProduct(Connection conn, Product p) {
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("update");
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, p.getpName());
			pstat.setInt(2, p.getPrice());
			pstat.setString(3, p.getDescription());
			pstat.setInt(4, p.getStock());
			pstat.setString(5, p.getProductId());
		
			result = pstat.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstat);
		}
		return result;
	}
	
	public int deleteProduct(Connection conn, String id) {
		int result = 0;
		try {
			String sql = prop.getProperty("delete");
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			
			result = pstat.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstat);
		}
		return result;
	}
	
	public ArrayList<Product> selectAll(Connection conn){
		ArrayList<Product> list = new ArrayList<>();
		Statement stat = null;
		ResultSet rset = null;

		String sql = prop.getProperty("allSelect");
		try {
			stat = conn.createStatement();
			rset = stat.executeQuery(sql);
			while (rset.next()) {
				Product p = new Product(rset.getString(1), rset.getString(2), rset.getInt(3),
						rset.getString(4),rset.getInt(5));

				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stat);
		}
		return list;
	}
	
	public ArrayList<Product> searchName(Connection conn, String name){
		ArrayList<Product> list = new ArrayList<>();
		PreparedStatement stat = null;
		ResultSet rset = null;

		String sql = prop.getProperty("search");
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, name);
			rset = stat.executeQuery();
			while (rset.next()) {
				Product p = new Product(rset.getString(1), rset.getString(2), rset.getInt(3),
						rset.getString(4),rset.getInt(5));

				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stat);
		}
		return list;
	}
	
}
