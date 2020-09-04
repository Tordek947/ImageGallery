package net.atlassian.cmathtutor.converter;

public interface Converter<D, E> {

    E convert(D data);

    D convertToData(E entity);
}
