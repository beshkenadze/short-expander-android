package in.appfolio.shortexpander.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import in.appfolio.shortexpander.model.ExpandModel;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 24/03/15.
 */
public class NavigationHelper {
    private static Context sContext;

    public static void open (Context context, ExpandModel expandModel) {
        sContext = context;
        if (TextUtils.isEmpty(expandModel.getContentType()) && TextUtils.isEmpty(expandModel.getUrl())) {
            return;
        }

        DetectTypeHelper.Type type = DetectTypeHelper.who(expandModel.getUrl(), expandModel.getContentType());
        // Todo: Support image, audio, file etc.
        switch (type) {
            case Image:
            case Video:
            case Audio:
            case File:
            case Unknown:
                openUnknown(expandModel.getUrl());
                break;
        }
    }

    private static void openUnknown (String url) {
        openInBrowser(url);
    }

    private static void openInApp (String url) {
        throw new UnsupportedOperationException("openInApp is not supported right now");
    }

    private static boolean isActivityExists (Intent intent) {
        return intent.resolveActivityInfo(sContext.getPackageManager(), 0) != null;
    }

    private static void openInBrowser (String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sContext.startActivity(intent);
    }
}
