package httpclient;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static httpclient.Headers.fromApacheHeaders;

public class Request extends ValueType {
    private final String url;
    private final String method;
    private final Headers headers;
    private final QueryParameters queryParameters;
    private final String body;

    Request(String url, String method, Headers headers, QueryParameters queryParameters, String body) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.queryParameters = queryParameters;
        this.body = body;
    }

//    public static getQueryParameters() {
//        return URLEncodedUtils.parse(new URI(request.getURI().toString()), "UTF-8");
//    }

    public static Request toNiceRequestForYatspec(HttpGet request) {
        try {
            List<NameValuePair> parse = URLEncodedUtils.parse(new URI(request.getURI().toString()), "UTF-8");
            for (NameValuePair param : parse) {
                System.out.println(param.getName() + " : " + param.getValue());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            return new Request(request.getURI().toURL().toString(), request.getMethod(), fromApacheHeaders(request.getAllHeaders()), QueryParameters.empty(), "");
        } catch (MalformedURLException exception) {
            throw new RuntimeException("TODO make sure that the request has a proper format", exception);
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s%s%n%s%n%n%s", method, url, queryParameters, headers, body);
    }
}
