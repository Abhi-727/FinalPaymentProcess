package com.org.constant;

import lombok.Getter;

public enum ProviderEnum {

	STRIPE(1, "STRIPE");

	@Getter
	private final int id;
	@Getter
	private final String name;

	private ProviderEnum(int id, String name) {
		this.id = id;
		this.name = name;

	}

	// find byid
	public static ProviderEnum getById(int id) {
		for (ProviderEnum method : ProviderEnum.values()) {

			if (method.getId() == id) {
				return method;
			}
		}
		System.out.println("Transaction id not found ");
		return null;
	}

	public static ProviderEnum getNameById(String name) {

		for (ProviderEnum mtdname : ProviderEnum.values()) {
			if (mtdname.getName().equalsIgnoreCase(name)) {
				return mtdname;
			}
		}
		System.out.println("Illegal payment method : " + name);
		return null;
	}
}
