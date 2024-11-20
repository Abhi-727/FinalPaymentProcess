package com.org.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.org.pojo.stripe.StripeEvent;
import com.org.service.stripeevent.IStripeNotificationService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.net.Webhook;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/stripe")
public class StripeNotificationController {

	@Autowired
	private HttpServletRequest httpRequest;

	@Autowired
	IStripeNotificationService stripeService;

	@Autowired
	private Gson gson;

	@Value("${stripe.notification.signin-secret}")
	private String endpointsecret;

	@PostMapping("/getnotification")
	public ResponseEntity<String> processNotification() {
		
		System.out.println();

		System.out.println("StripeNotificationController.stripeNotification() | httpreq : " + httpRequest);
		String reqAsString;

		try {
			reqAsString = StreamUtils.copyToString(httpRequest.getInputStream(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();

			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
		System.out.println("Got req as string : " + reqAsString);

		String sigHeader = httpRequest.getHeader("Stripe-Signature");
		System.out.println("incoming sigheader : " + sigHeader);

		// String endpointSecret =
		// "whsec_f5fdab7472f45da78cdb34b96ac72a1a019a6781327a84d6e478501bd7f0b959";

		try {
			Webhook.constructEvent(reqAsString, sigHeader, endpointsecret);
			System.out.println("HMACSHA256 SIGNATURE VERIFIED!");
		} catch (JsonSyntaxException e) {

			System.out.println(e);

			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} catch (SignatureVerificationException e) {
			System.out.println(e);
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		System.out.println();
		System.out.println("Signature VALID, continue further processing of event");

		try {
			StripeEvent convertEvent = gson.fromJson(reqAsString, StripeEvent.class);
			System.out.println("Incoming Event : " + convertEvent);
			
			stripeService.processNotification(convertEvent);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {

			// handle the exception
			return ResponseEntity.status(500).build();
		}

		// return new ResponseEntity<String>("got notification", HttpStatus.OK);

	}

}