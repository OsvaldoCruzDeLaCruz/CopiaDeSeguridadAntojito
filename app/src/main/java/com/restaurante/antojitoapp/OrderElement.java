package com.restaurante.antojitoapp;

public class OrderElement {
    public String idOrder, total, telefono;

    public OrderElement(String idOrder, String total, String telefono) {
        this.idOrder = idOrder;
        this.total = total;
        this.telefono = telefono;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
