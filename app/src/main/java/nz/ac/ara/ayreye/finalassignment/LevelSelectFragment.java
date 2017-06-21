package nz.ac.ara.ayreye.finalassignment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elliot on 6/19/2017.
 */

public class LevelSelectFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] levels = getArguments().getStringArray("levels");
        builder.setTitle(R.string.level_select_title)
                .setItems(levels, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selection = levels[which];
                        Intent intent = new Intent(getActivity(), GameActivity.class);
                        intent.putExtra(MainActivity.EXTRA_FILENAME, selection);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}
