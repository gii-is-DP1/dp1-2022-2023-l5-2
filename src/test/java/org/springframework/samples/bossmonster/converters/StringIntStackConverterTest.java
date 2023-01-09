package org.springframework.samples.bossmonster.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

class StringIntStackConverterTest {

    StringIntStackConverter converter;

    @BeforeEach
    void setUp() {
        converter = new StringIntStackConverter();
    }

    @Test
    void convertToDatabaseColumn() {
        Stack<Integer> stack = new Stack<>();
        stack.add(1);
        stack.add(2);
        stack.add(3);
        String result = converter.convertToDatabaseColumn(stack);
        assertThat(result, is("1;2;3"));
        String empty = converter.convertToDatabaseColumn(new Stack<>());
        assertThat(empty,is(""));
    }

    @Test
    void convertToEntityAttribute() {
        String regularString = "1;2;3";
        Stack<Integer> result = converter.convertToEntityAttribute(regularString);
        Stack<Integer> expectedStack = new Stack<>();
        expectedStack.add(1);
        expectedStack.add(2);
        expectedStack.add(3);
        assertThat(result,is(expectedStack));

        result = converter.convertToEntityAttribute("");
        assertThat(result,is(new Stack<>()));
    }
}
