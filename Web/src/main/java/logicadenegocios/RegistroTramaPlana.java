/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Josue
 */
public class RegistroTramaPlana {
  
    public static void RegistroTramaPlana(String ruta, String pFecha, String pHora, String pOperacion, String pVista, String pNumCuenta,String numero)
  {
    String registro = "";
    
    registro = AgregarNumCuenta(registro, pNumCuenta);
    registro = AgregarFecha( registro, pFecha);
    registro = AgregarHora( registro, pHora);
    registro = AgregarOperacion( registro, pOperacion);
    registro = AgregarVista( registro, pVista);
    
    try {
      String contenido = registro;
      File file = new File(ruta+"RegistroTramaPlana"+numero+".txt");
      // Si el archivo no existe es creado
      if (!file.exists()) {
          file.createNewFile();
      }
      FileWriter fw = new FileWriter(file);
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
