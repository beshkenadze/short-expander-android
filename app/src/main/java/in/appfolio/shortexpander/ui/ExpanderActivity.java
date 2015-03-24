package in.appfolio.shortexpander.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import in.appfolio.shortexpander.R;
import in.appfolio.shortexpander.helper.ExpanderHelper;
import in.appfolio.shortexpander.helper.NavigationHelper;
import in.appfolio.shortexpander.model.ExpandModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static rx.android.app.AppObservable.bindActivity;

public class ExpanderActivity extends Activity implements Observer<ExpandModel> {
    private Subscription mSubscription;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TextUtils.isEmpty(getIntent().getDataString())) {
            finish();
        }
        setFinishOnTouchOutside(true);
        setContentView(R.layout.activity_expander);
        subscribeToExpand(getIntent().getDataString());
    }

    public static Observable<ExpandModel> expand (final String shortUrl) {
        return Observable
                .create(new Observable.OnSubscribe<ExpandModel>() {
                    @Override
                    public void call (Subscriber<? super ExpandModel> subscriber) {
                        subscriber.onNext(ExpanderHelper.expand(shortUrl));
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void subscribeToExpand (String url) {
        mSubscription = bindActivity(this, expand(url)).subscribe(this);
    }

    @Override
    protected void onPause () {
        super.onDestroy();
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void onCompleted () {

    }

    @Override
    public void onError (Throwable e) {
        // Todo: show error message
        finish();
    }

    @Override
    public void onNext (ExpandModel expandModel) {
        NavigationHelper.open(getApplicationContext(), expandModel);
    }
}
