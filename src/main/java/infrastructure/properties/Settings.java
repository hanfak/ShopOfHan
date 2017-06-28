package infrastructure.properties;

import infrastructure.properties.DatabaseSettings;
import infrastructure.properties.PropertiesReader;

public class Settings implements DatabaseSettings, ServerSettings {
    private PropertiesReader propertiesReader;

    public Settings(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    @Override
    public int serverPort() {
        return Integer.parseInt(propertiesReader.readProperty("server.port"));
    }

    // TODO Set depending on the machine??
    @Override
    public String databaseURL() {
        return propertiesReader.readProperty("database.url");
    }
    @Override
    public String databaseUsername() {
        return propertiesReader.readProperty("database.username");
    }
    @Override
    public String databasePassword() {
        return propertiesReader.readProperty("database.password");
    }

}
