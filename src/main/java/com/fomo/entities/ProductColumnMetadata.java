package com.fomo.entities;
public class ProductColumnMetadata{
	private int order;
	private String type;
	private String description;
	private String displayName;
	private int iD;
	private String name;
	public void setOrder(int order){
		this.order=order;
	}
	public int getOrder(){
		 return this.order;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		 return this.type;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public String getDescription(){
		 return this.description;
	}
	public void setDisplayName(String displayName){
		this.displayName=displayName;
	}
	public String getDisplayName(){
		 return this.displayName;
	}
	public void setID(int iD){
		this.iD=iD;
	}
	public int getID(){
		 return this.iD;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		 return this.name;
	}
}