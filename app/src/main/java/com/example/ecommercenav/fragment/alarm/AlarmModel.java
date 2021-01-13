package com.example.ecommercenav.fragment.alarm;

public class AlarmModel {
    private String phone, mess, dated, time, uid, time_oder, price;

    public AlarmModel(String phone, String mess, String dated, String time, String uid, String time_oder, String price) {
        this.phone = phone;
        this.mess = mess;
        this.dated = dated;
        this.time = time;
        this.uid = uid;
        this.time_oder = time_oder;
        this.price = price;
    }

    @Override
    public String toString() {
        return "AlarmModel{" +
                "phone='" + phone + '\'' +
                ", mess='" + mess + '\'' +
                ", dated='" + dated + '\'' +
                ", time='" + time + '\'' +
                ", uid='" + uid + '\'' +
                ", time_oder='" + time_oder + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public AlarmModel() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getDated() {
        return dated;
    }

    public void setDated(String dated) {
        this.dated = dated;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime_oder() {
        return time_oder;
    }

    public void setTime_oder(String time_oder) {
        this.time_oder = time_oder;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
