/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import controlador.ControladorUsuario;
import static controlador.ControladorUsuario.montoMoneda;
import dao.CuentaDAO;
import dao.OperacionDAO;
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
import logicadenegocios.Operacion;
import logicadenegocios.Persona;
import webService.ConsultaMoneda;

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "consultarCuenta", urlPatterns = {"/consultarCuenta"})
public class ConsultarCuenta extends HttpServlet {
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
        String numero =request.getParameter("numero");
        Cuenta cuenta = CuentaDAO.obtenerCuenta(numero);
        int idDueno = CuentaDAO.obtenerPersonaCuenta(numero);
        String strIdDueno = Integer.toString(idDueno);
        String strSaldoColones = cuenta.getSaldo();
        double saldoColones = Double.parseDouble(strSaldoColones);
        double montoCorrec = montoMoneda(saldoColones, "colones");
        String strMontoCorrec = Double.toString(montoCorrec);
        Persona persona = PersonaDAO.obtenerPersona(idDueno);
        String nombreDueno = persona.getNombre() + " " + persona.getPrimerApellido() + " " + persona.getSegundoApellido();
        ArrayList<Operacion> operaciones = OperacionDAO.getOperacionesCuenta(numero);
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Consulta de cuenta</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h2 align=\"center\">Consulta de cuenta</h2>");
            out.println("<br>");
            out.println("<br>");
            out.println("<br>");
            out.println("<h3 align=\"center\">Número de cuenta: " + numero + "</h3>");
            out.println("<h3 align=\"center\">Pin de la cuenta: " + cuenta.getPin() + "</h3>");
            out.println("<h3 align=\"center\">Saldo de la cuenta: " + montoCorrec + "</h3>");
            out.println("<h3 align=\"center\">Identificación del dueño: " + idDueno + "</h3>");
            out.println("<h3 align=\"center\">Nombre del dueño: " + nombreDueno + "</h3>");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td>Fecha</td>");
            out.println("<td>Tipo</td>");
            out.println("<td>Comision</td>");
            for(Operacion operacion: operaciones)
            {
                out.println("<tr>");
                out.println("<td>"+ operacion.getFechaOperacion() +"</td>");
                out.println("<td>"+ operacion.getTipo() +"</td>");
                out.println("<td>"+ operacion.getMontoComision() +"</td>");
                out.println("</tr>");
            }
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
            Logger.getLogger(ConsultarCuenta.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConsultarCuenta.class.getName()).log(Level.SEVERE, null, ex);
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
