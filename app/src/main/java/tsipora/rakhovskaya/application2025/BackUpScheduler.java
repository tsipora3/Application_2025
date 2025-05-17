package tsipora.rakhovskaya.application2025;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

public class BackUpScheduler {
    private static final long BACKUP_INTERVAL = 24 * 60 * 60 * 1000; // 24 hours in milliseconds
    private static final int JOB_ID = 1;
    private static final String TAG = "BackupScheduler";

    public static void scheduleBackup(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(context, BackUpJobService.class);



        JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(JOB_ID, componentName)
                .setPeriodic(BACKUP_INTERVAL)
                .setRequiresCharging(true)
                .setPersisted(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            jobInfoBuilder.setRequiresBatteryNotLow(true);
        }

        JobInfo jobInfo = jobInfoBuilder.build();
        if (jobScheduler.schedule(jobInfo) == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Backup job scheduled to run approximately every 24 hours when charging.");
        } else {
            Log.e(TAG, "Error planning backup job");
        }
    }

}
