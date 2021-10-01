package edu.escuelaing.arep.LogBalancer.persistence;

import java.util.Date;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
 
/**
 *
 */
public class BDConection { 
	
	private MongoClientURI url;
    private MongoClient client;
    private MongoDatabase monguito;


    public BDConection() {
        url = new MongoClientURI("mongodb://localhost:27017");
        client = new MongoClient(url);
        monguito = client.getDatabase("db");

    }
    /**
     * Agrega un dato a la base de datos
     * @param mensaje
     */
    public void addData(String mensaje) {
    	MongoCollection<Document> collection = monguito.getCollection("balancer");
    	Document object = new Document();
        object.append("mensaje",mensaje);
        object.append("fecha", new Date().toString());
        collection.insertOne(object);
    }

    public String getData() {
    	MongoCollection<Document> collection = monguito.getCollection("balancer");
        String mensaje = "";
        FindIterable<Document> iterDoc = collection.find();
        for(Document d : iterDoc){
            mensaje += "<tr><td>" + d.get("mensaje").toString() + "</td><td>" + d.get("fecha").toString() + "</td></tr>";
        }
        System.out.println(mensaje);
        return mensaje;
    } 
    
}