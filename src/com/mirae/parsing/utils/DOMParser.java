package com.mirae.parsing.utils;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class DOMParser {
	private static Logger log = LoggerFactory.getLogger(DOMParser.class);
	
	public static String getFilename() {
		String filename = "";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse("./conf/information.xml");
			Element root = document.getDocumentElement();
			filename = root.getAttribute("name");
		} catch (ParserConfigurationException pce) {
			// TODO: handle exception
			pce.getStackTrace();
		} catch (SAXException saxe) {
			// TODO: handle exception
			saxe.getStackTrace();
		} catch (IOException ioe) {
			// TODO: handle exception
			ioe.getStackTrace();
		}
		log.debug("Get Filename \""+ filename+"\"");
		return filename;
	}

}
