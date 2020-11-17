package com.application.eshop.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.eshop.common.CommonConstant;
import com.application.eshop.common.CommonConstant.CommonMessage;
import com.application.eshop.entity.postgres.Product;
import com.application.eshop.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/product")
public class EShopRestProductController {
	
	@Autowired
	private ProductService productService;
	
	@Cacheable(value="product-list-cache")
	@GetMapping(value="/list")
	@ApiOperation(value="Get Product List", notes="Get Product List")
	public @ResponseBody List<Product> getProductList() {
		List<Product> result = productService.getProductList();
		if(!CollectionUtils.isEmpty(result)) {
			return result;
		} else {
			List<Product> emptyList = new ArrayList<>();
			Product empty = new Product();
			empty.setMessage(CommonMessage.PRODUCT_LIST_EMPTY.message);
			emptyList.add(empty);
			return emptyList;
		}
	}
	
	@CacheEvict(value="product-list-cache", allEntries=true)
	@GetMapping(value="/list/updated")
	@ApiOperation(value="Get Updated Product List", notes="Get Updated Product List")
	public @ResponseBody List<Product> getUpdatedProductList() {
		List<Product> result = productService.getProductList();
		if(!CollectionUtils.isEmpty(result)) {
			return result;
		} else {
			List<Product> emptyList = new ArrayList<>();
			Product empty = new Product();
			empty.setMessage(CommonMessage.PRODUCT_LIST_EMPTY.message);
			emptyList.add(empty);
			return emptyList;
		}
	}
	
	@Scheduled(cron = "0 0/1 * * * *")
	@CacheEvict(value="product-list-cache", allEntries=true)
	public void clearProductListCache() {
		System.out.println("Product list cache clear");
	}
	
	@GetMapping(value="/detail")
	@ApiOperation(value="Get Product Detail", notes="Get Product Detail")
	public @ResponseBody Product getProductDetail(@RequestParam(value="productId") String productId) {
		Product result = productService.getProductDetail(productId);
		if(result != null) {
			if(result.getProductQuantity().compareTo(new BigDecimal("0")) == 0) {
				result.setMessage(CommonMessage.PRODUCT_SOLDOUT.message);
			} else {
				result.setMessage(CommonMessage.PRODUCT_AVAILABLE.message);
			}
			return result;
		} else {
			Product empty = new Product();
			empty.setMessage(CommonMessage.PRODUCT_NOTFOUND.message);
			return empty;
		}
	}
	
	@PostMapping(value="/add", consumes = "application/json", produces = "application/json")
	@ApiOperation(value="Add Product", notes="Insert Product")
	public @ResponseBody Product insertProduct(@RequestParam(value="productId") String productId, @RequestBody Product product, 
			HttpServletRequest request) throws JsonProcessingException {
		product.setProductId(productId);
		
		Product result = productService.getProductDetail(productId);
		
		if(result != null) {
			product.setMessage(CommonMessage.PRODUCT_EXIST.message);
		} else {
			product = productService.insertProduct(product);
			if(product.getMessage().equals(CommonConstant.SUCCESS)) {
				product.setMessage(CommonMessage.PRODUCT_ADD_SUCCESS.message);
			} else {
				product.setMessage(CommonMessage.PRODUCT_ADD_FAIL.message);
			}
		}
		return product;
	}
	
	@PostMapping(value="/change", consumes = "application/json", produces = "application/json")
	@ApiOperation(value="Edit Product Detail", notes="Update Product")
	public @ResponseBody Product updateProduct(@RequestParam(value="productId") String productId, @RequestBody Product product, 
			HttpServletRequest request) throws JsonProcessingException {
		product.setProductId(productId);
		
		Product result = productService.getProductDetail(productId);
		
		if(result != null) {
			product = productService.updateProduct(product);
			if(product.getMessage().equals(CommonConstant.SUCCESS)) {
				product.setMessage(CommonMessage.PRODUCT_UPDATE_SUCCESS.message);
			} else {
				product.setMessage(CommonMessage.PRODUCT_UPDATE_FAIL.message);
			}
		} else {
			product.setMessage(CommonMessage.PRODUCT_NOTEXIST.message);
		}
		return product;
	}
	
	@PostMapping(value="/delete", consumes = "application/json", produces = "application/json")
	@ApiOperation(value="Delete Product", notes="Delete Product")
	public @ResponseBody Product deleteProduct(@RequestParam(value="productId") String productId, @RequestBody Product product, 
			HttpServletRequest request) throws JsonProcessingException {
		product.setProductId(productId);
		
		Product result = productService.getProductDetail(productId);
		
		if(result != null) {
			product = productService.deleteProduct(product);
			if(product.getMessage().equals(CommonConstant.SUCCESS)) {
				product.setMessage(CommonMessage.PRODUCT_DELETE_SUCCESS.message);
			} else {
				product.setMessage(CommonMessage.PRODUCT_DELETE_FAIL.message);
			}
		} else {
			product.setMessage(CommonMessage.PRODUCT_NOTEXIST.message);
		}
		return product;
	}
	
}
