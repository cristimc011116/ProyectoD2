/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Josue
 */


public abstract class Bitacora{
  protected Cuenta subject;
  
    private int id;
    private LocalDate fecha;
    private String hora;
    private String numCuenta;
    private String operacion;
    private String vista;

//-----------------------------------------------CONSTRUCTOR-----------------------------------------    
    /*public Bitacora(int pId, LocalDate pFecha, String pHora, String pNumCuenta, String pOperacion, String pVista)
    {
        setId(pId);
        setFecha(pFecha);
        setHora(pHora);
        setNumCuenta(pNumCuenta);
        setOperacion(pOperacion);
        setVista(pVista);
        
    }*/
    
    public static String obtenerHora(){
        long millis = System.currentTimeMillis();
        int hours   = (int) ((millis / (1000*60*60)) % 24);
        int minutes = (int) ((millis / (1000*60)) % 60);
        int seconds = (int) (millis / 1000) % 60 ;
        String hora =  hours + ":"+ minutes+":"+seconds;
        return hora;
    }

    public Cuenta getSubject() {
        return subject;
    }

    public void setSubject(Cuenta subject) {
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getVista() {
        return vista;
    }

    public void setVista(String vista) {
        this.vista = vista;
    }
    
    
  
  public abstract void update();
}
