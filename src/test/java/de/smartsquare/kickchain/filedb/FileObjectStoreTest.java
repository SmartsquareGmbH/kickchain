package de.smartsquare.kickchain.filedb;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FileObjectStoreTest {

    @Test
    public void test_storage() throws IOException {
        // given
        ExamplePojo pojo = new ExamplePojo("42", "Test!");
        FileObjectStore<ExamplePojo> pojoStore = new FileObjectStore<>();

        // when
        pojoStore.store("42", pojo);
        ExamplePojo result = pojoStore.read("42", ExamplePojo.class);

        // then
        assertEquals(pojo, result);
    }

    @Test(expected = IOException.class)
    public void test_storage_object_not_exist() throws IOException {
        // given
        ExamplePojo pojo = new ExamplePojo("42", "Test!");
        FileObjectStore<ExamplePojo> pojoStore = new FileObjectStore<>();

        // when
        pojoStore.store("42", pojo);
        ExamplePojo result = pojoStore.read("43", ExamplePojo.class);

        // then
        assertEquals(pojo, result);
    }

}