package com.example.ecommercenav.Model;

public class ProfileModel {


    private String profileID, profileName, profilePhone, profileAddress, profileEmail, profileLoaitaikhoan, profilePass, profileRePass, uid, profileIcon;

    public ProfileModel() {
    }

    @Override
    public String toString() {
        return "ProfileModel{" +
                "profileID='" + profileID + '\'' +
                ", profileName='" + profileName + '\'' +
                ", profilePhone='" + profilePhone + '\'' +
                ", profileAddress='" + profileAddress + '\'' +
                ", profileEmail='" + profileEmail + '\'' +
                ", profileLoaitaikhoan='" + profileLoaitaikhoan + '\'' +
                ", profilePass='" + profilePass + '\'' +
                ", profileRePass='" + profileRePass + '\'' +
                ", uid='" + uid + '\'' +
                ", profileIcon='" + profileIcon + '\'' +
                '}';
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfilePhone() {
        return profilePhone;
    }

    public void setProfilePhone(String profilePhone) {
        this.profilePhone = profilePhone;
    }

    public String getProfileAddress() {
        return profileAddress;
    }

    public void setProfileAddress(String profileAddress) {
        this.profileAddress = profileAddress;
    }

    public String getProfileEmail() {
        return profileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        this.profileEmail = profileEmail;
    }

    public String getProfileLoaitaikhoan() {
        return profileLoaitaikhoan;
    }

    public void setProfileLoaitaikhoan(String profileLoaitaikhoan) {
        this.profileLoaitaikhoan = profileLoaitaikhoan;
    }

    public String getProfilePass() {
        return profilePass;
    }

    public void setProfilePass(String profilePass) {
        this.profilePass = profilePass;
    }

    public String getProfileRePass() {
        return profileRePass;
    }

    public void setProfileRePass(String profileRePass) {
        this.profileRePass = profileRePass;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(String profileIcon) {
        this.profileIcon = profileIcon;
    }

    public ProfileModel(String profileID, String profileName, String profilePhone, String profileAddress, String profileEmail, String profileLoaitaikhoan, String profilePass, String profileRePass, String uid, String profileIcon) {
        this.profileID = profileID;
        this.profileName = profileName;
        this.profilePhone = profilePhone;
        this.profileAddress = profileAddress;
        this.profileEmail = profileEmail;
        this.profileLoaitaikhoan = profileLoaitaikhoan;
        this.profilePass = profilePass;
        this.profileRePass = profileRePass;
        this.uid = uid;
        this.profileIcon = profileIcon;
    }
}
