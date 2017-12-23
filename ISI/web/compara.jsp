<%--
  Created by IntelliJ IDEA.
  User: Francisco
  Date: 04/05/2017
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="com.compinstrumentos.client.ComparadorInstrumentos" %>
<%@ page import="com.compinstrumentos.client.Comparador" %>


<html>

<head>
    <link rel="icon" type="image/png" href="http://directorio.ugr.es/img/favicon.ico" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Comparador de precios</title>

</head>

<body>
<%
        Comparador c = new Comparador();
        c.doGet(request , response);

    ComparadorInstrumentos cmp = (ComparadorInstrumentos) request.getAttribute("nombre");

    if(cmp != null && !cmp.equals(null)){
%>
<h1>Tu comprarador de precios</h1>
<br/>

<h3>El resultado de su búsqueda fue el siguiente:</h3>
<br/>

<h3>Imagen</h3>
<img src="<%cmp.getImage();%>" alt="No se puede mostrar la imagen" onerror="this.src='http://www.fincasviladecans.com/img/no-imagen.jpg';"  height="600" width="400" style="float:left; margin:10px;" >
<form>
    <table>
        <tr>
            <td><input type="text" value=<% cmp.getNombre(); %> readonly="readonly"></td>
        </tr>
        <tr>
            <td><input type="text" value="Marca: <%cmp.getMarca();%>" readonly="readonly"></td>
        </tr>
        <tr>
            <td><input type="text" value="Comentario: <%cmp.getComentario();%>" readonly="readonly" id="comentario"></td>
        </tr>
        <tr>
            <td><input type="text" value="Precio Thomann: <%cmp.getPrecio_thomann();%>" readonly="readonly" id="precioThomann"></td>
            <td><a href="https://www.amazon.es/<%cmp.getUrlThomann();%>" target="_blank">Comprar en Thomann</a> </td>
        </tr>
        <tr>
            <td><input type="text" value="Precio Musical Guima: <%cmp.getPrecio_guima();%>" readonly="readonly" id="precioGuima"></td>
            <td><a href="http://musicalguima.com/<%cmp.getUrlGuima();%>" target="_blank">Comprar en Musical Guima</a> </td>
        </tr>
        <tr>
            <td><input type="text" value="Precio Amazon: <%cmp.getPrecio_amazon();%>" readonly="readonly" id="precioAmazon"></td>
            <td><a href="<%cmp.getUrlAmazon();%>" target="_blank">Comprar en Amazon</a> </td>
        </tr>

    </table>
</form>

<%
    }
%>

</body>

</html>
