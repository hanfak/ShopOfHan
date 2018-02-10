package testinfrastructure.testrepositories;

import hanfak.shopofhan.infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import hanfak.shopofhan.infrastructure.database.jdbc.repositories.JDBCProductRepository;
import org.slf4j.Logger;

public class TestProductRepository extends JDBCProductRepository {

    public TestProductRepository(Logger logger, JDBCDatabaseConnectionManager databaseConnectionManager) {
        super(logger, databaseConnectionManager);
    }
}
