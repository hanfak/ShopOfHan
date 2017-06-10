package infrastructure.web.productavailability;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;

public class ProductAvailabilityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get All params from queries
        Map<String, String[]> parameterMap = request.getParameterMap();
        //Get All keys
        List<String> keys = parameterMap.keySet().stream().collect(Collectors.toList());
        keys.forEach(System.out::println);
        //Get all values for each key, which is a list
        List<String[]> values = parameterMap.values().stream().collect(Collectors.toList());
        //get each value for each key
        values.stream().forEach((val)->Arrays.stream(val).forEach(System.out::println));
        //get each value for first key
        Stream<String> stream = Arrays.stream(values.get(0));
        // String reduce = stream.reduce("", String::concat);
        String reduce = stream.collect(Collectors.joining(", "));


        // procedural

        String allKeyValues = "";

        for(Map.Entry<String, String[]> entry : parameterMap.entrySet() ) {
            String key = entry.getKey();
            String[] vals = entry.getValue();
            String valsStr = Arrays.stream(vals).collect(Collectors.joining(", "));
            String x = "Parameter Name - " + key + ", Value - " + valsStr + "\n";
            System.out.println("key values = " + x);
            allKeyValues += x;
        }

        //functional way
        String s = parameterMap.entrySet()
                .stream()
                .map(e -> e.getKey() + " = " + getValues(e))
                .collect(Collectors.joining("\n"));

        response.getWriter().write(format("output of all values for first query %s \n\n" +
                "output of all queries (key and values) procedurally\n\n%s\n\n" +
                "output of all queries (key and values) functionally \n\n" +
                "%s", reduce, allKeyValues, s));
    }

    private String getValues(Map.Entry<String, String[]> map) {
        return Arrays.stream(map.getValue()).collect(Collectors.joining(", "));
    }

}
