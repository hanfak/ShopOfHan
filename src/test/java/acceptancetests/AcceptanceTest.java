package acceptancetests;

import acceptancetests.thens.Then;
import acceptancetests.whens.ANewProductIsAdded;
import acceptancetests.whens.StockIsAdded;
import acceptancetests.whens.Whens;
import com.googlecode.yatspec.junit.SpecResultListener;
import com.googlecode.yatspec.junit.WithCustomResultListeners;
import com.googlecode.yatspec.plugin.sequencediagram.SequenceDiagramGenerator;
import com.googlecode.yatspec.plugin.sequencediagram.SvgWrapper;
import com.googlecode.yatspec.rendering.html.DontHighlightRenderer;
import com.googlecode.yatspec.rendering.html.HtmlResultRenderer;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import hanfak.shopofhan.application.crosscutting.ProductStockRepository;
import hanfak.shopofhan.infrastructure.properties.Settings;
import hanfak.shopofhan.wiring.ShopOfHan;
import org.junit.After;
import org.junit.Before;
import testinfrastructure.TestWiring;
import testinfrastructure.testrepositories.TestProductRepository;
import testinfrastructure.testrepositories.TestStockRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.util.Collections.singletonList;
import static testinfrastructure.TestWiring.ENVIRONMENT;

public abstract class AcceptanceTest extends TestState implements WithCustomResultListeners {
    public static final String APPLICATION_NAME = "Shop Of Han app";
    private final ShopOfHan shopOfHan = new ShopOfHan();
    protected final acceptancetests.TestState testState = new acceptancetests.TestState();

    protected final Whens weMake = new Whens(testState); // TODO naming of these
    protected final ANewProductIsAdded aNewProductIsAdded = new ANewProductIsAdded(testState);
    protected final StockIsAdded stockIsAdded = new StockIsAdded(testState);

    protected final Then then = new Then(testState, capturedInputAndOutputs);
    private static final TestWiring TEST_WIRING = new TestWiring();
    public static final TestProductRepository productRepository = TEST_WIRING.testProductRepository();
    public static final TestStockRepository stockRepository = TEST_WIRING.testStockRepository();
    public static final ProductStockRepository testProductStockRepository = TEST_WIRING.productStockRepository();

    @Before
    public void setUp() throws Exception {
        resetDatabaseContents();
        shopOfHan.startWebServer(loadTestSettings(), new TestWiring());
    }

    @After
    public void tearDown() throws Exception {
        shopOfHan.stopWebServer();
        capturedInputAndOutputs.add("Sequence Diagram", generateSequenceDiagram());
    }

    @Override
    public Iterable<SpecResultListener> getResultListeners() throws Exception {
        return singletonList(new HtmlResultRenderer()
                .withCustomHeaderContent(SequenceDiagramGenerator.getHeaderContentForModalWindows())
                .withCustomRenderer(SvgWrapper.class, new DontHighlightRenderer<>()));
    }

    //Need to show theBodyIs of response and queries of request in diagrams
    private SvgWrapper generateSequenceDiagram() {
        return new SequenceDiagramGenerator().generateSequenceDiagram(new ByNamingConventionMessageProducer().messages(capturedInputAndOutputs));
    }

    private Settings loadTestSettings() {
        return TEST_WIRING.settings();
    }

// TODO reset primings for all tests in @Before
    public void resetDatabaseContents() throws IOException {
        executeSQL("DELETE FROM stock");
        executeSQL("DELETE FROM product");
    }

    @SuppressWarnings("ConstantConditions")
    private void executeSQL(String sql) {
        // Change to run stubs
        if (!ENVIRONMENT.equals("test")) {
            return;
        }
        try (Connection connection = TEST_WIRING.databaseConnectionManager().getDBConnection().get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            if (statement.execute()) {
                throw new IllegalArgumentException(statement.toString());
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
