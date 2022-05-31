/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

/**
 *
 * @author Josue
 */
public class Bitacora{
  public static void crearRegistros(String pFecha, String pHora, String pOperacion, String pVista, String pNumCuenta,String numero)
  {
    final String direccionArchivo = "C:/Users/Josue/OneDrive/Documentos/TEC/V semestre 2022/Diseño de software/Elementos XML/";

    RegistroCSV.crearArchivoCSV( direccionArchivo ,  pFecha,  pHora,  pOperacion,  pVista,  pNumCuenta, numero);
    RegistroTramaPlana.RegistroTramaPlana( direccionArchivo,  pFecha,  pHora,  pOperacion,  pVista,  pNumCuenta, numero);
    RegistroXML.Bitacoraxml( direccionArchivo, pFecha,  pHora,  pOperacion,  pVista,  pNumCuenta, numero);
  }
}
