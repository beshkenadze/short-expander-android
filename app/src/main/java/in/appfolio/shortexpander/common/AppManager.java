package in.appfolio.shortexpander.common;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;

import in.appfolio.shortexpander.BuildConfig;
import io.fabric.sdk.android.Fabric;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 24/03/15.
 */
public class AppManager {
    private static AppManager sInstance;
    private Context mContext;

    public static AppManager getInstance () {
        if (sInstance == null) {
            synchronized (AppManager.class) {
                if (sInstance == null) {
                    sInstance = new AppManager();
                }
            }
        }
        return sInstance;
    }

    public void init (Context context) {
        mContext = context;
        initLibs();
        initStrictMode();
    }

    private void initStrictMode () {
        if (!BuildConfig.DEBUG) {
            return;
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.detectLeakedRegistrationObjects();
        }
        StrictMode.VmPolicy policy = builder.build();
        StrictMode.setVmPolicy(policy);
    }

    private void initLibs () {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call (Subscriber<? super Object> subscriber) {
                try {
                    initCrashlytics(mContext);
                    initTimber();
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribe(MyObservers.empty());
    }

    private void initTimber () {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initCrashlytics (Context context) {
        Fabric.with(new Fabric.Builder(context)
                .debuggable(BuildConfig.DEBUG)
                .kits(new Crashlytics())
                .build());
    }

}
