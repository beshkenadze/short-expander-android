package in.appfolio.shortexpander.helper;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import in.appfolio.shortexpander.BuildConfig;
import in.appfolio.shortexpander.model.ExpandModel;
import timber.log.Timber;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 24/03/15.
 */
public class ExpanderHelper {
    public static ExpandModel expand (String url) {
        ExpandModel expandModel = new ExpandModel();
        OkHttpClient client = new OkHttpClient();
        if (BuildConfig.DEBUG) {
            client.networkInterceptors().add(new LoggingInterceptor());
        }
        Request request = new Request.Builder()
                .url(url)
                .head()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.headers() != null && response.message().equals("OK")) {
                String expandUrl = response.request().urlString();
                String contentType = response.header("Content-type");

                if (contentType.contains(";")) { // may be contain not only content-type.
                    String[] contentTypeInfo = contentType.split(";");
                    if (contentTypeInfo.length > 0) {
                        contentType = contentTypeInfo[0];
                    }
                }

                Timber.i("expandUrl: %s", expandUrl);
                Timber.i("contentType: %s", contentType);

                expandModel.setUrl(expandUrl);
                expandModel.setContentType(contentType);
            }
            response.body().close();
        } catch (IOException e) {
            Timber.e(e, "Expand %s", url);
        }

        return expandModel;
    }

    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept (Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Timber.i("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Timber.i("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            return response;
        }
    }
}
