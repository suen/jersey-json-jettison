package com.example.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

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
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            JettisonMarshaller jsonMarshaller = JettisonJaxbContext.getJSONMarshaller(jaxbContext.createMarshaller());
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
            JAXBContext jaxbContext = JAXBContext.newInstance(classe);
            JettisonUnmarshaller jsonUnmarshaller = JettisonJaxbContext
                    .getJSONUnmarshaller(jaxbContext.createUnmarshaller());

            T object = jsonUnmarshaller.unmarshalFromJSON(new StringReader(jsonString), classe);
            return object;
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }

    }

}
