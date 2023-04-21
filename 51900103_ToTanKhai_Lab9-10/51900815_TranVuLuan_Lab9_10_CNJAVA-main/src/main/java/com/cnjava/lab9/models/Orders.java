package com.cnjava.lab9.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@Entity(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int total;
	
	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    @NotNull(message = "List product of order can not null")
	private List<ProductOrder> productOrder;
	
	@Transient
	public int calTotal() {
		int sum = 0;
		List<ProductOrder> pros = getProductOrder();
		for (ProductOrder productOrder : pros) {
			sum += productOrder.getProducts().getPrice() * productOrder.getQuantity();
		}
		return sum;
	}
}
