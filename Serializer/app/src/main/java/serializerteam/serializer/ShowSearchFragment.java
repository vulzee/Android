package serializerteam.serializer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ShowSearchFragment extends Fragment {

    private String spinnerCategories[];
    private Spinner genreSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_shows, container, false);
        spinnerCategories=new String[5];
        spinnerCategories[0]="genre 1";
        spinnerCategories[1]="genre 2";
        spinnerCategories[2]="genre 3";
        spinnerCategories[3]="genre 4";
        spinnerCategories[4]="genre 5";
        genreSpinner = (Spinner) view.findViewById(R.id.genre_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, spinnerCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        genreSpinner.setAdapter(adapter);
        return view;
    }
}
