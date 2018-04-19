package com.github.caoyeung.inout;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseRequest implements Serializable {

	private static final long serialVersionUID = -992717648810995148L;
	private String version = "1.0.0";
	private Map<String,String> reqMap = new HashMap<String, String>();
	public Map<String, String> getReqMap() {
		return reqMap;
	}
	public void setReqMap(Map<String, String> reqMap) {
		this.reqMap = reqMap;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
