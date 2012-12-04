package in.appfolio.shortexpander;

import uk.co.senab.photoview.PhotoViewAttacher;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class PreviewActivity extends Activity {

	public static final String URL = "Preview:URL";
	private PhotoViewAttacher mAttacher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);

		if (!getIntent().getExtras().containsKey(URL)) {
			finish();
		}

		final String originalUrl = getIntent().getExtras().getString(URL);

		String imageUrl = originalUrl;

		if (ImageService.check(originalUrl)) {
			imageUrl = ImageService.get(originalUrl);
		}
		Debug.i("Preview Url:" + imageUrl);
		
		ImageView imageView = (ImageView) findViewById(R.id.iv_preview);
		ImageButton share = (ImageButton) findViewById(R.id.ib_share);
		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(originalUrl));
				startActivity(browserIntent);
			}
		});
		final ProgressBar loaderBar = (ProgressBar) findViewById(R.id.pg_preview);

		mAttacher = new PhotoViewAttacher(imageView);
		UrlImageViewHelper.setUrlDrawable(imageView, imageUrl,
				R.drawable.ic_launcher, new UrlImageViewCallback() {

					@Override
					public void onLoaded(ImageView imageView,
							Drawable loadedDrawable, String url,
							boolean loadedFromCache) {
						loaderBar.setVisibility(View.GONE);
						imageView.setVisibility(View.VISIBLE);
						mAttacher.update();
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_preview, menu);
		return true;
	}

}
