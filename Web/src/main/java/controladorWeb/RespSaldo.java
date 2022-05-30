/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import static CLI.CLI.montoValidoDeposito;
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

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "respSaldo", urlPatterns = {"/respSaldo"})
public class RespSaldo extends HttpServlet {
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
            throws ServletException, IOException, ClassNotFoundException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        String numero =request.getParameter("numero");
        String pin = request.getParameter("pin");
        String moneda = request.getParameter("moneda");
        int contador = 0;
        int insertar = 0; 
        Cuenta cuentaBase = CuentaDAO.obtenerCuenta(numero);
        if(!"inactiva".equals(cuentaBase.getEstatus()))
        {
            contador += validarIngreso(numero, "cuenta");
            contador += validarIngreso(pin, "pin");
            if(contador == 0)
            {
              insertar += validarCuentaPinSaldo(numero, pin);
              if (insertar == 0)
              {
                Cuenta cuenta = CuentaDAO.obtenerCuenta(numero);
                String saldo = cuenta.getSaldo();
                String resultado = ControladorUsuario.imprimirResultadoConsultaSaldo( moneda,  saldo);
                try ( PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>El resultado de la consulta es: </title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<br>");
                    out.println("<h2 align=\"center\">Consulta de saldo</h2>");
                    out.println("<br>");
                    out.println("<br>");
                    out.println("<br>");
                    out.println("<h3 align=\"center\">" + resultado + "</h3>");
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
                    out.println("<h1 align=\"center\">Verifique sus datos por favor</h1>");
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
    
    public int validarCuentaPinSaldo(String numCuenta, String pin) throws ClassNotFoundException, Exception
    {
      int insertar = 0;
      insertar += validarEntrCuenta(numCuenta);
      if(insertar==0)
      {
        insertar += validarPinSaldo(numCuenta, pin);
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
    
    public int validarPinSaldo(String pNumCuenta, String pPin) throws ClassNotFoundException, Exception
    {
      if(esPinCuentaConsultaSaldo(pNumCuenta, pPin))
      {
        return 0;
      }
      return 1;
    }
    
    public boolean esPinCuentaConsultaSaldo(String pNumCuenta, String pin) throws ClassNotFoundException, Exception
    {
      Cuenta cuenta = CuentaDAO.obtenerCuenta(pNumCuenta);
      String pinDesencriptado = Encriptacion.desencriptar(cuenta.getPin());
      if (!pin.equals(pinDesencriptado))
      {
        cont++;

        if(cont >= 2)
        {
          Cuenta.inactivarCuenta(pNumCuenta, "Hola, se ha desactivado la cuenta por motivo del ingreso incorrecto del pin");
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
            Logger.getLogger(RespSaldo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RespSaldo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RespSaldo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RespSaldo.class.getName()).log(Level.SEVERE, null, ex);
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
