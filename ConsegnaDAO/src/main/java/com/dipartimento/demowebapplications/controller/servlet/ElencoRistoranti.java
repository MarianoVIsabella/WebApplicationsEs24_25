package com.dipartimento.demowebapplications.controller.servlet;

import com.dipartimento.demowebapplications.model.Ristorante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ristoranti")
public class ElencoRistoranti extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Funziona!!");
        //PrintWriter writer = resp.getWriter();
        //writer.println("<html><strong>Funziona!!</strong></html>");

        String username = (String) req.getSession(true).getAttribute("username");
        System.out.println("username: " + username);
        if (username != null) {
            //Ho fatto la query
            List<Ristorante> ristoranti = new ArrayList<Ristorante>();
            Ristorante r1 = new Ristorante();
            r1.setNome("Le Casette di Zio Santino");
            r1.setDescrizione("Ristorante/Pizzeria");
            r1.setUbicazione("Rende");
            ristoranti.add(r1);

            Ristorante r2 = new Ristorante();
            r2.setNome("Fratfrode");
            r2.setDescrizione("Pizzeria");
            r2.setUbicazione("Rende");
            ristoranti.add(r2);

            req.setAttribute("ristoranti", ristoranti);

            RequestDispatcher dispatcher = req.getRequestDispatcher("views/elenco_ristoranti.html");
            dispatcher.forward(req, resp);
        }else{
            RequestDispatcher dispatcher = req.getRequestDispatcher("views/notAuthorized.html");
            dispatcher.forward(req, resp);
        }

    }
}
