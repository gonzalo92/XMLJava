package proyecto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


import org.apache.commons.lang3.StringEscapeUtils;
import org.htmlcleaner.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;


public class Proyecto {

	   private static Document abrirSitio(String ruta){
			try {
				// Creamos una instancia de HtmlCleaner y le pedimos que parsee la página
				TagNode tagNode;
				HtmlCleaner cleaner=new HtmlCleaner();
				
				CleanerProperties props = cleaner.getProperties();
				 
				props.setCharset("UTF-8");
				props.setTranslateSpecialEntities(false);
				props.setRecognizeUnicodeChars(true);
				props.setTransResCharsToNCR(false);
				
				URL url=new URL(ruta);
				HttpURLConnection httpcon=(HttpURLConnection)url.openConnection();
				httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:29.0) Gecko/20100101 Firefox/29.0");
				tagNode=cleaner.clean(httpcon.getInputStream(),"UTF-8");

				//for(TagNode s:tagNode.getAllElements(true))
				//System.out.println(s.getText());
				
				// Para trabajar con DOM, necesitamos serializarlo y generar el arbol DOM
				Document document = new DomSerializer(props).createDOM(tagNode);
				
		      	return document;				
				
			} catch (IOException | ParserConfigurationException e) {
				System.out.println("No se ha podido leer HTML");
				e.printStackTrace();
			    return null;
			}
	   }
	   
	   private static Document abrirArchivo(String ruta){
				try {
					// Creamos una instancia de HtmlCleaner y le pedimos que parsee la página
					TagNode tagNode;
					HtmlCleaner cleaner=new HtmlCleaner();
					tagNode=cleaner.clean(new File(ruta));

					// Para trabajar con DOM, necesitamos serializarlo y generar el arbol DOM
					Document document = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
					
			      	return document;				
					
				} catch (IOException | ParserConfigurationException e) {
				    return null;
				}
	   }
	   
	   private static boolean guardarArchivoUsandoXSLT(Document doc, String rutaXsl, String ruta) throws UnsupportedEncodingException, FileNotFoundException{
			try {
				
		        File xsltFile = new File(rutaXsl);

		        // JAXP lee utilizando una instancia Source
		        Source xsltSource = new StreamSource(xsltFile);
		        Source xmlSource = new DOMSource(doc);
		        Result htmlResult = new StreamResult(ruta);

		        // Transformer soporta procesado XSLT
		        TransformerFactory transFact =
		                TransformerFactory.newInstance();
		        Transformer trans = transFact.newTransformer(xsltSource);

		        
		        // Aplicar XSLT y generar html
		        trans.transform(xmlSource, htmlResult);
		    	
		    	return true;
			
			   } catch (TransformerException | TransformerFactoryConfigurationError e) {
				  e.printStackTrace(); 
			      return false;
			   }
	   }
	   
	   private static NodeList evaluarXPath(Document doc, String xpath){
					XPathFactory xpFactory = XPathFactory.newInstance();
					XPath xPath = xpFactory.newXPath();
					NodeList nodos;
					try {
						 nodos = (NodeList) xPath.evaluate(
								xpath, doc, XPathConstants.NODESET);
					} catch (XPathExpressionException e) {
						
						nodos=null;
					}
							
				    return nodos;
	   }
	
	   private static String limpiarEntidades(String origen)
	   {
          return StringEscapeUtils.unescapeHtml4(origen);
	   }
	   
	   public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, InterruptedException{
		   String archivoPrecioXsl="archivos/htmlproyecto/indexPrecio.xsl",
				   archivoResultado="archivos/htmlproyecto/Resultado3.html";
		  
		   Document docPcComponentes =  abrirSitio("http://www.pccomponentes.com/tablets.html");
		   Document docMediaMarkt =  abrirSitio("http://tiendas.mediamarkt.es/tablets-pc");
		   
		   NodeList tabletNombre=evaluarXPath(docPcComponentes,"//*[@class='listado-familias']//span[@class='nombre']");
		   NodeList tabletURL = evaluarXPath(docPcComponentes,"//*[@class='listado-familias']//span[@class='nombre']/a/@href");
		   NodeList tabletPrecio=evaluarXPath(docPcComponentes,"//*[@class='listado-familias']//span[@class='precio']/strong");
		   NodeList tabletImagen=evaluarXPath(docPcComponentes,"//*[@class='listado-familias']//a[@class='imagen-articulo']/img/@src");
		   NodeList tabletWeb=evaluarXPath(docPcComponentes,"//*[@class='menu-principal']//li[1]//@href");
		   
		   NodeList tabletNombres2=evaluarXPath(docMediaMarkt,"//div[@class='product9Description']/a/text()");
		   NodeList tabletURL2 = evaluarXPath(docMediaMarkt,"//div[@class='product9Description']/a/@href");
		   NodeList tabletPrecio2=evaluarXPath(docMediaMarkt,"//div[@class='productPrices']//@alt");
		   NodeList tabletImagene2=evaluarXPath(docMediaMarkt,"//div[@class='product9ImageProduct']/img/@src");
		   NodeList tabletWeb2=evaluarXPath(docMediaMarkt,"//div[@id='navigation']//li[1]//@href");
		   
		   Document docXML=null;
		   try {
			   DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			   DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			   docXML = docBuilder.newDocument();
		   } catch (ParserConfigurationException e) {
			   e.printStackTrace();
		   }
		   
		   Element raiz = (Element)docXML.createElement("tablets"); 
		   docXML.appendChild(raiz);
			     
		   
		   for(int i=0;i<tabletNombre.getLength();i++){
			   
			   Node tablet=docXML.createElement("tablet");

			   Node pagreferencia=docXML.createElement("pagreferencia");
			   Node imagen=docXML.createElement("imagen");
			   Node nombre=docXML.createElement("nombre");
			   Node referencia=docXML.createElement("referencia");
			   Node precio=docXML.createElement("precio");
			   
			   pagreferencia.setTextContent(tabletWeb.item(0).getTextContent());
			   imagen.setTextContent(limpiarEntidades(tabletImagen.item(i).getTextContent()));
			   nombre.setTextContent(limpiarEntidades(tabletNombre.item(i).getTextContent()));
			   referencia.setTextContent(limpiarEntidades(tabletURL.item(i).getTextContent()));
			   String numero = limpiarEntidades(tabletPrecio.item(i).getTextContent()).trim();
			   precio.setTextContent(numero.substring(0, numero.length()-1));

			   tablet.appendChild(pagreferencia);
			   tablet.appendChild(imagen);
			   tablet.appendChild(nombre);
			   tablet.appendChild(referencia);
			   tablet.appendChild(precio);
			   
			   raiz.appendChild(tablet);
		   }     
		   
		   for(int i=0;i<tabletNombres2.getLength();i++){
			   
			   Node tablet=docXML.createElement("tablet");
			   
			   Node pagreferencia=docXML.createElement("pagreferencia");
			   Node imagen=docXML.createElement("imagen");
			   Node nombre=docXML.createElement("nombre");
			   Node referencia=docXML.createElement("referencia");
			   Node precio=docXML.createElement("precio");
			   
			   pagreferencia.setTextContent(tabletWeb2.item(0).getTextContent());
			   imagen.setTextContent(limpiarEntidades(tabletImagene2.item(i).getTextContent()));
			   nombre.setTextContent(limpiarEntidades(tabletNombres2.item(i).getTextContent()));
			   referencia.setTextContent(limpiarEntidades("http://tiendas.mediamarkt.es/tablets-pc"+tabletURL2.item(i).getTextContent()));
			   String numero = limpiarEntidades(tabletPrecio2.item(i).getTextContent());
			   try{
				   precio.setTextContent(numero.substring(0, numero.length()-3));
			   }catch(Exception e){
				   System.out.println("Se ha registrado un problema al transformar a número " + numero);
			   }

			   tablet.appendChild(pagreferencia);
			   tablet.appendChild(imagen);
			   tablet.appendChild(nombre);
			   tablet.appendChild(referencia);
			   tablet.appendChild(precio);
			   
			   raiz.appendChild(tablet);
		   }
		  
		   guardarArchivoUsandoXSLT(docXML,archivoPrecioXsl,archivoResultado);
			   
	   }
}