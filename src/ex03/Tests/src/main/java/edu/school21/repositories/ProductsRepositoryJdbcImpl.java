package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private Connection connection;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public List<Product> findAll() {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<Product> res = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM products ORDER BY id ASC";
            pstmt = connection.prepareStatement(SQL);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
                res.add(new Product(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getDouble("price")));
        } catch (SQLException e1) {
        }
        return res;
    }

    public Optional<Product> findById(Long id) {
        Product res = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String SQL = "SELECT * from products where id = ?";
            pstmt = connection.prepareStatement(SQL);
            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                res = new Product(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getDouble("price"));
            }
        } catch (SQLException e1) {
        }


        return Optional.ofNullable(res);

    }

    public void update(Product product) {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String SQL = "UPDATE products SET  name = ?, price = ? WHERE id = ?";
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setLong(3, product.getId());
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
        }
    }
    public void save(Product product) {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String SQL = "INSERT INTO products (name, price) VALUES (?, ?)";
            pstmt = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.executeUpdate();
            connection.commit();
            resultSet = pstmt.getGeneratedKeys();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }
    public void delete(Long id) {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String SQL = "DELETE FROM products WHERE  id = ?";
            pstmt = connection.prepareStatement(SQL);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
