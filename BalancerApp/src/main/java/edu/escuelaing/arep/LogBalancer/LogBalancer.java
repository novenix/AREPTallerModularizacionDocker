package edu.escuelaing.arep.LogBalancer;

import static spark.Spark.*;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import edu.escuelaing.arep.LogBalancer.persistence.BDConection;

/**
 *
 *
 */
public class LogBalancer{
	private static BDConection mongo = new BDConection();
    public static void main(String... args){
        port(getPort());
        get ("hello", (req,res) -> "Hello Docker!");
        post("/result",(req,res) -> inputDataPage(req, res));
        get("/result",(req,res)-> resultDataPage(req,res));
    }


    private static String inputDataPage(spark.Request req, spark.Response res) {
    	mongo.addData((String) req.body());
    	System.out.println((String) req.body());
        String pageContent
                = resultDataPage(req,res);
        return pageContent;
    }
    /**
     * Encargado de retornar datos de mongo
     * @param req request 
     * @param res response
     * @return datos 
     */
    private static String resultDataPage(spark.Request req, spark.Response res) {
    	String pageContent = mongo.getData();
    	System.out.print(pageContent);
        return pageContent;
    }
    
    /**
     * Puerto por el que se ejecuta la app
     * @return puerto
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568;
    }
}