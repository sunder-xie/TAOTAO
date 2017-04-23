package com.taotao.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertieService {
	@Value("${TAOTAO_ORDER_URL}")
	public String TAOTAO_ORDER_URL;
	@Value("${TAOTAO_SSO_URL}")
	public String TAOTAO_SSO_URL;
	@Value("${TAOTAO_CART_URL}")
	public String TAOTAO_CART_URL;
	@Value("${TAOTAO_MANAGE_URL}")
	private String TAOTAO_MANAGE_URL;
	@Value("${INDEX_AD1_URL}")
	private String INDEX_AD1_URL;
	@Value("${INDEX_AD2_URL}")
	private String INDEX_AD2_URL;
}
