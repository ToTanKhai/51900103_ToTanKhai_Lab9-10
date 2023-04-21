package com.cnjava.lab9.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@Entity(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 255)
	@NotEmpty(message = "Name field cannot be left blank")
	@NotNull(message = "Name field cannot be left null")
	private String name;
	
	@NotNull(message = "Name field cannot be left null")
	private int price;
	
	@Column(length = 255)
	@NotEmpty(message = "Illustration field cannot be left blank")
	@NotNull(message = "Name field cannot be left null")
	private String illustration;
	
	@Column(length = 255)
	@NotEmpty(message = "Description field cannot be left blank")
	@NotNull(message = "Name field cannot be left null")
	private String description;
	
	@JsonIgnore
	@JsonManagedReference
	@OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private List<ProductOrder> productOrder;
}
