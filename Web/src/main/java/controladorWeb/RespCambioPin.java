/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import controlador.ControladorUsuario;
import static controlador.ControladorUsuario.auxNumCuentaP1;
import static controladorWeb.MostrarCuenta.validarIngreso;
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
import util.Encriptacion;
import validacion.ExpresionesRegulares;

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "respCambioPin", urlPatterns = {"/respCambioPin"})
public class RespCambioPin extends HttpServlet {
    static int cont = 0;
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
        String pinA = request.getParameter("pinA");
        String pinN = request.getParameter("pinN");
        int contador = 0;
        int insertar = 0;
        contador += validarIngreso(numero, "cuenta");
        contador += validarIngreso(pinA, "pin actual");
        contador += validarIngreso(pinN, "pin nuevo");
        if(contador == 0)
        {
          insertar += validarCuentaPinCambio(numero,  pinA, pinN);
          if (insertar == 0){
            Operacion.cambiarPIN(numero, pinN);
            try ( PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Cambio PIN</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1 align=\"center\">Cambio de PIN a cuenta</h1>");
                out.println("<br>");
                out.println("<br>");
                out.println("<br>");
                out.println("<table border=\"1\" align=\"center\">");
                out.println("<tr>");
                out.println("<td align=\"center\">Estimado usuario, se ha cambiado satisfactoriamente el PIN de su cuenta " + numero + "</td>");
                out.println("</tr>");
                out.println("</table>");
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
                out.println("<h1 align=\"center\">Verifique sus datos</h1>");
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
    
    public int validarCuentaPinCambio(String numCuenta, String pin, String pinNuevo) throws ClassNotFoundException
    {
      int insertar = 0;
      insertar += validarEntrCuenta(numCuenta);
      if(insertar==0)
      {
        insertar += validarPinCambio(numCuenta, pin, pinNuevo);
        if(insertar==0)
        {
           return 0;
        }
      }
      return 1;
    }
    
    public int validarEntrCuenta(String numCuenta) throws ClassNotFoundException
    {
        if(auxNumCuentaP1(numCuenta))
        {
            return 0;
        }
        return 1;
    }
    
    public int validarPinCambio(String pNumCuenta, String pPin, String pinNuevo) throws ClassNotFoundException
    {
      if(esPinCuentaCambioPin(pNumCuenta, pPin) & ExpresionesRegulares.validarPin(pinNuevo))
      {
        return 0;
      }
      return 1;
    }
    
    public boolean esPinCuentaCambioPin(String pNumCuenta, String pin) throws ClassNotFoundException
    {
      Cuenta cuenta = CuentaDAO.obtenerCuenta(pNumCuenta);
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
            Logger.getLogger(RespCambioPin.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RespCambioPin.class.getName()).log(Level.SEVERE, null, ex);
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
