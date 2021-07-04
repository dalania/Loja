package tads.eaj.ufrn.implantacao2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tads.eaj.ufrn.implantacao2.models.UserSQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/users")
public class UserServelet extends HttpServlet {
UserSQL userD = new UserSQL();

    @PostMapping
    public void Register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var name = request.getParameter("name");
        var email = request.getParameter("email");
        var password = request.getParameter("password");
        var type = request.getParameter("type");

        User user = new User(name, email, password, type);
        userD.insert(user);
        userD.criar();
        response.getWriter().println(name);


    }
}
