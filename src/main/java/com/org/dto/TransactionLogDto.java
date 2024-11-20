package com.org.dto;

import lombok.Data;

@Data
public class TransactionLogDto {

	private Integer transactionId;
	private String txnFromStatus;
	private String txnToStatus;
}
