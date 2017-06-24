package nz.ac.ara.ayreye.finalassignment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Elliot on 6/21/2017.
 */

public class EndGameFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] options = new String[] {"prev", "replay", "next", "home"}; //getArguments().getStringArray("options");
        builder.setTitle(getArguments().getString("title"))
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selection = options[which];
                        Intent intent = null;
                        String extra = null;
                        switch (selection) {
                            case "prev":
                                intent = new Intent(getActivity(), GameActivity.class);
                                // TODO: some garbage about finding relative levels?
                                extra = getArguments().getString("currentLevel");
                                break;
                            case "replay":
                                intent = new Intent(getActivity(), GameActivity.class);
                                extra = getArguments().getString("currentLevel");
                                break;
                            case "next":
                                intent = new Intent(getActivity(), GameActivity.class);
                                extra = getArguments().getString("currentLevel");
                                break;
                            case "home":
                                intent = new Intent(getActivity(), MainActivity.class);
                                break;
                        }
                        intent.putExtra("selection", selection);
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
}
