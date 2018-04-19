package com.github.caoyeung.inout;

import java.io.Serializable;

class Content implements Serializable {
	private static final long serialVersionUID = -2976351039316552360L;
	private String objectData;
	public String getObjectData() {
		return objectData;
	}
	public void setObjectData(String objectData) {
		this.objectData = objectData;
	}

	
}