package nz.ac.ara.ayreye.finalassignment;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by Elliot on 6/19/2017.
 */

public class AssetLoader extends FileLoader {

    private Context context;

    public AssetLoader(Context context) {
        this.context = context;
    }


    @Override
    protected Map<String, Object> loadFile(String filename) {
        String level = "";

        AssetManager manager = this.context.getAssets();
        // InputStream stream = am.open("maze_map.txt");

        InputStream inputStream = null;
        BufferedReader reader = null;

        try {
            inputStream = manager.open(filename);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder strBuilder = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                strBuilder.append(line);
                line = reader.readLine();
                if (line != null) {
                    strBuilder.append("\n");
                }
            }

            level = strBuilder.toString();
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) { }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) { }
            }
        }
        return super.parse(level);
    }
}
