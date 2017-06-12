package infrastructure.web.productavailability;

import application.ProductCheckUseCase;
import domain.ProductStock;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductAvailabilityServlet extends HttpServlet {

    private final ProductAvailabilityUnmarshaller unmarshaller;
    private final ProductAvailabilityMarshaller marshaller; //Use interface type?? Need to be injecte??
    private ProductCheckUseCase productCheckUseCase;
    private Logger logger;

    public ProductAvailabilityServlet(ProductAvailabilityUnmarshaller unmarshaller, ProductAvailabilityMarshaller marshaller, ProductCheckUseCase productCheckUseCase, Logger logger) {
        this.unmarshaller = unmarshaller;
        this.marshaller = marshaller;
        this.productCheckUseCase = productCheckUseCase;
        this.logger = logger;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // move to a webervice class
        ProductAvailabilityRequest productAvailabilityRequest = unmarshaller.unmarshall(request);
        logger.info("Unmarshalling");
        Optional<ProductStock> productStock = productCheckUseCase.checkStock(productAvailabilityRequest);
        // Need to change, this logic should be usecase
        if (productStock.isPresent() ) {
            logger.info("Product exists");

            response.setContentType("application/json");
            response.getWriter().write(marshaller.marshall(productStock.get()));
        } else {
            logger.info("Product does not exist");
            response.getWriter().write("No product of this name is stocked here");
        }
    }
}
