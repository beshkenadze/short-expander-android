package in.appfolio.shortexpander;

import java.util.Arrays;

import android.net.Uri;

public class ImageService {
	static String[] services = { "twitpic.com", "img.ly" };

	static public boolean check(String url) {
		Uri parsedUrl = Uri.parse(url);
		return Arrays.asList(services).contains(parsedUrl.getHost());
	}

	static public String twitpic(String url) {
		Uri parsedUrl = Uri.parse(url);
		return "http://twitpic.com/show/full" + parsedUrl.getPath();
	}

	static public String imgly(String url) {
		Uri parsedUrl = Uri.parse(url);
		return "http://img.ly/show/full" + parsedUrl.getPath();
	}

	public static String get(String url) {
		Uri parsedUrl = Uri.parse(url);
		Debug.i("Host:" + parsedUrl.getHost());
		if (parsedUrl.getHost().contains("twitpic.com")) {
			return twitpic(url);
		}
		if (parsedUrl.getHost().contains("img.ly")) {
			return imgly(url);
		}
		return url;
	}
}
