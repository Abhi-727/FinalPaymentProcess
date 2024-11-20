package com.org.pojo;

import java.util.List;

import lombok.Data;

@Data
public class InitiatePaymentReq {

	private List<LineItem> lineitem;
	private String successUrl;
	private String cancelUrl;
}
