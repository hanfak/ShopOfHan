package infrastructure.web;

import domain.ProductStock;

import java.io.IOException;

public interface Marshaller {
    // Make param abstract ie Result result
    String marshall(ProductStock productStock) throws IOException;

    }
