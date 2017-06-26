package nz.ac.ara.ayreye.finalassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_FILENAME = "nz.ac.ara.ayreye.finalassignment.FILENAME";
    public static final String EXTRA_LOAD_METHOD = "nz.ac.ara.ayreye.finalassignment.LOAD_METHOD";
    public String[] fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.populateFileList();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playButton:
                String firstLevel = this.fileList[0];
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra(MainActivity.EXTRA_FILENAME, firstLevel);
                intent.putExtra(MainActivity.EXTRA_LOAD_METHOD, "assets");
                startActivity(intent);
                break;
            case R.id.selectLevelButton:
                LevelSelectFragment levelSelect = new LevelSelectFragment();
                Bundle args = new Bundle();
                args.putStringArray("levels", this.fileList);
                args.putString(MainActivity.EXTRA_LOAD_METHOD, "assets");
                levelSelect.setArguments(args);
                levelSelect.show(getFragmentManager(), "levels");
                break;
            case R.id.exitButton:
                System.exit(0);
        }
    }

    private void populateFileList() {
        try {
            String[] list = getAssets().list("");
            List<String> arrayList = new ArrayList<>();
            for (String file : list) {
                Log.d("MainActv Created: Assts", file);
                if (file.endsWith(".txt")) {
                    arrayList.add(file);
                }
            }
            this.fileList = new String[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                this.fileList[i] = arrayList.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
