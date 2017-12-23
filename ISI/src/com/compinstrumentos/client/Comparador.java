package com.compinstrumentos.client;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.HashMap;
import java.lang.String;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.w3c.dom.*;
import org.w3c.dom.Document;

import java.net.URLConnection;


/**
 * Created by sergi on 08/05/2017.
 */

public class Comparador extends HttpServlet {
    private static final String AWS_ACCESS_KEY_ID = "AKIAJX7SMG4IOSGEE45Q";
    private static final String AWS_SECRET_KEY = "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";
    //private static final String ENDPOINT = "ecs.amazonaws.com";
    private static final String ENDPOINTSP="webservices.amazon.es";
    private static final String AMAZON_ASSOCIATE_TAG="comparadori-20";
    private String[] resultados=null;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException
    {
        try {
            String[] nombres = req.getParameterValues("nombre");
            ComparadorInstrumentos cmp = new ComparadorInstrumentos();


            for (int i = 0; i < nombres.length; i++){

                if (nombres[i].length() > 0) {

                    //Hace un chequeo a la web de Thomann
                     cmp = check(nombres[i], cmp);

                    //Hace un chequeo en la web de Amazon.
                    checkAmazon(cmp.getASIN(), cmp);

                }

                if (cmp != null && cmp.getNombre() != null) {
                    req.setAttribute("nombre", cmp);
                    ComparadorInstrumentos h = (ComparadorInstrumentos) req.getAttribute("nombre");
                   // resp.sendRedirect("http://localhost:8888/index.jsp");
                    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                    rd.forward(req, resp);
                } else {
                    System.out.println("dentro del else");
                    resp.sendRedirect("error1.html");
                }
            }

        }catch (Exception error){
                resp.sendRedirect("error.html");
                error.printStackTrace();
            }
        }


    public void checkAmazon(String nombre, ComparadorInstrumentos cmp){
        try {
            SignedRequestsHelper helper1,helper2;
            try {
                helper1 = SignedRequestsHelper.getInstance(ENDPOINTSP, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("Service", "AWSECommerceService");
            params.put("Version", "2011-08-01");
            params.put("AssociateTag", AMAZON_ASSOCIATE_TAG);
            params.put("Operation", "ItemLookup");
            params.put("IdType", "ASIN");
            params.put("ResponseGroup", "Medium");
            params.put("ItemId", nombre);
            params.put("SearchIndex", "All");
            String requestUrl1 = helper1.sign(params);
            String queryString="&Version=2011-08-01" + "&AssociateTag=" + AMAZON_ASSOCIATE_TAG + "&Operation=ItemLookup"+ "&IdType=" + "ASIN" +
                    "&ResponseGroup="+"Medium" + "&ItemId="+nombre;
            requestUrl1 = helper1.sign(queryString);
            System.out.println(requestUrl1);
            DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            db = dbf.newDocumentBuilder();
            Document doc1=null;
            try {
                try {
                    doc1 = db.parse(requestUrl1);
                } catch (org.xml.sax.SAXException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



            System.out.println(getElementValue(doc1,"LowestNewPrice"));
            //busqueda Precios
            NodeList LowestNodes = getElements(doc1,"LowestNewPrice");
            String LowestPrices[]=new String[LowestNodes.getLength()];
            String prices[]=new String [LowestPrices.length];

            for (int i=0; i<LowestPrices.length; i++){
                LowestPrices[i] = LowestNodes.item(i).getTextContent();
                prices[i]="";


                for(int j=LowestPrices[i].indexOf(" ");j<LowestPrices[i].length();j++){
                    prices[i]+=LowestPrices[i].charAt(j);
                }


            }

            //System.out.println(prices[0]);

            cmp.setPrecio_amazon(prices[0]);

            NodeList asinNodes = getElements(doc1,"ASIN");
            String asinCodes[]=new String[asinNodes.getLength()];
            for (int i=0; i<asinCodes.length; i++)
                asinCodes[i] = asinNodes.item(i).getTextContent();


            String url =getElementValue(doc1,"DetailPageURL");
            resultados=asinCodes;
            System.out.println(url);
            cmp.setUrlAmazon(url);


        } catch (ParserConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }


    private String getElementValue (Document doc, String tag)
    {
        NodeList nodelist = doc.getElementsByTagName(tag);
        return ( (nodelist.getLength()>0) ? nodelist.item(0).getTextContent() : null );
    }

    private NodeList getElements (Document doc, String tag)
    {
        return doc.getElementsByTagName(tag);
    }


    /*********************************************************************************************\
     *  FUNCION: callURL                                                                         *
     *  IN: String(url)                                                                          *
     *  OUT: ComparadorInstrumentos(cmp)                                                                      *
     *  Que hace: función encargada de hacer la llamada a la URL para obtener los datos          *
     *  del instrumento del que queremos comparar los precios                                               *
     \*********************************************************************************************/


    public static String callURL(String myURL) {
        System.out.println("Requested URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();

            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ myURL, e);
        }

        return sb.toString();
    }

    private String obtenerURL(int posicion, String cadena, ComparadorInstrumentos cmp, int posicion_llegar)
    {
        posicion += posicion_llegar; //Saltamos la cadena

        String url = "";

        char letra = 'a';

        while(letra != '"')
        {
            letra = cadena.charAt(posicion);

            if(letra !='"')
            {
                url+=letra;
            }
            posicion++;
        }

        return url;
    }

    private String obtenerDatos(int posicion, String cadena, ComparadorInstrumentos cmp, int posicion_llegar)
    {
        posicion += posicion_llegar; //Saltamos la cadena

        String titulo = "";

        char letra = 'a';

        while(letra != '<')
        {
            letra = cadena.charAt(posicion);

            if(letra !='<')
            {
                titulo+=letra;
            }
            posicion++;
        }

        return titulo;
    }

    private String obtenerDatosPrecio(int posicion, String cadena, ComparadorInstrumentos cmp, int posicion_llegar)
    {
        posicion += posicion_llegar; //Saltamos la cadena

        String precio = "";

        char letra = 'a';

        while(letra != '<')
        {
            letra = cadena.charAt(posicion);

            if(letra !='<')
            {
                precio+=letra;
            }
            posicion++;
        }

        return precio;
    }

    private String obtenerDatosPrecioIber(int posicion, String cadena, ComparadorInstrumentos cmp, int posicion_llegar)
    {
        posicion += posicion_llegar; //Saltamos la cadena

        String precio = "";

        char letra = 'a';

        while(letra != '<')
        {
            letra = cadena.charAt(posicion);

            if(letra !='<')
            {
                precio+=letra;
            }
            posicion++;
        }
        precio +=" €";

        return precio;
    }

    private String obtenerDatosComentario(int posicion, String cadena, ComparadorInstrumentos cmp, int posicion_llegar)
    {
        posicion += posicion_llegar; //Saltamos la cadena

        String comentario = "";

        char letra = 'a';

        while(letra != '<')
        {

            letra = cadena.charAt(posicion);

            if(letra !='<')
            {
                comentario+=letra;
            }
            posicion++;
        }

        return comentario;
    }

    private String obtenerASIN(int posicion, String cadena, ComparadorInstrumentos cmp, int posicion_llegar)
    {
        posicion += posicion_llegar; //Saltamos la cadena

        String ASIN = "";

        char letra = 'a';

        while(letra != '&')
        {

            letra = cadena.charAt(posicion);

            if(letra !='&')
            {
                ASIN+=letra;
            }
            posicion++;
        }

        return ASIN;
    }

    private ComparadorInstrumentos check(String nombre, ComparadorInstrumentos cmp) throws IOException {
        //Inicializamos variables.
        String cadena = callURL("https://www.thomann.de/es/search_dir.html?bf=&sw="+nombre);
        String cadena_guima = callURL("http://musicalguima.com/buscar?controller=search&orderby=position&orderway=desc&search_query="+nombre);
        String cadena_amazon = callURL("https://www.amazon.es/s/ref=nb_sb_noss?__mk_es_ES=%C3%85M%C3%85%C5%BD%C3%95%C3%91&url=search-alias%3Daps&field-keywords="+nombre);


        int pos_url_prod=cadena_amazon.indexOf("a-link");
        String url_prod=obtenerURL(pos_url_prod, cadena_amazon,cmp, 35);

        org.jsoup.nodes.Document doc = Jsoup.connect(url_prod).get();
        Elements body = doc.select("div#detail_bullets_id");

        String table="";
        for (org.jsoup.nodes.Element step : body){
            table = step.select("table#productDetailsTable").text();
        }


        String prod_u=callURL(url_prod);
        int pos_ASIN= prod_u.indexOf("contentID", 50000);
        String ASIN= obtenerASIN(pos_ASIN, prod_u, cmp, 10);
        cmp.setASIN(ASIN);


        int posicion_precio_guima = cadena_guima.indexOf("\"price\"");
        String url_guima = obtenerDatosPrecio(posicion_precio_guima, cadena_guima, cmp, 8 );

        //Para obtener la imagen
        int  URLnueva = cadena.indexOf("article-head\">  ");
        String url =obtenerURL(URLnueva, cadena, cmp, 25);
        String url_imagen = callURL(url);

        int  URLnueva_Iberlibro = cadena_guima.indexOf("product_lnk");
        String url_Guima = obtenerURL(URLnueva_Iberlibro, cadena_guima, cmp, 19);
        //System.out.println(cadena_iberlibro.charAt(URLnueva_Iberlibro + 11));


        //ASIGNACION DE URL
        cmp.setUrlThomann(url);
        //System.out.println("Soy Iberlibro: " + cadena_iberlibro);
        cmp.setUrlGuima(url_Guima);

        //Obtenemos fecha con la url de la imagen
        String url_fecha = url_imagen;

        int posicion_titulo = cadena.indexOf("modelName");
        int posicion_comentario = cadena.indexOf("prod-features");

        //Usamos nueva url
        int posicion_imagen = cadena.indexOf("original");

        int posicion_fecha = url_fecha.indexOf("Fecha");

        int posicion_precio = cadena.indexOf("price-primary");

        int pos_marca = cadena.indexOf("manufacturerName");
        String marca = obtenerDatos(pos_marca, cadena, cmp, 18);
        cmp.setMarca(marca);

        String titulo = obtenerDatos(posicion_titulo, cadena ,cmp, 11);
        cmp.setNombre(titulo);

        String comentario = obtenerDatosComentario(posicion_comentario, cadena, cmp, 28);
        cmp.setComentario(comentario);

        String precio_thomann = obtenerDatosPrecio(posicion_precio, cadena, cmp, 15);
        cmp.setPrecio_thomann(precio_thomann);

        //String precio_mguima = obtenerDatosPrecioIber(posicion_precio_guima, cadena_guima, cmp, 36 );
        cmp.setPrecio_guima(url_guima);

        String imagen = obtenerURL(posicion_imagen, cadena, cmp, 10);
        cmp.setImage(imagen);

        return cmp;
    }


}
