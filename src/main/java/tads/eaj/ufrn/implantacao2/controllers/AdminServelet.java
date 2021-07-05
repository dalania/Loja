package tads.eaj.ufrn.implantacao2.controllers;
import tads.eaj.ufrn.implantacao2.persistencia.ProductSQL;
import tads.eaj.ufrn.implantacao2.dominio.Product;

import javax.servlet.http.Cookie;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller

public class AdminServelet {
    ProductSQL productSQL = new ProductSQL();


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductSQL productSQL = new ProductSQL();
        response.getWriter().println("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Cadastro de Usuários</title>" +
                "</head>" +
                "<body>" +
                "<form action=\"/cadastrar\" method=\"post\">" +
                "    id:<input type=\"number\" name=\"id\"> <br /><br />" +
                "    Nome:<input type=\"text\" name=\"name\"> <br /><br />" +
                "    Descricao:<input type=\"text\" name=\"description\"> <br /><br />" +
                "    Categoria: <input type=\"text\" name=\"category\"> <br /><br />" +
                "    Preco: <input type=\"number\" name=\"price\"> <br /><br />" +
                "    Quantidade: <input type=\"number\" name=\"amount\"> <br /><br />" +
                "    Marca: <input type=\"text\" name=\"brand\"> <br /><br />" +
                "    <button type=\"submit\">Cadastar</button>" +
                "</form>" +
                "</body>" +
                "</html>");

        HttpSession session = request.getSession();
        var date = new Date(session.getCreationTime());
        SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy--hh:mm:ss");
        Cookie c = new Cookie("visit", formatData.format(date));
        c.setMaxAge(60*60*24);
        response.addCookie(c);
        response.getWriter().println("Data do ultimo acesso:"+formatData.format(date));
    }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
    public void RegisterProduct(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        Double price = Double.parseDouble(request.getParameter("price"));
        Integer amount = Integer.parseInt(request.getParameter("amount"));
        String brand = request.getParameter("brand");
        Product product = new Product(id,name,description,category,price,amount,brand);
        productSQL.insert(product);
        response.sendRedirect("/admin");
    }


}
