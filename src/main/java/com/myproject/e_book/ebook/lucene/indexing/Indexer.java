package com.myproject.e_book.ebook.entity;

public class UserTokenState {
	
	private String access_token;
	private int userId;
	private String userAutority;
    public UserTokenState() {
        this.access_token = null;
    }
    
    public UserTokenState(String access_token, int userId,String userAutority) {
		super();
		this.access_token = access_token;
		this.userId = userId;
		this.userAutority = userAutority;
	}

	public UserTokenState(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserAutority() {
		return userAutority;
	}

	public void setUserAutority(String userAutority) {
		this.userAutority = userAutority;
	}
    	

}
