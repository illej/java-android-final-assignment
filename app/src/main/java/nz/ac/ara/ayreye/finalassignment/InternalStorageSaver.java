package nz.ac.ara.ayreye.finalassignment;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by eha2 on 25/06/2017.
 */

public class InternalStorageSaver extends FileSaver {

    private Context context;

    public InternalStorageSaver(Context context) {
        this.context = context;
    }

    @Override
    public void write(String level, String fileName) {

        FileOutputStream outputStream;

        try {
            outputStream = this.context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(level.getBytes());
            outputStream.close();

            String[] fileList = this.context.fileList();
            for (String file : fileList) {
                Log.d("files", String.valueOf(file));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
