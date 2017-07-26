package infrastructure.web.statusprobeservlet;

import domain.monitoring.ProbeResult;
import domain.monitoring.ProbeStatus;
import infrastructure.monitoring.DatabaseConnectionProbe;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatusProbeServlet extends HttpServlet {

    private final DatabaseConnectionProbe databaseConnectionProbe;

    public StatusProbeServlet(DatabaseConnectionProbe databaseConnectionProbe) {
        this.databaseConnectionProbe = databaseConnectionProbe;
    }

    @SuppressWarnings("UnusedLocalVariable")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        ProbeResult probe = databaseConnectionProbe.probe();
        String description = probe.description;
        String name = probe.name;
        ProbeStatus status = probe.status;

        response.getWriter().write(description);
    }
}
