/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import modelo.Pelicula;
import dao.PeliculaDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PelisUsuario")
public class PelisUsuario extends HttpServlet {

    private final PeliculaDAO peliculaDAO;
    private final ObjectMapper objectMapper;

    public PelisUsuario() {
        this.peliculaDAO = new PeliculaDAO();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        
        if ( email != null ) {
            List<String> peliculas = peliculaDAO.obtenerPelis(email);
            objectMapper.writeValue(response.getWriter(), peliculas);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Pelicula pelicula = objectMapper.readValue(request.getReader(), Pelicula.class);
        boolean exito = peliculaDAO.insertarPel(pelicula);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"exito\": " + exito + "}");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Pelicula pelicula = objectMapper.readValue(request.getReader(), Pelicula.class);
        boolean exito = peliculaDAO.eliminarPel(pelicula);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"exito\": " + exito + "}");
    }
}
