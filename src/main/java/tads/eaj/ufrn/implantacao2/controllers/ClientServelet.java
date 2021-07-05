package tads.eaj.ufrn.implantacao2.controllers;
import tads.eaj.ufrn.implantacao2.persistencia.ProductSQL;
import  tads.eaj.ufrn.implantacao2.dominio.Product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@Controller

public class ClientServelet {
    ProductSQL productSQL = new ProductSQL();
    @RequestMapping(value="/cliente", method = RequestMethod.GET)
    public void ListProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<Product> arrayProducts = new ArrayList<>();
        ProductSQL productSQL = new ProductSQL();
        arrayProducts = productSQL.listProduct();

        response.getWriter().println("<table width=100%  border=\"1\">" +
               " <thead> " +
                "<tr> " +
                "<td> Nome </td>" +
                "<td> Descricao </td>" +
                "<td> Preco </td>" +
                "<td> Adicionar </td>" +
                "</tr>" +
                " </thead>"
               );
        response.getWriter().println("<tbody>");
        for(var product:arrayProducts){
            response.getWriter().println("<tr text-align=\"left\">" +
                    "<td>"+product.getName() + "</td>" +
                    "<td>"+product.getDescription() + "</td>" +
                    "<td>"+product.getPrice()+ "</td>" +
                    "<td><a href=\"/adicionarCarrinho?id=" +product.getId()+ "\">Adicionar</a></td>"+

                    "</tr>");
        }
        response.getWriter().println("</tbody");
        response.getWriter().println("</table>");
        response.getWriter().println("<a href=\"/verCarrinho\">Ver Carrinho </a>");

        }

    @RequestMapping(value="adicionarCarrinho", method = RequestMethod.GET)
    public  void AddProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Product pro;

        HttpSession session = request.getSession();
        if(session.getAttribute("carrinho")==null){
            session.setAttribute("carrinho", new ArrayList<Product>());
        }

        ArrayList<Product> productsCar = (ArrayList<Product>) session.getAttribute("carrinho");
        Integer id = Integer.parseInt(request.getParameter("id"));
        response.getWriter().println(id);
        pro= productSQL.searchProduct(id);
        response.getWriter().println(pro.getName());
        productsCar.add(pro);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente");
        dispatcher.forward(request,response);
    }

    @RequestMapping(value="verCarrinho", method = RequestMethod.GET)
    public void SeeCart(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();


        if(session.getAttribute("carrinho")==null){
            response.sendRedirect("/cliente");
        }else{

            ArrayList<Product> arrayProduct = (ArrayList<Product>) session.getAttribute("carrinho");
            response.getWriter().println("<header>\n" +
                    "    <ul>\n" +
                    "      <li> <a href=\"/cliente\"> Voltar para a loja</a></li>\n" +
                    "      <li> <a href=\"/finalizarCompra\">Finalizar Compra </a></li>\n" +
                    "    </ul>\n" +
                    "  </header>");

            response.getWriter().println("<table width=100%  border=\"1\">" +
                    " <thead> " +
                    "<tr> " +
                    "<td> Nome </td>" +
                    "<td> Descricao </td>" +
                    "<td> Preco </td>" +
                    "</tr>" +
                    " </thead>"
            );
            response.getWriter().println("<tbody>");
            for(var product:arrayProduct){
                response.getWriter().println("<tr text-align=\"left\">" +
                        "<td>"+product.getName() + "</td>" +
                        "<td>"+product.getDescription() + "</td>" +
                        "<td>"+product.getPrice()+ "</td>" +
                        "</tr>");
            }
            response.getWriter().println("</tbody");
            response.getWriter().println("</table>");

        }

    }

    @RequestMapping(value = "finalizarCompra", method = RequestMethod.GET)
    public void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.getSession().invalidate();
        response.sendRedirect("/");

    }

}
