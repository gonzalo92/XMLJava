package ejemplos;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class E03XPath {

	public static void main(String[] args) {
		Document doc=E01HolaXML.abrirXML("archivos/libros.xml");
		
		XPathFactory xpFactory = XPathFactory.newInstance();
		XPath xPath=xpFactory.newXPath();

		String consulta = "/biblioteca/libro[1]/titulo/text()";
		Node elLibro;
		String nombre="";
		
		// obtener un texto
		try {
			nombre=(String)xPath.evaluate(consulta, doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			System.out.println("Error en el XPath");
			e.printStackTrace();
		}
		
		System.out.println(nombre);
		
		
		// obtener un nodo
		consulta = "//fechaPublicacion[@año='1969']/../titulo";
		Node titulo=null;
		try {
			titulo=(Node)xPath.evaluate(consulta, doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			System.out.println("Error en el XPath");
			e.printStackTrace();
		}
		
		System.out.println(titulo.getNodeName() + ": "+ titulo.getTextContent());
		
		
		
	}

}
