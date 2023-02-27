package net.osandman.util.net.httpclient_example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpResponseEx {
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private HttpRequest buildRequest(URI uri, String builder, HttpRequest.BodyPublisher publisher) {

        return HttpRequest.newBuilder(uri)
                .method(builder, publisher)
                .build();
    }

    public HttpResponse<String> postRequest(URI uri, String postStr) throws IOException, InterruptedException {
        HttpRequest httpRequest = buildRequest(uri, "POST", HttpRequest.BodyPublishers.ofString(postStr));
        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> getRequest(URI uri) throws IOException, InterruptedException {
        HttpRequest httpRequest = buildRequest(uri, "GET", HttpRequest.BodyPublishers.ofString(""));
        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    }
}
