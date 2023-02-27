package net.osandman.util.net.httpclient_example;

import java.net.http.HttpResponse;

public class PrintResponse {
    static boolean initStrNeed = true;
    static final String RESET = "\033[0m";
    static final String GREEN = "\033[0;32m";
    static final String YELLOW = "\033[0;33m";


    public static void printAll(HttpResponse<String> response) {
        printInitStr(response);
        initStrNeed = false;
        printHeader(response);
        printBody(response);
        initStrNeed = true;
    }

    public static void printBody(HttpResponse<String> response) {
        printInitStr(response);
        System.out.println(GREEN + "Body:" + RESET);
        System.out.println(response.body());
    }

    public static void printHeader(HttpResponse<String> response) {
        printInitStr(response);
        System.out.println(GREEN + "Headers:" + RESET);
        response.headers().map().forEach((key, values) -> System.out.println(key + "=" + values));
    }

    private static void printInitStr(HttpResponse<String> response) {
        if (initStrNeed) {
            System.out.printf(YELLOW + "URL: %s, Method: %s, Status code = %d" + RESET + "\n",
                    response.uri().toString(), response.request().method(), response.statusCode());
        }
    }
}
