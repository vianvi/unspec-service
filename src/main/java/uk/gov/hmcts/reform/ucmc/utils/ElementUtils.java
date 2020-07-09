package uk.gov.hmcts.reform.ucmc.utils;

import uk.gov.hmcts.reform.ucmc.model.common.Element;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

public class ElementUtils {

    private ElementUtils() {
        //NO-OP
    }

    @SafeVarargs
    public static <T> List<Element<T>> wrapElements(T... elements) {
        return Stream.of(elements)
            .filter(Objects::nonNull)
            .map(element -> Element.<T>builder().value(element).build())
            .collect(toList());
    }

    public static <T> List<T> unwrapElements(List<Element<T>> elements) {
        return nullSafeCollection(elements)
            .stream()
            .map(Element::getValue)
            .filter(Objects::nonNull)
            .collect(toUnmodifiableList());
    }

    public static <T> Element<T> element(T element) {
        return Element.<T>builder()
            .id(UUID.randomUUID())
            .value(element)
            .build();
    }

    public static <T> Element<T> element(UUID id, T element) {
        return Element.<T>builder()
            .id(id)
            .value(element)
            .build();
    }

    public static <T> Optional<Element<T>> findElement(UUID id, List<Element<T>> elements) {
        return nullSafeCollection(elements).stream()
            .filter(element -> Objects.equals(element.getId(), id))
            .findFirst();
    }

    private static <T> Collection<T> nullSafeCollection(Collection<T> collection) {
        return collection == null ? Collections.emptyList() : collection;
    }
}