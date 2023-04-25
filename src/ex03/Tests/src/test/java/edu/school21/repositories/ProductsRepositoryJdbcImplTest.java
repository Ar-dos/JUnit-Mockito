package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.persistence.EntityNotFoundException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

public class ProductsRepositoryJdbcImplTest {
    private DataSource dataSource;
    private ProductsRepositoryJdbcImpl productsRepositoryJdbc;
    @BeforeEach
    void init() throws SQLException {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScripts("data.sql")
                .build();
        dataSource = db;
        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void connectionTest() throws SQLException {
        assertNotNull(dataSource.getConnection());
    }

    @Test
    void findAllTest() {
        List<Product> products = productsRepositoryJdbc.findAll();
        for (int i = 1; i < 6;i++) {
            assertEquals(productsRepositoryJdbc.findById(new Long(i)).get().getName(), products.get(i-1).getName());
            assertEquals(productsRepositoryJdbc.findById(new Long(i)).get().getPrice(), products.get(i-1).getPrice());
        }
    }

    @Test
    void deleteTest() {
        List<Product> products = productsRepositoryJdbc.findAll();
        productsRepositoryJdbc.delete(3L);
        List<Product> result = productsRepositoryJdbc.findAll();
        assertEquals(result.get(0),products.get(0));
        assertEquals(result.get(1),products.get(1));
        assertEquals(result.get(2),products.get(3));
        assertEquals(result.get(3),products.get(4));
    }

    @Test
    void updateTest() {
        Product product = new Product(5L,"name6",666.666);
        productsRepositoryJdbc.update(product);
        assertEquals(productsRepositoryJdbc.findById(5l).get(),product);
    }

    @Test
    void saveTest() {
        Product product = new Product(0L,"name6",666.666);
        productsRepositoryJdbc.save(product);
        assertEquals(productsRepositoryJdbc.findById(6L).get().getName(),product.getName());
        assertEquals(productsRepositoryJdbc.findById(6L).get().getPrice(),product.getPrice());
        assertNotEquals(productsRepositoryJdbc.findById(6L).get().getId(),product.getId());
    }



    @Test
    void getByIdTest() throws SQLException {
        for (int i = 1; i < 6;i++)
            assertEquals(productsRepositoryJdbc.findById(new Long(i)).get().getName(), "name" + i);
        assertFalse(productsRepositoryJdbc.findById(new Long(6L)).isPresent());
    }

}
