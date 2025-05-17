package tsipora.rakhovskaya.application2025;

import static android.graphics.Color.*;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class NewEntry extends MenuManager {
    TextView tvDate;
    Context context;
    ImageButton bHappy, bContent, bSad;
    Button bAddEntry, bDate;
    EditText etEntry;
    String mood, entry;
    MaterialDatePicker<Long> datePicker;
    String formattedDate;
    DiaryEntry de;
    HelperDB helperDB;
    boolean isPressed =false;
    CoordinatorLayout coordinator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        initcomp();
        bDate.setOnClickListener(v -> {
            // Restrict selection to today or past dates
            CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointBackward.now()); // Only allow today or past dates

            // Create a MaterialDatePicker instance
            datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select a Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())  // Default to today
                    .setCalendarConstraints(constraintsBuilder.build())
                    .build();
                    datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                formattedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selection);
                tvDate.setText("Selected Date: " + formattedDate);
                tvDate.setBackgroundColor(WHITE);
            });
        });
        bHappy.setOnClickListener(v -> {
            bHappy.setBackgroundColor(GREEN);
            bContent.setBackgroundColor(WHITE);
            bSad.setBackgroundColor(WHITE);
            isPressed=true;
            mood = "Happy";
                });
        bSad.setOnClickListener(v -> {
            bSad.setBackgroundColor(RED);
            bContent.setBackgroundColor(WHITE);
            bHappy.setBackgroundColor(WHITE);
            isPressed=true;
            mood = "Sad";
        });
        bContent.setOnClickListener(v -> {
            bContent.setBackgroundColor(YELLOW);
            bHappy.setBackgroundColor(WHITE);
            bSad.setBackgroundColor(WHITE);
            isPressed=true;
            mood = "Content";
        });

        bAddEntry.setOnClickListener(v -> {
            if(etEntry.getText().toString().isEmpty()){
                etEntry.setError("Required");
                etEntry.setBackgroundColor(RED);
                return;
            }
            if(!isPressed){
                bHappy.setBackgroundColor(RED);
                bContent.setBackgroundColor(RED);
                bSad.setBackgroundColor(RED);
                return;
            }
            if(tvDate.getText().toString().isEmpty()||tvDate.getText().toString().equals("Please select a date")){
                tvDate.setError("Required");
                tvDate.setText("Please select a date");
                tvDate.setBackgroundColor(RED);
                return;
            }

            entry = etEntry.getText().toString();
                de.setEntry(entry);
                de.setMood(mood);
                de.setDate(formattedDate);
                helperDB.addEntryAsync(de, coordinator, context);
        });
        
    }

    private void initcomp() {
        context = this;
        bHappy = findViewById(R.id.bHappy);
        bContent = findViewById(R.id.bContent);
        bSad = findViewById(R.id.bSad);
        bAddEntry = findViewById(R.id.bAddEntry);
        etEntry = findViewById(R.id.etEntry);
        bDate = findViewById(R.id.bDate);
        tvDate=findViewById(R.id.tvDate);
        de=new DiaryEntry();
        helperDB=new HelperDB(context);
        coordinator=findViewById(R.id.coordinator);

    }
}