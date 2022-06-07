/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Josue
 */


public abstract class Bitacora{
  protected Cuenta subject;
    
    public static String obtenerHora(){
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    public Cuenta getSubject() {
        return subject;
    }

    public void setSubject(Cuenta subject) {
        this.subject = subject;
    }
  
  public abstract void update();
}
