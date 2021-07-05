package tads.eaj.ufrn.implantacao2.controllers;
import org.springframework.cglib.proxy.Dispatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tads.eaj.ufrn.implantacao2.persistencia.ProductSQL;
import tads.eaj.ufrn.implantacao2.dominio.Product;

import org.springframework.stereotype.Controller;

import java.io.Writer;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/config")
public class Config {
    ProductSQL productSQL = new ProductSQL();

    @GetMapping
    public void InsertProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try{
            productSQL.createTable();
            Product product = new Product(1,"Creme Pos-Quimica","Banho de creme para cabelos danificados", "Cremes de Hidratacao",100, 10,"Bio Extratus");
            Product product2 = new Product(2,"Batom nude rosado","Batom nude Rosado", "Maquiagem",12, 100,"MAK Cosmeticos");
            Product product3 = new Product(3,"Po Compacto","Po compacto bege, ideal para pele morena", "Maquiagem",45, 20,"MAK Cosmeticos");
            Product product4 = new Product(4,"Perfume 312","Perfume 312 amadeirado", "Perfumes",12, 12,"Twoone OneTwo");
            Product product5 = new Product(5,"Hidrante Amendoas","Hidratante para pele seca", "Corpo",120, 50,"Natura");
            productSQL.insert(product);
            productSQL.insert(product2);
            productSQL.insert(product3);
            productSQL.insert(product4);
            productSQL.insert(product5);

            response.getWriter().println("OK");

        }catch (IOException error) {
            response.getWriter().println("Erro ao criar o banco de dados!");


        }



    }
}
