package model;

public class OrderForAdmin {
    String item, number, price, userId, discount, paid;

    public OrderForAdmin() {
    }

    public OrderForAdmin(String item, String number, String price, String userId, String discount, String paid) {
        this.item = item;
        this.number = number;
        this.price = price;
        this.userId = userId;
        this.discount = discount;
        this.paid = paid;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }
}
