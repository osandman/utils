package net.osandman.util.parsing.jackson_example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

public class Run {
    static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) throws IOException {
        SomeData.InnerData innerData = new SomeData.InnerData("some value in inner class", List.of(new String[]{"1", "2", "345"}));
        SomeData someData = new SomeData("hello", 5, true, 38.80f, innerData);

        SomeData.InnerData innerData1 = new SomeData.InnerData("second value inner class", List.of(new String[]{"100", "12", "-5"}));
        SomeData someData1 = new SomeData("world", -11, false, -.021f, innerData1);

        Path path = Path.of("src/main/resources/test.json");

        writeJsonToFile(path, someData);
        writeJsonToFile(path, someData, someData1);

        //извлекаем данные из файла
        for (SomeData currentData : readJson(path)) {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(currentData);
            System.out.println(json);
        }
    }

    private static void writeJsonToFile(Path path, SomeData... someData) throws IOException {
        FileWriter fileWriter = new FileWriter(path.toFile());
        SequenceWriter sequenceWriter = objectMapper.writer().writeValuesAsArray(fileWriter);
        for (SomeData currenData : someData) {
            sequenceWriter.write(currenData);
        }
        sequenceWriter.close();
    }

    private static SomeData[] readJson(Path path) throws IOException {
        SomeData[] result = objectMapper.readValue(path.toFile(), SomeData[].class);
        return result;
    }
}
