package com.example.ecommercenav.Model;

public class CartItemModel {
    String id, pId, pName, pPrice, pCost, pQuantity;

    public CartItemModel() {
    }

    public CartItemModel(String id, String pId, String pName, String pPrice, String pCost, String pQuantity) {
        this.id = id;
        this.pId = pId;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pCost = pCost;
        this.pQuantity = pQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpCost() {
        return pCost;
    }

    public void setpCost(String pCost) {
        this.pCost = pCost;
    }

    public String getpQuantity() {
        return pQuantity;
    }

    public void setpQuantity(String pQuantity) {
        this.pQuantity = pQuantity;
    }
}
