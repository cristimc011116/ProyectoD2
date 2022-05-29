/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logicadenegocios.Operacion;

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "realizarTransferencia", urlPatterns = {"/realizarTransferencia"})
public class RealizarTransferencia extends HttpServlet {

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
        String pin = request.getParameter("pin");
        String mensaje = Operacion.enviarMensaje(numero);
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Realizar transferencia</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 align=\"center\">Proceda a realizar la transferencia</h1>");
            out.println("<br>");
            out.println("<br>");
            out.println("<div align=\"center\">Complete los datos</div>");
            out.println("<br>");
            
            out.println("<form action=\"respTransferencia\" method=\"post\">");
            out.println("<table border=\"1\" align=\"center\">");
            out.println("<tr>");
            out.println("<td>Digite el numero de cuenta</td>");
            out.println("<td><input type=\"text\" name=\"numero\"/></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Digite el pin de la cuenta</td>");
            out.println("<td><input type=\"text\" name=\"pin\"/></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Digite la palabra previamente enviada</td>");
            out.println("<td><input type=\"text\" name=\"palabra\"/></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Digite el monto a tranferir</td>");
            out.println("<td><input type=\"text\" name=\"monto\"/></td>");
            out.println("</tr>");
             out.println("<tr>");
            out.println("<td>Digite la cuenta de destino</td>");
            out.println("<td><input type=\"text\" name=\"cuentaDestino\"/></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"transferir\"/></td>");
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
            Logger.getLogger(RealizarTransferencia.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RealizarTransferencia.class.getName()).log(Level.SEVERE, null, ex);
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
