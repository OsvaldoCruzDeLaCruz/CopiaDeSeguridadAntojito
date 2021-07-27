package com.restaurante.antojitoapp.Model;

import java.io.Serializable;

public class CartElement  implements Serializable {
    public String idProducto,image, nombreProducto, descripcion, cantidadProducto, precio, categoria;

    public CartElement(String id, String image, String nombreProducto,String descripcion, String cantidadProducto, String precio, String categoria) {
        this.idProducto = id;
        this.image = image;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.cantidadProducto = cantidadProducto;
        this.precio = precio;
        this.categoria = categoria;

    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(String cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
