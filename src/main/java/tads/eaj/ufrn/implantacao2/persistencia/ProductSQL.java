package tads.eaj.ufrn.implantacao2.persistencia;
import tads.eaj.ufrn.implantacao2.dominio.Product;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductSQL {

    private final String INSERT = "INSERT INTO product (\"id\",\"name\",\"description\", \"category\", \"price\", \"amount\", \"brand\") VALUES (?,?,?,?,?,?,?)";
    private final  String SELECT = "SELECT * FROM product";
    private final String SELECTWHERE = "SELECT * FROM product WHERE id=?";
    private final String CREATE = "CREATE TABLE IF NOT EXISTS public.product\n" +
            "(\n" +
            "    id integer NOT NULL,\n" +
            "    name text COLLATE pg_catalog.\"default\",\n" +
            "    description text COLLATE pg_catalog.\"default\",\n" +
            "    category text COLLATE pg_catalog.\"default\",\n" +
            "    price real,\n" +
            "    amount integer,\n" +
            "    brand text COLLATE pg_catalog.\"default\",\n" +
            "    CONSTRAINT pk_id PRIMARY KEY (id)\n" +
            ")";


    public void insert(Product product) {
        Connection connection;

        try {
            connection = ConectaBanco.getConnection();
            PreparedStatement instruction = connection.prepareStatement(INSERT);
            instruction.setInt(1, product.getId());
            instruction.setString(2,product.getName());
            instruction.setString(3, product.getDescription());
            instruction.setString(4, product.getCategory());
            instruction.setDouble(5,product.getPrice());
            instruction.setInt(6,product.getAmount());
            instruction.setString(7, product.getBrand());
            instruction.execute();
            connection.close();

        } catch (SQLException | URISyntaxException e) {
            System.out.println("ERROR INSERTING USER IN DATABASE" + e.getMessage());

        }

    }
    public ArrayList<Product> listProduct(){
        ArrayList<Product> listProducts = new ArrayList<>();
        Connection connection;
        try{
            connection = ConectaBanco.getConnection();
            PreparedStatement instruction = connection.prepareStatement(SELECT);
            ResultSet res = instruction.executeQuery();
            while (res.next()){
                Product product = new Product(res.getInt("id"),
                res.getString("name"),
                res.getString("description"),
                res.getString("category"),
                res.getDouble("price"),
                res.getInt("amount"),
                res.getString("brand"));
                listProducts.add(product);
            }
            connection.close();

        }catch (SQLException | URISyntaxException e) {
            System.out.println("ERROR SELECT PRODUCT IN DATABASE" + e.getMessage());

        }

        return listProducts;

    }
    public Product searchProduct(int id){
        Product productFound = null;
        Connection connection;
        try {
            connection = ConectaBanco.getConnection();
            PreparedStatement instruction = connection.prepareStatement(SELECTWHERE);
            instruction.setInt(1,id);
            ResultSet res = instruction.executeQuery();
            if(res.next()){
                productFound = new Product(res.getInt("id"),
                        res.getString("name"),
                        res.getString("description"),
                        res.getString("category"),
                        res.getDouble("price"),
                        res.getInt("amount"),
                        res.getString("brand"));
                        instruction.execute();


            }
            instruction.close();
            return productFound;


        }catch (SQLException | URISyntaxException e){
            System.out.println("ERROR SELECT WHERE PRODUCT IN DATABASE" + e.getMessage());

        }


        return null;

    }

    public void createTable(){
        Connection connection;

        try{
            connection = ConectaBanco.getConnection();
            PreparedStatement instruction = connection.prepareStatement(CREATE);
            instruction.execute();
            connection.close();

        }catch (SQLException | URISyntaxException e) {
            System.out.println("ERROR  CREATE DATABASE DATABASE" + e.getMessage());

        }
    }

};
