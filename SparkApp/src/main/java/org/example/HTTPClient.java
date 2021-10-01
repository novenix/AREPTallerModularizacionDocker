package org.example;
import okhttp3.*;

import java.io.IOException;
public class HTTPClient {


    private String url = "http://localhost";
    private String[] ports = {":35001", ":35002", ":35003"};
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private int serverNumber = 0;
    private OkHttpClient httpClient;

    public HTTPClient() {
        httpClient = new OkHttpClient();
    }

    /**
     * Peticion GET al log service
     *
     * @return
     * @throws IOException
     */
    public String getMessages() throws IOException {
        Request request = request = new Request.Builder().url(url + ports[serverNumber] + "/result").get().build();
        System.out.println("Request enviado a nodo " + url + ports[serverNumber] + "/result");
        Response response = httpClient.newCall(request).execute();
        return response.body().string();

    }

    /**
     * Peticion POST al log service
     *
     * @param message
     * @return
     * @throws IOException
     */
    public String postMessage(String message) throws IOException {
        RequestBody body = RequestBody.create(message, JSON);
        Request request = new Request.Builder().url(url + ports[serverNumber] + "/result").post(body).build();
        System.out.println("Sending POST to node: " + url + ports[serverNumber] + "/result");
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    /*
     * Puerto random
     */
    public void roundRobin() {
        this.serverNumber = (this.serverNumber + 1) % ports.length;
        System.out.println(url + ports[serverNumber] + "/result");
    }
}