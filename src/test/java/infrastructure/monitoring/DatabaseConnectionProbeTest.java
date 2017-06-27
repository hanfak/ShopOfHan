//package infrastructure.properties.monitoring;
//
//import org.assertj.core.api.WithAssertions;
//import org.junit.Test;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
// //TODO Avoid testing logs
//public class DatabaseConnectionProbeTest implements WithAssertions {
//
//    private static final String DATABASE_URL = "jdbc:oracle:thin:@//hamster-db3.sns.sky.com:1521/DEVBLD";
//
//    private final TestLogger logger = new TestLogger();
//    private final Settings settings = settings();
//    private final DatabaseConnectionProvider connectionProvider = mock(DataSourceDatabaseConnectionProvider.class);
//    private final Connection connection = mock(Connection.class);
//    private final PreparedStatement preparedStatement = mock(PreparedStatement.class);
//    private final ResultSet resultSet = mock(ResultSet.class);
//
//    @Test
//    public void nameMentionsDatabaseURL() {
//        DatabaseConnectionProbe databaseConnectionProbe = new DatabaseConnectionProbe(logger, settings, connectionProvider);
//
//        assertThat(databaseConnectionProbe.name()).isEqualTo("Database Connection to 'jdbc:oracle:thin:@//hamster-db3.sns.sky.com:1521/DEVBLD'");
//    }
//
//    @Test
//    public void failsOnSQLException() throws Exception {
//        SQLException sqlException = new SQLException("message");
//        when(connectionProvider.getConnection()).thenReturn(connection);
//        when(connection.prepareStatement("SELECT 1 FROM DUAL")).thenThrow(sqlException);
//
//        DatabaseConnectionProbe databaseConnectionProbe = new DatabaseConnectionProbe(logger, settings, connectionProvider);
//
//        ProbeResult result = databaseConnectionProbe.probe();
//
//        assertThat(result.description).isEqualTo("SQLException: message");
//        assertThat(result.status).isEqualTo(ProbeStatus.FAIL);
//        assertThat(logger.errorLogs()).containsExactly("SQLException: message");
//        assertThat(logger.errorCauses()).containsExactly(sqlException);
//    }
//
//    @Test
//    public void failsWhenTestQueryHasNoResult() throws Exception {
//        when(connectionProvider.getConnection()).thenReturn(connection);
//        when(connection.prepareStatement("SELECT 1 FROM DUAL")).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//        when(resultSet.next()).thenReturn(false);
//
//        DatabaseConnectionProbe databaseConnectionProbe = new DatabaseConnectionProbe(logger, settings, connectionProvider);
//
//        ProbeResult result = databaseConnectionProbe.probe();
//
//        assertThat(result.description).isEqualTo("Database test query 'SELECT 1 FROM DUAL' was not successful. Result was not 1.");
//        assertThat(result.status).isEqualTo(ProbeStatus.FAIL);
//    }
//
//    @Test
//    public void failsWhenTestQueryHasBadResult() throws Exception {
//        when(connectionProvider.getConnection()).thenReturn(connection);
//        when(connection.prepareStatement("SELECT 1 FROM DUAL")).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//        when(resultSet.next()).thenReturn(true);
//        when(resultSet.getInt(1)).thenReturn(999);
//
//        DatabaseConnectionProbe databaseConnectionProbe = new DatabaseConnectionProbe(logger, settings, connectionProvider);
//
//        ProbeResult result = databaseConnectionProbe.probe();
//
//        assertThat(result.description).isEqualTo("Database test query 'SELECT 1 FROM DUAL' was not successful. Result was not 1.");
//        assertThat(result.status).isEqualTo(ProbeStatus.FAIL);
//    }
//
//    private Settings settings() {
//        Settings settings = mock(Settings.class);
//        when(settings.databaseUrl()).thenReturn(DATABASE_URL);
//        return settings;
//    }
//}