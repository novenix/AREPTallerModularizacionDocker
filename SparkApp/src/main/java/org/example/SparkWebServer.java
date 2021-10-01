package org.example;

import spark.Request;
import spark.Response;

import java.io.IOException;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;;

public class SparkWebServer {

    private static HTTPClient client = new HTTPClient();
    public static void main(String... args){
        if(client==null){
            System.out.println("no creado we");
        }else {
            System.out.println("creado cliente");
        }
        port(getPort());
        get ("hello", (req,res) -> "Hello Docker!");
        post("/result",(req,res) -> inputDataPage(req, res));
        get("/result",(req,res)-> resultDataPage(req,res));

    }

    /**
     * Encargado de retornar datos de mongo
     * @param req request
     * @param res response
     * @return datos
     */
    private static String inputDataPage(Request req, Response res) throws IOException {
        client.postMessage(req.body());
        System.out.println(req.body());
        System.out.println(req.queryParams("mensaje"));
        String pageContent
                =resultDataPage(req,res);
        client.roundRobin();
        return pageContent;
    }

    /**
     * Encargado de agregar data a la base de datos
     * @param req request
     * @param res response
     * @return datos
     */
    private static String resultDataPage(Request req, Response res) throws IOException {
        String datos = client.getMessages();
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<style>"
                + "body {text-align: center;"
                + " font-family: \"new century schoolbook\";}"
                + "h2 {text-align: center;}"
                + "table {text-align: center;}"
                + "a {text-align: center;}"
                + "div {text-align: center;}"
                + "form action {text-align: center;}"
                + "</style>"
                + "</head>"
                +"<title>LogBalancer</title>"
                + "<h2 text-aling =\"center\">Recopilador de datos </h2>"
                + "<form method=\"post\" action=\"/result\">"
                + "  Ingresa mensaje:<br>"
                + "  <input type=\"text\" name=\"mensaje\">"
                + "  <br>"
                + "  <input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "<body style=\"background-color:powderblue;\">"
                +"<table style="+"width:100%"+">"
                +"<tr>"
                +"<th>mensaje</th>"
                +"<th>Fecha</th>"
                +"</tr>"
                + datos
                +"</table>"
                + "</body>"
                + "</html>";
        client.roundRobin();
        return pageContent;
    }
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}
