package com.ror.vo;

public class RORResponseVO {

	private String statusCode;
	private String statusMessage;
	
	public RORResponseVO() {
	}

	public RORResponseVO(String statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public String toString() {
		return "RORResponseVO [statusCode=" + statusCode + ", statusMessage=" + statusMessage + "]";
	}

}
