package com.ykhan.lolmatchup.model.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import no.stelar7.api.r4j.basic.constants.types.lol.LaneType;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class LaneTypeConverter implements AttributeConverter<LaneType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LaneType lane) {
        if(lane == null) {
            return null;
        }

        return lane.ordinal();
    }

    @Override
    public LaneType convertToEntityAttribute(Integer id) {
        if(id == null) {
            return null;
        }

        return Stream.of(LaneType.values()).filter(s -> s.ordinal() == id).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
