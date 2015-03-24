package in.appfolio.shortexpander;

import android.app.Application;
import android.content.Context;

import in.appfolio.shortexpander.common.AppManager;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 24/03/15.
 */
public class App extends Application {
    private Context mContext;

    private static App sInstance;
    {
        sInstance = this;
    }

    @Override
    public void onCreate () {
        super.onCreate();
        mContext = getApplicationContext();
        AppManager.getInstance().init(mContext);
    }

    public static Context getContext () {
        return sInstance.mContext;
    }

}
