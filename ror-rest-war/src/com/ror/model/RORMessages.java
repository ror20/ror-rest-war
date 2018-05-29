package com.ror.model;

import java.util.List;

public class RORMessages {
	
	private String rorUserId;
	private List<MessageDetails> sent;
	private List<MessageDetails> received;

	public RORMessages() {
	
	}

	public RORMessages(String rorUserId, List<MessageDetails> sent, List<MessageDetails> received) {
		super();
		this.rorUserId = rorUserId;
		this.sent = sent;
		this.received = received;
	}

	public String getRorUserId() {
		return rorUserId;
	}

	public void setRorUserId(String rorUserId) {
		this.rorUserId = rorUserId;
	}

	public List<MessageDetails> getSent() {
		return sent;
	}

	public void setSent(List<MessageDetails> sent) {
		this.sent = sent;
	}

	public List<MessageDetails> getReceived() {
		return received;
	}

	public void setReceived(List<MessageDetails> received) {
		this.received = received;
	}

	@Override
	public String toString() {
		return "RORMessages [rorUserId=" + rorUserId + ", sent=" + sent + ", received=" + received + "]";
	}


	

	
}
