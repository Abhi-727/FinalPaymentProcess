package com.org.stripeprovider;

import java.util.List;

import lombok.Data;

@Data
public class CreatePaymentReq {

	private String txnRef;
	
	private List<LineItem> lineitem;
	private String successUrl;
	private String cancelUrl;
}
