package net.osandman.util.net.jackson_test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Run {
    static final ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) throws IOException {
        SomeData.InnerData innerData = new SomeData.InnerData("some value in inner class", List.of(new String[]{"1", "2", "345"}));
        SomeData someData = new SomeData("hello", 5, true, 38.80f, innerData);
        Path path = Path.of("src/main/resources/test.json");
        writeJson(path, someData);
        SomeData someData1 = readJson(path);
        System.out.println(someData1);
    }
    private static void writeJson(Path path, SomeData someData) throws IOException {
        objectMapper.writeValue(path.toFile(), someData);
    }
    private static SomeData readJson(Path path) throws IOException {
        return objectMapper.readValue(path.toFile(), SomeData.class);
    }

}
