package com.restaurante.antojitoapp;

public class ListElement {
    public String imagen;
    public String name;
    public String type;
    public String size;

    public ListElement(String imagen, String name, String type, String size) {
        this.imagen = imagen;
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
