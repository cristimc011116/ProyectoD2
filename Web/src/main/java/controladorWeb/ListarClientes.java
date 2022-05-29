/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import controlador.ControladorUsuario;
import dao.PersonaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import logicadenegocios.Cuenta;
import logicadenegocios.Persona;
import util.ComparatorApellido;

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "listarCliente", urlPatterns = {"/listarCliente"})
public class ListarClientes extends HttpServlet {
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
        
        ArrayList<Persona> listaPersonas = PersonaDAO.getPersonasBD();
        Collections.sort(listaPersonas, new ComparatorApellido());
        //listaPersonas.sort((Persona persona1, Persona persona2)-> persona1.getPrimerApellido().compareTo(persona2.getPrimerApellido()));
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Listar clientes</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h2 align=\"center\">Clientes registrados en el sistema</h2>");
            out.println("<br>");
            out.println("<br>");
            out.println("<br>");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td>Identificación</td>");
            out.println("<td>Nombre completo</td>");
            out.println("</tr>");
            for(Persona persona: listaPersonas){
                out.println("<tr>");
                out.println("<td>"+ persona.getId() +"</td>");
                out.println("<td>"+ persona.getNombre() + " " + persona.getPrimerApellido() + " " + persona.getSegundoApellido() +"</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<br>");
            out.println("<br>");
            
            out.println("<form action=\"consultarCliente\" method=\"post\">");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td>Digite la identificacion del cliente que quiere consultar</td>");
            out.println("<td><input type=\"text\" name=\"id\"/></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"consultar\"/></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
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
            Logger.getLogger(ListarClientes.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ListarClientes.class.getName()).log(Level.SEVERE, null, ex);
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
