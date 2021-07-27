package com.restaurante.antojitoapp;

public class Order {
    private String idOrder, size, numberUser;

    public Order(String idOrder, String size, String numberUser) {
        this.idOrder = idOrder;
        this.size = size;
        this.numberUser = numberUser;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNumberUser() {
        return numberUser;
    }

    public void setNumberUser(String numberUser) {
        this.numberUser = numberUser;
    }
}
