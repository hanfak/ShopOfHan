package infrastructure.web.productavailability;

import application.ProductCheckUseCase;
import domain.ProductStock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductAvailabilityServlet extends HttpServlet {

    private final ProductAvailabilityUnmarshaller unmarshaller;
    private final ProductAvailabilityMarshaller marshaller; //Use interface type?? Need to be injecte??
    private ProductCheckUseCase productCheckUseCase;

    public ProductAvailabilityServlet(ProductAvailabilityUnmarshaller unmarshaller, ProductAvailabilityMarshaller marshaller, ProductCheckUseCase productCheckUseCase) {
        this.unmarshaller = unmarshaller;
        this.marshaller = marshaller;
        this.productCheckUseCase = productCheckUseCase;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductAvailabilityRequest productAvailabilityRequest = unmarshaller.unmarshall(request);

        ProductStock productStock = productCheckUseCase.checkStock(productAvailabilityRequest);

        response.setContentType("application/json");
        response.getWriter().write(marshaller.marshall(productStock));
    }
}
