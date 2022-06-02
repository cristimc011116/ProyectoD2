/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import static dao.AdmiDAO.iniciarSesion;
import static dao.BitacoraDAO.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static util.ConsultaBitacora.*;

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "ConsultasAdmi", urlPatterns = {"/ConsultasAdmi"})
public class ConsultaRegistrosTODO extends HttpServlet {

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
        
        ArrayList<String> nombre;
        ArrayList<String> nombre2;
        ArrayList<String> nombre3;
        nombre = buscarNombreBitacora("CLI");
        nombre2 = buscarNombreBitacora("GUI");
        nombre3 = buscarNombreBitacora("WEB");
                
        String BitacorasCLI = imprimirArchivosTXT(nombre) + imprimirArchivosXML(nombre) + imprimirArchivosCSV(nombre);
        String BitacorasGUI = imprimirArchivosTXT(nombre2) + imprimirArchivosXML(nombre2) + imprimirArchivosCSV(nombre2);
        String BitacorasWEB = imprimirArchivosTXT(nombre3) + imprimirArchivosXML(nombre3) + imprimirArchivosCSV(nombre3);
        
           try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Consultas de todas las operaciones realizadas</title>");            
            out.println("</head>");
            out.println("<body>");
            
            //imprimir texto
            
            out.println("<tr>");
            out.println("<td>"+BitacorasCLI+"</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>"+BitacorasGUI+"</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>"+BitacorasWEB+"</td>");
            out.println("</tr>");
            

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
            Logger.getLogger(ConsultaRegistrosTODO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConsultaRegistrosTODO.class.getName()).log(Level.SEVERE, null, ex);
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
