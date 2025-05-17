package tsipora.rakhovskaya.application2025;

import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class ScheduleNotifications extends MenuManager {

    private TextView tvDateNotification, tvTimeNotification;
    private EditText etEvent;
    private Button bDateNotification, bTimeNotification, bSchedule;
    private int selectedHour, selectedMinute, selectedDay, selectedMonth, selectedYear;;
    Context context;
    private Calendar selectedDateTime = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_notifications);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission();
        }
        initcomp();

        bDateNotification.setOnClickListener(view -> showDatePicker());
        bTimeNotification.setOnClickListener(view -> showTimePicker());
        bSchedule.setOnClickListener(view -> setNotification());
    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                    1);
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, yearSelected, monthSelected, daySelected) -> {
            selectedYear = yearSelected;
            selectedMonth = monthSelected;
            selectedDay = daySelected;
            tvDateNotification.setText(String.format("Selected Date: %02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear));
        }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()); // Prevent past dates
        datePickerDialog.show();
    }

    private void initcomp() {
        context = this;
        tvDateNotification = findViewById(R.id.tvDateNotification);
        tvTimeNotification = findViewById(R.id.tvTimeNotification);
        etEvent = findViewById(R.id.etEvent);
        bDateNotification = findViewById(R.id.bDateNotification);
        bTimeNotification = findViewById(R.id.bTimeNotification);
        bSchedule=findViewById(R.id.bSchedule);
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (TimePicker view, int hourOfDay, int minute1) -> {
                    selectedHour = hourOfDay;
                    selectedMinute = minute1;
                    String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    tvTimeNotification.setText("Selected Time: " + formattedTime);
                }, hour, minute, true);

        timePickerDialog.show();
    }

    private void setNotification() {
        String eventText = etEvent.getText().toString().trim();

        if (eventText.isEmpty()) {
            Toast.makeText(this, "Please enter an event name", Toast.LENGTH_SHORT).show();
            return;
        }


        selectedDateTime.set(Calendar.YEAR, selectedYear);
        selectedDateTime.set(Calendar.MONTH, selectedMonth);
        selectedDateTime.set(Calendar.DAY_OF_MONTH, selectedDay);
        selectedDateTime.set(Calendar.HOUR_OF_DAY, selectedHour);
        selectedDateTime.set(Calendar.MINUTE, selectedMinute);
        selectedDateTime.set(Calendar.SECOND, 0);

        if (selectedDateTime.before(Calendar.getInstance())) {
            Toast.makeText(this, "Selected time must be in the future", Toast.LENGTH_SHORT).show();
            return;
        }


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("event_name", etEvent.getText().toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, selectedDateTime.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Notification set for " + etEvent.getText().toString(), Toast.LENGTH_SHORT).show();
        }
}

}