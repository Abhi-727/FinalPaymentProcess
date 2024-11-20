package com.org.util;

import org.modelmapper.AbstractConverter;

import com.org.constant.PaymentMethodEnum;

public class PaymentMethodIdConverter  extends AbstractConverter<Integer, String>{


	@Override
	protected String convert(Integer source) {
		return PaymentMethodEnum.getById(source).getName();
	}

}
