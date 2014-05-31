package ejercicios;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ejemplos.E01HolaXML;

public class Contactos {
	public static void main(String[] args) {
		
		Document doc=E01HolaXML.abrirXML("archivos/agenda.xml");
		
		Element raiz = doc.getDocumentElement();
		
		NodeList hijos = raiz.getChildNodes(); 
		
		for(int i=0;i<hijos.getLength();i++) {
			Node elemento = hijos.item(i);
			
			if (elemento.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println(elemento.getChildNodes().item(1).getTextContent());
			}
		}
	}
}
