package infrastructure.properties;

public class Settings implements DatabaseSettings, ServerSettings {
    public static final String MAC = "Mac";
    private final PropertiesReader propertiesReader;

    public Settings(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    @Override
    public int serverPort() {
        return Integer.parseInt(propertiesReader.readProperty("server.port"));
    }

    @Override
    public String databaseURL() {
        if (System.getProperty("os.name").contains(MAC)) {
            return propertiesReader.readProperty("database.local.url");
        } else {
            return propertiesReader.readProperty("database.work.url");
        }
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
