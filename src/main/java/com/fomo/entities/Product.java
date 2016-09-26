package com.fomo.entities;
public class Product{
	private String description;
	private int iD;
	private String name;
	public void setDescription(String description){
		this.description=description;
	}
	public String getDescription(){
		 return this.description;
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