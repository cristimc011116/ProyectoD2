/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import static CLI.CLI.montoValidoDeposito;
import controlador.ControladorUsuario;
import dao.CuentaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "respDeposito", urlPatterns = {"/respDeposito"})
public class RespDeposito extends HttpServlet {
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
        String monto = request.getParameter("monto");
        String moneda = request.getParameter("moneda");
        Cuenta cuentaBase = CuentaDAO.obtenerCuenta(numero);
        if(!"inactiva".equals(cuentaBase.getEstatus()))
        {
            double montoD = Double.parseDouble(monto);
            double montoCorrecto = montoValidoDeposito(montoD, numero, moneda);
            double comision = ControladorUsuario.aplicaComision(numero, montoCorrecto);
            Operacion.realizarDeposito(montoD, moneda, numero, "WEB");
            String resultado = ControladorUsuario.imprimirResultadoDeposito(moneda, comision, montoCorrecto,numero);
            try ( PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Realizar depósito</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<br>");
                out.println("<h2 align=\"center\">Resultado del depósito</h2>");
                out.println("<br>");
                out.println("<br>");
                out.println("<br>");
                out.println("<h3 align=\"center\">" + resultado + "</h3>");
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
                    out.println("<h1 align=\"center\">Su cuenta se encuentra desactivada</h1>");
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
            Logger.getLogger(RespDeposito.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RespDeposito.class.getName()).log(Level.SEVERE, null, ex);
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
