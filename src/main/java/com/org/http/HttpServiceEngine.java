package com.org.http;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpServiceEngine {

	private RestTemplate template;

	public HttpServiceEngine(RestTemplate template) {
		this.template = template;
		System.out.println("HttpServiceEngine.HttpServiceEngine()" + template);
	}

	
	
	public ResponseEntity<String> makeHttpRequest(HttpRequest httpReq ){
		try {
			HttpHeaders httpHeaders = httpReq.getHttpHeaders();
			if(httpHeaders !=null) {
				 httpHeaders = new HttpHeaders();
			}
			HttpEntity<Object> entity = new HttpEntity<>(httpReq.getRequest(),httpHeaders);
			
			ResponseEntity<String> httpresonse = template.exchange(httpReq.getUrl(), httpReq.getMethod(), entity, String.class);
			System.out.println();
			System.out.println("entity : " + entity);
			System.out.println();
			System.out.println("got httpresponse : "+httpresonse);
			
			return httpresonse;
			
		}catch(HttpClientErrorException | HttpServerErrorException ex){
			System.out.println("Got Client/server error while making http call");	
			ex.printStackTrace();
			return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
				
		}catch(Exception ex) {
			System.out.println("exception maling http call :" + ex);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			
		}
		
		}
}


