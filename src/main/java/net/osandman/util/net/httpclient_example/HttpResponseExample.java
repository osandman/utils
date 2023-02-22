package net.osandman.util.net.httpclient_example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpResponseExample {
    static HttpClient httpClient = HttpClient.newHttpClient();

    public static void main(String[] args) throws IOException, InterruptedException {
        URI uri = URI.create("https://httpbin.org/ip");
        String postStr = "";
        printAllResponse(getRequest(uri));
        printAllResponse(postRequest(uri, postStr));
    }

    private static void printAllResponse(HttpResponse<String> response) {
        System.out.println(response.request().method());
        System.out.println("Status code = " + response.statusCode());
        System.out.println("Headers:");
        response.headers().map().forEach((key, values) -> System.out.println(key + "=" + values));
        System.out.println("Body:");
        System.out.println(response.body());
    }

    private static HttpRequest buildRequest(URI uri, String builder, HttpRequest.BodyPublisher publisher) {
        return HttpRequest.newBuilder(uri)
                .method(builder, publisher)
                .build();
    }

    private static HttpResponse<String> postRequest(URI uri, String postStr) throws IOException, InterruptedException {
        HttpRequest httpRequest = buildRequest(uri, "POST", HttpRequest.BodyPublishers.ofString(postStr));
        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> getRequest(URI uri) throws IOException, InterruptedException {
        HttpRequest httpRequest = buildRequest(uri, "GET", HttpRequest.BodyPublishers.ofString(""));
        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    }
}
