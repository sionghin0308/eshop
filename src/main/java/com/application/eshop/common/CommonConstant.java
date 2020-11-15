package com.application.eshop.common;

public class CommonConstant {

	public static final String YES = "yes";
	public static final String NO = "no";
	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";
	public static final String AVAILABLE = "available";
	public static final String UNAVAILABLE = "unavailable";
	public static final String ORDER = "ORDER_";
	public static final String PENDING = "pending";
	public static final String TOTAL = "Total Price: ";

	public enum CommonMessage {
		PRODUCT_AVAILABLE("Product is available!"),
		PRODUCT_UNAVAILABLE("Product is not available!"),
		PRODUCT_NOTFOUND("Product is not found!"),
		PRODUCT_SOLDOUT("Product sold out!"),
		PRODUCT_LIST_EMPTY("Product list empty!"),
		PRODUCT_EXIST("Product exist!"),
		PRODUCT_NOTEXIST("Product doesn't exist!"),
		PRODUCT_ADD_SUCCESS("Product added successful!"),
		PRODUCT_UPDATE_SUCCESS("Product updated successful!"),
		PRODUCT_DELETE_SUCCESS("Product deleted successful!"),
		PRODUCT_ADD_FAIL("Product fail to add!"),
		PRODUCT_UPDATE_FAIL("Product detail fail to update!"),
		PRODUCT_DELETE_FAIL("Product fail to delete!"),
		
		CUSTOMER_LIST_EMPTY("Customer list empty!"),
		CUSTOMER_FOUND("User found!"),
		CUSTOMER_NOTFOUND("User not found!"),
		CUSTOMER_EXIST("User exist!"),
		CUSTOMER_NOTEXIST("User doesn't exist!"),
		CUSTOMER_ADD_SUCCESS("User register successful!"),
		CUSTOMER_UPDATE_SUCCESS("User updated successful!"),
		CUSTOMER_DELETE_SUCCESS("User deleted successful!"),
		CUSTOMER_ADD_FAIL("User fail to register!"),
		CUSTOMER_UPDATE_FAIL("User detail fail to update!"),
		CUSTOMER_DELETE_FAIL("User fail to delete!"),
		CUSTOMER_LOGIN_SUCCESS("User login success!"),
		CUSTOMER_LOGIN_FAIL("Password incorrect!"),
		
		ORDER_LOGGIN("Please login first!"),
		ORDER_LOGOUT("Logout successful!"),
		ORDER_SUCCESS(" order successful!"),
		ORDER_LIST_EMPTY("Order list empty!"),
		ORDER_NOTFOUND("Order not found!"),
		ORDER_PAID("Order pay successful!"),
		
		DELETE_SUCCESS(" delete successful!"),
		UPDATE_SUCCESS(" update successful!");

		public final String message;

		private CommonMessage(String message) {
			this.message = message;
		}
	}

}
