/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import static dao.AdmiDAO.iniciarSesion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "ConsultasAdmi", urlPatterns = {"/ConsultasAdmi"})
public class ConsultasAdmi extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        String usuario =request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");
        if(iniciarSesion(usuario, contrasena))
        {
           try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Consultas admin</title>");            
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1 align=\"center\">Proceda a hacer consultas</h1>");
            out.println("<br>");
            out.println("<br>");
            
            out.println("<form action=\"respEstatus\" method=\"post\">");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Registros de hoy\"/></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</form>");
            
            out.println("<form action=\"respEstatus\" method=\"post\">");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Registros CLI\"/></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</form>");
            
            out.println("<form action=\"respEstatus\" method=\"post\">");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Registros GUI\"/></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</form>");
            
            out.println("<form action=\"respEstatus\" method=\"post\">");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Registros Web\"/></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</form>");
            
            out.println("<form action=\"respEstatus\" method=\"post\">");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Todos los registros\"/></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</form>");
            
            out.println("</body>");
            out.println("</html>");
            } 
        }
        else{
                try ( PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1 align=\"center\">El usuario o contraseña no son válidos</h1>");
                out.println("</body>");
                out.println("</html>");
            }
            }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConsultasAdmi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConsultasAdmi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
