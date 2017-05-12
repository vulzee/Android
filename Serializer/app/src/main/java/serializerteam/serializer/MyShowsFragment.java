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
import android.widget.Toast;

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
import serializerteam.serializer.api.StatusCodes;
import serializerteam.serializer.api.UrlApi;
import serializerteam.serializer.dto.EpisodeDto;
import serializerteam.serializer.dto.PersonDto;
import serializerteam.serializer.dto.SearchedShow;
import serializerteam.serializer.dto.ShowDto;
import serializerteam.serializer.model.showList.ShowListAdapter;
import serializerteam.serializer.model.showList.ShowListItem;

import static android.content.Context.MODE_PRIVATE;

public class MyShowsFragment extends Fragment implements ShowListAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ArrayList<ShowDto> list;
    private ShowListAdapter showListAdapter;
    private volatile int completedTasks =0;
    // if search set to true
    private boolean searchedShows = false;
    private String userId;

    int[] myFavouriteShows;
    private View view;
  //  private int[] searchedShowsId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_shows, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_shows_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //get shows ids from DB or anywhere else
        //showsID=....;
        userId = getActivity().getSharedPreferences("serializer", MODE_PRIVATE).getString("userId", null);
        Log.v("aaaaaaaaaaaaaaaa", userId);
        if(searchedShows){
            setShowListAdapter();
        }else
            initData();

        return view;
    }

    public void setSearchedShows(ArrayList<ShowDto> searchedShows){
        if(searchedShows==null) return;
            this.searchedShows=true;
        this.list = searchedShows;

    }

    private void initData() {

        ApiSettings.usersApi.getUserShows(userId).enqueue(new Callback<int[]>() {
            @Override
            public void onResponse(Call<int[]> call, Response<int[]> response) {
                myFavouriteShows = new int[response.body().length];
                for(int i=0;i<response.body().length;i++) {
                    myFavouriteShows[i] = response.body()[i];
                }
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
                            if(completedTasks == myFavouriteShows.length) {
                                setShowListAdapter();
                            }
                        }

                        @Override
                        public void onFailure(Call<ShowDto> call, Throwable t) {
                            Log.e("ERR",t.toString());
                            completedTasks++;
                            if(completedTasks==myFavouriteShows.length)
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
        Log.v("aaaaa", Integer.toString(list.size()));
        if(list.size()==0)
            view.findViewById(R.id.no_shows_found).setVisibility(View.VISIBLE);
        else
            view.findViewById(R.id.no_shows_found).setVisibility(View.GONE);
       // if(completedTasks!=myFavouriteShows.length) return;
        showListAdapter = new ShowListAdapter(list, getActivity());
        showListAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(showListAdapter);
    }

    @Override
    public void onItemClick(View view, ShowDto showListItem) {
        ShowDetailsActivity.navigate((AppCompatActivity) getActivity(), view.findViewById(R.id.item_image), showListItem);
    }
}
