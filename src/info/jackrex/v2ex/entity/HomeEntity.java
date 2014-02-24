package info.jackrex.v2ex.entity;

import java.io.Serializable;

import android.net.UrlQuerySanitizer.ParameterValuePair;
import android.os.Parcel;
import android.os.Parcelable;

public class HomeEntity implements Serializable {

	private String avater;
	private String title;
	private String smallfade;
	private String count;
	private String jumpUrl;
	
	
	
	
	public String getJumpUrl() {
		return jumpUrl;
	}
	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}
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
	public String getSmallfade() {
		return smallfade;
	}
	public void setSmallfade(String smallfade) {
		this.smallfade = smallfade;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}

	
	
	
}
