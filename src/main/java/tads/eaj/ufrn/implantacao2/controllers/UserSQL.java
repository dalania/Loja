package tads.eaj.ufrn.implantacao2.controllers;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

public class UserSQL {

    private final String INSERT = "INSERT INTO usuario (\"name\", \"email\", \"password\", \"type\" ) VALUES (?,?,?,?)";

    public void insert(User user){



        Connection connection;
        try{
            connection = ConectaBanco.getConnection();
            System.out.println(user.getName());
            PreparedStatement instruction = connection.prepareStatement(INSERT);
            instruction.setString(1, user.getName());
            instruction.setString(2, user.getEmail());
            instruction.setString(3, user.getPassword());
            instruction.setString(4, user.getType());
            instruction.execute();
            connection.close();
        }catch (SQLException | URISyntaxException e){
            System.out.println("ERROR INSERTING USER IN DATABASE"+e.getMessage());

        }
    }
}
