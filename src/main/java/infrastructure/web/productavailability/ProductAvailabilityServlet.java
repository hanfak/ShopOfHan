package infrastructure.web.productavailability;

import domain.ProductStock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductAvailabilityServlet extends HttpServlet {

    private final ProductAvailabilityUnmarshaller unmarshaller;
    private final ProductAvailabilityMarshaller marshaller; //Use interface type?? Need to be injecte??

    public ProductAvailabilityServlet(ProductAvailabilityUnmarshaller unmarshaller, ProductAvailabilityMarshaller marshaller) {
        this.unmarshaller = unmarshaller;
        this.marshaller = marshaller;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductAvailabilityRequest productAvailabilityRequest = unmarshaller.unmarshall(request);

        //productCheckUseCase.checkStock(productAvailabilityRequest);
        ProductStock productStock;
        if (productAvailabilityRequest.productName.equals("Han")) {
            productStock = new ProductStock(productAvailabilityRequest.productName, 5);
        }
        else {
            productStock = new ProductStock(productAvailabilityRequest.productName, 0);
        }

        response.setContentType("application/json");
        response.getWriter().write(marshaller.marshall(productStock));
    }
}
