package serializerteam.serializer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serializerteam.serializer.api.ApiSettings;
import serializerteam.serializer.api.StatusCodes;
import serializerteam.serializer.dto.SettingsDto;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {

    private String userId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        getActivity().setTitle(getString(R.string.settings));

        userId = getContext().getSharedPreferences("serializer", MODE_PRIVATE).getString("userId", null);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSettings();

        Button button = (Button)getView().findViewById(R.id.settings_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = Integer.parseInt(((EditText)getView().findViewById(R.id.notification_time)).getText().toString());
                boolean areNotificationsOn = !(((ToggleButton)getView().findViewById(R.id.notitications_toggle)).isChecked());

                ApiSettings.usersApi.saveSettings(time,areNotificationsOn,userId).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getActivity(), "Settings saved", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("ERR", t.getMessage());
                        Toast.makeText(getActivity(), "Couldn't save settings", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initSettings() {
        ApiSettings.usersApi.getSettings(userId).enqueue(new Callback<SettingsDto>() {
            @Override
            public void onResponse(Call<SettingsDto> call, Response<SettingsDto> response) {
                EditText editText = (EditText)getView().findViewById(R.id.notification_time);
                editText.setText(Integer.toString(response.body().getTime()));

                ToggleButton toggleButton = (ToggleButton)getView().findViewById(R.id.notitications_toggle);
                toggleButton.setChecked(!response.body().isAreNotificationsOn());
            }

            @Override
            public void onFailure(Call<SettingsDto> call, Throwable t) {
                Log.e("ERR", t.getMessage());
            }
        });
    }
}
