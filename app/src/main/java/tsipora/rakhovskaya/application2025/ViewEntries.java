package tsipora.rakhovskaya.application2025;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewEntries extends MenuManager {
    ArrayList<DiaryEntry> entries;
    String mood;
    LinearLayout llViewEntries;
    ListView lvViewEntries;
    HelperDB helperDB;
    ArrayAdapter adapter;
    Context context;
    TextView tvViewEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_entries);
        initcomp();
        if(mood.equals("Happy")) {
            llViewEntries.setBackgroundColor(Color.YELLOW);
            tvViewEntries.setText("Happy Memories");
        }
        if(mood.equals("Sad")){
            llViewEntries.setBackgroundColor(Color.BLUE);
            tvViewEntries.setText("Sad Memories");
        }

        if(mood.equals("Content")) {
            llViewEntries.setBackgroundColor(Color.GREEN);
            tvViewEntries.setText("Content Memories");
        }
    }

    private void initcomp() {
        context=this;
        llViewEntries=findViewById(R.id.llViewEntries);
        lvViewEntries=findViewById(R.id.lvViewEntries);
        helperDB=new HelperDB(context);
        mood=getIntent().getStringExtra("mood");
        entries=helperDB.getEntries(mood);
        adapter=new ArrayAdapter(context, android.R.layout.simple_list_item_1, entries);
        lvViewEntries.setAdapter(adapter);
        tvViewEntries=findViewById(R.id.tvViewEntries);

    }
}