/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

/**
 *
 * @author Josue
 */


public abstract class Bitacora{
  String file;
  String fecha;
  String hora;
  String operacion;
  String vista;
  String numCuenta;
  String numero;
  
  protected Operacion bitacora;
  public abstract void update(String file, String pFecha, String pHora, String pOperacion, String pVista, String pNumCuenta,String numero);
}
