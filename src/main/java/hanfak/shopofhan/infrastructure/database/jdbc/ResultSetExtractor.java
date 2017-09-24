package hanfak.shopofhan.infrastructure.database.jdbc;

import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetExtractor<Record> {
    Record extract(EnhancedResultSet resultSet) throws SQLException;
}
