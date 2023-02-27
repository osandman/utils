package net.osandman.util.net.httpclient_example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Run {
    static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException, InterruptedException {

        Map<String, String> values = new HashMap<>() {
            {
                put("name", "John Doe");
                put("occupation", "gardener");
            }
        };

        HttpResponseEx response = new HttpResponseEx();
        URI uri = URI.create("https://httpbin.org/post");
//        URI uri = URI.create("curl https://reqbin.com/echo");
//        URI uri = URI.create("http://webcode.me");
        String postStr = mapper.writeValueAsString(values);
//        postStr = "";
//        PrintResponse.printAll(response.getRequest(uri));
        PrintResponse.printAll(response.postRequest(uri, postStr));
    }


}
