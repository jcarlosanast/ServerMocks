import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Wiremock {

    @Test
    public void exampleTest() {
        // Setup the WireMock mapping stub for the test
        stubFor(post("/my/resource")
                .withHeader("Content-Type", containing("xml"))
                .willReturn(ok()
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>SUCCESS</response>")));

        // Setup HTTP POST request (with HTTP Client embedded in Java 11+)
        final HttpClient client = HttpClient.newBuilder().build();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(wiremockServer.url("/my/resource"))
                .header("Content-Type", "text/xml")
                .POST().build();

        // Send the request and receive the response
        final HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verify the response (with AssertJ)
        assertThat(response.statusCode()).as("Wrong response status code").isEqualTo(200);
        assertThat(response.body()).as("Wrong response body").contains("<response>SUCCESS</response>");
    }
}
