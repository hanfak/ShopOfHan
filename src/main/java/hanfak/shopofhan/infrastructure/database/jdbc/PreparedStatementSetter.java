package hanfak.shopofhan.infrastructure.database.jdbc;

import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter<Parameters> {
    void setParameters(EnhancedPreparedStatement preparedStatement, Parameters parameters) throws SQLException;
}
