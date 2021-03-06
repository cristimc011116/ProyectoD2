/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;
import dao.CuentaDAO;
import dao.PersonaDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;
import java.util.Collections;
import javax.swing.JOptionPane;
import util.CorreoElectronico;
import util.Encriptacion;
import util.IdiomaCorreoDecorator;
/**
 *
 * @author Cristi Martínez
 */
public class Cuenta implements Comparable<Cuenta>{
    private String numero;
    private String pin;
    private LocalDate fechaCreacion;
    private String saldo;
    private String estatus;
    private Persona dueno;
    private ArrayList<Operacion> operaciones;
    
    private static Operacion exchangeRate;
    private static ArrayList<Bitacora> observers;
  
    
//---------------------------------------------CONSTRUCTORES-------------------------------------    
    public Cuenta(String pNumero, String pPin, LocalDate pFechaCreacion, String pSaldo, String pEstatus)
    {
        setNumero(pNumero);
        setPin(pPin);
        setFechaCreacion();
        setSaldo(pSaldo);
        setEstatus(pEstatus);
        this.operaciones = new ArrayList<>();   
        this.observers = new ArrayList<>();
    }
    
    public Cuenta(String pNumero, String pEstatus, String pSaldo)
    {
        setNumero(pNumero);
        setSaldo(pSaldo);
        setEstatus(pEstatus);
        this.operaciones = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public Cuenta()
    {
        setNumero("");
        setSaldo("");
        this.operaciones = new ArrayList<>();
        this.observers = new ArrayList<>();
    }
    
    //--------------------------------------METODOS DE CLASE--------------------------------------
    public static String insertarCuenta(String pPin, String pMonto, int pId) throws ClassNotFoundException
    {
      String numero = Cuenta.generarNumCuenta();
      LocalDate fecha = Cuenta.setFechaCreacion();
      Cuenta cuenta = new Cuenta(numero, pPin, fecha, pMonto, "activo");
      CuentaDAO.insertarCuenta(cuenta,fecha);
      CuentaDAO.asignarCuentaCliente(cuenta, pId);

      return numero;
    }
    
    public void asignarDueno(Persona persona){
        this.setDueno(persona);
    }
    
    public void asignarOperacion(Operacion pOperacion){
        this.operaciones.add(pOperacion);
    }
    
    public static ArrayList<Cuenta> ordenarCuentas() throws ClassNotFoundException{
        ArrayList<Cuenta> cuentasSistema = CuentaDAO.getCuentasBD();
        Collections.sort(cuentasSistema);
        return cuentasSistema;
    }
    
    public static void inactivarCuenta(String pNumCuenta, String mensaje) throws ClassNotFoundException, Exception
    {
      Cuenta cuenta = CuentaDAO.obtenerCuenta(pNumCuenta);
      int id = CuentaDAO.obtenerPersonaCuenta(pNumCuenta);
      Persona persona = PersonaDAO.obtenerPersona(id);
      String correo = persona.getCorreo();
      cuenta.setEstatus("inactiva");
      //CorreoElectronico.enviarCorreo(correo, "Inactivación de cuenta: " + pNumCuenta, "Hola, se ha desactivado la cuenta por motivo del ingreso incorrecto del pin o la palabra clave");
      IdiomaCorreoDecorator.enviarCorreo(correo, "Inactivación de cuenta: " + pNumCuenta, mensaje);
      CuentaDAO.inactivarCuentaBase(pNumCuenta);
    }
    
    public static String generarNumCuenta()
    {
        int min = 0;
        int max = 9;
        String numCuenta = "";
        Random random = new Random();
        
        for(int i=0; i<9; i++){
           int valor = random.nextInt(max+min)+min; 
           String strValor = Integer.toString(valor);
           numCuenta += valor;
        }
        return numCuenta;
    }
    
    @Override
    public int compareTo(Cuenta e){
        String strSaldoEncrip1 = e.getSaldo();
        String strSaldo1 = Encriptacion.desencriptar(strSaldoEncrip1);
        double saldo1 = Double.parseDouble(strSaldo1);
        
        String strSaldoEncrip2 = saldo;
        String strSaldo2 = Encriptacion.desencriptar(strSaldoEncrip2);
        double saldo2 = Double.parseDouble(strSaldo2);
        if(saldo1<saldo2){
            return -1;
        }
        else if(saldo1==saldo2){
            return 0;
            
        }
        else{
            return 1;
        }
    }
    
    public void attach(Bitacora pObserver){
        observers.add(pObserver);
    }

   
//-------------------------------------METODOS ACCESORES------------------------------------------
    public String getNumero() {
        return numero;
    }

    public void setNumero(String pNumero) {
        this.numero = pNumero;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pPin) {
        this.pin = pPin;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String pSaldo) {
        this.saldo = pSaldo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String pEstatus) {
        this.estatus = pEstatus.toLowerCase();
    }
    
    public Persona getDueno() {
        return dueno;
    }

    public void setDueno(Persona dueno) {
        this.dueno = dueno;
    }

    public Operacion getExchangeRate() {
        return exchangeRate;
    }

    public static void setExchangeRate(Operacion pExchangeRate, String pNumCuenta) throws ClassNotFoundException {
        exchangeRate = pExchangeRate;
        Cuenta cuenta = CuentaDAO.obtenerCuenta(pNumCuenta);
        RegistroXML registroxml = new RegistroXML(cuenta);
        RegistroTramaPlana registrotp = new RegistroTramaPlana(cuenta);
        RegistroCSV registrocsv = new RegistroCSV(cuenta);
        notifyAllObservers();
    }
    
    public static void notifyAllObservers(){
        for(int i=0; i<observers.size();i++){
            
            observers.get(i).update();
        }
    }
    
    
    
    public String toString()
    {
        String mensaje = "";
        String num = Encriptacion.desencriptar(this.numero);
        String saldo = Encriptacion.desencriptar(this.saldo);
        mensaje = "Número de cuenta= " + num + "\n" + "Estatus de la cuenta= " 
            + this.estatus + "\n" + "Saldo actual= " + saldo + "\n";
        return mensaje;
    }
    
    public String toString1()
    {
        String mensaje = "";
        mensaje = "Número de cuenta= " + this.numero + "\n" + "Estatus de la cuenta= " 
            + this.estatus + "\n" + "Saldo actual= " + this.saldo + "\n";
        return mensaje;
    }

    public static LocalDate setFechaCreacion()
    {
        LocalDate fechaActual = LocalDate.now();
        String formattedDate = fechaActual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return fechaActual;
    }
        
}

