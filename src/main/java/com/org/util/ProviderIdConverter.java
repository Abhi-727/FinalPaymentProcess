package com.org.util;

import org.modelmapper.AbstractConverter;

import com.org.constant.ProviderEnum;

public class ProviderIdConverter extends AbstractConverter<Integer, String> {

	@Override
	protected String convert(Integer source) {
		// TODO Auto-generated method stub
		return ProviderEnum.getById(source).getName();
	}

}
