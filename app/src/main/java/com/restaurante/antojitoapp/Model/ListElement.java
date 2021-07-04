package com.restaurante.antojitoapp.Model;

import java.io.Serializable;

public class ListElement implements Serializable {

    public String id;
    public String imagen;
    public String name;
    public String type;
    public String size;

    public ListElement(String id, String imagen, String name, String type, String size) {
        this.id = id;
        this.imagen = imagen;
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
