package tsipora.rakhovskaya.application2025;

import java.io.Serializable;

public class DiaryEntry implements Serializable {
    private String id, date, mood, entry;

    public DiaryEntry() {

    }


    public DiaryEntry(String date, String mood, String entry) {

        this.date = date;
        this.mood = mood;
        this.entry = entry;

    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    @Override
    public String toString() {
        return date + "\n" +
                entry + "\n\n\n" ;
    }
}
