package serializerteam.serializer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serializerteam.serializer.api.ApiSettings;
import serializerteam.serializer.api.StatusCodes;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {

    private String userId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        getActivity().setTitle(getString(R.string.settings));

        userId = getContext().getSharedPreferences("serializer", MODE_PRIVATE).getString("userId", null);
        initSettings();

        return view;
    }

    private void initSettings() {
        ApiSettings.usersApi.getUserNotificationTime(userId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("EEEEEEEEEEEEEEEEEEEE", response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERR", t.getMessage());
            }
        });
    }
}
