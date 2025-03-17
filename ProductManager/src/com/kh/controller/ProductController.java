package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.ProductService;
import com.kh.model.vo.Product;
import com.kh.view.ProductMenu;

public class ProductController {
	private ProductService pSer = new ProductService();

	public void insertProduct(String id, String name, int price, String description, int stock) {
		Product p = new Product(id, name, price, description, stock);

		int result = pSer.insertProduct(p);

		if (result > 0) {
			new ProductMenu().displaySuccess("* 상품 추가 성공 *");
		} else {
			new ProductMenu().displayFailed("* 상품 추가 실패 *");
		}
	}

	public void selectAll() {
		ArrayList<Product> list = pSer.selectAllList();

		if (list.isEmpty()) {
			new ProductMenu().displayNoData();
		} else {
			new ProductMenu().displayAllProducts(list);
		}
	}

	public void searchName(String name) {
		ArrayList<Product> list = pSer.searchName(name);
		if (list.size() == 0) {
			new ProductMenu().displayNoData();
		} else {
			new ProductMenu().displayAllProducts(list);
		}
	}

	public void updateProduct(String productId, String pName, int price, String description, int stock) {
		Product p = new Product(productId, pName, price, description, stock);
		int result = pSer.updateProduct(p);
		if (result > 0) {
			new ProductMenu().displaySuccess(" 상품 정보 수정 성공 ");
		} else {
			
			new ProductMenu().displayFailed(" 상품 정보 수정 실패 ");
		}
	}

	public void deleteProduct(String id) {

		int result = pSer.deleteProduct(id);
		if (result > 0) {
			new ProductMenu().displaySuccess(" 상품 삭제 성공 ");
		} else {
			new ProductMenu().displayFailed(" 상품 삭제 실패 ");
		}

	}

}
