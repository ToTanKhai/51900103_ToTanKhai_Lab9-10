package com.cnjava.lab9.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cnjava.lab9.models.Orders;
import com.cnjava.lab9.models.ProductOrder;
import com.cnjava.lab9.models.Products;
import com.cnjava.lab9.models.ResponseObject;
import com.cnjava.lab9.repositories.OrdersRepository;
import com.cnjava.lab9.repositories.ProductsRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api")
public class ApiController {
	@Autowired
	private OrdersRepository ordersRepo;

	@Autowired
	private ProductsRepository productsRepo;

	@GetMapping("/products")
	public ResponseEntity<ResponseObject> getAllProduct() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("OK", "Query all products have successfully", productsRepo.findAll()));
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ResponseObject> getProductById(@PathVariable int id) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("OK", "Query product by id have successfully", productsRepo.findById(id)));
	}

	@PostMapping("/products")
	public ResponseEntity<ResponseObject> addProdcut(@RequestBody Products newProduct) {
		List<Products> foundProducts = productsRepo.findByName(newProduct.getName().trim());
		if (foundProducts.size() > 0) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
					.body(new ResponseObject("failed", "Product name already taken", ""));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", "Insert Product successfully", productsRepo.save(newProduct)));
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<ResponseObject> updateProduct(@RequestBody @Valid Products newProduct, @PathVariable int id) {

		Products updatedProduct = productsRepo.findById(id).map(product -> {
			product.setName(newProduct.getName());
			product.setPrice(newProduct.getPrice());
			product.setIllustration(newProduct.getIllustration());
			product.setDescription(newProduct.getDescription());

			return productsRepo.save(product);
		}).orElseGet(() -> {
			newProduct.setId(id);
			return productsRepo.save(newProduct);
		});
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", "Update Product successfully", updatedProduct));
	}

	@PatchMapping("/products/{id}")
	public ResponseEntity<ResponseObject> changeProduct(@RequestBody Products newProduct, @PathVariable int id) {

		Products updatedProduct = productsRepo.findById(id).map(product -> {
			if (newProduct.getName() != null && newProduct.getName() != "") {
				product.setName(newProduct.getName());
			}

			if (newProduct.getPrice() != 0) {
				product.setPrice(newProduct.getPrice());
			}

			if (newProduct.getIllustration() != null && newProduct.getIllustration() != "") {
				product.setIllustration(newProduct.getIllustration());
			}

			if (newProduct.getDescription() != null && newProduct.getDescription() != "") {
				product.setDescription(newProduct.getDescription());
			}

			return productsRepo.save(product);
		}).orElseGet(() -> {
			return null;
		});

		if (updatedProduct == null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("fail", "Could not found product", null));
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", "Update Product successfully", updatedProduct));
	}

	@DeleteMapping("/products/{id}")
	ResponseEntity<ResponseObject> deleteProduct(@PathVariable int id) {
		boolean exists = productsRepo.existsById(id);
		if (exists) {
			productsRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", "Delete product successfully", ""));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseObject("failed", "Cannot find product to delete", ""));
	}

	@GetMapping("/orders")
	public ResponseEntity<ResponseObject> getAllOrders() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("OK", "Query all orders have successfully", ordersRepo.findAll()));
	}

	@PostMapping("/orders")
	public ResponseEntity<ResponseObject> addOrder(@RequestBody List<ProductOrder> productOrder) {
		Orders order = new Orders();
		for (ProductOrder po : productOrder) {
			po.setOrders(order);
		}
		order.setProductOrder(productOrder);

		order.setTotal(order.calTotal());

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseObject("OK", "Create order has successfully", ordersRepo.save(order)));
	}

	@GetMapping("/orders/{id}")
	public ResponseEntity<ResponseObject> getOrderById(@PathVariable int id) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("OK", "Query orders by have successfully", ordersRepo.findById(id)));
	}

	@PutMapping("/orders/{id}")
	public ResponseEntity<ResponseObject> updateOrder(@PathVariable int id, @RequestBody @Valid Orders newOrder) {
		Orders updateOrder = ordersRepo.findById(id).map(order -> {
			for (ProductOrder po : newOrder.getProductOrder()) {
				po.setOrders(order);
			}

			order.setProductOrder(newOrder.getProductOrder());
			order.setTotal(newOrder.calTotal());

			return ordersRepo.save(order);
		}).orElseGet(() -> {
			return null;
		});
		if (updateOrder == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("fail", "Could not found order", null));
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", "Update Product successfully", updateOrder));
	}

	@DeleteMapping("/orders/{id}")
	public ResponseEntity<ResponseObject> deleteOrder(@PathVariable int id) {
		boolean exists = ordersRepo.existsById(id);
		if (exists) {
			ordersRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete order successfully", ""));
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseObject("failed", "Cannot find product to delete", ""));
	}
}
