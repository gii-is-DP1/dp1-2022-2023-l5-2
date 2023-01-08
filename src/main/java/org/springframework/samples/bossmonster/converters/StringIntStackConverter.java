package org.springframework.samples.bossmonster.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;

@Converter
public class StringIntStackConverter implements AttributeConverter<Stack<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(Stack<Integer> attribute) {
        return attribute.stream().map(i->i.toString()).collect(Collectors.joining(";"));
    }

    @Override
    public Stack<Integer> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(";"))
            .filter(s->!s.isEmpty())
            .map(Integer::parseInt)
            .collect(Collectors.toCollection(Stack::new));

    }
}
