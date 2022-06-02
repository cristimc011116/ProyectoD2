/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;
import controlador.ControladorUsuario;
import static controlador.ControladorUsuario.montoCorrecto;
import static controlador.ControladorUsuario.montoMoneda;
import dao.BitacoraDAO;
import dao.CuentaDAO;
import dao.OperacionDAO;
import dao.PersonaDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import util.Encriptacion;
import util.Mensaje;
import webService.ConsultaMoneda;
import webService.ConsultaMonedaSingleton;
/**
 *
 * @author Cristi Martínez
 */
public class Operacion {
    private int id;
    private LocalDate fechaOperacion;
    private String tipo;
    private boolean comision;
    private double montoComision;
    private String vista;
    private String hora;
    private static ArrayList<Bitacora> bitacoras;
    Cuenta cuenta = new Cuenta();

//-----------------------------------------------CONSTRUCTOR-----------------------------------------    
    public Operacion(int pId, LocalDate pFechaOperacion, String pTipo, boolean pComision, double pMontoComision)
    {
        setId(pId);
        setFechaOperacion(pFechaOperacion);
        setTipo(pTipo);
        setComision(pComision);
        setMontoComision(pMontoComision);
        
    }
    
    public Operacion(int pId, LocalDate pFechaOperacion, String pTipo, boolean pComision, double pMontoComision, String pVista, String pHora)
    {
        setId(pId);
        setFechaOperacion(pFechaOperacion);
        setTipo(pTipo);
        setComision(pComision);
        setMontoComision(pMontoComision);
        setVista(pVista);
        setHora(pHora);
        
    }

  public Operacion() {
    bitacoras = new ArrayList();
  }
   

//------------------------------------------METODOS DE CLASE----------------------------------------    
    
  public void agregarBitacora(Bitacora pBitacoras)
  {
    bitacoras.add(pBitacoras);
  }
  
  /*public static void crearBitacoras(LocalDate pFecha, String pHora, String pOperacion, String pVista, String pNumCuenta) throws ClassNotFoundException
  {
    int id = BitacoraDAO.cantBitacorasBD();
    Operacion operacion = new Operacion();
    String direccionArchivo = "C:/Users/Josue/OneDrive/Documentos/TEC/V semestre 2022/Diseño de software/Elementos XML/";
    //update( direccionArchivo,  pFecha,  pHora,  pOperacion,  pVista,  pNumCuenta, id);
    Cuenta.setExchangeRate(operacion);
  }*/
  
  public static void crearBitacoras(Operacion operacion, String pNumCuenta) throws ClassNotFoundException
  {
    //int id = BitacoraDAO.cantBitacorasBD();
    String direccionArchivo = "C:/Users/Josue/OneDrive/Documentos/TEC/V semestre 2022/Diseño de software/Elementos XML/";
    //update( direccionArchivo,  pFecha,  pHora,  pOperacion,  pVista,  pNumCuenta, id);
    Cuenta.setExchangeRate(operacion, pNumCuenta);
  }
  
  public static void cambiarPIN(String pCuenta, String pPinNuevo, String pVista) throws ClassNotFoundException
    {
      CuentaDAO.actualizarPin(pCuenta, pPinNuevo);
      insertarOperacion("Cambiar Pin", false , 0.00, pCuenta, pVista);
    }
    
    public static void realizarDeposito(double monto, String moneda, String cuenta, String pVista) throws ClassNotFoundException{
        double comision;
        double nuevoMonto;
        Cuenta cuentaBase = CuentaDAO.obtenerCuenta(cuenta);
        monto = montoCorrecto(monto, moneda);
        comision = ControladorUsuario.aplicaComisionRetiro(cuenta, monto, 3);
        nuevoMonto = monto + comision;
        String strSaldoViejo = cuentaBase.getSaldo();
        double saldoViejo = Double.parseDouble(strSaldoViejo);
        double nuevoSaldo = saldoViejo + monto - comision;
        String strNuevoSaldo = Double.toString(nuevoSaldo);
        cuentaBase.setSaldo(strNuevoSaldo);
        CuentaDAO.actualizarSaldo(cuenta, strNuevoSaldo);
        insertarOperacion("deposito", (comision>0.00) , comision, cuenta, pVista);
    }
    
    public static void realizarRetiro(double monto, String moneda, String cuenta, String pVista) throws ClassNotFoundException{
        double comision;
        double nuevoMonto;
        Cuenta cuentaBase = CuentaDAO.obtenerCuenta(cuenta);
        monto = montoCorrecto(monto, moneda);
        comision = ControladorUsuario.aplicaComisionRetiro(cuenta, monto, 3);
        nuevoMonto = monto + comision;
        String strSaldoViejo = cuentaBase.getSaldo();
        double saldoViejo = Double.parseDouble(strSaldoViejo);
        double nuevoSaldo = saldoViejo - nuevoMonto;
        String strNuevoSaldo = Double.toString(nuevoSaldo);
        cuentaBase.setSaldo(strNuevoSaldo);
        CuentaDAO.actualizarSaldo(cuenta, strNuevoSaldo);
        insertarOperacion("retiro", (comision>0.00) , comision, cuenta, pVista);
    }
    
    public static void realizarTransferencia(String cuentaOrigen, String cuentaDestino, double monto, String pVista) throws ClassNotFoundException{
        //rebajar saldo    
        Cuenta cuentaBaseOrigen = CuentaDAO.obtenerCuenta(cuentaOrigen);
        Cuenta cuentaBaseDestino = CuentaDAO.obtenerCuenta(cuentaDestino);
        double comision;
        double nuevoMonto;
        boolean aplicaCom = false;
        comision = 0.00;
        String strSaldoViejo = cuentaBaseOrigen.getSaldo();
        double saldoViejo = Double.parseDouble(strSaldoViejo);
        double nuevoSaldo = saldoViejo - monto;
        String strNuevoSaldo = Double.toString(nuevoSaldo);
        cuentaBaseOrigen.setSaldo(strNuevoSaldo);
        CuentaDAO.actualizarSaldo(cuentaOrigen, strNuevoSaldo);
        insertarOperacion("transferencia", false , comision, cuentaOrigen, pVista);

        //hacer transferencia

        String strSaldoViejoDestino = cuentaBaseDestino.getSaldo();
        double saldoViejoDestino = Double.parseDouble(strSaldoViejoDestino);
        double nuevoSaldoDestino = saldoViejoDestino + monto;
        String strNuevoSaldoDestino = Double.toString(nuevoSaldoDestino);
        cuentaBaseDestino.setSaldo(strNuevoSaldoDestino);
        CuentaDAO.actualizarSaldo(cuentaDestino, strNuevoSaldoDestino);
    }
    
    public static Double consultarCambioDolar(String opcion){
      Double resultado= 0.0;
      ConsultaMoneda consultaMoneda = ConsultaMonedaSingleton.getInstance();
      if (opcion == "compra"){
          resultado = consultaMoneda.consultaCambioCompra();
      }
      else if (opcion == "venta"){
          resultado = consultaMoneda.consultaCambioVenta();
      }
    return resultado;
    }
    
    public static String enviarMensaje(String numCuenta) throws ClassNotFoundException
    {
      int id = CuentaDAO.obtenerPersonaCuenta(numCuenta);
      Persona persona = PersonaDAO.obtenerPersona(id);
      int numero = persona.getNumero();
      String mensaje = Mensaje.crearPalabra();
      Mensaje.enviarMensaje(numero, mensaje);
      return mensaje;
    }
    
    public static String consultarSaldo(String pNumCenta) throws ClassNotFoundException
    {
      Cuenta cuenta = CuentaDAO.obtenerCuenta(pNumCenta);
      String saldo = cuenta.getSaldo();
      return saldo;
    }
    
    public static double consultarSaldoDolares(String pNumCenta) throws ClassNotFoundException
    {
      ConsultaMoneda consultaMoneda = ConsultaMonedaSingleton.getInstance();
      double cambio = consultaMoneda.consultaCambioVenta();
      Cuenta cuenta = CuentaDAO.obtenerCuenta(pNumCenta);
      String strSaldo = cuenta.getSaldo();
      double saldo = Double.parseDouble(strSaldo);
      double dolares = saldo/cambio;
      return dolares;
    }
    
    public static String consultarStatus(String pNumCenta) throws ClassNotFoundException
    {
      Cuenta cuenta = CuentaDAO.obtenerCuenta(pNumCenta);
      String status = cuenta.getEstatus();
      return status;
    }
    
    public static String consultarEstadoCuenta(String pNumCuenta, String moneda) throws ClassNotFoundException{
        ConsultaMoneda consultaMoneda = ConsultaMonedaSingleton.getInstance();
        String strSaldo = "";
        String strPin = "";
        String resultado = "";
        int contador = 0;
        String oper = "";
        Cuenta cuentaBase = CuentaDAO.obtenerCuenta(pNumCuenta);
        int idDueno = CuentaDAO.obtenerPersonaCuenta(pNumCuenta);
        String strSaldoColones = cuentaBase.getSaldo();
        double saldoColones = Double.parseDouble(strSaldoColones);
        double montoCorrec = montoMoneda(saldoColones, moneda);
        strSaldo = Double.toString(montoCorrec);
        Persona persona = PersonaDAO.obtenerPersona(idDueno);
        String nombreDueno = persona.getNombre() + " " + persona.getPrimerApellido() + " " + persona.getSegundoApellido();
        ArrayList<Operacion> operaciones = OperacionDAO.getOperacionesCuenta(pNumCuenta);
        for(Operacion operacion: operaciones)
        {
            if("colones".equals(moneda))
            {
                strPin = cuentaBase.getPin();
                LocalDate fecha = operacion.getFechaOperacion();
                String tipo = operacion.getTipo();
                double montoComision = operacion.getMontoComision();
                contador++;
                oper += "Operacion #" + contador + "\nFecha: " + fecha + "\nTipo: " + tipo + "\nComisión: " + montoComision + "\n\n";
            }
            else
            {
                double venta = consultaMoneda.consultaCambioVenta();
                strPin = cuentaBase.getPin();
                double comisionDolares = (operacion.getMontoComision()/venta);
                LocalDate fecha = operacion.getFechaOperacion();
                String tipo = operacion.getTipo();
                contador++;
                oper += "Operacion #" + contador + "\nFecha: " + fecha + "\nTipo: " + tipo + "\nComisión: " + comisionDolares + "\n\n";
            }
        }
        resultado += "Información de la cuenta\n\n" + "Número de cuenta: " + pNumCuenta + "\nPin encriptado de la cuenta: " + strPin
                        + "\nNombre del dueño: " + nombreDueno + "\nIdentificación del dueño: " + idDueno + "\nSaldo de la cuenta: " 
                        + strSaldo + "\n\n\nOperaciones de la cuenta: " + "\n\n" + oper;
        return resultado;
    }
    
    public static void insertarOperacion(String pTipo, boolean pEsComision, double pMontoComision, String pNumCuenta, String pVista) throws ClassNotFoundException
    {
      String direccionArchivo = "C:/Users/Josue/OneDrive/Documentos/TEC/V semestre 2022/Diseño de software/Elementos XML/";
      int id = OperacionDAO.cantOperacionesBD();
      int idBitacora = BitacoraDAO.cantBitacorasBD();
      LocalDate fecha = Cuenta.setFechaCreacion();
      String hora = Bitacora.obtenerHora();
      Operacion operacion = new Operacion(id, fecha, pTipo, pEsComision, pMontoComision, pVista, hora);
      OperacionDAO.insertarOperacion(operacion,fecha);
      OperacionDAO.asignarOperacionCuenta(operacion, pNumCuenta);
      BitacoraDAO.insertarBitacora(idBitacora, fecha, hora, pNumCuenta, pTipo, pVista);
      crearBitacoras(operacion, pNumCuenta);
    }
    
    public static double consultarGananciaCuentaBanco(String numCuenta) throws ClassNotFoundException{
        ArrayList<Operacion> operaciones = OperacionDAO.getOperacionesCuenta(numCuenta);
        double comisionTotal = 0.00;
        for(Operacion operacion: operaciones){
            comisionTotal += operacion.getMontoComision();
        }
        return comisionTotal;
    }
    
    public static double consultarGananciaBancoTotal() throws ClassNotFoundException{
        ArrayList<Operacion> operaciones = OperacionDAO.getOperacionesBD();
        double comisionTotal = 0.00;
        for(Operacion operacion: operaciones){
            comisionTotal += operacion.getMontoComision();
        }
        return comisionTotal;
    }
    
    public void consultarOperacionesRealizadas(String pNumCuenta) throws ClassNotFoundException{
        OperacionDAO.getOperacionesCuenta(pNumCuenta);
    }
    
    public void asignarOperacionACuenta(Cuenta cuenta){
        this.setCuenta(cuenta);
    }

    public static double sumarComisionesRetiros(String pnumCuenta) throws ClassNotFoundException
    {
      double suma = CuentaDAO.comisionesPorRetiro(pnumCuenta);
      return suma;
    }
    
    public static double sumarComisionesdepositos(String pnumCuenta) throws ClassNotFoundException
    {
      double suma = CuentaDAO.comisionesPorDeposito(pnumCuenta);
      return suma;
    }
    
    public static double sumarComisionesTotalesRetiros() throws ClassNotFoundException
    {
      ArrayList<Cuenta> listaCuentas = CuentaDAO.getCuentasBD();
      double sumaRetiros = 0.00;
      for(Cuenta cuenta: listaCuentas){
          String numCuenta = Encriptacion.desencriptar(cuenta.getNumero());
          sumaRetiros += sumarComisionesRetiros(numCuenta);
      }
      return sumaRetiros;
    }
    
    public static double sumarComisionesTotalesdepositos() throws ClassNotFoundException
    {
      ArrayList<Cuenta> listaCuentas = CuentaDAO.getCuentasBD();
      double sumaDepositos = 0.00;
      for(Cuenta cuenta: listaCuentas){
          String numCuenta = Encriptacion.desencriptar(cuenta.getNumero());
          sumaDepositos += sumarComisionesdepositos(numCuenta);
      }
      return sumaDepositos;
    }
    
//-------------------------------------METODOS ACCESORES--------------------------------------------------
    
    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(LocalDate fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isComision() {
        return comision;
    }

    public void setComision(boolean comision) {
        this.comision = comision;
    }

    public double getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(double montoComision) {
        this.montoComision = montoComision;
    }

    public String getVista() {
        return vista;
    }

    public void setVista(String vista) {
        this.vista = vista;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    

}

