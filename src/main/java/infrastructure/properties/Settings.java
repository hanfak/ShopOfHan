package infrastructure.properties;

public class Settings implements DatabaseSettings{
    private PropertiesReader propertiesReader;

    public Settings(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    public int serverPort() {
        return Integer.parseInt(propertiesReader.readProperty("server.port"));
    }

    // Set depending on the machine??
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
