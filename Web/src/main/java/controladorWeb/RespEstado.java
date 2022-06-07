/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladorWeb;

import static CLI.CLI.montoValidoDeposito;
import GUI.ConsultarEstadoCuenta;
import controlador.ControladorUsuario;
import static controlador.ControladorUsuario.auxNumCuentaP1;
import static controlador.ControladorUsuario.montoMoneda;
import static controladorWeb.MostrarCuenta.validarIngreso;
import dao.CuentaDAO;
import dao.OperacionDAO;
import dao.PersonaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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
import util.Encriptacion;
import webService.ConsultaMoneda;
import logicacreacional.ConsultaMonedaSingleton;

/**
 *
 * @author Cristi Martínez
 */
@WebServlet(name = "respEstado", urlPatterns = {"/respEstado"})
public class RespEstado extends HttpServlet {
    public ConsultarEstadoCuenta vista4;
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
        this.vista4 = new ConsultarEstadoCuenta();
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
            contador += validarIngreso(moneda, "moneda");
            if(contador==0){
                insertar += validarCuentaPin2(numero, pin);
                if(insertar==0){
                    Cuenta cuenta = CuentaDAO.obtenerCuenta(numero);
                    ConsultaMoneda consultaMoneda = ConsultaMonedaSingleton.getInstance();
                    int idDueno = CuentaDAO.obtenerPersonaCuenta(numero);
                    String strIdDueno = Integer.toString(idDueno);
                    String strSaldoColones = cuenta.getSaldo();
                    double saldoColones = Double.parseDouble(strSaldoColones);
                    double montoCorrec = montoMoneda(saldoColones, moneda);
                    String strMontoCorrec = Double.toString(montoCorrec);
                    Persona persona = PersonaDAO.obtenerPersona(idDueno);
                    String nombreDueno = persona.getNombre() + " " + persona.getPrimerApellido() + " " + persona.getSegundoApellido();
                    ArrayList<Operacion> operaciones = OperacionDAO.getOperacionesCuenta(numero);
                    try ( PrintWriter out = response.getWriter()) {
                        /* TODO output your page here. You may use following sample code. */
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Estado de cuenta</title>");            
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h2 align=\"center\">Estado de cuenta</h2>");
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
                          if("colones".equals(moneda))
                          {
                            out.println("<tr>");
                            out.println("<td>"+ operacion.getFechaOperacion() +"</td>");
                            out.println("<td>"+ operacion.getTipo() +"</td>");
                            out.println("<td>"+ operacion.getMontoComision() +"</td>");
                            out.println("</tr>");
                          }
                          else
                          {
                            double venta = consultaMoneda.consultaCambioVenta();
                            double comisionDolares = (operacion.getMontoComision()/venta);
                            out.println("<tr>");
                            out.println("<td>"+ operacion.getFechaOperacion() +"</td>");
                            out.println("<td>"+ operacion.getTipo() +"</td>");
                            out.println("<td>"+ comisionDolares +"</td>");
                            out.println("</tr>");
                          }
                        }
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
      //String cont;
      int contador;
      String pinDesencriptado = Encriptacion.desencriptar(cuenta.getPin());
      
      if (!pin.equals(pinDesencriptado))
      {
        //cont = this.vista4.lbintentos.getText();
        //contador = Integer.parseInt(cont);
        //contador++;
          cont++;
        //cont = Integer.toString(contador);

        if(cont >= 2)
        {
          //this.vista4.lbintentos.setText("2");
          Cuenta.inactivarCuenta(pNumCuenta, "Hola, se ha desactivado la cuenta por motivo del ingreso incorrecto del pin");
          JOptionPane.showMessageDialog(null, "Se ha desactivado la cuenta por el ingreso del pin incorrecto");
        }
        else
        {
          //this.vista4.lbintentos.setText(cont);
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
            Logger.getLogger(RespEstado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RespEstado.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RespEstado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RespEstado.class.getName()).log(Level.SEVERE, null, ex);
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
