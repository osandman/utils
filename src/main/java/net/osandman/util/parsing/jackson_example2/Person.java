package net.osandman.util.parsing.jackson_example2;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Arrays;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(@JsonSubTypes.Type(value = Person.class, name = "Human"))
public class Person {
    private String name;

    private List<Item> assets;

    private Pet[] pet;

    public Person() {
    }

    public Person(String name, List<Item> assets) {
        this.name = name;
        this.assets = assets;
    }

    public void setAssets(List<Item> assets) {
        this.assets = assets;
    }

    public Pet[] getPet() {
        return pet;
    }

    public void setPet(Pet ... pet) {
        this.pet = pet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getAssets() {
        return assets;
    }

    public void addAsset(Item... item) {
        assets.addAll(Arrays.asList(item));
    }

    @Override
    public String toString() {
        return "Person{" +
               "name='" + name + '\'' +
               ", assets=" + assets +
               ", pet=" + Arrays.toString(pet) +
               '}';
    }
}
