package note.lym.org.tinkersample;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @author yaoming.li
 * @since 2017-06-14 16:36
 */
@DefaultLifeCycle(
        application = "note.lym.org.tinkersample.Application",             //这里将com.github.tinkersample.SampleApplicationLike更改为你自己的包名即可
        flags = ShareConstants.TINKER_ENABLE_ALL)
public class TinkerApplication extends DefaultApplicationLike {
    public TinkerApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        TinkerInstaller.install(this);
        MultiDex.install(base);
    }
}
