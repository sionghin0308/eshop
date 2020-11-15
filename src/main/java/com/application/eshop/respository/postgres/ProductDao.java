package com.application.eshop.respository.postgres;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.application.eshop.common.CommonConstant;
import com.application.eshop.entity.postgres.Product;


@Repository
@Transactional(value = "transactionManager")
public class ProductDao {

	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * Get Product List from table Product
	 * 
	 * @return Product List
	 */
	public List<Product> getProductList() {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "from Product where productQuantity != 0";
		Query query = session.createQuery(sql, Product.class);
	
		List<Product> results = query.getResultList();
		
		if(!CollectionUtils.isEmpty(results)){
			return results;
		} else {
			return null;
		}
	}
	
	/**
	 * Get Multiple Product List from table Product
	 * 
	 * @return Multiple Product List
	 */
	public List<Product> getProductByMultipleId(List<String> productId){
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "from Product where productId in (:productId) and productQuantity != 0";
		Query query = session.createQuery(sql, Product.class);
		query.setParameterList("productId", productId);
		return query.getResultList();
	}
	
	/**
	 * Get Product Detail from table Product
	 * 
	 * @return Product Detail
	 */
	public Product getProductDetail(String productId) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "from Product where productId = :productId";
		Query query = session.createQuery(sql, Product.class);
		query.setParameter("productId", productId);
		
		Product productDetail = new Product();
		if(!CollectionUtils.isEmpty(query.getResultList())){
			productDetail = (Product) query.getResultList().get(0);
			return productDetail;
		} else {
			return null;
		}
	}
	
	/**
	 * Insert Product to table Product
	 * 
	 * @return Product
	 */
	public Product insertProduct(Product product) throws HibernateException {
		Session session = this.sessionFactory.getCurrentSession();
        try {
        	session.save(product);
        	product.setMessage(CommonConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            product.setMessage(CommonConstant.FAILED);
        }
        return product;
	}
	
	/**
	 * Update Product Detail to table Product
	 * 
	 * @return Product
	 */
	public Product updateProduct(Product product) throws HibernateException {
		Session session = this.sessionFactory.getCurrentSession();
        try {
        	session.update(product);
        	product.setMessage(CommonConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            product.setMessage(CommonConstant.FAILED);
        }
        return product;
	}
	
	/**
	 * Delete Product from table Product
	 * 
	 * @return Product
	 */
	public Product deleteProduct(Product product) throws HibernateException {
		Session session = this.sessionFactory.getCurrentSession();
        try {
        	session.remove(product);
        	product.setMessage(CommonConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            product.setMessage(CommonConstant.FAILED);
        }
        return product;
	}

}
