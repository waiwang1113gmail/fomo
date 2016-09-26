package com.fomo.entities;
public class ProductSpecification{
	private ProductColumnMetadata columnID;
	private String value;
	private Product productID;
	public void setColumnID(ProductColumnMetadata columnID){
		this.columnID=columnID;
	}
	public ProductColumnMetadata getColumnID(){
		 return this.columnID;
	}
	public void setValue(String value){
		this.value=value;
	}
	public String getValue(){
		 return this.value;
	}
	public void setProductID(Product productID){
		this.productID=productID;
	}
	public Product getProductID(){
		 return this.productID;
	}
}