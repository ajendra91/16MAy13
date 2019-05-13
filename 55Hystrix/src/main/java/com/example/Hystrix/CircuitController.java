package com.example.Hystrix;

import java.net.URI;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@EnableCircuitBreaker
//@EnableHystrix
@EnableHystrixDashboard
public class CircuitController {
	
	@Autowired
	public RestTemplate rt;
	
	@RequestMapping("/cr")
	@HystrixCommand(fallbackMethod="myfallBack", commandKey = "greeting",
		commandProperties = {
			@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value = "2000"),
			@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "5")
		}
	)
    public String greeting() {
		URI uri = URI.create("http://localhost:8090/app");
        return this.rt.getForObject(uri, String.class);
    }

	public String myfallBack() {
		return "myfallBack is called....";
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
}
