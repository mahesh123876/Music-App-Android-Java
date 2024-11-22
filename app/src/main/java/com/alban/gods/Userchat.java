package com.alban.gods;

public class Userchat {
    public   String usernamechat;
    public   String emailchat;
    public   String profilePicturechat;

    public   String profileurl;
    public  String phonenumber;


    public Userchat() {

    }

    public Userchat(String usernamechat, String emailchat, String profilePicturechat, String profileurl, String phonenumber) {
        this.usernamechat = usernamechat;
        this.emailchat = emailchat;
        this.profilePicturechat = profilePicturechat;
        this.profileurl = profileurl;
        this.phonenumber = phonenumber;
    }

    public String getUsernamechat() {
        return usernamechat;
    }

    public void setUsernamechat(String usernamechat) {
        this.usernamechat = usernamechat;
    }

    public String getEmailchat() {
        return emailchat;
    }

    public void setEmailchat(String emailchat) {
        this.emailchat = emailchat;
    }

    public String getProfilePicturechat() {
        return profilePicturechat;
    }

    public void setProfilePicturechat(String profilePicturechat) {
        this.profilePicturechat = profilePicturechat;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
