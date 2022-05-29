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
import java.text.DecimalFormat;
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
import logicadenegocios.Persona;
import validacion.ExpresionesRegulares;

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "mostrarCuenta", urlPatterns = {"/imprimirCuenta"})
public class MostrarCuenta extends HttpServlet {
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
        String pin = request.getParameter("pin");
        String monto = request.getParameter("monto");
        int contador = 0;
        int insertar = 0;
        contador += validarIngreso(strId, "identificacion");
        contador += validarIngreso(pin, "pin");
        contador += validarIngreso(monto, "monto");
        if(contador==0){
            insertar += validarEntrId(strId);
            insertar += validarEntrPin(pin);
            insertar += validarEntrMonto(monto);
            if(insertar==0){
                int id = Integer.parseInt(strId);
                String numero = Cuenta.insertarCuenta(pin, monto, id);
                Cuenta cuenta = CuentaDAO.obtenerCuenta(numero);
                DecimalFormat df = new DecimalFormat("#.00");
                String strSaldo = cuenta.getSaldo();
                double saldo = Double.parseDouble(strSaldo);
                Persona persona = PersonaDAO.obtenerPersona(id);
                try ( PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Cuenta creada </title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1 align=\"center\">Información de la cuenta</h1>");
                    out.println("<br>");
                    out.println("<br>");
                    out.println("<div align=\"center\">Se ha creado una nueva cuenta en el sistema, los datos de la cuenta son:</div>");
                    out.println("<br>");
                    out.println("<table border=\"1\" align=\"center\">");
                    out.println("<tr>");
                    out.println("<td colspan=\"6\" align=\"center\">Información de la cuenta" + "</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan=\"2\" align=\"center\">Número de cuenta</td>");
                    out.println("<td colspan=\"2\" align=\"center\">Estatus de la cuenta</td>");
                    out.println("<td colspan=\"2\" align=\"center\">Saldo actual</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan=\"2\" align=\"center\">" + numero + "</td>");
                    out.println("<td colspan=\"2\" align=\"center\">" + cuenta.getEstatus() + "</td>");
                    out.println("<td colspan=\"2\" align=\"center\">" + (df.format(saldo)) + "</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("<br>");
                    out.println("<br>");
                    out.println("<table border=\"1\" align=\"center\">");
                    out.println("<tr>");
                    out.println("<td colspan=\"6\" align=\"center\">Información del dueño de la cuenta" + "</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan=\"2\" align=\"center\">Nombre del dueño de la cuenta</td>");
                    out.println("<td colspan=\"2\" align=\"center\">Número de teléfono 'asociado' a la cuenta</td>");
                    out.println("<td colspan=\"2\" align=\"center\">Dirección de correo electrónico 'asociada' a la cuenta</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan=\"2\" align=\"center\">" + persona.getNombre() + " " + persona.getPrimerApellido() + " " + persona.getSegundoApellido() + "</td>");
                    out.println("<td colspan=\"2\" align=\"center\">" + persona.getNumero() + "</td>");
                    out.println("<td colspan=\"2\" align=\"center\">" + persona.getCorreo() + "</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }else{
                int insertar1 = 0;
                int insertar2 = 0;
                int insertar3 = 0;
                String mensaje = "";
                insertar1 += validarEntrId(strId);
                insertar2 += validarEntrPin(pin);
                insertar3 += validarEntrMonto(monto);
                if(insertar1==1){
                    mensaje += "identificación ";
                }
                if(insertar2==1){
                    mensaje += "pin ";
                }
                if(insertar3==1){
                    mensaje += "monto";
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
    
    public static int validarIngreso(String pEntrada, String opcion)
    {
      if(pEntrada.length() == 0)
      {
        return 1;
      }
      return 0;
    }
    
    public static int validarEntrId(String strId) throws ClassNotFoundException
    {
      boolean esId = ControladorUsuario.auxIdP1(strId);
      if (esId==false)
      {
        return 1;
      }
      return 0;
    }
    
    public static int validarEntrPin(String pin)
    {
      boolean esPin = ExpresionesRegulares.validarPin(pin);
      if (esPin == false)
      {
        return 1;
      }
      return 0;
    }
    
    public static int validarEntrMonto(String strMonto)
    {
      boolean esNum = ExpresionesRegulares.esNumero(strMonto);
      if (esNum == false)
      {
        return 1;
      }
      return 0;
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
            Logger.getLogger(MostrarCuenta.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MostrarCuenta.class.getName()).log(Level.SEVERE, null, ex);
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
