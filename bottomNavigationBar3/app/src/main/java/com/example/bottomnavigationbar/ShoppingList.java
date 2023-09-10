// done by Yap Xiang Ying Samantha Lok
package com.example.bottomnavigationbar;

public class ShoppingList {

    String id,title,cateogry,locations,expirydate,
            quantity,weight,purchasedate,brand,shop,price,note;

    public ShoppingList() {
    }


    public ShoppingList(String id, String title, String cateogry, String locations, String expirydate, String quantity, String weight, String purchasedate, String brand, String shop, String price, String note) {
        this.id=id;
        this.title = title;
        this.cateogry = cateogry;
        this.locations = locations;
        this.expirydate = expirydate;

        this.quantity = quantity;
        this.weight = weight;
        this.purchasedate = purchasedate;
        this.brand = brand;
        this.shop = shop;
        this.price = price;
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCateogry() {
        return cateogry;
    }

    public void setCateogry(String cateogry) {
        this.cateogry = cateogry;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(String purchasedate) {
        this.purchasedate = purchasedate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
