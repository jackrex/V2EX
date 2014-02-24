package info.jackrex.v2ex.entity;

import java.io.Serializable;

public class DetailEntity implements Serializable {

	private String avater;
	private String title;
	private String reply_count;
	public String getAvater() {
		return avater;
	}
	public void setAvater(String avater) {
		this.avater = avater;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReply_count() {
		return reply_count;
	}
	public void setReply_count(String reply_count) {
		this.reply_count = reply_count;
	}
	
	
	
}
