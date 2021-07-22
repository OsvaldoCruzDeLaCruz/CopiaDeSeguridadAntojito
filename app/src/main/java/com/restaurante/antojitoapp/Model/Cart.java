package com.restaurante.antojitoapp.Model;

public class Cart {

    private String pid, pname, price, amountProduct;


    public Cart(String pid, String pname, String price, String amountProduct) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.amountProduct = amountProduct;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmountProduct() {
        return amountProduct;
    }

    public void setAmountProduct(String amountProduct) {
        this.amountProduct = amountProduct;
    }
}
