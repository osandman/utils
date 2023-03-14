package net.osandman.util.parsing.xml_example;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "test")
public class TestForXml {
    @XmlElement(name = "needCDATA", type = String.class)
    public String[] cdata = new String[]{"<needCDATA><![CDATA[need CDATA because of < & and >]]>!!!</needCDATA>", "ABC"};
    @XmlElement(name = "second")
    List<String> list = new ArrayList<>();
    @XmlElement(name = "third")
    List<String> list1 = new ArrayList<>();
}
