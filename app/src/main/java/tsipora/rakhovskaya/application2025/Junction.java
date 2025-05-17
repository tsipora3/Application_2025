package tsipora.rakhovskaya.application2025;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Junction extends MenuManager {
    Button bAdd, bView, bViewHappy, bViewSad, bViewContent, bSetNotification;
    Context context;
    Intent go;
    BatteryLevelReceiver batteryLevelReceiver;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junction);
        initcomp();
        BackUpScheduler.scheduleBackup(this);
        bAdd.setOnClickListener(v -> {
            go = new Intent(context, NewEntry.class);
            startActivity(go);
        });
        bView.setOnClickListener(v -> {
            if(bViewHappy.getVisibility()==View.VISIBLE)
            {
                bViewHappy.setVisibility(View.GONE);
                bViewSad.setVisibility(View.GONE);
                bViewContent.setVisibility(View.GONE);
            }
            else {
                bViewHappy.setVisibility(View.VISIBLE);
                bViewSad.setVisibility(View.VISIBLE);
                bViewContent.setVisibility(View.VISIBLE);

            bViewHappy.setOnClickListener(v1 ->{
                go = new Intent(context, ViewEntries.class);
                go.putExtra("mood", "Happy");
                startActivity(go);
            });
            bViewSad.setOnClickListener(v1 ->{
                go = new Intent(context, ViewEntries.class);
                go.putExtra("mood", "Sad");
                startActivity(go);
            });
            bViewContent.setOnClickListener(v1 ->{
                go = new Intent(context, ViewEntries.class);
                go.putExtra("mood", "Content");
                startActivity(go);
            });
        }


    });
        bSetNotification.setOnClickListener(v -> {
            go = new Intent(context, ScheduleNotifications.class);
            startActivity(go);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(batteryLevelReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batteryLevelReceiver);
    }

    private void initcomp() {
        context = this;
        bAdd = findViewById(R.id.bAdd);
        bView = findViewById(R.id.bView);
        bViewHappy = findViewById(R.id.bViewHappy);
        bViewSad = findViewById(R.id.bViewSad);
        bViewContent = findViewById(R.id.bViewContent);
        bSetNotification=findViewById(R.id.bSetNotification);
        ll=findViewById(R.id.ll);
        batteryLevelReceiver=new BatteryLevelReceiver(ll);

    }
}