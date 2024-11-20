package com.org.util;

import org.modelmapper.AbstractConverter;

import com.org.constant.PaymentTypeEnum;

public class PaymentTypeIdConverter extends AbstractConverter<Integer, String> {

	@Override
	protected String convert(Integer source) {

		return PaymentTypeEnum.getById(source).getName();
	}

}
