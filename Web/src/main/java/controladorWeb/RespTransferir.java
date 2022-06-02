/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import static CLI.CLI.montoValido;
import static CLI.CLI.montoValidoDeposito;
import static CLI.CLI.pedirNumCuenta;
import controlador.ControladorUsuario;
import static controlador.ControladorUsuario.auxNumCuentaP1;
import static controladorWeb.MostrarCuenta.validarEntrMonto;
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
@WebServlet(name = "respTransferencia", urlPatterns = {"/respTransferencia"})
public class RespTransferir extends HttpServlet {
    static int contPin = 0;
    static int contPalabra = 0;
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
        String numeroDestino =request.getParameter("cuentaDestino");
        String pin =request.getParameter("pin");
        String palabra =request.getParameter("palabra");
        String monto = request.getParameter("monto");
        String palabraClave = request.getParameter("Respuesta");
        int insertar = 0;
        int contador = 0;
        Cuenta cuentaBase = CuentaDAO.obtenerCuenta(numero);
        if(!"inactiva".equals(cuentaBase.getEstatus()))
        {
            contador += validarIngreso(numero, "cuenta");
            contador += validarIngreso(pin, "pin");
            contador += validarIngreso(palabra, "palabra clave");
            contador += validarIngreso(monto, "monto");
            contador += validarIngreso(numeroDestino, "cuenta destino");
            contador += validarEntrMonto(monto);

            if(contador == 0)
            {
              insertar += validarCuentaPin2(numero, pin);
              insertar += validarPalabra(palabra, numero, palabraClave);
              if (insertar == 0)
              {
                double montoD = Double.parseDouble(monto);
                double montoCorrecto = montoValido(montoD, numero, "colones");
                Operacion.realizarTransferencia(numero, numeroDestino, montoD, "WEB");
                String resultado = ControladorUsuario.imprimirResultadoTransf(montoCorrecto);
                try ( PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Transferencia</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<br>");
                    out.println("<h2 align=\"center\">Resultado de la transferencia</h2>");
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
          
    public static int validarCuentaPin2(String numCuenta, String pin) throws ClassNotFoundException, Exception
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
    
    public static int validarPin2(String pNumCuenta, String pPin) throws ClassNotFoundException, Exception
    {
      if(esPinCuenta2(pNumCuenta, pPin))
      {
        return 0;
      }
      return 1;
    }
    
    public static boolean esPinCuenta2(String pNumCuenta, String pin) throws ClassNotFoundException, Exception
    {
       
      Cuenta cuenta = CuentaDAO.obtenerCuenta(pNumCuenta);
      int contador;
      String pinDesencriptado = Encriptacion.desencriptar(cuenta.getPin());
      
      if (!pin.equals(pinDesencriptado))
      {
          contPin++;

        if(contPin >= 2)
        {
          Cuenta.inactivarCuenta(pNumCuenta, "Hola, se ha desactivado la cuenta por motivo del ingreso incorrecto del pin");
          JOptionPane.showMessageDialog(null, "Se ha desactivado la cuenta por el ingreso del pin incorrecto");
        }
        return false;
      }
      return true;
    }
    
    public int validarPalabra(String pPalabra, String pNumCuenta, String palabraClave) throws ClassNotFoundException, Exception
    {
      if(pedirPalabra(pPalabra, pNumCuenta, palabraClave))
      {
        return 0;
      }
      return 1;
    }
    
    public boolean pedirPalabra(String pPalabra, String pNumCuenta, String palabraClave) throws ClassNotFoundException, Exception
    {
      Cuenta cuenta = CuentaDAO.obtenerCuenta(pNumCuenta);
      String cont;
      int contador;
      String mensaje = "";
      if (!pPalabra.equals(palabraClave))
      {
        contPalabra++;
        if(contPalabra >= 2)
        {
          Cuenta.inactivarCuenta(pNumCuenta, "Hola, se ha desactivado la cuenta por motivo del ingreso incorrecto de la palabra clave");
          JOptionPane.showMessageDialog(null, "Se ha desactivado la cuenta por el ingreso incorrecto de la palabra clave");
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
            Logger.getLogger(RespTransferir.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RespTransferir.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RespTransferir.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RespTransferir.class.getName()).log(Level.SEVERE, null, ex);
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
