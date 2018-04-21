package com.github.i24x.springcloud.commons.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service("OrderService")
public class OrderServiceImpl implements IOrderService{
	
	public static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Override
	public void updateOrder() {
		logger.info("更新订单中");
	}

}
