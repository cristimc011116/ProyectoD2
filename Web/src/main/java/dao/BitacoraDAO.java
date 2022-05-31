/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import logicadenegocios.Bitacora;
import logicadenegocios.Operacion;
import util.ConexionBase;

/**
 *
 * @author Josue
 */
public class BitacoraDAO {
  private static ArrayList<Operacion> operaciones;
    public static void insertarBitacora(String file, LocalDate fecha, String hora, String operacion, String vista, String numCuenta, int numero) throws ClassNotFoundException
    {
        ConexionBase con = new ConexionBase();
        con.obtenerConexion();
        con.excSentenciaSQL("INSERT INTO Bitacora VALUES(" + numero + ", '" + fecha + "', '" + hora +
                "','" + file + "','" + numCuenta + "','" + operacion + "','" + vista +"')");
        con.desconectar();
    }
    
    public static int cantBitacorasBD() throws ClassNotFoundException{
        ConexionBase con = new ConexionBase();
        con.obtenerConexion();
        ResultSet resultado;
        int contador = 1;
        try{
            resultado = con.consultas("SELECT * FROM Bitacora");
            while(resultado.next()){
                contador++;
            }
        }catch(SQLException ex){
           JOptionPane.showMessageDialog(null, ex.toString());
        }
        con.desconectar();
        return contador;
    }
}