package com.org.util;

import org.modelmapper.AbstractConverter;

import com.org.constant.ProviderEnum;

public class ProviderEnumConverter  extends AbstractConverter<String, Integer>{

	@Override
	protected Integer convert(String source) {
		return ProviderEnum.getNameById(source).getId();
	}
	

}
