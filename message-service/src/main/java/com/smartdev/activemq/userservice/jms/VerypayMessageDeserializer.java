package com.smartdev.activemq.userservice.jms;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VerypayMessageDeserializer extends StdDeserializer<VerypayMessage<?>>  {

    public VerypayMessageDeserializer() {
        this(null);
    }

    public VerypayMessageDeserializer(Class<?> vc) {
        super(vc);
    }

    private JavaType valueType;

    @Override
    public VerypayMessage<?> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
// add include filters which matches all the classes (or use your own)
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile("com.smartdev.activemq.*")));
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile("ch.smartdev.activemq.*")));

// get matching classes defined in the package

        String bodyClass = node.get("body").get("@className").asText();
        BeanDefinition classes = provider.findCandidateComponents("*.smartdev").stream().filter(x->x.getBeanClassName().contains(bodyClass))
                .findAny().get();

        VerypayMessage verypayMessage = new VerypayMessage<>();
       ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
           verypayMessage.setBody(mapper.readValue(node.get("body").toString(), Class.forName(classes.getBeanClassName())));
       }catch (Exception ex) {
           throw new RuntimeException(ex);
       }
        return verypayMessage;

    }

}
