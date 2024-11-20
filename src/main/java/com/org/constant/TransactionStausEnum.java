package com.org.constant;

import lombok.Getter;

public enum TransactionStausEnum {

	    CREATED(1, "Created"),
	    INITIATED(2, "Initiated"),
	    PENDING(3, "Pending"),
	    SUCCESS(4, "Success"),
	    FAIL(5, "Fail");

		@Getter
	    private final int id;
		@Getter
	    private final String name;

	    // Constructor
	    TransactionStausEnum(int id, String name) {
	        this.id = id;
	        this.name = name;
	    }


	    // Method to get enum by id
	    public static TransactionStausEnum getById(int id) {
	        for (TransactionStausEnum status : values()) {
	            if (status.getId() == id) {
	                return status;
	            }
	        }
	        System.out.println("No Transaciton found for id : "+id);
	        return null;
	    }

	    // Method to get enum by name
	    public static TransactionStausEnum getByName(String name) {
	        for (TransactionStausEnum status : values()) {
	            if (status.getName().equalsIgnoreCase(name)) {
	                return status;
	            }
	        }
	        System.out.println("Invalid TransactionEnum name : " + name);
	       return null;
	    }
	}

	

