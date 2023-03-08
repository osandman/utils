package net.osandman.util.parsing.jackson_example2;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "My kitty")
public class Cat extends Pet {
    int age;

    public Cat(String name) {
        super(name);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
