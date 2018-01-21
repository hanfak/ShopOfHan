package hanfak.shopofhan.infrastructure.web.removeproduct;

import hanfak.shopofhan.application.removeproduct.RemoveProductUseCase;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static hanfak.shopofhan.infrastructure.web.RenderedContent.successContent;
import static java.lang.String.format;

public class RemoveProductServlet extends HttpServlet {
    private final Unmarshaller<ProductId> unmarshaller;
    private final RemoveProductUseCase removeProductUseCase;

    public RemoveProductServlet(Unmarshaller<ProductId> unmarshaller, RemoveProductUseCase removeProductUseCase) {
        this.unmarshaller = unmarshaller;
        this.removeProductUseCase = removeProductUseCase;
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductId productId = unmarshaller.unmarshall(request);
        removeProductUseCase.removeProduct(productId);
        successContent(format("Product, %s, has been deleted", productId.value))
                .render(response);
    }
}
