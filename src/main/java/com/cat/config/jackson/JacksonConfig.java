package com.cat.config.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class JacksonConfig {

    @Bean
    ObjectMapper objectMapper() {

        return new JacksonNonBlockingObjectMapperFactory()
                .setJsonDeserializers(
                        Arrays.asList(
                                new NumberDeserializers.ShortDeserializer(Short.class, null),
                                new NumberDeserializers.IntegerDeserializer(Integer.class, null),
                                new NumberDeserializers.CharacterDeserializer(Character.class, null),
                                new NumberDeserializers.LongDeserializer(Long.class, null),
                                new NumberDeserializers.FloatDeserializer(Float.class, null),
                                new NumberDeserializers.DoubleDeserializer(Double.class, null),
                                new NumberDeserializers.BooleanDeserializer(Boolean.class, null),
                                new NumberDeserializers.NumberDeserializer(),
                                new NumberDeserializers.BigDecimalDeserializer(),
                                new NumberDeserializers.BigIntegerDeserializer()
                        )
                ).createObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE,false)
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
                .configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS,false)
                .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES,false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE,true);
    }
}
