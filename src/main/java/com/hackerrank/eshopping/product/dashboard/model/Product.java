package com.hackerrank.eshopping.product.dashboard.model;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@JsonPropertyOrder({ "id","name","category","retail_price","discounted_price","availability"})
public class Product{
	@Id 
    private Long id;
    private String name;
    private String category;
    @JsonProperty("retail_price")
    @Column(name = "retailprice")
    private Double retailPrice;
    @JsonProperty("discounted_price")
    @Column(name = "discountedprice")
    private Double discountedPrice;
    private Boolean availability;
    @JsonIgnore
    @Column(name = "discountpercentage")
    private Integer discountPercentage;

    public Product() {
    }

    public Product(Long id, String name, String category, Double retailPrice, Double discountedPrice, Boolean availability) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.retailPrice = retailPrice;
        this.discountedPrice = discountedPrice;
        this.availability = availability;
    }
    
    public void mergeProduct(ProductUpdate product) {
    	discountedPrice = Optional.ofNullable(product.getDiscountedPrice()).orElseGet(()->this.discountedPrice);
    	retailPrice = Optional.ofNullable(product.getRetailPrice()).orElseGet(()->this.retailPrice);
    	availability = Optional.ofNullable(product.getAvailability()).orElseGet(()->this.availability);
    }

    @PrePersist @PreUpdate
    private void CalculateDiscountPercentage() {
    	if(this.retailPrice <= 0 || this.discountedPrice == null) 
    		this.discountPercentage = 0;
    	else
    		this.discountPercentage = (int)Math.round((this.retailPrice - this.discountedPrice) / this.retailPrice *100);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

	public Integer getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Integer discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(Double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
}
