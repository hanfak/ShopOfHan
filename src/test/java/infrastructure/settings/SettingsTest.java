package infrastructure.settings;

import infrastructure.Settings;
import infrastructure.PropertiesReader;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SettingsTest implements WithAssertions {
    @Test
    public void mongoDbPortTest() throws Exception {
        PropertiesReader properties = mock(PropertiesReader.class);
        Settings settings = new Settings(properties);

        when(properties.readProperty("server.port")).thenReturn("8081");

        assertThat(settings.serverPort()).isEqualTo(8081);
    }
}