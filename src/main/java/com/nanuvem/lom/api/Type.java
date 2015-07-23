package com.nanuvem.lom.api;

public enum Type {

	TEXT, LONGTEXT, PASSWORD, INTEGER;
	
	public static Type getType(String type){
		for(int i = 0; i < values().length; i++){
			if(type != null && values()[i].toString().equals(type)){
				return values()[i]; 
			}
		}
		return null;
	}

}
