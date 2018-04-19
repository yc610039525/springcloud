package com.github.caoyeung.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.codingapi.tx.config.service.TxManagerTxUrlService;

public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {
	@Value("${tm.manager.url}")
	private String url;

	@Override
	public String getTxUrl() {
		System.out.println("load tm.manager.url ");
		return url;
	}
}
