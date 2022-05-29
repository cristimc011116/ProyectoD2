/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import controlador.ControladorUsuario;
import static controladorWeb.MostrarCuenta.validarEntrId;
import static controladorWeb.MostrarCuenta.validarEntrMonto;
import static controladorWeb.MostrarCuenta.validarEntrPin;
import static controladorWeb.MostrarCuenta.validarIngreso;
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
@WebServlet(name = "consultarCliente", urlPatterns = {"/consultarCliente"})
public class ConsultarCliente extends HttpServlet {
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
        String strId =request.getParameter("id");
        int contador = 0;
        int insertar = 0;
        contador += validarIngreso(strId, "identificacion");
        if(contador==0){
            insertar += validarEntrId(strId);
            if(insertar==0){
                int id = Integer.parseInt(strId);
                Persona persona = PersonaDAO.obtenerPersona(id);
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
                    out.println("<td>"+ persona.getNombre() + " " + persona.getPrimerApellido() + " " + persona.getSegundoApellido() +"</td>");
                    out.println("<td>"+ persona.getId() +"</td>");
                    out.println("<td>"+ persona.getCodigo() +"</td>");
                    out.println("<td>"+ persona.getFechaNacimiento() +"</td>");
                    out.println("<td>"+ persona.getNumero() +"</td>");
                    out.println("<td>"+ persona.getCorreo() +"</td>");
                    out.println("<td>"+ persona.getRol() +"</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan=\"7\" align=\"center\">Cuentas asignadas</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan=\"7\" align=\"center\">"+ CuentaDAO.obtenerCuentasPersona(id) +"</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }else{
                int insertar1 = 0;
                String mensaje = "";
                insertar1 += validarEntrId(strId);
                if(insertar1==1){
                    mensaje += "identificación ";
                }
                try ( PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Error</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1 align=\"center\">Error en: " + mensaje + "</h1>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }
        }else{
            try ( PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1 align=\"center\">Complete todos sus datos</h1>");
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
            Logger.getLogger(ConsultarCliente.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConsultarCliente.class.getName()).log(Level.SEVERE, null, ex);
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
