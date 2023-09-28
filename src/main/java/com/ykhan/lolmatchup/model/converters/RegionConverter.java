package com.ykhan.lolmatchup.model.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RegionConverter implements AttributeConverter<LeagueShard, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LeagueShard region) {
        if(region == null) {
            return null;
        }

        return region.ordinal();
    }

    @Override
    public LeagueShard convertToEntityAttribute(Integer id) {
        if(id == null) {
            return null;
        }

        return Stream.of(LeagueShard.values()).filter(s -> s.ordinal() == id).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
