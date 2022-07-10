package com.couponz.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.couponz.app.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{
	
	public Long countByProductId(Integer id);
	
	@Query(value = "select * from product p where p.product_name like %:keyword% ", nativeQuery = true)
	public  List<Product> findByKeyword(@Param("keyword") String keyword);


}
