package com.github.caoyeung.model;

import com.alibaba.fastjson.JSON;

public class Result {
	private int code;
	private String message;
	private Object data;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private String desc;

	public Result setCode(int code) {
		this.code = code;
		return this;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Result setMessage(String message) {
		this.message = message;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Result setData(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
