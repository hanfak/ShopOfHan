package infrastructure.properties;

public class Settings {
    private PropertiesReader propertiesReader;

    public Settings(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    public int serverPort() {
        return Integer.parseInt(propertiesReader.readProperty("server.port"));
    }

    public String databaseURL() {
        return propertiesReader.readProperty("database.url");
    }

    public String databaseUsername() {
        return propertiesReader.readProperty("database.username");
    }

    public String databasePassword() {
        return propertiesReader.readProperty("database.password");
    }

}
