package serializerteam.serializer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serializerteam.serializer.api.ApiSettings;
import serializerteam.serializer.dto.SearchedShow;
import serializerteam.serializer.dto.ShowDto;
import serializerteam.serializer.model.showList.ShowListAdapter;

public class ShowSearchFragment extends Fragment implements ShowListAdapter.OnItemClickListener {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_shows, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.search_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getActivity().setTitle(getString(R.string.search_shows));

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchShows(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchShows(newText);
                return true;
            }
        });

    }

   private void searchShows(String query) {
       ApiSettings.showsApiService.searchShows(query).enqueue(new Callback<SearchedShow[]>() {
            @Override
            public void onResponse(Call<SearchedShow[]> call, Response<SearchedShow[]> response) {
                ArrayList<ShowDto> searchedShows= new ArrayList<>(response.body().length);
                for(SearchedShow i : response.body()){
                    searchedShows.add(i.getShow());
                }
                setShowListAdapter(searchedShows);
            }

            @Override
            public void onFailure(Call<SearchedShow[]> call, Throwable t) {
                Log.e("ERR",t.toString());
                Toast.makeText(getActivity(), "Something gone bad.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setShowListAdapter (ArrayList<ShowDto> list){
        if(list.size()==0)
            getView().findViewById(R.id.no_shows_found).setVisibility(View.VISIBLE);
        else
            getView().findViewById(R.id.no_shows_found).setVisibility(View.GONE);
        ShowListAdapter showListAdapter = new ShowListAdapter(list, getActivity());
        showListAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(showListAdapter);
    }

    @Override
    public void onItemClick(View view, ShowDto showListItem) {
        ShowDetailsActivity.navigate((AppCompatActivity) getActivity(), view.findViewById(R.id.item_image), showListItem);
    }


}
