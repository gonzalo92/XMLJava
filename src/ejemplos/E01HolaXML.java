package ejemplos;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class E01HolaXML {

	public static Document abrirXML(String archivo) {
		
			Document doc = null;
			try {
				DocumentBuilderFactory dbFactory = 
						DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(archivo);
			} catch (ParserConfigurationException e) {
				System.out.println("No se pudo construir DocumentBuilder");
				e.printStackTrace();
			} catch (SAXException e) {
				System.out.println("Error en el documento XML");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("No se puede abrir " + archivo);
				e.printStackTrace();
			}
		
		return doc;
		/* Imprimir propiedades documento
		System.out.println("Documento leido");
		System.out.println("\tArchivo: " + doc.getDocumentURI());
		System.out.println("\tCodificación: " + doc.getXmlEncoding());
		System.out.println("\tVersion: " + doc.getXmlVersion());
		System.out.println("\traiz: " + 
			doc.getDocumentElement().getNodeName());
		*/
		
	}

	public static void main(String[] args) throws ParserConfigurationException,
												SAXException, IOException {
		// Crear factoría
		DocumentBuilderFactory dbFactory = 
				DocumentBuilderFactory.newInstance();
		// Crear document builder
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		// Crear document
		Document doc = dBuilder.parse("archivos/libros.xml");
		// Imprimir propiedades documento
		System.out.println("Documento leido");
		System.out.println("\tArchivo: " + doc.getDocumentURI());
		System.out.println("\tCodificación: " + doc.getXmlEncoding());
		System.out.println("\tVersion: " + doc.getXmlVersion());
		System.out.println("\traiz: " + 
			doc.getDocumentElement().getNodeName());
	}

}
