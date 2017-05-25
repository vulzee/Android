package serializerteam.serializer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serializerteam.serializer.api.ApiSettings;
import serializerteam.serializer.database.ShowsDbAdapter;
import serializerteam.serializer.dto.SettingsDto;
import serializerteam.serializer.dto.ShowDto;
import serializerteam.serializer.model.SettingsEntity;
import serializerteam.serializer.model.showList.ShowListAdapter;

import static android.content.Context.MODE_PRIVATE;

public class MyShowsFragment extends Fragment implements ShowListAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private static ArrayList<ShowDto> list = new ArrayList<>();
    private volatile int completedTasks = 0;
    private String userId;
    public ShowsDbAdapter showsDbAdapter;

    int[] myFavouriteShows;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_shows, container, false);
        showsDbAdapter = new ShowsDbAdapter(getContext()).getDbContext();

        getActivity().setTitle(getString(R.string.my_shows));

        recyclerView = (RecyclerView) view.findViewById(R.id.my_shows_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        userId = getActivity().getSharedPreferences("serializer", MODE_PRIVATE).getString("userId", null);
        initData();
        initSettings();

        return view;
    }

    @Override
    public void onDestroy() {
        showsDbAdapter.closeDbContext();
        super.onDestroy();
    }

    public static ArrayList<ShowDto> getShowsList(){
        if(list==null)
            return new ArrayList<>();

        return list;
    }
    private void initData() {
        ApiSettings.usersApi.getUserShows(userId).enqueue(new Callback<int[]>() {
            @Override
            public void onResponse(Call<int[]> call, Response<int[]> response) {
                myFavouriteShows = new int[response.body().length];
                System.arraycopy(response.body(), 0, myFavouriteShows, 0, response.body().length);
                list.clear();
                Call<ShowDto>[] tasks = new Call[myFavouriteShows.length];

                for (int i = 0; i < myFavouriteShows.length; i++) {
                    tasks[i] = ApiSettings.showsApiService.getShow(myFavouriteShows[i]);
                    tasks[i].enqueue(new Callback<ShowDto>() {
                        @Override
                        public void onResponse(Call<ShowDto> call, Response<ShowDto> response) {
                            if (response.body() != null)
                                list.add(response.body());
                            completedTasks++;
                            if (completedTasks == myFavouriteShows.length) {
                                setShowListAdapter();
                            }
                        }

                        @Override
                        public void onFailure(Call<ShowDto> call, Throwable t) {
                            Log.e("ERR", t.toString());
                            completedTasks++;
                            if (completedTasks == myFavouriteShows.length)
                                setShowListAdapter();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<int[]> call, Throwable t) {
                Log.e("ERR", t.getMessage());
                Toast.makeText(getActivity(), "Couldn't get favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSettings() {
        ApiSettings.usersApi.getSettings(userId).enqueue(new Callback<SettingsDto>() {
            @Override
            public void onResponse(Call<SettingsDto> call, Response<SettingsDto> response) {
                showsDbAdapter.saveSettings(response.body().isAreNotificationsOn(), response.body().getTime());
            }

            @Override
            public void onFailure(Call<SettingsDto> call, Throwable t) {
                Log.e("ERR", t.getMessage());
            }
        });


    }

    private void setShowListAdapter() {
       /* if (list.size() == 0)
            view.findViewById(R.id.no_shows_found).setVisibility(View.VISIBLE);
        else
            view.findViewById(R.id.no_shows_found).setVisibility(View.GONE);*/
        saveShowsLocally();
        ShowListAdapter showListAdapter = new ShowListAdapter(list, getActivity());
        showListAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(showListAdapter);
    }

    @Override
    public void onItemClick(View view, ShowDto showListItem) {
        ShowDetailsActivity.navigate((AppCompatActivity) getActivity(), view.findViewById(R.id.item_image), showListItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        setShowListAdapter();
    }

    public static void removeShowFromFavourites(final int id) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId() == id){
                index = i;
                break;
            }
        }
        if(index > -1) {
            list.remove(index);
        }
    }

    private void saveShowsLocally() {
        showsDbAdapter.deleteFavourites();

        for(ShowDto elem: list) {
            showsDbAdapter.addShowToFavourites(elem.getName(), elem.getId());
        }
    }
}
