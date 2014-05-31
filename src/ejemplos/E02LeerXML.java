package ejemplos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class E02LeerXML {

	public static void main(String[] args) {
		
		Document doc=E01HolaXML.abrirXML("archivos/libros.xml");

		Element raiz = doc.getDocumentElement();
		
		NodeList hijos = raiz.getChildNodes(); 
		
		for(int i=0;i<hijos.getLength();i++) {
			Node elemento = hijos.item(i);
			
			if (elemento.getNodeType() == Node.ELEMENT_NODE) {
			//if (elemento instanceof Element) {
				System.out.println("Elemento:" + elemento.getNodeName());
			} else if (elemento.getNodeType() == Node.TEXT_NODE) {
				System.out.println("Texto:" + elemento.getTextContent());
			}
		}
	}

	
	
	
	
	
}
