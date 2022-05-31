/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.time.LocalDate;

/**
 *
 * @author Josue
 */


public abstract class Bitacora{
  String file;
  LocalDate fecha;
  String hora;
  String operacion;
  String vista;
  String numCuenta;
  int numero;

  public Bitacora(String file, LocalDate fecha, String hora, String operacion, String vista, String numCuenta, int numero) {
    this.file = file;
    this.fecha = fecha;
    this.hora = hora;
    this.operacion = operacion;
    this.vista = vista;
    this.numCuenta = numCuenta;
    this.numero = numero;
    this.bitacora = bitacora;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
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

  public String getNumCuenta() {
    return numCuenta;
  }

  public void setNumCuenta(String numCuenta) {
    this.numCuenta = numCuenta;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public Operacion getBitacora() {
    return bitacora;
  }

  public void setBitacora(Operacion bitacora) {
    this.bitacora = bitacora;
  }
  
  protected Operacion bitacora;
  public abstract void update(String file, LocalDate pFecha, String pHora, String pOperacion, String pVista, String pNumCuenta,int numero);
}
