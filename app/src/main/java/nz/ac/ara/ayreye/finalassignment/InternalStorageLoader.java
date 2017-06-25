package nz.ac.ara.ayreye.finalassignment;

import android.content.Context;
import android.util.Log;
import android.view.ViewDebug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by eha2 on 25/06/2017.
 */

public class InternalStorageLoader extends FileLoader {

    private Context context;

    public InternalStorageLoader(Context context) {
        this.context = context;
    }

    @Override
    protected Map<String, Object> loadFile(String filename) {
        String level = "";

        String[] fileList = this.context.fileList();
        for (String file : fileList) {
            Log.d("files", String.valueOf(file));
            if (file.endsWith(".txt")) {
                Log.d("loading file", String.valueOf(file));
                //level = file;
            }
        }

//        Call openFileInput() and pass it the name of the file to read. This returns a FileInputStream.
//        Read bytes from the file with read().
//        Then close the stream with close().

        FileInputStream fis;
        InputStreamReader isr;
        BufferedReader br;

        try {
            fis = this.context.openFileInput(filename);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                Log.d("line", String.valueOf(line));
                sb.append(line);
            }
            level = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return super.parse(level);
    }
}
