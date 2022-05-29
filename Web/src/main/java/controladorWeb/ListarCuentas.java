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
import util.Encriptacion;

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "listarCuenta", urlPatterns = {"/listarCuenta"})
public class ListarCuentas extends HttpServlet {
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
        
        ArrayList<Cuenta> listaCuentas = Cuenta.ordenarCuentas();
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Listar cuentas</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h2 align=\"center\">Cuentas registradas en el sistema</h2>");
            out.println("<br>");
            out.println("<br>");
            out.println("<br>");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td>Número de cuenta</td>");
            out.println("<td>Estatus</td>");
            out.println("<td>Saldo</td>");
            out.println("</tr>");
            for(Cuenta cuenta: listaCuentas){
                out.println("<tr>");
                out.println("<td>"+ Encriptacion.desencriptar(cuenta.getNumero()) +"</td>");
                out.println("<td>"+ cuenta.getEstatus() +"</td>");
                out.println("<td>"+ Encriptacion.desencriptar(cuenta.getSaldo()) +"</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<br>");
            out.println("<br>");
            
            out.println("<form action=\"consultarCuenta\" method=\"post\">");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td>Digite el número de cuenta que desea consultar</td>");
            out.println("<td><input type=\"text\" name=\"numero\"/></td>");
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
            Logger.getLogger(ListarCuentas.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ListarCuentas.class.getName()).log(Level.SEVERE, null, ex);
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
