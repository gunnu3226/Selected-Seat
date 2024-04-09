package io.nbc.selectedseat.elasticsearch.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class JsonMapperUtil {

    private final ObjectMapper objectMapper;

    public JsonMapperUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T mapper(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }
}

