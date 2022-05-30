/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import GUI.Palabra;
import static controlador.ControladorUsuario.auxNumCuentaP1;
import static controladorWeb.MostrarCuenta.validarIngreso;
import dao.CuentaDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
import util.Encriptacion;

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "realizarRetiro", urlPatterns = {"/realizarRetiro"})
public class RealizarRetiro extends HttpServlet {
    static int cont = 0;
    public Palabra palabra;
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
        this.palabra = new Palabra();
        String numero =request.getParameter("numero");
        String pin = request.getParameter("pin");
        int contador = 0;
        int insertar = 0;
        contador += validarIngreso(numero, "cuenta");
        contador += validarIngreso(pin, "pin");
        if(contador==0){
            insertar += validarCuentaPin2(numero, pin);
            if(insertar==0){
        
                String mensaje = Operacion.enviarMensaje(numero);
                this.palabra.lbPalabra.setText(mensaje);
                try ( PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Realizar retiro</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1 align=\"center\">Proceda a realizar el retiro</h1>");
                    out.println("<br>");
                    out.println("<br>");
                    out.println("<h3 align=\"center\">Estimado usuario se ha enviado una palabra por mensaje de texto, por favor revise sus mensajes y proceda a digitar la palabra enviada</h3>");
                    out.println("<div align=\"center\">Complete los datos</div>");
                    out.println("<br>");

                    out.println("<form action=\"respRetiro\" method=\"post\">");
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
                    out.println("<td>Digite el monto a retirar</td>");
                    out.println("<td><input type=\"text\" name=\"monto\"/></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td>Digite la moneda del deposito</td>");
                    out.println("<td><input type=\"text\" name=\"moneda\"/></td>");
                    out.println("</tr>");
                    
                    out.println("<tr>");
                    out.println("<td>Marque si posee los datos completos</td>");
                    out.println("<td><input name=\"Respuesta\" type=\"radio\" value=\""+mensaje+"\"></td>");
                    out.println("</tr>");
                    
                    out.println("<tr>");
                    out.println("<td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"retirar\"/></td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</form>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }else{
                try ( PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1 align=\"center\">Error en cuenta o pin</h1>");
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
    
    public static int validarCuentaPin2(String numCuenta, String pin) throws ClassNotFoundException
    {
      int insertar = 0;
      insertar += validarEntrCuenta(numCuenta);
      if(insertar==0)
      {
        insertar += validarPin2(numCuenta, pin);
        if(insertar==0)
        {
          return 0;
        }
      }
      return 1;
    }
    
    public static int validarEntrCuenta(String numCuenta) throws ClassNotFoundException
    {
        if(auxNumCuentaP1(numCuenta))
        {
            return 0;
        }
        return 1;
    }
    
    public static int validarPin2(String pNumCuenta, String pPin) throws ClassNotFoundException
    {
      if(esPinCuenta2(pNumCuenta, pPin))
      {
        return 0;
      }
      return 1;
    }
    
    public static boolean esPinCuenta2(String pNumCuenta, String pin) throws ClassNotFoundException
    {
       
      Cuenta cuenta = CuentaDAO.obtenerCuenta(pNumCuenta);
      int contador;
      String pinDesencriptado = Encriptacion.desencriptar(cuenta.getPin());
      
      if (!pin.equals(pinDesencriptado))
      {
          cont++;

        if(cont >= 2)
        {
          Cuenta.inactivarCuenta(pNumCuenta);
          JOptionPane.showMessageDialog(null, "Se ha desactivado la cuenta por el ingreso del pin incorrecto");
        }
        return false;
      }
      return true;
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
            Logger.getLogger(RealizarRetiro.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RealizarRetiro.class.getName()).log(Level.SEVERE, null, ex);
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
