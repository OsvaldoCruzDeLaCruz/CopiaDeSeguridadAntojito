package com.restaurante.antojitoapp.Model;

public class Users {

    private String usuario, phone, password, admin;

    public Users(){

    }

    public Users(String usuario, String phone, String password, String admin) {
        this.usuario = usuario;
        this.phone = phone;
        this.password = password;
        this.admin = admin;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
