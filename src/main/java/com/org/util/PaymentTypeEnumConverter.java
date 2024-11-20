package com.org.util;

import org.modelmapper.AbstractConverter;

import com.org.constant.PaymentTypeEnum;

public class PaymentTypeEnumConverter extends AbstractConverter<String, Integer> {

	@Override
	protected Integer convert(String source) {
		return PaymentTypeEnum.getByName(source).getId();
	}
	

}
