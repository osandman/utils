package net.osandman.util.parsing.jackson_example2;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "My doggy")
public class Dog extends Pet {
    public Dog() {
    }

    public Dog(String name) {
        super(name);
    }

}
