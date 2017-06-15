package note.lym.org.tinkersample;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView mTvError;
    private Button mBtnLoadPatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTvError = (TextView) findViewById(R.id.tv_error);
        mTvError.setText("this is error");
        mBtnLoadPatch = (Button) findViewById(R.id.btn_load_patch);
        mBtnLoadPatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loadPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk";
                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), loadPath);
            }
        });
    }
}
