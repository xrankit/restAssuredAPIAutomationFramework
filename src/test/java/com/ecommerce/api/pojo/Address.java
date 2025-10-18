package com.ecommerce.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String city;
	private String street;
	private int number;
	private String zipcode;
	private Geolocation geolocation;
}
