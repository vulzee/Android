package serializerteam.serializer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import serializerteam.serializer.api.ApiSettings;
import serializerteam.serializer.api.ShowsApi;
import serializerteam.serializer.api.UrlApi;
import serializerteam.serializer.dto.EpisodeDto;
import serializerteam.serializer.dto.PersonDto;
import serializerteam.serializer.dto.SearchedShow;
import serializerteam.serializer.dto.ShowDto;
import serializerteam.serializer.model.showList.ShowListAdapter;
import serializerteam.serializer.model.showList.ShowListItem;

public class MyShowsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ShowDto> list;
    private ShowListAdapter showListAdapter;
    private int[] myFavouriteShows = {1456,224,1323,412};
    private volatile int completedTasks =0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_shows, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_shows_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        initData();

        return view;
    }

    private void initData() {
        list = new ArrayList<>();
        Call<ShowDto>[] tasks = new Call[myFavouriteShows.length];
       // list.add(0, new ShowListItem(0, "Super movie", "Super duper", ""));
        for (int i=0;i<myFavouriteShows.length;i++) {
            tasks[i]=ApiSettings.showsApiService.getShow(myFavouriteShows[i]);
            tasks[i].enqueue(new Callback<ShowDto>() {
                @Override
                public void onResponse(Call<ShowDto> call, Response<ShowDto> response) {
                    if(response.body()!=null)
                        list.add(response.body());
                    completedTasks++;
                    if(completedTasks!=myFavouriteShows.length)
                        setShowListAdapter();
                }

                @Override
                public void onFailure(Call<ShowDto> call, Throwable t) {
                    Log.e("ERR",t.toString());
                    completedTasks++;
                    if(completedTasks!=myFavouriteShows.length)
                        setShowListAdapter();
                }
            });
        }
//        ApiSettings.urlApi.getResponse("http://api.tvmaze.com/episodes/999541").enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.body()!=null) {
//                    EpisodeDto pa = new Gson().fromJson(response.body().charStream(), EpisodeDto.class);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("ERR",t.toString());
//            }
//        });
        //wywolujesz api
//            ApiSettings.showsApiService.getShowWithCast(1).enqueue(new Callback<ShowDto>() {
//                @Override
//                public void onResponse(Call<ShowDto> call, Response<ShowDto> response) {
//                    ShowDto a = response.body();
//                }
//
//                @Override
//                public void onFailure(Call<ShowDto> call, Throwable t) {
//                    Log.e("ERR",t.toString());
//                }
//            });
//            Call<SearchedShow[]> call=ApiSettings.showsApiService.searchShows("name");
//            call.enqueue(new Callback<SearchedShow[]>() {
//                @Override
//                public void onResponse(Call<SearchedShow[]> call, Response<SearchedShow[]> response) {
//                    SearchedShow[] myItem=response.body();
//                }
//
//                @Override
//                public void onFailure(Call<SearchedShow[]> call, Throwable t) {
//                    //Handle failure
//                    Log.e("ERR",t.toString());
//                }
//            });

       // boolean result=false;
    }

    private void setShowListAdapter (){
       // if(completedTasks!=myFavouriteShows.length) return;
        showListAdapter = new ShowListAdapter(list, getActivity(), getFragmentManager());
        recyclerView.setAdapter(showListAdapter);
    }
}
