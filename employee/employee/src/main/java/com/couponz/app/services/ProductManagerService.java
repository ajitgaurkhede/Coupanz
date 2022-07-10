package com.couponz.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.couponz.app.dao.ProductRepo;
import com.couponz.app.entity.Product;

@Service
public class ProductManagerService {

	@Autowired
	ProductRepo productRepo;

	public List<Product> getAllProducts() {

		List<Product> products = this.productRepo.findAll();

		return products;
	}

	public void addOrUpdate(Product product) {
		this.productRepo.save(product);
	}
	
	@Modifying
	@Query("update Product p where p.productId < :id")
	public void updateProduct(Product product) {

		this.productRepo.save(product);
	}
	
	public Product getProduct(Integer id) throws ProductNotFoundException {

		Optional<Product> result = this.productRepo.findById(id);

		if (result.isPresent()) {
			return result.get();
		}

		throw new ProductNotFoundException("not found product " + id);
	}
	
	public void deleteProduct(Integer id) throws ProductNotFoundException {
		Long count = this.productRepo.countByProductId(id);
		if(count == null || count == 0){
			throw new ProductNotFoundException("not found product " + id);
		}
		this.productRepo.deleteById(id);
	}
	
	public List<Product> serachProduct(String keyword) throws ProductNotFoundException {
		List<Product> result = null;
		result = this.productRepo.findByKeyword(keyword);
		if(result.isEmpty()) {
			throw new ProductNotFoundException("not found product " + keyword);
		}
		
		return result;
	}

}
