package in.appfolio.shortexpander.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import in.appfolio.shortexpander.common.Intents;
import in.appfolio.shortexpander.helper.ExpanderHelper;
import in.appfolio.shortexpander.helper.NavigationHelper;
import timber.log.Timber;

public class ExpanderService extends IntentService {
    public static void startActionExpand (Context context, Uri data) {
        Intent intent = new Intent(context, ExpanderService.class);
        intent.setData(data);
        intent.setAction(Intents.Action.EXPAND);
        context.startService(intent);
    }

    public ExpanderService () {
        super("ExpanderService");
    }

    @Override
    protected void onHandleIntent (Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Intents.Action.EXPAND.equals(action)) {
                handleActionExpand(intent.getData());
            }
        }
    }

    private void handleActionExpand (Uri data) {
        Timber.i("uri: %s", data);
        NavigationHelper.open(getApplicationContext(), ExpanderHelper.expand(data.toString()));
    }

}
