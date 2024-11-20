package com.org.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import com.org.dto.TransactionDto;
import com.org.entity.TransactionEntity;
import com.org.util.PaymentMethodEnumConverter;
import com.org.util.PaymentMethodIdConverter;
import com.org.util.PaymentTypeEnumConverter;
import com.org.util.PaymentTypeIdConverter;
import com.org.util.ProviderEnumConverter;
import com.org.util.ProviderIdConverter;
import com.org.util.TxnStatusEnumConverter;
import com.org.util.TxnStatusIdConverter;

@Configuration
public class AppConfig {

	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	 ModelMapper getModelMapper() {
		ModelMapper modelmapper = new ModelMapper();
		
		Converter<String, Integer> paymentMethodEnumConverter = new PaymentMethodEnumConverter();
        Converter<String, Integer> providerEnumConverter = new ProviderEnumConverter();
        Converter<String, Integer> txnStatusEnumConverter = new TxnStatusEnumConverter();
        Converter<String, Integer> paymentTypeEnumConverter = new PaymentTypeEnumConverter();
        
        
        Converter<Integer, String> paymentMethodIdConverter = new PaymentMethodIdConverter();
        Converter<Integer, String> providerIdConverter = new ProviderIdConverter();
        Converter<Integer, String> txnStatusIdConverter = new TxnStatusIdConverter();
        Converter<Integer, String> paymentTypeIdConverter = new PaymentTypeIdConverter();
        // Define converters for TxnStatusEnum and PaymentTypeEnum if needed
        
        modelmapper.addMappings(new PropertyMap<TransactionDto, TransactionEntity>() {
            @Override
            protected void configure() {
                using(paymentMethodEnumConverter).map(source.getPaymentMethod(), destination.getPaymentMethodId());
                using(providerEnumConverter).map(source.getProvider(), destination.getProviderId());
                using(txnStatusEnumConverter).map(source.getTxnStatus(), destination.getTxnStatusId());
                using(paymentTypeEnumConverter).map(source.getPaymentType(), destination.getPaymentTypeId());
            }
        });
        
       
        
        modelmapper.addMappings(new PropertyMap<TransactionEntity, TransactionDto>() {
            @Override
            protected void configure() {
                using(paymentMethodIdConverter).map(source.getPaymentMethodId(), destination.getPaymentMethod());
                using(providerIdConverter).map(source.getProviderId(), destination.getProvider());
                using(txnStatusIdConverter).map(source.getTxnStatusId(), destination.getTxnStatus());
                using(paymentTypeIdConverter).map(source.getPaymentTypeId(), destination.getPaymentType());
            }
        });
		return modelmapper;
	}
	@Bean
	public ThreadPoolTaskExecutor taskExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25);
		executor.setThreadNamePrefix("Asnc-Task-");
		executor.initialize();
		return executor;
	}
	
}
