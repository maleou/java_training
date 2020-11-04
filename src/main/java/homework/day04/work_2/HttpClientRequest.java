package homework.day04.work_2;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class HttpClientRequest {

    public static void main(String[] args) throws InterruptedException {
        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost:8801";
        Request request =
                new Request.Builder()
                        .url(url)
                        .get()
                        .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.info("request test failure : {}", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println( response.body().string());
//                log.info("request test success : {}", response.body().string());
                response.close();
            }
        });
        Thread.sleep(5000);
    }

}
