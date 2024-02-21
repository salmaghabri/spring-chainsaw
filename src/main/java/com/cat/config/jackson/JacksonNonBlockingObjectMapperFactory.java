package com.cat.config.jackson;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JacksonNonBlockingObjectMapperFactory {

    /**
     * Deserializer that won't block if value parsing doesn't match with target type
     *
     * @param <T> Handled type
     */
    private static class NonBlockingDeserializer<T> extends JsonDeserializer<T> {
        private final StdDeserializer<T> delegate;

        public NonBlockingDeserializer(StdDeserializer<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
            try {
                return delegate.deserialize(jsonParser, context);
            } catch (JsonMappingException jsonMappingException) {
                log.warn("Cannot deserialize attribute : " + jsonParser.getCurrentName()
                        + ", error detail : " + jsonMappingException.getMessage());
                return null;
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private List<StdDeserializer> jsonDeserializers = new ArrayList<>();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        var customJacksonModuleVersion = new Version(0, 1, 0, null, null, null);
        SimpleModule customJacksonModule = new SimpleModule("customJacksonModule", customJacksonModuleVersion);
        for (StdDeserializer jsonDeserializer : jsonDeserializers)
            customJacksonModule.addDeserializer(jsonDeserializer.handledType(), new NonBlockingDeserializer(jsonDeserializer));

        objectMapper.registerModule(customJacksonModule);
        return objectMapper;
    }

    @SuppressWarnings("rawtypes")
    public JacksonNonBlockingObjectMapperFactory setJsonDeserializers(List<StdDeserializer> jsonDeserializers) {
        this.jsonDeserializers = jsonDeserializers;
        return this;
    }
}