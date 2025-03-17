package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.ProductController;
import com.kh.model.vo.Product;

public class ProductMenu {

	Scanner sc = new Scanner(System.in);

	private ProductController pController = new ProductController();
	
 
	public void mainMenu() {
		while (true) {
			System.out.println(" ========== 상품 관리 프로그램 ==========");
			System.out.println("1. 전체 조회");
			System.out.println("2. 상품 추가");
			System.out.println("3. 상품 수정"); 
			System.out.println("4. 상품 삭제 "); 
			System.out.println("5. 상품 검색"); 
			System.out.println("9. 프로그램 종료 ");
			
			System.out.print(">> 메뉴 번호 : ");
			int menu = sc.nextInt();
			sc.nextLine();

			switch (menu) {
			case 1:
				pController.selectAll();
				break;
			case 2:
				addProductMenu();
				break;
			case 3:
				updateProductMenu();
				break;
			case 4:
				deleteProductMenu();
				break;
			case 5:
				searchByNameMenu();
				break;
			case 9:
				System.out.println("프로그램이 종료됩니다...");
				return;
			}
		}
	}


	private void deleteProductMenu() {
		System.out.print("삭제할 상품 아이디 : ");
		String id = sc.nextLine();
		
		pController.deleteProduct(id);
	}


	private void updateProductMenu() {
		System.out.print("상품 아이디 : ");
		String productId = sc.nextLine();
		System.out.print("상품명 : ");
		String pName = sc.nextLine();
		System.out.print("상품가격 : ");
		int price = sc.nextInt();
		sc.nextLine();
		System.out.print("상품 상세 정보 : ");
		String description = sc.nextLine();
		System.out.print("재고 : ");
		int stock = sc.nextInt();
		sc.nextLine();
		
		pController.updateProduct(productId, pName, price, description, stock);		
	}


	private void searchByNameMenu() {
		System.out.print("조회할 상품명 입력 : ");
		String searchName = sc.nextLine();
		
		pController.searchName(searchName);		
	}

	public void displayAllProducts(ArrayList<Product> list) {
		System.out.println(" ----- 조회된 상품 목록 -----");
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	public void displayNoData() {
		System.out.println(" ----- 조회된 상품정보가 없습니다. -----");
	}
	
	private void addProductMenu() {
		System.out.print("상품 아이디 : ");
		String productId = sc.nextLine();
		System.out.print("상품명 : ");
		String pName = sc.nextLine();
		System.out.print("상품가격 : ");
		int price = sc.nextInt();
		sc.nextLine();
		System.out.print("상품 상세 정보 : ");
		String description = sc.nextLine();
		System.out.print("재고 : ");
		int stock = sc.nextInt();
		sc.nextLine();
		pController.insertProduct(productId, pName, price, description, stock);
	}
	
	public void displaySuccess(String message) {
		System.out.println("* 서비스 요청 성공 : " + message);
	}
	
	public void displayFailed(String message) {
		System.out.println("* 서비스 요청 실패 : " + message);
	}
	
	public void displayProduct(Product m) {
		System.out.println(" ---- 조회된 상품 정보 ----");
		System.out.println(m);
	}

}
