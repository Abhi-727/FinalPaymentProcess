package com.org.entity;

import lombok.Data;

@Data
public class TransactionLogEntity {
	private Integer transactionId;
	private String txnFromStatus;
	private String txnToStatus;
	
	
}
