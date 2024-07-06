package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UsuarioDAO;
import modelo.Usuario;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/GestionUsuariosServlet")
public class GestionUsuariosServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO;
    private final ObjectMapper objectMapper;

    public GestionUsuariosServlet() {
        this.usuarioDAO = new UsuarioDAO();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String param = request.getParameter("documento");
        if (param != null) {
            long documento = Long.parseLong(param);
            Usuario usuario = usuarioDAO.obtenerUs(documento);
            objectMapper.writeValue(response.getWriter(), usuario);
        } else {
            List<Usuario> usuarios = usuarioDAO.obtenerTodos();
            objectMapper.writeValue(response.getWriter(), usuarios);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Usuario usuario = objectMapper.readValue(request.getReader(), Usuario.class);
        boolean exito = usuarioDAO.actualizarUs(usuario);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"exito\": " + exito + "}");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long documento = Long.parseLong(request.getParameter("documento"));

        boolean exito = usuarioDAO.eliminarUs(documento);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"exito\": " + exito + "}");

    }
}
