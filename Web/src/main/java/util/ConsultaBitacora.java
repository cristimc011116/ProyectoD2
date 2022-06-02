/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import com.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import com.opencsv.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class ConsultaBitacora {
     
    
//------------------------------------IMPRIMIR TXT---------------------------------------------------------   
    public static String imprimirArchivosTXT(ArrayList pCantArchivos) throws FileNotFoundException{
        String texto = "";
        for(int i = 0; i < pCantArchivos.size(); i++){     
            String nombre = pCantArchivos.get(i).toString();
            InputStream ins = new FileInputStream("C:\\Users\\Cristi Martínez\\Documents\\ArchivosTP\\RegistroTramaPlana"+nombre+".txt");
            Scanner obj = new Scanner(ins);
            while (obj.hasNextLine())
            texto += obj.nextLine(); 
            texto += "\n\n";
        }
        return texto;
}

//------------------------------------IMPRIMIR XML---------------------------------------------------------
    
    public static String imprimirArchivosXML(ArrayList pCantArchivos) throws FileNotFoundException{
        String texto = "";
        for(int i = 0; i < pCantArchivos.size(); i++){     
            String nombre = pCantArchivos.get(i).toString();                                         //cambiar nombre prueba
            InputStream ins = new FileInputStream("C:\\Users\\Cristi Martínez\\Documents\\ArchivosXML\\RegistroXML"+nombre+".xml");
            Scanner obj = new Scanner(ins);
            while (obj.hasNextLine())
            texto += obj.nextLine();
            texto += "\n\n";
        }
        return estructuraArbolXML(texto);
    }
    
    public static String estructuraArbolXML(String pTexto){
       String estructura= "";
    for (int n = 0; n <pTexto.length(); n++) {
        estructura+= pTexto.charAt(n);
        if (pTexto.charAt(n) == '>')
        {
            estructura+= "\n";
        }
    }
    
    return estructura;
    }
    
    
//------------------------------------IMPRIMIR CSV---------------------------------------------------------
  
public static String imprimirArchivosCSV(ArrayList pCantArchivos) throws FileNotFoundException, IOException{
    String texto = "";
    for(int i = 0; i < pCantArchivos.size(); i++){     
        String nombre = pCantArchivos.get(i).toString();  
        String file = "C:\\Users\\Cristi Martínez\\Documents\\ArchivosCSV\\RegistroCSV"+nombre+".csv";
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    texto += (cell + "\t");
                    texto += ",";
                }
            }
            texto += "\n\n";
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }  
    }
    return texto;
}
    
    
    
}
