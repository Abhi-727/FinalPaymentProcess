package com.org.service.factory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.org.constant.ProviderEnum;
import com.org.service.interfaces.ProviderService;
import com.org.serviceImpl.Handler.CreateStatusHandler;
import com.org.serviceImpl.provider.Handler.StripeProviderHandler;


@Component
public class PaymentProviderFactory {
	
	
	@Autowired
	private ApplicationContext ctxt;

	public ProviderService paymentProcess(ProviderEnum providerenum) {
		
		
		switch(providerenum){
		case STRIPE:
				System.out.println("creating stripehandler :"+providerenum);
				StripeProviderHandler stripeProviderhandler= ctxt.getBean(StripeProviderHandler.class);
				
				System.out.println("got bean from Applicationcontext  | stripehandler : " + stripeProviderhandler);
				
				return stripeProviderhandler;
			
			
		default:
			System.out.println("unable to find handler ");
			return null;
		
		}
		
	
	}
}
