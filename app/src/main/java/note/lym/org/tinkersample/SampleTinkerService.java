package note.lym.org.tinkersample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.TinkerServiceInternals;

import java.io.File;

/**
 *
 * @author yaoming.li
 * @since 2017-06-15 17:16
 */
public class SampleTinkerService extends DefaultTinkerResultService {

    private static final String TAG = "SampleTinkerService";

    @Override
    public void onPatchResult(PatchResult result) {
        super.onPatchResult(result);
        if (result == null) {
            TinkerLog.e(TAG, "SampleResultService received null result!!!!");
            return;
        }
        TinkerLog.i(TAG, "SampleResultService receive result: %s", result.toString());

        //first, we want to kill the recover process
        TinkerServiceInternals.killTinkerPatchServiceProcess(getApplicationContext());
        if(result.isSuccess){
            deleteRawPatchFile(new File(result.rawPatchFilePath));
            if(checkIfNeedKill(result)){
                TinkerLog.i(TAG, "tinker wait screen to restart process");
                new ScreenState(getApplicationContext(), new ScreenState.IOnScreenOff() {
                    @Override
                    public void onScreenOff() {
                        restartProcess();
                    }
                });
            }else{
                TinkerLog.i(TAG, "I have already install the newly patch version!");
            }
        }
    }

    /**
     * you can restart your process through service or broadcast
     */
    private void restartProcess() {
        TinkerLog.i(TAG, "app is background now, i can kill quietly");
        //you can send service or broadcast intent to restart your process
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    static class ScreenState {
        interface IOnScreenOff {
            void onScreenOff();
        }

        ScreenState(Context context, final IOnScreenOff onScreenOffInterface) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            context.registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent in) {
                    String action = in == null ? "" : in.getAction();
                    TinkerLog.i(TAG, "ScreenReceiver action [%s] ", action);
                    if (Intent.ACTION_SCREEN_OFF.equals(action)) {

                        context.unregisterReceiver(this);

                        if (onScreenOffInterface != null) {
                            onScreenOffInterface.onScreenOff();
                        }
                    }
                }
            }, filter);
        }
    }

}
