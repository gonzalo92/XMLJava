<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" encoding="utf-8" indent="yes" />
<xsl:template match="/">
<xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html></xsl:text>
<html>
  <head>
    <meta charset="utf-8"/>
    <title>Coches</title>
    <link type="text/css" rel="stylesheet" href="style.css" />
  </head>
  <body>
    --<div>
      <div>
        <div><br/>
          <h2>Tu comparador de coches</h2>
        </div>
        <div>
          <div>
            <xsl:for-each select="coches/coche">
			<xsl:sort select="precio_coche" data-type="number" order="ascending"/>
            <div>
			  <img><xsl:attribute name="vicStage"><xsl:value-of select="imagen_coche"/></xsl:attribute></img>
              <h2><a><xsl:attribute name="vicVehicle"><xsl:value-of select="./referencia"/></xsl:attribute><xsl:value-of select="./nombre"/></a></h2>
              Precio: <xsl:value-of select="span[3]"/>€
			  <p><a><xsl:attribute name="topNavi_homeLink"><xsl:value-of select="./pagreferencia_coche"/></xsl:attribute><xsl:value-of select="./pagreferencia_coche"/></a></p></div>
			  
            <br />
            </xsl:for-each>
          </div>
          <br/>
          <div></div>
        </div>
        
        <div><br />
            Datos tomados de <a href="https://www.bmw.es/home/home.html">bmw.es</a> y <a href="http://www.volvocars.com/ES/pages/default.aspx">volvocars.com</a>
          <div></div>
        </div>
      </div>
    </div>
  </body>
</html>
</xsl:template>
</xsl:stylesheet>
