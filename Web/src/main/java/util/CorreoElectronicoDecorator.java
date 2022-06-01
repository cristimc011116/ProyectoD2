/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Cristi Martínez
 */
public abstract class CorreoElectronicoDecorator extends CorreoElectronico{
    protected static CorreoElectronico decoratedCorreo;
    
    public CorreoElectronicoDecorator(CorreoElectronico pDecoratedCorreo){
        decoratedCorreo = pDecoratedCorreo;
    }
    
    public static void enviarCorreo(String destinatario, String asunto, String cuerpo) throws Exception{
        decoratedCorreo.enviarCorreo(destinatario, asunto, cuerpo);
    }
}
