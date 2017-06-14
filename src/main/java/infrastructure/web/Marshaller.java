package infrastructure.web;

import java.io.IOException;

public interface Marshaller<T> {
    String marshall(T object) throws IOException;
    }
