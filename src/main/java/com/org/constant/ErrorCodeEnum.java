package com.org.constant;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

	INVALID_TXN_STATUS("20001" ," Invalid Request. Incorrect txnStatus received" ),
	TXN_STATUS_HANDLER_NOT_CONFIGURED("20002", "StatusHandler not configured, Try again later."),
	GENERIC_ERROR("20000","Unable to process"),
	DUPLICATE_TXN_REFERENCE("20003" , " Duplicate Txn is Entered");
	
	private final String errorCode;
	private final String errorMessage;
	
	ErrorCodeEnum(String errorCode , String errorMessage ){
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
}
