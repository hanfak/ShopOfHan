package hanfak.shopofhan.infrastructure.database.jdbc;


import hanfak.shopofhan.domain.crosscutting.ValueType;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

import static hanfak.shopofhan.infrastructure.database.jdbc.OracleColumnAdapters.adaptToEmptyStringRepresentation;

// TODO test
public class EnhancedPreparedStatement extends ValueType {

    private final PreparedStatement preparedStatement;
    private int index = 1;

    public EnhancedPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public void setLong(long aLong) throws SQLException {
        preparedStatement.setLong(index++, aLong);
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) throws SQLException {
        preparedStatement.setTimestamp(index++, Timestamp.from(zonedDateTime.toInstant()));
    }

    public void setString(String string) throws SQLException {
        preparedStatement.setString(index++, adaptToEmptyStringRepresentation(string));
    }

    public void setClob(String string) throws SQLException {
        preparedStatement.setClob(index++, new StringReader(adaptToEmptyStringRepresentation(string)));
    }

    public void addBatch() throws SQLException {
        preparedStatement.addBatch();
        index = 1;
    }
}
