package acceptancetests;

import acceptancetests.thens.Thens;
import acceptancetests.whens.WhenAPostIsMadeToShopOfHanToCreateANewProduct;
import acceptancetests.whens.Whens;
import com.googlecode.yatspec.junit.SpecResultListener;
import com.googlecode.yatspec.junit.WithCustomResultListeners;
import com.googlecode.yatspec.plugin.sequencediagram.SequenceDiagramGenerator;
import com.googlecode.yatspec.plugin.sequencediagram.SvgWrapper;
import com.googlecode.yatspec.rendering.html.DontHighlightRenderer;
import com.googlecode.yatspec.rendering.html.HtmlResultRenderer;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import hanfak.shopofhan.infrastructure.properties.Settings;
import hanfak.shopofhan.wiring.ShopOfHan;
import org.junit.After;
import org.junit.Before;
import testinfrastructure.TestWiring;

import static java.util.Collections.singletonList;

public abstract class AcceptanceTest extends TestState implements WithCustomResultListeners {
    public static final String APPLICATION_NAME = "Shop Of Han app";
    private final ShopOfHan shopOfHan = new ShopOfHan();
    private final acceptancetests.TestState testState = new acceptancetests.TestState();
    protected final Whens weMake = new Whens(testState);
    protected final WhenAPostIsMadeToShopOfHanToCreateANewProduct whenAPostIsMadeToShopOfHanToCreateANewProduct = new WhenAPostIsMadeToShopOfHanToCreateANewProduct(testState);
    protected final Thens the = new Thens(testState, capturedInputAndOutputs); // TODO rename
    private final TestWiring wiring = new TestWiring();

    @Before
    public void setUp() throws Exception {
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

    //Need to show body of response and queries of request in diagrams
    private SvgWrapper generateSequenceDiagram() {
        return new SequenceDiagramGenerator().generateSequenceDiagram(new ByNamingConventionMessageProducer().messages(capturedInputAndOutputs));
    }

    private  Settings loadTestSettings() {
        return wiring.settings();
    }
}
