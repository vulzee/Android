package serializerteam.serializer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeFragment extends Fragment {

    Runnable r = new Runnable() {

        @Override
        public void run() {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        Handler h = new Handler();
        h.postDelayed(r, 3000);

        return view;
    }


}
