package com.application.eshop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.eshop.entity.postgres.Product;
import com.application.eshop.respository.postgres.ProductDao;

@Service("productService")
public class ProductService {

	@Autowired
	private ProductDao productDao;

    public List<Product> getProductList() {
    	return productDao.getProductList();
    }
    
	public List<Product> getProductByMultipleId(List<String> productId){
		return productDao.getProductByMultipleId(productId);
	}
    
    public Product getProductDetail(String productId) {
    	return productDao.getProductDetail(productId);
    }
    
    public Product insertProduct(Product product) {
    	return productDao.insertProduct(product);
    }
    
    public Product updateProduct(Product product) {
    	return productDao.updateProduct(product);
    }
    
    public Product deleteProduct(Product product) {
    	return productDao.deleteProduct(product);
    }
    
}
