package serializerteam.serializer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serializerteam.serializer.api.ApiSettings;
import serializerteam.serializer.dto.SearchedShow;
import serializerteam.serializer.dto.ShowDto;

public class ShowSearchFragment extends Fragment {

    private String spinnerCategories[];
    private Spinner genreSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_shows, container, false);
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

        Button searchShowsButton = (Button)view.findViewById(R.id.search_shows_button);
        searchShowsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchShows(view);
            }
        });
        return view;
    }

    private void searchShows(View view) {
        final FragmentManager fragmentManager = getFragmentManager();
        final MyShowsFragment fragment = new MyShowsFragment();

        String name = ((EditText)view.findViewById(R.id.name_input)).getText().toString();
        ApiSettings.showsApiService.searchShows(name).enqueue(new Callback<SearchedShow[]>() {
            @Override
            public void onResponse(Call<SearchedShow[]> call, Response<SearchedShow[]> response) {
                ArrayList<ShowDto> searchedShows= new ArrayList<>(response.body().length);
                for(SearchedShow i : response.body()){
                    searchedShows.add(i.getShow());
                }
                fragment.setSearchedShows(searchedShows);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame,fragment)
                        .commit();
            }

            @Override
            public void onFailure(Call<SearchedShow[]> call, Throwable t) {
                Log.e("ERR",t.toString());
                Toast.makeText(getActivity(), "Something gone bad.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
