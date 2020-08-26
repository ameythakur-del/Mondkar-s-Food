package model;

public class Order {
    public String userName, item, quantity, delivery;

    public Order() {
    }

    public Order(String userName, String item, String quantity, String delivery) {
        this.userName = userName;
        this.item = item;
        this.quantity = quantity;
        this.delivery = delivery;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
