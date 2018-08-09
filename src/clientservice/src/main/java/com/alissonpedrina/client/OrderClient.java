package com.alissonpedrina.client;

import com.alissonpedrina.model.OrderTO;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface OrderClient {
	
	@RequestLine("GET /v1/config/partners/{partnerSlug}/feeds/{feedSlug}")
	@Headers("Content-Type: application/json")
	OrderTO fetchConfig(@Param("partnerSlug") String partnerSlug, @Param("feedSlug") String feedSlug);

}
