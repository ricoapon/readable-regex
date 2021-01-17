package io.github.ricoapon.readableregex.instantiation;

import io.github.ricoapon.readableregex.ReadableRegexPattern;
import io.github.ricoapon.readableregex.RegexObjectInstantiation;
import io.github.ricoapon.readableregex.RegexObjectInstantiationException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.regex.Pattern;

import static io.github.ricoapon.readableregex.ReadableRegex.regex;
import static io.github.ricoapon.readableregex.RegexObjectInstantiation.instantiateObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegexObjectInstantiationTest {
    @Test
    void primitivesCanBeInjectedAsBoxedOrPrimitive_AndStringCanBeInjected() {
        // Given
        String value = "Byte:1byte:1Short:500short:500Integer:35000integer:35000Long:3000000000long:3000000000" +
                "Float:3.6float:3.6Double:3.6double:3.6Boolean:trueboolean:trueCharacter:ccharacter:cString:abc";
        ReadableRegexPattern pattern = regex()
                .literal("Byte:").group("byteBoxed", regex().digit())
                .literal("byte:").group("bytePrimitive", regex().digit())
                .literal("Short:").group("shortBoxed", regex().digit().oneOrMore())
                .literal("short:").group("shortPrimitive", regex().digit().oneOrMore())
                .literal("Integer:").group("integerBoxed", regex().digit().oneOrMore())
                .literal("integer:").group("integerPrimitive", regex().digit().oneOrMore())
                .literal("Long:").group("longBoxed", regex().digit().oneOrMore())
                .literal("long:").group("longPrimitive", regex().digit().oneOrMore())
                .literal("Float:").group("floatBoxed", regex().anyCharacterOf("0-9\\.").oneOrMore())
                .literal("float:").group("floatPrimitive", regex().anyCharacterOf("0-9\\.").oneOrMore())
                .literal("Double:").group("doubleBoxed", regex().anyCharacterOf("0-9\\.").oneOrMore())
                .literal("double:").group("doublePrimitive", regex().anyCharacterOf("0-9\\.").oneOrMore())
                .literal("Boolean:").group("booleanBoxed", regex().word())
                .literal("boolean:").group("booleanPrimitive", regex().word())
                .literal("Character:").group("characterBoxed", regex().anyCharacter())
                .literal("character:").group("characterPrimitive", regex().anyCharacter())
                .literal("String:").group("string", regex().word())
                .build();

        // When
        PrimitiveAndBoxedPrimitiveTypesAndString primitiveAndBoxedPrimitiveTypesAndString =
                instantiateObject(pattern, value, PrimitiveAndBoxedPrimitiveTypesAndString.class);

        // Then
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.byteBoxed, equalTo((byte) 1));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.bytePrimitive, equalTo((byte) 1));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.shortBoxed, equalTo((short) 500));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.shortPrimitive, equalTo((short) 500));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.integerBoxed, equalTo(35000));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.integerPrimitive, equalTo(35000));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.longBoxed, equalTo(3000000000L));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.longPrimitive, equalTo(3000000000L));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.floatBoxed, equalTo(3.6f));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.floatPrimitive, equalTo(3.6f));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.doubleBoxed, equalTo(3.6d));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.doublePrimitive, equalTo(3.6d));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.booleanBoxed, equalTo(Boolean.TRUE));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.booleanPrimitive, equalTo(true));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.characterBoxed, equalTo('c'));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.characterPrimitive, equalTo('c'));
        assertThat(primitiveAndBoxedPrimitiveTypesAndString.string, equalTo("abc"));
    }

    @Test
    void throwIfConstructorIsNotValid() {
        ReadableRegexPattern pattern = regex().group("n", regex().digit()).build();
        String data = "1";

        assertThrows(RegexObjectInstantiationException.class, () -> instantiateObject(pattern, data, MultipleConstructorsWithMultipleInjectAnnotations.class));
        assertDoesNotThrow(() -> instantiateObject(pattern, data, MultipleConstructorsWithSingleInjectAnnotation.class));
        assertDoesNotThrow(() -> instantiateObject(pattern, data, SingleConstructorWithInjectAnnotation.class));
        assertDoesNotThrow(() -> instantiateObject(pattern, data, SingleConstructorWithoutInjectAnnotation.class));
    }

    @Test
    void throwIfConstructorParamIsUnknownType() {
        ReadableRegexPattern pattern = regex().group("unknownType", regex()).build();
        String data = "";

        assertThrows(RegexObjectInstantiationException.class, () -> instantiateObject(pattern, data, ConstructorParamUnknownType.class));
    }

    @Test
    void throwIfParameterNameDoesNotOccurAsGroupName() {
        ReadableRegexPattern pattern = regex().build();
        String data = "";

        assertThrows(RegexObjectInstantiationException.class, () -> instantiateObject(pattern, data, SingleConstructorWithoutInjectAnnotation.class));
    }

    @Test
    void throwIfMatchIsNotExact() {
        ReadableRegexPattern pattern = regex().group("n", regex().digit()).build();
        String data = "1a";

        assertThrows(RegexObjectInstantiationException.class, () -> instantiateObject(pattern, data, SingleConstructorWithoutInjectAnnotation.class));
    }

    private static class NotInstantiatable {
        public NotInstantiatable() {
        }
    }

    @Test
    void throwIfClassCannotBeInstantiated() {
        ReadableRegexPattern pattern = regex().build();
        String data = "";

        assertThrows(RegexObjectInstantiationException.class, () -> instantiateObject(pattern, data, NotInstantiatable.class));
    }
}
