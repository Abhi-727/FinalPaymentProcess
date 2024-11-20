package com.org.util;

import org.modelmapper.AbstractConverter;

import com.org.constant.TransactionStausEnum;

public class TxnStatusEnumConverter extends AbstractConverter<String, Integer> {

	@Override
	protected Integer convert(String source) {
		return TransactionStausEnum.getByName(source).getId();
	}
	

}
