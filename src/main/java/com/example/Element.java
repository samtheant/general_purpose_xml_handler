package com.example;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Element {
    private String name;
    private Map<String, Element> children;
    private Element parent;

    private String value;

//    use if there are duplicate nodes of the same name
    private List<Element> duplicates;
    /*****************************
     NOTE:
     The way to index into a list is slightly unintuitive; it was done this way so that the parser
     can parse in one pass without going back to retroactively change the element if it's a list
     as opposed to a single element.
     Example of how to index into a list:
     <someDocument>
         <listName>
            <listItem/>
            <listItem/>>
            <listItem/>
         </listName>
     </someDocument>
     What you might intuitively expect:
        Element firstItem = someDocument.get("listName").get(0);
     How it actually works:
        Element firstItem = someDocument.get("listName").get("listItem").get(0);

     Similarly, to return the entire list, do:
     ArrayList<Element> elementList = someDocument.get("listName").get("listItem").getSequence();
     ******************************/

    public Element(String name, Element parent) {
        this.name = name;
        this.parent = parent;
        this.children = new LinkedHashMap<String, Element>();
        this.duplicates = new ArrayList<Element>();
    }

    public Element(String name) {
        this.name = name;
        this.children = new LinkedHashMap<String, Element>();
        this.duplicates = new ArrayList<Element>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Element get(String child) {
        return children.get(child);
    }

    public Element get(int index) {
        if (index == 0) {
            return this;
        } else {
            return duplicates.get(index - 1);
        }
    }

    public boolean hasChild(String child) {
        return children.containsKey(child);
    }

    public ArrayList<Element> getChildren() {
        return new ArrayList<>(children.values());
    }

    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<Element> getSequence() {
        ArrayList<Element> sequence = new ArrayList<>();
        sequence.add(this);
        sequence.addAll(duplicates);
        return sequence;
    }

    protected Element putInSequence(String childName, Element parent) {
        Element child = new Element(childName, parent);
        duplicates.add(child);
        return child;
    }

    public Element put(String childName) {
        if (children.containsKey(childName)) {
            Element child = children.get(childName);
            assert child != null;
            return child.putInSequence(childName, this);
        } else {
            Element child = new Element(childName, this);
            children.put(childName, child);
            return child;
        }
    }

    protected void printSelf(String indent) {
        System.out.println(indent + name);
        String newIndent = indent + "  ";
        if (value != null) {
            System.out.println(newIndent + value);
        }
        for (Element child : children.values()) {
            child.printSelf(newIndent);
        }
        for (Element duplicate : duplicates) {
            duplicate.printSelf(indent);
        }
    }

    public void printSelf() {
        printSelf("");
    }
}
