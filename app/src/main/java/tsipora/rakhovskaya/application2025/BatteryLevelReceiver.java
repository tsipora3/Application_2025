package tsipora.rakhovskaya.application2025;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.LinearLayout;

public class BatteryLevelReceiver extends BroadcastReceiver {
    LinearLayout ll;
    public BatteryLevelReceiver(LinearLayout ll) {
        super();
        this.ll=ll;
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        if (level<15) {
            ll.setBackgroundResource(R.drawable.baseline_battery_alert_24);;
        }
    }
}