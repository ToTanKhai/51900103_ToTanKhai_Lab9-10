package com.cnjava.lab9.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@Entity(name="productorder")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Products products;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "orderId")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
	private Orders orders;
	
	private int quantity;
}
