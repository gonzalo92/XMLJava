<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" encoding="utf-8" indent="yes" />
<xsl:template match="/">
<xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html></xsl:text>
<html>
  <head>
    <meta charset="utf-8" />
    <title>Tablets</title>
    <link type="text/css" rel="stylesheet" href="templatemo_style.css" />
  </head>
  <body>
    <div id="templatemo_wrapper_outer">
      <div id="templatemo_wrapper">
        <div id="templatemo_banner"><br />
          <h2>Proyecto Lenguaje de Marcas<span>Web Scraping</span></h2>
        </div>
        <div id="templatemo_content_wrapper">
          <div id="content">
            <xsl:for-each select="tablets/tablet">
			<xsl:sort select="precio" data-type="number" order="ascending"/>
            <div class="section_w610 divider">
			  <img><xsl:attribute name="src"><xsl:value-of select="./imagen"/></xsl:attribute></img>
              <h2><a><xsl:attribute name="href"><xsl:value-of select="./referencia"/></xsl:attribute><xsl:value-of select="./nombre"/></a></h2>
              Precio: <xsl:value-of select="./precio"/>€
			  <p><a><xsl:attribute name="href"><xsl:value-of select="./pagreferencia"/></xsl:attribute><xsl:value-of select="./pagreferencia"/></a></p></div>  
            <br />
            </xsl:for-each>
          </div>
          <br/>
          <div class="cleaner"></div>
        </div>
        
        <div id="templatemo_footer"><br />
            Datos tomados de <a href="http://www.mediamarkt.es">mediamarkt.es</a> y <a href="http://www.pccomponentes.com">pccomponentes.com</a>
          <div class="cleaner"></div>
        </div>
      </div>
    </div>
  </body>
</html>
</xsl:template>
</xsl:stylesheet>