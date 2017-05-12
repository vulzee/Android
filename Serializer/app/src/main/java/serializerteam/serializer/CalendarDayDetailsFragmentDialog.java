package serializerteam.serializer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;

import java.util.Date;

import serializerteam.serializer.api.UsersApi;

public class CalendarDayDetailsFragmentDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragment_calendar_day_details_dialog, null);

        builder.setView(v)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CalendarDayDetailsFragmentDialog.this.getDialog().cancel();
                    }
                });

        ((TextView)v.findViewById(R.id.item_title)).setText("Some show");
        ((TextView)v.findViewById(R.id.item_description)).setText("Description");
        ((TextView)v.findViewById(R.id.show_time)).setText("16:30");
        return builder.create();
    }
}
