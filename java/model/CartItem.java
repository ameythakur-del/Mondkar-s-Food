package model;

public class CartItem {
    private String item;
    private String taste;
    private String imageUrl;
    private String price;
    private String per;
    private String delivery;
    private String category;
    private String number;
    private String userId;
    private String time;
    private String original;
    private String discount;
    private String paid;

    public CartItem() {
    }

    public CartItem(String item, String taste, String imageUrl, String price, String per, String delivery, String category, String number, String userId, String time, String original, String discount, String paid) {
        this.item = item;
        this.taste = taste;
        this.imageUrl = imageUrl;
        this.price = price;
        this.per = per;
        this.delivery = delivery;
        this.category = category;
        this.number = number;
        this.userId = userId;
        this.time = time;
        this.original = original;
        this.discount = discount;
        this.paid = paid;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
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
