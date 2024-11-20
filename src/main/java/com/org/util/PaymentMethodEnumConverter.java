package com.org.util;

import org.modelmapper.AbstractConverter;

import com.org.constant.PaymentMethodEnum;

public class PaymentMethodEnumConverter  extends AbstractConverter<String, Integer>{

	@Override
	protected Integer convert(String source) {
		return PaymentMethodEnum.getByName(source).getId();
	}

}
