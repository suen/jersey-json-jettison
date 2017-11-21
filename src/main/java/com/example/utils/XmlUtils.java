package com.example.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public final class XmlUtils {

    private Unmarshaller unmarshaller;
    private Marshaller marshaller;

    public XmlUtils(Class<?>... classes) {

        try {
            JAXBContext jaxbContext;
            if (classes != null && classes.length > 0) {
                jaxbContext = JAXBContext.newInstance(classes);
            } else {
                throw new IllegalArgumentException("No class in the JAXB context");
            }
            this.unmarshaller = jaxbContext.createUnmarshaller();
            this.marshaller = jaxbContext.createMarshaller();
            this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> T unmarshal(String xmlStr, Class<T> classe) {
        try {
            T t = (T) unmarshaller.unmarshal(new StringReader(xmlStr));
            return t;
        } catch (JAXBException e) {
            throw new IllegalArgumentException("Deserialization XML failed: " + xmlStr, e);
        }

    }

    public String marshal(Object object) {
        StringWriter writer = new StringWriter();
        try {
            marshaller.marshal(object, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new IllegalArgumentException("Serialization XML failed : " + object, e);
        }

    }

}
