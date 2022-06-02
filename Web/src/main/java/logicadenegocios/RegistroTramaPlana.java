/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import dao.BitacoraDAO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Josue
 */
public class RegistroTramaPlana extends Bitacora{
  static File file = new File("C:\\Users\\Cristi Martínez\\Documents\\ArchivosTP\\");
  public RegistroTramaPlana(Cuenta pSubject)
  {
    subject = pSubject;
    subject.attach(this);
  }
  
  @Override
  public void update(){
      try{
          System.out.println("ACAA7");
          Operacion operacion = subject.getExchangeRate();
          añadirBitacoraTP(operacion, subject.getNumero());
      }catch(Exception ex){
          Logger.getLogger(RegistroXML.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  
    public void añadirBitacoraTP(Operacion pOperacion, String pNumCuenta)
  {
    String registro = "";
    
    registro = AgregarNumCuenta(registro, pNumCuenta);
    registro = AgregarFecha( registro, pOperacion.getFechaOperacion().toString());
    registro = AgregarHora( registro, pOperacion.getHora());
    registro = AgregarOperacion( registro, pOperacion.getTipo());
    registro = AgregarVista( registro, pOperacion.getVista());
    
    try {
      String contenido = registro;
      int idBitacora = BitacoraDAO.cantBitacorasBD()-1;
      File file1 = new File(file+"RegistroTramaPlana"+idBitacora+".txt");
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
