package in.appfolio.shortexpander;

import in.appfolio.shortexpander.Expander.OnRequestListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class TestActivity extends Activity implements OnRequestListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// String testUrl = "http://goo.gl/dtejR";
		// String testUrl = "http://goo.gl/W46FU"; // http://twitpic.com/1e10q
		String testUrl = "http://goo.gl/WWqdc"; // http://img.ly/3de

		new Expander(testUrl, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test, menu);
		return true;
	}

	@Override
	public void onSuccess(ShortInfo si) {
		Debug.i("URI:" + si.getUrl());
		Debug.i("TYPE:" + si.getType());
		Debug.i("CHECK:" + ImageService.check(si.getUrl()));
		ExpanderActivity.processImage(si, TestActivity.this);
	}

	@Override
	public void onError() {

	}

}
