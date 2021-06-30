package com.restaurante.antojitoapp.Model;

public class Users {

    private String usuario, phone, password;

    public Users(){

    }

    public Users(String usuario, String phone, String password) {
        this.usuario = usuario;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return usuario;
    }

    public void setName(String name) {
        this.usuario = usuario;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
