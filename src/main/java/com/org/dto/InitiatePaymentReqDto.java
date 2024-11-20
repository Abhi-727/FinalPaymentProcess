package com.org.dto;

import java.util.List;

import lombok.Data;

@Data
public class InitiatePaymentReqDto {

	private Integer id;
	
	private List<LineItemDto> lineitem;
	private String successUrl;
	private String cancelUrl;
	
}
