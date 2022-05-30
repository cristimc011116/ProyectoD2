/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import util.ConexionBase;
import logicadenegocios.Persona;
import util.Encriptacion;
/**
 *
 * @author Cristi Martínez
 */
public class AdmiDAO {
    public static boolean estaRegistrado(String usuario) throws ClassNotFoundException{
        int contador = 0;
        boolean estaRegistrada = false;
        ConexionBase con = new ConexionBase();
        con.obtenerConexion();
        ResultSet buscarAdmi = con.consultas("SELECT * FROM Administrador WHERE usuario = " + "'" + usuario + "'");
        if(buscarAdmi == null){
            return false;
        }
        return true;
    }
    
    public static Boolean buscarContraseña(String usuario, String contraseña) throws ClassNotFoundException{
        ConexionBase con = new ConexionBase();
        con.obtenerConexion();
        ResultSet buscar = con.consultas("SELECT * FROM Administrador WHERE usuario = " + "'" + usuario + "'");
        try{
            while(buscar.next()){
              String EsContraseña = (buscar.getString("contrasena"));
              if (EsContraseña.equals(contraseña)){
                return true;
              }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
        return false;
    }
    
    /* OPCION 1
        //comparar que sea diferente a true
    public static Boolean iniciarSesion(String usuario, String contraseña){
        return buscarContraseña(usuario, contraseña) && estaRegistrado(usuario)== true;
    }*/
    
    //OPCION 2
    public static Boolean iniciarSesion(String usuario, String contraseña) throws ClassNotFoundException{
    if(buscarContraseña(usuario, contraseña) && estaRegistrado(usuario)== true){
      return true;
    }else
      return false;
    }
 }