package tsipora.rakhovskaya.application2025;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class ViewEntriesSlider extends AppCompatActivity {
    ViewPager2 viewPager;
    ArrayList <DiaryEntry> entries;
    DiaryEntryPageAdapter adapter;
    Context context;
    HelperDB helperDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries_slider);
        context=this;
        helperDB = new HelperDB(context);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1);

        entries = helperDB.getAllEntries();
        adapter = new DiaryEntryPageAdapter(entries);
        viewPager.setAdapter(adapter);

    }
}