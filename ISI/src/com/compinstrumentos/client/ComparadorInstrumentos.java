package com.compinstrumentos.client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by Francis on 04/05/2017.
 */


@XmlRootElement(name="bookInfo")
@XmlType(propOrder={
        "nombre",
        "marca",
        "comentario",
        "image"
})
public class ComparadorInstrumentos implements Serializable{

    private String nombre = "";
    private String marca = "";
    private String comentario = "";
    private String precio_amazon = "";
    private String precio_thomann = "";
    private String precio_guima = "";
    private String image = "";
    private String urlAmazon = "";
    private String urlThomann = "";
    private String urlGuima	= "";
    private String ASIN="";

    //Constructor sin parámetros

    public ComparadorInstrumentos(){

    }

    //Constructor con parámetros:
    public ComparadorInstrumentos(String nombre_p, String marca_p, String comentario_p, String precio_t, String precio_g,String precio_a, String image_p, String urlGu, String urlTh, String urlAm, String AS){
        nombre=nombre_p;
        marca = marca_p;
        comentario=comentario_p;
        precio_amazon=precio_a;
        precio_thomann= precio_t;
        precio_guima = precio_g;
        image=image_p;
        urlAmazon = urlAm;
        urlThomann = urlTh;
        urlGuima = urlGu;
        ASIN=AS;
    }

    //Metodos de acceso a datos:
    @XmlElement(required=true)
    public String getNombre(){return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement(required=true)
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @XmlElement(required=true)
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @XmlElement(required=true)
    public String getPrecio_amazon() {
        return precio_amazon;
    }

    public void setPrecio_amazon(String precio_amazon) {
        this.precio_amazon = precio_amazon;
    }

    @XmlElement(required=true)
    public String getPrecio_thomann() {
        return precio_thomann;
    }

    public void setPrecio_thomann(String precio_thomann) {
        this.precio_thomann = precio_thomann;
    }

    @XmlElement(required=true)
    public String getPrecio_guima() {
        return precio_guima;
    }


    //Metodos de escritura de datos
    public void setPrecio_guima(String precio_guima) {
        this.precio_guima = precio_guima;
    }

    @XmlElement(required=true)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @XmlElement(required=true)
    public String getUrlAmazon() {
        return urlAmazon;
    }

    public void setUrlAmazon(String urlAmazon) {
        this.urlAmazon = urlAmazon;
    }

    @XmlElement(required=true)
    public String getUrlThomann() {
        return urlThomann;
    }

    public void setUrlThomann(String urlThomann) {
        this.urlThomann = urlThomann;
    }

    @XmlElement(required=true)
    public String getUrlGuima() {
        return urlGuima;
    }

    public void setUrlGuima(String urlGuima) {
        this.urlGuima = urlGuima;
    }

    @XmlElement(required=true)
    public String getASIN() {
        return ASIN;
    }

    @XmlElement(required=true)
    public void setASIN(String ASIN) {
        this.ASIN = ASIN;
    }
}
