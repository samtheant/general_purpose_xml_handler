package com.example;

import java.io.FileInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

public class Main {
    public static void main(String[] args){

        try {
            FileInputStream inputStream = new FileInputStream("general_purpose_xml_handler/src/main/resources/cd_catalog.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GeneralHandler testHandler = new GeneralHandler();
            InputSource is = new InputSource(inputStream);
            saxParser.parse(is, testHandler);
            Element result = testHandler.getResult();
            result.printSelf();
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }
 
    }
}