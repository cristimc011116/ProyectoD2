/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.io.File;
import java.time.LocalDate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Josue
 */
public class RegistroXML extends Bitacora{
 
  public void RegistroXML(Operacion pOperacion)
  {
    bitacora = pOperacion;
    bitacora.agregarBitacora(this);
  }
  
  public void update(String file, LocalDate pFecha, String pHora, String pOperacion, String pVista, String pNumCuenta,int numero)
  {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      //Elemento raíz
      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement("Bitacora");
      doc.appendChild(rootElement);
      //Primer elemento
      Element nodo = doc.createElement("Registro");
      rootElement.appendChild(nodo);
      //Se agrega un atributo al nodo y su valor
      Attr attr = doc.createAttribute("NumeroCuenta");
      attr.setValue(pNumCuenta);
      nodo.setAttributeNode(attr);

      Element fecha = doc.createElement("Fecha");
      fecha.setTextContent(pFecha.toString());
      rootElement.appendChild(fecha);

      Element hora = doc.createElement("Hora");
      hora.setTextContent(pHora);
      rootElement.appendChild(hora);

      Element operacion = doc.createElement("Operacion");
      operacion.setTextContent(pOperacion);
      rootElement.appendChild(operacion);

      Element tipoVista = doc.createElement("Vista");
      tipoVista.setTextContent(pVista);
      rootElement.appendChild(tipoVista);

      //Se escribe el contenido del XML en un archivo
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(file+"RegistroXML"+numero+".xml"));
      transformer.transform(source, result);
    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    } catch (TransformerException tfe) {
      tfe.printStackTrace();
    }
  }
}
