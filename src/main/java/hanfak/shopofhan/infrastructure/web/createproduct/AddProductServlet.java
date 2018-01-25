package hanfak.shopofhan.infrastructure.web.createproduct;

import hanfak.shopofhan.application.productavailability.AllProductsCheckUseCase;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.infrastructure.web.Marshaller;
import hanfak.shopofhan.infrastructure.web.RenderedContent;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static hanfak.shopofhan.infrastructure.web.RenderedContent.jsonContent;
//TODO Change class name
public class AddProductServlet extends HttpServlet {
    private final Unmarshaller<Product> unmarshaller;
    private final Marshaller<List<Product>> marshaller;
    private final AddProductWebService addProductWebService;
    private final AllProductsCheckUseCase allProductsCheckUseCase;

    public AddProductServlet(Unmarshaller<Product> unmarshaller, Marshaller<List<Product>> marshaller, AddProductWebService addProductWebService, AllProductsCheckUseCase allProductsCheckUseCase) {
        this.unmarshaller = unmarshaller;
        this.marshaller = marshaller;
        this.addProductWebService = addProductWebService;
        this.allProductsCheckUseCase = allProductsCheckUseCase;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO will be cleaned up when testing duplicate
        RenderedContent renderedContent = null;
        try {
            renderedContent = addProductWebService.addProduct(unmarshaller.unmarshall(request));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        renderedContent.render(response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<Product>> allProducts = allProductsCheckUseCase.getAllProducts();
        String marshalledProducts = marshaller.marshall(allProducts.get());
        jsonContent(marshalledProducts)
                .render(response);
    }
}
