package in.appfolio.shortexpander.common;

import rx.Observer;
import timber.log.Timber;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 24/03/15.
 */
public class MyObservers {
    private static final Observer EMPTY = new Observer() {
        @Override
        public void onCompleted () {

        }

        @Override
        public void onError (Throwable e) {
            Timber.e(e, e.getMessage());
        }

        @Override
        public void onNext (Object o) {

        }
    };

    @SuppressWarnings("unchecked")
    public static <T> Observer<T> empty () {
        return (Observer<T>) EMPTY;
    }
}
