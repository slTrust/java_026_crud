package com.xiedaimala.shopping_cart.product.model;

import com.xiedaimala.shopping_cart.product.model.api.CreateProductRequest;
import com.xiedaimala.shopping_cart.product.model.api.UpdateProductRequest;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDao {
    private Statement statement;

    public ProductDao(Statement statement) {
        this.statement = statement;

        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM `product`");
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to query product from DB.");
        }
    }

    public Product get(int id) {
        try {
            System.out.println("test1");

            String query = "SELECT * FROM `product` WHERE id = " + id;
            System.out.println(query);
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                System.out.println("find one product.");
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to query product from DB.");
        }

        return null;
    }

    public List<Product> getLists() {
        List<Product> res = new ArrayList<>();
        try {
            System.out.println("test1");

            String query = "SELECT * FROM `product`";
            System.out.println(query);
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                res.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                ));
                System.out.println("find one product.");

            }
            if(res.size()>0){
                return res;
            }else{
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to query product from DB.");
        }

        return null;
    }

    public Product post(CreateProductRequest product) {
        String name = product.getName();
        String description = product.getDescription();
        Double price = product.getPrice();
        try {
            System.out.println("test1");


            String query = "SELECT * FROM `product` WHERE name='" + name + "' and description='" + description + "' and price=" + price ;
            System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                System.out.println("find one product.");
                return null;
            } else {
                String sql = "insert into `product` (name,description,price) values('" +name +"','" + description +"'," + price + ")";
                statement.executeUpdate(sql);
                ResultSet rs2 = statement.executeQuery(query);
                return new Product(
                        rs2.getInt("id"),
                        rs2.getString("name"),
                        rs2.getString("description"),
                        rs2.getDouble("price")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Failed to query product from DB.");
        }

        return null;
    }

    public Product put(Long id,UpdateProductRequest product) {
        String name = product.getName();
        String description = product.getDescription();
        Double price = product.getPrice();
        try {
            System.out.println("test put");
            String query = "SELECT * FROM `product` WHERE id = " + id;
            System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                System.out.println("find one product.");
                String sql = "update  `product` set name='"+product.getName() + "',description='"+ product.getDescription() +"',price="+product.getPrice() +" where id="+id;
                statement.executeUpdate(sql);
                ResultSet rs2 = statement.executeQuery(query);
                return new Product(
                        rs2.getInt("id"),
                        rs2.getString("name"),
                        rs2.getString("description"),
                        rs2.getDouble("price")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Failed to query product from DB.");
        }

        return null;
    }
}
