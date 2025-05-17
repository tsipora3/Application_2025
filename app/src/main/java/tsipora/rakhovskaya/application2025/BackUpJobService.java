package tsipora.rakhovskaya.application2025;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class BackUpJobService extends JobService {
    private static final String TAG = "BackUpJobService";
    private static final String BACKUP_DIR="DiaryDatabaseBackupFolder";


    @Override
    public boolean onStartJob(JobParameters params) {
        new Thread(() -> {
            backupDatabase();
            jobFinished(params, false);
        }).start();
        return true;

        }

        private void backupDatabase() {
            File dbFile = getDatabasePath(HelperDB.DB_NAME);
            File backupDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BACKUP_DIR);

            if (!backupDir.exists()) {
                if (!backupDir.mkdirs()) {
                    Log.e(TAG, "Error creating backup directory");
                    return;
                }
            }

            String backupFileName = "backup_diary_" + System.currentTimeMillis() + ".db";
            File backupFile = new File(backupDir, backupFileName);

            try (FileChannel src = new FileInputStream(dbFile).getChannel();
                 FileChannel dst = new FileOutputStream(backupFile).getChannel()) {
                dst.transferFrom(src, 0, src.size());
            } catch (IOException e) {
                Log.e(TAG, "error in backup");
            }
        }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
