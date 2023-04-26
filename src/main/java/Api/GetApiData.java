package Api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class GetApiData {

    final OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    public static String run (int start, int end) {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
            urlBuilder.append("/" + URLEncoder.encode("7846544c6f6a6f6438365359566359", "UTF-8"));
            urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
            urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8"));
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(start), "UTF-8"));
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(end), "UTF-8"));
            String url = urlBuilder.toString();
            GetApiData getApi = new GetApiData();
            String response = getApi.run(url);
            return response;



        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}
