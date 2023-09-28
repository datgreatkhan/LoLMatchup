package com.ykhan.lolmatchup.model.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Converter(autoApply = true)
public class MapConverter implements AttributeConverter<Map<Integer, Object>, String> {

    private static final Logger logger = LoggerFactory.getLogger(MapConverter.class);

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<Integer, Object> map) {
        if(map == null) {
            return null;
        }

        try {
            return JSON_MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<Integer, Object> convertToEntityAttribute(String json) {
        if(json == null) {
            return null;
        }

        try {
            return JSON_MAPPER.readValue(json, new TypeReference<Map<Integer, Object>>() {});
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
    }
}
