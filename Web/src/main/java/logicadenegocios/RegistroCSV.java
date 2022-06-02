/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import dao.BitacoraDAO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Josue
 */
public class RegistroCSV extends Bitacora{
  static File file = new File("C:\\Users\\Cristi Martínez\\Documents\\ArchivosCSV\\Registro\\");
  public RegistroCSV(Cuenta pSubject)
  {
    subject = pSubject;
    subject.attach(this);
  }
  
  @Override
  public void update(){
      try{
          Operacion operacion = subject.getExchangeRate();
          añadirBitacoraCSV(operacion, subject.getNumero());
      }catch(Exception ex){
          Logger.getLogger(RegistroXML.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  
  public void añadirBitacoraCSV(Operacion pOperacion, String pNumCuenta) throws ClassNotFoundException {
		final String NEXT_LINE = "\n";
    String delim = ",";
		try {
                        int idBitacora = BitacoraDAO.cantBitacorasBD()-1;
			FileWriter fw = new FileWriter(file+"CSV"+idBitacora+".csv");
      
			fw.append(pNumCuenta);
			fw.append(delim);
			fw.append(pOperacion.getFechaOperacion().toString());
      fw.append(delim);
			fw.append(pOperacion.getHora());
      fw.append(delim);
			fw.append(pOperacion.getTipo());
      fw.append(delim);
			fw.append(pOperacion.getVista());
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
