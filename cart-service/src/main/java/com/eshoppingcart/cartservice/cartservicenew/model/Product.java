package com.eshoppingcart.cartservice.cartservicenew.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="product_list")
public class Product {
	
	@Transient
	public static final String SEQUENCE_NAME="users_sequence";
	
	
	@Id
	@NotNull(message = "Product ID is required")
	private int productId;
	
    private String productType;
    private String productName;
    
    @NotBlank(message = "Category is required")
    private String category;
//    private Map<Integer, Double> rating;
//    private Map<Integer, String> review;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private double price;
    
    @NotBlank(message = "Description is required")
    private String description;
  //  private Map<String, String> specification;
    
    private Set<ImageModel>productImages;
    
    
    //default constructor
    public Product() {
    }

    //parameterized constructor
	public Product(@NotNull(message = "Product ID is required") int productId, String productType, String productName,
			@NotBlank(message = "Category is required") String category, Map<Integer, Double> rating,
			Map<Integer, String> review,
			@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero") double price,
			@NotBlank(message = "Description is required") String description, Map<String, String> specification,String id) {
		super();
		this.productId = productId;
		this.productType = productType;
		this.productName = productName;
		this.category = category;
	//	this.rating = rating;
	//	this.review = review;
		this.price = price;
		this.description = description;
	//	this.specification = specification;
		
	}

	//getters and setters
	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


//	public Map<Integer, Double> getRating() {
//		return rating;
//	}
//
//
//	public void setRating(Map<Integer, Double> rating) {
//		this.rating = rating;
//	}


//	public Map<Integer, String> getReview() {
//		return review;
//	}
//
//
//	public void setReview(Map<Integer, String> review) {
//		this.review = review;
//	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	


//	public Map<String, String> getSpecification() {
//		return specification;
//	}
//
//
//	public void setSpecification(Map<String, String> specification) {
//		this.specification = specification;
//	}
//	
	


	

	public Set<ImageModel> getProductImages() {
		return productImages;
	}

	public void setProductImages(Set<ImageModel> productImages) {
		this.productImages = productImages;
	}

	//toString method
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productType=" + productType + ", productName=" + productName
				+ ", category=" + category + ", price=" + price + ", description=" + description + "]";
	}

	//hashcode
	@Override
	public int hashCode() {
		return Objects.hash(category, description, price, productId, productName, productType);
	}

	

	//equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(category, other.category) && Objects.equals(description, other.description)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& productId == other.productId && Objects.equals(productName, other.productName)
				&& Objects.equals(productType, other.productType);
	}
        

}
