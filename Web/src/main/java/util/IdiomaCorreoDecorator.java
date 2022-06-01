/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import static util.Translator.traducir;

/**
 *
 * @author Cristi Martínez
 */
public class IdiomaCorreoDecorator extends CorreoElectronicoDecorator{
    public IdiomaCorreoDecorator(CorreoElectronico pDecoratedCorreo){
        super(pDecoratedCorreo);
    }
    
    public static void enviarCorreo(String destinatario, String asunto, String cuerpo) throws Exception {
        setIdiomaCorreo(decoratedCorreo, destinatario,asunto,cuerpo);
    }
    
    private static void setIdiomaCorreo(CorreoElectronico pDecoratedCorreo, String destinatario, String asunto, String cuerpo) throws Exception{
        String traducido = traducir(cuerpo);
        String mensaje = cuerpo + "\n" + traducir(cuerpo);
        decoratedCorreo.enviarCorreo(destinatario,asunto,mensaje);
    }
}
