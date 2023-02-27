package net.osandman.util.parsing.jackson_example2;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Run {
    public static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    public static void main(String[] args) throws IOException {
        Item item1 = new Item("Car", 125);
        Item item2 = new Item("House", 2340);
        List<Item> items = new ArrayList<>();
        Person person1 = new Person("Petr", items);
        person1.addAsset(item1, item2);
        Pet myDog = new Dog("Baron");
        person1.setPet(myDog);
        myDog.setOwner(person1);

        System.out.println(person1);

        File jsonFile = new File("src/main/resources/person.json");
        String jsonStr = mapper.writeValueAsString(person1);
        System.out.println(jsonStr);
        mapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter())
                .writeValue (jsonFile, person1);

        Person person2 = mapper.readValue(jsonFile, Person.class);

        System.out.println(person2);
        System.out.println(person2.getAssets().getClass().getName());
    }
}
