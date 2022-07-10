package com.couponz.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.couponz.app.entity.Product;
import com.couponz.app.services.ProductManagerService;
import com.couponz.app.services.ProductNotFoundException;

@Controller
public class ProductDataController {

	@Autowired
	ProductManagerService productManagerService;
	
	List<Product> productLst = null;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getAllProducts(Model model) {

		this.productLst = productManagerService.getAllProducts();
		model.addAttribute("pageTitle", "Add New Product");
		model.addAttribute("productLst", productLst);

		return "index";
	}

	@RequestMapping(value = "/new/product", method = RequestMethod.GET)
	public String addNewProductForm(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("pageTitle", "Add New Product");
		return "createNewProduct";
	}

	@RequestMapping(value = "/save/product", method = RequestMethod.POST)
	public String addNewProduct(Product product) {
		productManagerService.addOrUpdate(product);
		return "redirect:/home";
	}

	@RequestMapping(value = "/edit/product/{id}", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Product product = null;
		try {
			product = this.productManagerService.getProduct(id);
			model.addAttribute("pageTitle", "Edit Product : " + id);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
		}
		model.addAttribute("product", product);
		return "edit";
	}

	@RequestMapping(value = "/product/update/{id}", method = RequestMethod.POST)
	public String updateProduct(@PathVariable("id") Integer id, Product product) {
		product.setProductId(id);
		productManagerService.addOrUpdate(product);
		return "redirect:/home";
	}

	@RequestMapping(value = "/delete/product/{id}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable("id") Integer id) {

		try {
			this.productManagerService.deleteProduct(id);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
		}
		return "redirect:/home";

	}
	
	@RequestMapping(value = "/search/product", method = RequestMethod.GET)
	public String serachProduct(Model model,String keyword) {
		if (keyword != null) {
			try {
				this.productLst = this.productManagerService.serachProduct(keyword);
			} catch (ProductNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			this.productLst = productManagerService.getAllProducts();
		}
		model.addAttribute("productLst", productLst);
		System.out.println(keyword+"--" );
		this.productLst.forEach(i -> System.out.println(i.getBrand()));
		return "index";
	}
	
	
	

}
