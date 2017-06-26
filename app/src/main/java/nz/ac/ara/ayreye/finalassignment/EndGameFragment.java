package nz.ac.ara.ayreye.finalassignment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elliot on 6/21/2017.
 */

public class EndGameFragment extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] options = new String[] {"prev", "replay", "next", "home"}; //getArguments().getStringArray("options");
        String title = getArguments().getString("title");

        builder.setTitle(title)
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selection = options[which];
                        Intent intent = null;
                        String extra = null;
                        String current = null;
                        switch (selection) {
                            case "prev":
                                intent = new Intent(getActivity(), GameActivity.class);
                                current = getArguments().getString("currentLevel");
                                extra = getLevel(current, -1);
                                break;
                            case "replay":
                                intent = new Intent(getActivity(), GameActivity.class);
                                extra = getArguments().getString("currentLevel");
                                break;
                            case "next":
                                intent = new Intent(getActivity(), GameActivity.class);
                                current = getArguments().getString("currentLevel");
                                extra = getLevel(current, 1);
                                break;
                            case "home":
                                intent = new Intent(getActivity(), MainActivity.class);
                                break;
                        }
                        intent.putExtra(MainActivity.EXTRA_FILENAME, extra);
                        intent.putExtra(MainActivity.EXTRA_LOAD_METHOD, "assets");
                        Log.d("extra", String.valueOf(extra));
                        startActivity(intent);
                    }
                });
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private String getLevel(String current, int offset) {
        String result = "";

        try {
            String[] list = getActivity().getAssets().list("");
            List<String> arrayList = new ArrayList<>();
            for (String file : list) {
                Log.d("END_GAME>assets", file);
                if (file.endsWith(".txt")) {
                    arrayList.add(file);
                }
            }

            int idx = arrayList.indexOf(current);
            if ((idx == 0 && offset == -1)
                    || ((idx == arrayList.size() - 1) && offset == 1)) {
                offset = 0;
            }
            Log.d("> OFFSET", String.valueOf(offset));
            result = arrayList.get(idx + offset);
            Log.d("> RELTV LVL", String.valueOf(result));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
