package tsipora.rakhovskaya.application2025;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DiaryEntryPageAdapter extends RecyclerView.Adapter<DiaryEntryPageAdapter.EntryViewHolder> {

    private final ArrayList<DiaryEntry> entryList;

    public DiaryEntryPageAdapter(ArrayList<DiaryEntry> entryList) {
        this.entryList = entryList;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary_entry, parent, false);
        return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        DiaryEntry entry = entryList.get(position);
        holder.dateText.setText(entry.getDate());
        holder.moodText.setText("Mood: " + entry.getMood());
        holder.entryText.setText(entry.getEntry());
        if(entry.getMood().equals("Happy")) {
            holder.itemView.setBackgroundResource(R.drawable.very_happy);
        }
        else if(entry.getMood().equals("Content")) {
            holder.itemView.setBackgroundResource(R.drawable.content);
        }
        else if(entry.getMood().equals("Sad")) {
            holder.itemView.setBackgroundResource(R.drawable.sad);
        }
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, moodText, entryText;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.tvDate);
            moodText = itemView.findViewById(R.id.tvMood);
            entryText = itemView.findViewById(R.id.tvEntry);

        }
    }
}

