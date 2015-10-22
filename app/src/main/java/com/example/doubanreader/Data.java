package com.example.doubanreader;


public class Data {
	private String access_token;
	private String expires_in;
	private String refresh_token;
	private String douban_user_id;
	public Data(String access_token, String expires_in, String refresh_token, String douban_user_id){
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.refresh_token = refresh_token;
		this.douban_user_id = douban_user_id;
	}
	
	public void putAccessToken(String access_token){this.access_token = access_token;}
	public void putExpiresIn(String expires_in){this.expires_in = expires_in;}
	public void putRefreshToken(String refresh_token){this.refresh_token = refresh_token;}
	public void putDoubanUserId(String douban_user_id){this.douban_user_id = douban_user_id;}
	
	public String getAccessToken(){return access_token;}
	public String getExpiresIn(){return expires_in;}
	public String getRefreshToken(){return refresh_token;}
	public String getDoubanUserId(){return douban_user_id;}

}
