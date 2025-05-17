package tsipora.rakhovskaya.application2025;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Opening extends AppCompatActivity {
    MaterialButton bYalla;
    Context context;
    Intent go;
    ImageView ivDiary;
    CountDownTimer cdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        initcomp();
        cdt = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(go);
            }
        }.start();
        bYalla.setOnClickListener(v -> {
            cdt.cancel();
            startActivity(go);
        });

    }

    private void initcomp() {
        context = this;
        bYalla = findViewById(R.id.bYalla);
        go = new Intent(context, Junction.class);

        ivDiary = findViewById(R.id.ivDiary);
        ivDiary.setImageResource(R.drawable.diary);
        ivDiary.animate().rotation(360).setDuration(10000);

    }
}