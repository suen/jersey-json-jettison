package com.example.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.glassfish.jersey.jettison.JettisonConfig;
import org.glassfish.jersey.jettison.JettisonJaxbContext;
import org.glassfish.jersey.jettison.JettisonMarshaller;
import org.glassfish.jersey.jettison.JettisonUnmarshaller;
import org.junit.Test;

public class TestJettison {

    @Test
    public void testDeserialisation() {

        PetStore petstore = StaticPetStoreBuilder.build();
        String json = toJson(petstore);

        System.out.println("[Java] -> [Json]");
        System.out.println(json);

        PetStore petStoreFromJson = toObject(json, PetStore.class);
        String json2 = toJson(petStoreFromJson);

        System.out.println("[Java] -> [Json] -> [Java] -> [Json]");
        System.out.println(json2);

        assertThat("Failure JSON deserialisation with @xsi.type", json, equalTo(json2));

    }

    public static String toJson(Object object) {

        try {

            Map<String, String> xml2JsonNs = new HashMap<>();
            xml2JsonNs.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
            JettisonConfig jettisonConfig = JettisonConfig.mappedJettison().xml2JsonNs(xml2JsonNs).build();
            JettisonJaxbContext jettisonContext = new JettisonJaxbContext(jettisonConfig, object.getClass());

            JettisonMarshaller jsonMarshaller = jettisonContext.createJsonMarshaller();
            jsonMarshaller.setProperty(JettisonMarshaller.FORMATTED, true);

            StringWriter writer = new StringWriter();
            jsonMarshaller.marshallToJSON(object, writer);
            return writer.toString();

        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }

    }

    public static <T> T toObject(String jsonString, Class<T> classe) {
        try {
            Map<String, String> xml2JsonNs = new HashMap<>();
            xml2JsonNs.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
            JettisonConfig jettisonConfig = JettisonConfig.mappedJettison().xml2JsonNs(xml2JsonNs).build();
            JettisonJaxbContext jettisonContext = new JettisonJaxbContext(jettisonConfig, classe);

            JettisonUnmarshaller jsonUnmarshaller = jettisonContext.createJsonUnmarshaller();

            T object = jsonUnmarshaller.unmarshalFromJSON(new StringReader(jsonString), classe);
            return object;
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }

    }

}
