package ejemplos;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class E04XPath {

	public static void main(String[] args) {
		Document doc=E01HolaXML.abrirXML("archivos/libros.xml");
		
		XPathFactory xpFactory = XPathFactory.newInstance();
		XPath xPath=xpFactory.newXPath();

		String consulta = "/biblioteca/libro/fechaPublicacion[@año='1973']/../titulo";
		NodeList libros=null;
		
		try {
			libros=(NodeList)xPath.evaluate(consulta, doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			System.out.println("Error en el XPath");
			e.printStackTrace();
		}
		
		if (libros!=null) {
			for(int i=0;i<libros.getLength();i++)
				System.out.println(libros.item(i).getTextContent());
		}

	}

}
