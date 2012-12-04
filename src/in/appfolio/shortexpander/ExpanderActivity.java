package in.appfolio.shortexpander;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.MimeTypeMap;

/**
 * User: Aleksandr Beshkenadze Date: 01.11.12 Time: 16:39
 */
public class ExpanderActivity extends Activity implements
		Expander.OnRequestListener {
	private ProgressDialog mProgess;
	private Expander mExpander;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Uri data = getIntent().getData();
		if (data == null) {
			finish();
		}

		expandUrl(data.toString());
	}

	/**
	 * stop activity event
	 */
	@Override
	protected void onStop() {
		super.onStop();

		// stop thread
		if (mExpander != null) {
			mExpander.cancel(false);
			mExpander = null;
		}

		// remove progress
		if (mProgess != null) {
			mProgess.dismiss();
			mProgess = null;
		}

		// end activity
		finish();
	}

	private void expandUrl(String url) {

		if (mProgess == null) {
			// show progress
			mProgess = new ProgressDialog(this);
			mProgess.setMessage(getString(R.string.loading));
			mProgess.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					finish();
				}
			});
			mProgess.show();
		}

		// URL redirect
		mExpander = new Expander(url, this);
	}

	static public void processImage(ShortInfo si, Activity a) {
		String url = si.getUrl();
		Log.i("ExpanderActivity", "Data:" + url);

		if (si.getType().contains("image") || ImageService.check(si.getUrl())) {
			// show image
			Intent i = new Intent(a, PreviewActivity.class);
			i.putExtra(PreviewActivity.URL, url);
			MyWindowActivity.openWindow(a, i);
		} else {
			// open URL
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			a.startActivity(intent);
			a.finish();
		}
	}

	@Override
	public void onSuccess(ShortInfo si) {
		processImage(si, ExpanderActivity.this);
	}

	@Override
	public void onError() {

	}
}