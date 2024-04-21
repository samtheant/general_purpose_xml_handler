package com.example;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

//
////following guide at https://mkyong.com/java/how-to-read-xml-file-in-java-sax-parser/
public class GeneralHandler extends DefaultHandler {
    Element root;
    Element current;

    StringBuilder elementValue;

    @Override
    public void startDocument() {
        root = new Element("Root", null);
        current = root;
    }

    @Override
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes) {
        current = current.put(qName);
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) {
        if (elementValue.length() > 0) {
            current.setValue(elementValue.toString());
            elementValue = null;
        }
        current = current.getParent();
    }

    public Element getResult() {
        return root.getChildren().get(0);
    }


    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            for (int i = start; i < start + length; i++) {
                if (!Character.isWhitespace(ch[i])) {
                    elementValue.append(ch[i]);
                }
            }
        }
    }
}


