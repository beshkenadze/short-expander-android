package in.appfolio.shortexpander;

import android.os.AsyncTask;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * User: Aleksandr Beshkenadze Date: 01.11.12 Time: 17:00
 */

public class Expander extends AsyncTask<String, Void, ShortInfo> {

	public interface OnRequestListener {
		void onSuccess(ShortInfo si);

		void onError();
	}


	private OnRequestListener mListener;

	public Expander(String url, OnRequestListener listener) {
		mListener = listener;
		execute(url);
	}

	@Override
	protected ShortInfo doInBackground(String... strings) {
		return expand(strings[0]);
	}

	public ShortInfo expand(String uri) {
		ShortInfo si = new ShortInfo();
		HttpClient httpclient = new DefaultHttpClient();
		HttpHead httphead = new HttpHead(uri);
		HttpContext context = new BasicHttpContext();

		try {
			HttpResponse response = httpclient.execute(httphead, context);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
				throw new IOException(response.getStatusLine().toString());
			HttpUriRequest currentReq = (HttpUriRequest) context
					.getAttribute(ExecutionContext.HTTP_REQUEST);
			HttpHost currentHost = (HttpHost) context
					.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
			String currentUrl = (currentReq.getURI().isAbsolute()) ? currentReq
					.getURI().toString() : (currentHost.toURI() + currentReq
					.getURI());
			String type = response.getFirstHeader("Content-type").getValue();
			si.setUrl(currentUrl);
			si.setType(type);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return si;
	}

	/**
	 * post execute procedure
	 */
	@Override
	protected void onPostExecute(ShortInfo result) {
		if (mListener != null) {
			if (result != null) {
				mListener.onSuccess(result);
			} else {
				mListener.onError();
			}
		}
	}
}
