/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicacreacional;

import webService.*;

/**
 *
 * @author Cristi Martínez
 */
public class ConsultaMonedaSingleton {
    private static ConsultaMoneda instance = new ConsultaMoneda();
    
    private ConsultaMonedaSingleton(){
        
    }
    
    public static ConsultaMoneda getInstance(){
        return instance;
    }
}
