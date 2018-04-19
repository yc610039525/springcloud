package com.github.caoyeung.inout;

public class HttpResult {

	private Object data;
	private int resCode = 0;
	private String resMsg = "^_^";

	public HttpResult() {
		super();
	}

	public HttpResult(int resCode, String resMsg) {
		this.resCode = resCode;
		this.resMsg = resMsg;
	}

	public HttpResult(HttpResultEnum httpResult) {
		setResCode(httpResult.getStatus());
		setResMsg(httpResult.getMsg());
	}

	public HttpResult(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getResCode() {
		return resCode;
	}

	public void setResCode(int resCode) {
		this.resCode = resCode;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public enum HttpResultEnum {
		
		SERVER_INTERNAL_ERROR(10000, "系统内部错误"),
		SQL_ERROR_OR_NULL(10001, "数据库执行异常"),
		SQL_ERROR_RECORD_NOT_EXIST(10002, "记录在数据库不存在");
		private int status;
		private String msg;

		HttpResultEnum(int status, String msg) {
			this.status = status;
			this.msg = msg;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public static HttpResultEnum getHttpResult(int status) {
			for (HttpResultEnum s : HttpResultEnum.values()) {
				if (s.getStatus() == status)
					return s;
			}
			throw new IllegalArgumentException(String.format(
					"Unknown HttpResult Type %d", status));
		}
	}

	public void setHttpResult(HttpResultEnum httpResult) {
		setResCode(httpResult.getStatus());
		setResMsg(httpResult.getMsg());
	}
}
