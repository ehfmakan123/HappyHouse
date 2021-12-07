package com.ssafy.vue.model;

public class LikesDto {
	
	private int likeid;
	private String userid;
	private String aptname;
	private String aptaddress;
	private double lat;
	private double lng;
	
	public int getLikeid() {
		return likeid;
	}
	public void setLikeid(int likeid) {
		this.likeid = likeid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAptname() {
		return aptname;
	}
	public void setAptname(String aptname) {
		this.aptname = aptname;
	}
	public String getAptaddress() {
		return aptaddress;
	}
	public void setAptaddress(String aptaddress) {
		this.aptaddress = aptaddress;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	@Override
	public String toString() {
		return "LikesDto [likeid=" + likeid + ", userid=" + userid + ", aptname=" + aptname + ", aptaddress="
				+ aptaddress + ", lat=" + lat + ", lng=" + lng + "]";
	}

}
