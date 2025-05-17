package tsipora.rakhovskaya.application2025;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public abstract class MenuManager extends AppCompatActivity {

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuViewHappy) {
            Intent go=new Intent(this, ViewEntries.class);
            go.putExtra("mood", "Happy");
            startActivity(go);

        } else if (id == R.id.menuViewSad) {
            Intent go=new Intent(this, ViewEntries.class);
            go.putExtra("mood", "Sad");
            startActivity(go);
        }
        else if (id == R.id.menuViewContent) {
            Intent go=new Intent(this, ViewEntries.class);
            go.putExtra("mood", "Content");
            startActivity(go);
        }
        else if (id == R.id.menuViewAllMemories) {
            Intent go=new Intent(this, ViewEntriesSlider.class);
            go.putExtra("mood", "All");
            startActivity(go);
        }
        else if (id == R.id.menuSetNotification) {
            Intent go=new Intent(this, ScheduleNotifications.class);
            startActivity(go);
        }
        else if (id == R.id.menuAddEntry) {
            Intent go=new Intent(this, NewEntry.class);
            startActivity(go);
        }

        return super.onOptionsItemSelected(item);
    }
}