package net.osandman.util.parsing.jackson_example2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Dog.class),
        @JsonSubTypes.Type(value = Cat.class)
})
public abstract class Pet {
    String name;
    @JsonIgnore
    Person owner;

    public Pet() {
    }

    public Pet(String name) {
        this.name = name;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pet{" +
               "name='" + name + '\'' +
               '}';
    }
}
