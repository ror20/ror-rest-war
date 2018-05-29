package com.ror.model;

public class MessageDetails {

	private String fromUserId;
	private String toUserId;
	private String message;
	private String messageSentTime;

	public MessageDetails() {

	}

	public MessageDetails(String fromUserId, String toUserId, String message, String messageSentTime) {
		super();
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.message = message;
		this.messageSentTime = messageSentTime;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageSentTime() {
		return messageSentTime;
	}

	public void setMessageSentTime(String messageSentTime) {
		this.messageSentTime = messageSentTime;
	}

	@Override
	public String toString() {
		return "MessageSent [fromUserId=" + fromUserId + ", toUserId=" + toUserId + ", message=" + message
				+ ", messageSentTime=" + messageSentTime + "]";
	}

}
