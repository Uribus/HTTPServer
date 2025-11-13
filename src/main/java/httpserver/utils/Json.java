package httpserver.utils;

import java.io.IOException;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;


public class Json {
    private static ObjectMapper objectMapper;

    private static ObjectMapper defaulObjectMapper() {
        ObjectMapper objMapper = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();
        //objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); not working anymore in v.3.0.0
        return objMapper;
    }

    public static JsonNode parse(String jsonSrc) throws IOException {
        return objectMapper.readTree(jsonSrc);
    }

    public static <T> T fromJson(JsonNode node, Class<T> toClass) throws JacksonException {
        return objectMapper.treeToValue(node, toClass);
    }

    public static JsonNode toJson(Object obj) {
        return objectMapper.valueToTree(obj);
    }

    public static String nodeToString(JsonNode node) {
        return generateJson(node, false);
    }

    public static String nodeToStringPretty(JsonNode node) {
        return generateJson(node, true);
    }

    private static String generateJson(Object o, boolean pretty) throws JacksonException {
        ObjectWriter oWriter = objectMapper.writer();

        if (pretty)
            oWriter = oWriter.with(SerializationFeature.INDENT_OUTPUT); 
        
        return oWriter.writeValueAsString(oWriter);
    }
}
