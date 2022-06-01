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

/**
 *
 * @author User
 */
public class ConsultaBitacora {
     
    
//------------------------------------IMPRIMIR TXT---------------------------------------------------------   
    public static String imprimirArchivosTXT(int pCantArchivos) throws FileNotFoundException{
        String texto = "";
        for(int i = 1; i <= pCantArchivos; i++){                                          //cambiar nombre prueba
            InputStream ins = new FileInputStream("C:\\Users\\User\\OneDrive - Estudiantes ITCR\\Escritorio\\ArchivoTXT"+i+".txt");
            Scanner obj = new Scanner(ins);
            while (obj.hasNextLine())
            texto += obj.nextLine(); 
            texto += "\n\n";
        }
        return texto;
}

//------------------------------------IMPRIMIR XML---------------------------------------------------------
    
    public static String imprimirArchivosXML(int pCantArchivos) throws FileNotFoundException{
        String texto = "";
        for(int i = 1; i <= pCantArchivos; i++){                                          //cambiar nombre prueba
            InputStream ins = new FileInputStream("C:\\Users\\User\\OneDrive - Estudiantes ITCR\\Escritorio\\ArchivoXML"+i+".xml");
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
  
public static String imprimirArchivosCSV(int pCantArchivos) throws FileNotFoundException, IOException{
    String texto = "";
    for(int i = 1; i <= pCantArchivos; i++){
        String file = "C:\\Users\\User\\OneDrive - Estudiantes ITCR\\Escritorio\\ArchivoCSV"+i+".csv";
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    texto += (cell + "\t");
                    texto += "\n";
                }
            }
            texto += "\n";
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }  
    }
    return texto;
}
    
    
    
}
