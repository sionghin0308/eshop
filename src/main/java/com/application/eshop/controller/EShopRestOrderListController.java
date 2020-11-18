package com.application.eshop.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.eshop.common.CommonConstant;
import com.application.eshop.common.CommonConstant.CommonMessage;
import com.application.eshop.entity.postgres.Cart;
import com.application.eshop.entity.postgres.OrderList;
import com.application.eshop.entity.postgres.Product;
import com.application.eshop.services.OrderListService;
import com.application.eshop.services.ProductService;
import com.application.eshop.util.DateTimeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/orderList")
public class EShopRestOrderListController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderListService orderListService;
	
	@GetMapping(value="/list")
	@ApiOperation(value="Get Order List", notes="Get Order List")
	public @ResponseBody List<OrderList> getOrderList() {
		List<OrderList> result = orderListService.getOrderList();
		if(!CollectionUtils.isEmpty(result)) {
			return result;
		} else {
			List<OrderList> emptyList = new ArrayList<>();
			OrderList empty = new OrderList();
			empty.setMessage(CommonMessage.ORDER_LIST_EMPTY.message);
			emptyList.add(empty);
			return emptyList;
		}
	}
	
	@GetMapping(value="/list/customer")
	@ApiOperation(value="Get Customer Order List", notes="Get Customer Order List")
	public @ResponseBody List<OrderList> getCustomerOrderList(@RequestParam(value = "orderStatus") String orderStatus, 
			HttpServletRequest request, HttpServletResponse response) {
		
		if(request.getSession().getAttribute("userId") != null) {
			String userId = request.getSession().getAttribute("userId").toString();
			List<OrderList> result = orderListService.getCustomerOrderList(new BigDecimal(userId), orderStatus);
			if(!CollectionUtils.isEmpty(result)) {
				BigDecimal total = BigDecimal.ZERO;
				for(OrderList orderList : result) {
					total = total.add(orderList.getOrderPrice());
				}
				OrderList totalPrice = new OrderList();
				totalPrice.setMessage(CommonConstant.TOTAL + total);
				result.add(totalPrice);
				return result;
			} else {
				List<OrderList> emptyList = new ArrayList<>();
				OrderList empty = new OrderList();
				empty.setMessage(CommonMessage.ORDER_LIST_EMPTY.message);
				emptyList.add(empty);
				return emptyList;
			}
		} else {
			List<OrderList> emptyList = new ArrayList<>();
			OrderList empty = new OrderList();
			empty.setMessage(CommonMessage.ORDER_LOGGIN.message);
			emptyList.add(empty);
			return emptyList;
		}
		
	}
	
	@PostMapping(value="/add", consumes = "application/json", produces = "application/json")
	@ApiOperation(value="Add to cart", notes="Insert Order List")
	public @ResponseBody String insertOrderList(@RequestBody List<Cart> cartList, 
			HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		Map<String, String> resMap = new HashMap<String,String>();
		String orderId = CommonConstant.ORDER + DateTimeUtils.getFormatDate(DateTimeUtils.getCurrentDate(), "yyyyMMddHHmmss");
		
		List<String> productId = cartList.stream().map(id->{ return id.getProductId(); })
				.collect(Collectors.toList());
		
		List<Product> productList = productService.getProductByMultipleId(productId);
		
		if(request.getSession().getAttribute("userId") != null) {
			String userId = request.getSession().getAttribute("userId").toString();
			if(!CollectionUtils.isEmpty(productList)) {
				for(Cart cart: cartList) {
					Cart cartDetail = cartList.stream()
							.filter(a->a.getProductId().equals(cart.getProductId()))
							.findAny()
							.orElse(null);
					
					Product productDetail = productList.stream()
							.filter(a->a.getProductId().equals(cart.getProductId()))
							.findAny()
							.orElse(null);

					if(cartDetail != null && productDetail != null) {
						OrderList orderList = new OrderList();
						orderList.setOrderId(orderId);
						orderList.setProductId(cartDetail.getProductId());
						orderList.setCustomerId(new BigDecimal(userId));
						orderList.setOrderQuantity(cartDetail.getOrderQuantity());
						orderList.setOrderPrice(productDetail.getProductPrice().multiply(cartDetail.getOrderQuantity()));
						orderList.setOrderTime(DateTimeUtils.getTimestamp());
						orderList.setOrderStatus(CommonConstant.PENDING);
						orderListService.insertOrderList(orderList);
					}
				}
				resMap.put(CommonConstant.SUCCESS, orderId + CommonMessage.ORDER_SUCCESS.message);
			}
		} else {
			resMap.put(CommonConstant.FAILED, CommonMessage.ORDER_LOGGIN.message);
		}
		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.writeValueAsString(resMap);
	}
	
	@PostMapping(value="/edit")
	@ApiOperation(value="Update cart", notes="Update Order List")
	public @ResponseBody String updateOrderList(@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "productId") String productId, @RequestParam(value = "orderQuantity") BigDecimal orderQuantity,
			HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		Map<String, String> resMap = new HashMap<String,String>();
		
		if(request.getSession().getAttribute("userId") != null) {
			
			OrderList orderList = orderListService.getOrderDetail(orderId, productId, CommonConstant.PENDING);
			if(orderList != null) {
				if(orderQuantity.compareTo(new BigDecimal("0")) == 0) {
					orderListService.deleteOrderList(orderList);
					resMap.put(CommonConstant.SUCCESS, orderId + CommonMessage.DELETE_SUCCESS.message);
				} else {
					Product product = productService.getProductDetail(productId);
					if(product != null && product.getProductQuantity().compareTo(new BigDecimal("0")) != 0) {
						orderList.setOrderQuantity(orderQuantity);
						orderList.setOrderPrice(product.getProductPrice().multiply(orderQuantity));
						orderListService.updateOrderList(orderList);
						resMap.put(CommonConstant.SUCCESS, orderId + CommonMessage.UPDATE_SUCCESS.message);
					} else {
						resMap.put(CommonConstant.FAILED, CommonMessage.PRODUCT_SOLDOUT.message);
					}
				}
			} else {
				resMap.put(CommonConstant.FAILED, CommonMessage.ORDER_NOTFOUND.message);
			}
		} else {
			resMap.put(CommonConstant.FAILED, CommonMessage.ORDER_LOGGIN.message);
		}
		
		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.writeValueAsString(resMap);
	}
	
	@CacheEvict(value="product-list-cache", allEntries=true)
	@GetMapping(value = "/submitOrder")
	@ApiOperation(value = "Submit Order List", notes = "Submit Order List")
	public @ResponseBody String submitOrderList(HttpServletRequest request, HttpServletResponse response) 
			throws JsonProcessingException {
		Map<String, String> resMap = new HashMap<String,String>();
		
		if(request.getSession().getAttribute("userId") != null) {
			String userId = request.getSession().getAttribute("userId").toString();
			List<OrderList> result = orderListService.getCustomerOrderList(new BigDecimal(userId), CommonConstant.PENDING);
			if(!CollectionUtils.isEmpty(result)) {
				for(OrderList orderList : result) {
					Product product = productService.getProductDetail(orderList.getProductId());
					BigDecimal remainingQty = product.getProductQuantity().subtract(orderList.getOrderQuantity());
					product.setProductQuantity(remainingQty);
					productService.updateProduct(product);
					orderList.setOrderStatus(CommonConstant.SUCCESS);
					orderListService.updateOrderList(orderList);
				}
				resMap.put(CommonConstant.SUCCESS, CommonMessage.ORDER_PAID.message);
			} else {
				resMap.put(CommonConstant.FAILED, CommonMessage.ORDER_LIST_EMPTY.message);
			}
		} else {
			resMap.put(CommonConstant.FAILED, CommonMessage.ORDER_LOGGIN.message);
		}
		
		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.writeValueAsString(resMap);
	}
}
