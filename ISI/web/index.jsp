<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.lang.System.*" %>
<%@ page import="com.compinstrumentos.client.ComparadorInstrumentos" %>

<html>

<head>
  <link rel="stylesheet" type="text/css" href="compara.css">
  <link rel="icon" type="image/png" href="https://www.escribircanciones.com.ar/images/teoriamusical/imagenesdenotasmusicales/corchea.png" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Comparador de precios</title>

</head>

<body background="https://victoranderssen.com/templates/1-nordica/images/pattern.svg">
<%
  ComparadorInstrumentos cmp = (ComparadorInstrumentos) request.getAttribute("nombre");

  request.setAttribute("img", cmp.getImage());
  request.setAttribute("name", cmp.getNombre());
  request.setAttribute("marca", cmp.getMarca());
  request.setAttribute("amazon", cmp.getPrecio_amazon());
  request.setAttribute("comentario", cmp.getComentario());
  request.setAttribute("thomann", cmp.getPrecio_thomann());
  request.setAttribute("guima", cmp.getPrecio_guima());
  request.setAttribute("urlA", cmp.getUrlAmazon());
  request.setAttribute("urlT", cmp.getUrlThomann());
  request.setAttribute("urlG", cmp.getUrlGuima());


  if(cmp != null && !cmp.equals(null)){
%>
<h1>Tu comprarador de precios</h1>
<br/>

<h3>El resultado de su búsqueda fue el siguiente:</h3>
<br/>

<img id="imag" src='<%=request.getAttribute("img")%>' alt="No se puede mostrar la imagen" onerror="this.src='http://www.fincasviladecans.com/img/no-imagen.jpg';" >

<section id="detalles">
  <p>
  <h3>Marca:</h3>
  <%=request.getAttribute("marca")%>
  <br>
  <h3>Modelo:</h3>
  <%=request.getAttribute("name")%>
  <br>
  <h3>Comentario:</h3>
  <%=request.getAttribute("comentario")%>
  </p>
</section>

<section id="prec_t">
  <a href="<%=request.getAttribute("urlT")%>">
    <h1>Thomann:</h1>
    <h3><%=request.getAttribute("thomann")%></h3>
  </a>
</section>

<section id="prec_g">
  <a href="<%=request.getAttribute("urlG")%>">
    <h1>Musical Guima:</h1>
    <h3><%=request.getAttribute("guima")%></h3>
  </a>
</section>
<section id="prec_a">
  <a href="<%=request.getAttribute("urlA")%>">
    <h1>Amazon:</h1>
    <h3><%=request.getAttribute("amazon")%></h3>
  </a>
</section>

<a href="<%=request.getAttribute("urlA")%>">
  <img id="amazon" src="http://g-ec2.images-amazon.com/images/G/01/social/api-share/amazon_logo_500500.png">
</a>
<a href="<%=request.getAttribute("urlT")%>">
  <img id="thomann" src="https://rankabrand.org/uploads/brand/16694/Thoman-logo.jpg">
</a>
<a href="<%=request.getAttribute("urlG")%>">
  <img id="guima" src="https://pbs.twimg.com/profile_images/2722126285/d196e2c4b10d91f2bc8773a0fa688f0d_400x400.jpeg">
</a>

<%
  }

%>

</body>

</html>