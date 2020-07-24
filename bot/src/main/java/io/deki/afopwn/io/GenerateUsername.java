package io.deki.afopwn.io;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GenerateUsername {

    public static String get() {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            HttpResponse response = client.execute(new HttpGet("https://api.deki.io/username-generator.php"));
            String html = EntityUtils.toString(response.getEntity()).replace("    ", "");
            return html;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
