/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import controlador.ControladorUsuario;
import dao.CuentaDAO;
import dao.PersonaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "mostrarCliente", urlPatterns = {"/imprimirCliente"})
public class MostrarCliente extends HttpServlet {
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
        String primerApellido =request.getParameter("primerApellido");
        String segundoApellido =request.getParameter("segundoApellido");
        String nombre =request.getParameter("nombre");
        String strId =request.getParameter("id");
        int id = Integer.parseInt(strId);
        String fechaNacimiento =request.getParameter("fechaNacimiento");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);
        String numero = request.getParameter("numero");
        int intNum = Integer.parseInt(numero);
        String correo = request.getParameter("correo");
        Persona num = Persona.insertarCliente(primerApellido,segundoApellido,nombre,id,fechaNac,intNum,correo);
        //mensaje = nombre.toStringCompleto();
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Información del cliente</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h2 align=\"center\">Información del cliente</h2>");
            out.println("<br>");
            out.println("<br>");
            out.println("<br>");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td>Nombre completo</td>");
            out.println("<td>Identificacion</td>");
            out.println("<td>Código</td>");
            out.println("<td>Fecha de nacimiento</td>");
            out.println("<td>Número de teléfono</td>");
            out.println("<td>Correo</td>");
            out.println("<td>Rol</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>"+ nombre + " " + primerApellido + " " + segundoApellido +"</td>");
            out.println("<td>"+ id +"</td>");
            out.println("<td>"+ num.getCodigo() +"</td>");
            out.println("<td>"+ fechaNac +"</td>");
            out.println("<td>"+ num.getNumero() +"</td>");
            out.println("<td>"+ num.getCorreo() +"</td>");
            out.println("<td>"+ num.getRol() +"</td>");
            out.println("</tr>");
            out.println("</table>");
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
            Logger.getLogger(MostrarCliente.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MostrarCliente.class.getName()).log(Level.SEVERE, null, ex);
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
