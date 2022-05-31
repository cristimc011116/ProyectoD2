/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;

/**
 *
 * @author Josue
 */
public class RegistroTramaPlana extends Bitacora{
  
  public void RegistroTramaPlana(Operacion pOperacion)
  {
    bitacora = pOperacion;
    bitacora.agregarBitacora(this);
  }
  
    public void update(String file, LocalDate pFecha, String pHora, String pOperacion, String pVista, String pNumCuenta,int numero)
  {
    String registro = "";
    
    registro = AgregarNumCuenta(registro, pNumCuenta);
    registro = AgregarFecha( registro, pFecha.toString());
    registro = AgregarHora( registro, pHora);
    registro = AgregarOperacion( registro, pOperacion);
    registro = AgregarVista( registro, pVista);
    
    try {
      String contenido = registro;
      File file1 = new File(file+"RegistroTramaPlana"+numero+".txt");
      // Si el archivo no existe es creado
      if (!file1.exists()) {
          file1.createNewFile();
      }
      FileWriter fw = new FileWriter(file1);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(contenido);
      bw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static String AgregarNumCuenta(String pRegistro, String pNumCuenta)
  {
    char[] caracteres = pNumCuenta.toCharArray();
    for(int i=0;i<20;i++)
    {
      if(i < caracteres.length){
        pRegistro = pRegistro + caracteres[i];
      }else
      {
        pRegistro = pRegistro + " ";
      }
    }
    return pRegistro;
  }
  
  public static String AgregarFecha(String pRegistro, String pFecha)
  {
    char[] caracteres = pFecha.toCharArray();
    for(int i=0;i<17;i++)
    {
      if( i < caracteres.length){
        pRegistro = pRegistro + caracteres[i];
      }else
      {
        pRegistro = pRegistro + " ";
      }
    }
    return pRegistro;
  }
  
  public static String AgregarHora(String pRegistro, String pHora)
  {
    char[] caracteres = pHora.toCharArray();
    for(int i=0;i<9;i++)
    {
      if( i <caracteres.length){
        pRegistro = pRegistro + caracteres[i];
      }else
      {
        pRegistro = pRegistro + " ";
      }
    }
    return pRegistro;
  }
  
  public static String AgregarOperacion(String pRegistro, String pOperacion)
  {
    char[] caracteres = pOperacion.toCharArray();
    for(int i=0;i<40;i++)
    {
      if(i < caracteres.length){
        pRegistro = pRegistro + caracteres[i];
      }else
      {
        pRegistro = pRegistro + " ";
      }
    }
    return pRegistro;
  }
  
  public static String AgregarVista(String pRegistro, String pVista)
  {
    char[] caracteres = pVista.toCharArray();
    for(int i=0;i<5;i++)
    {
      if( i < caracteres.length){
        pRegistro = pRegistro + caracteres[i];
      }else
      {
        pRegistro = pRegistro + " ";
      }
    }
    return pRegistro;
  }
  
}
