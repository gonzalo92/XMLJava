package ejemplos;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class E05Temperatura {

	public static void main(String[] args) {
		
		Document doc=E01HolaXML.abrirXML("http://www.aemet.es/xml/municipios/localidad_45023.xml");
		
		XPathFactory xpFactory = XPathFactory.newInstance();
		XPath xPath=xpFactory.newXPath();

		String consulta = "/root/prediccion/dia[1]/temperatura/maxima/text()";
		String consulta2 = "/root/prediccion/dia[1]/temperatura/minima/text()";
		String maxima=null,minima=null;
		
		try {
			maxima=(String)xPath.evaluate(consulta, doc, XPathConstants.STRING);
			minima=(String)xPath.evaluate(consulta2, doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			System.out.println("Error en el XPath");
			e.printStackTrace();
		}
		
		if (maxima!=null) {
			System.out.println(
				"La temperatura esperada para mañana en Burguillos es:" + maxima);
			System.out.println(
					"La mínima: " + minima);
		}
	}	
}

