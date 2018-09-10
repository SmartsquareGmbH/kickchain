package de.smartsquare.kickchain.filedb;

import java.util.Objects;

public class ExamplePojo {

    private String id;
    private String name;

    public ExamplePojo() {
    }

    public ExamplePojo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamplePojo pojo = (ExamplePojo) o;
        return Objects.equals(id, pojo.id) &&
                Objects.equals(name, pojo.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
