/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Josue
 */
public class RegistroCSV extends Bitacora{
  
  public void registroCSV(Operacion pOperacion)
  {
    bitacora = pOperacion;
    bitacora.agregarBitacora(this);
  }
  
  public void update(String file, String pFecha, String pHora, String pOperacion, String pVista, String pNumCuenta,String numero) {
		final String NEXT_LINE = "\n";
    String delim = ",";
		try {
			FileWriter fw = new FileWriter(file+"bitacorasCSV"+numero+".csv");
      
			fw.append(pNumCuenta);
			fw.append(delim);
			fw.append(pFecha);
      fw.append(delim);
			fw.append(pHora);
      fw.append(delim);
			fw.append(pOperacion);
      fw.append(delim);
			fw.append(pVista);
			fw.append(NEXT_LINE);
      
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// Error al crear el archivo, por ejemplo, el archivo 
			// está actualmente abierto.
			e.printStackTrace();
		}
	}
  
}
