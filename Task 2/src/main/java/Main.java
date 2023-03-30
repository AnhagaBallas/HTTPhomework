import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet request = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=UDXmZdAtRYilZKPHKdD2EF2F9Fs2tBZVeFBHj1By");
        CloseableHttpResponse response = httpClient.execute(request);
        Post post = mapper.readValue(response.getEntity().getContent(), Post.class);
        String url = post.getUrl();
        HttpGet requstUrl = new HttpGet(url);
        CloseableHttpResponse responseUrl = httpClient.execute(requstUrl);
        File file = new File("C:\\Users\\USER\\IdeaProjects\\Nasa\\image");
        file.createNewFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(responseUrl.getEntity().getContent().readAllBytes());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(post.toString());

    }
}
