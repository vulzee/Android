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
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serializerteam.serializer.api.ApiSettings;
import serializerteam.serializer.dto.CastDto;
import serializerteam.serializer.dto.EpisodeDto;
import serializerteam.serializer.dto.ShowDto;
import serializerteam.serializer.model.cast.CastListAdapter;
import serializerteam.serializer.model.cast.CastListItem;

public class ShowDetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CastDto> list;
    private CastListAdapter castListAdapter;
    private ShowDto showDto;
    private int showId;
    private EpisodeDto episode;
    private String seriesName;
    private String summary;
    private String image;
    private String genre;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        showId=args.getInt("id");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_show_details, container, false);
        list=new ArrayList<>();
        ApiSettings.showsApiService.getShowWithCast(showId).enqueue(new Callback<ShowDto>() {
            @Override
            public void onResponse(Call<ShowDto> call, Response<ShowDto> response) {
                if(response.body()!=null) {
                    showDto = response.body();
                    ((TextView)view.findViewById(R.id.item_title)).setText(showDto.getName());
                    ((TextView)view.findViewById(R.id.item_genre)).setText(showDto.getGenres().toString());
                    ((TextView)view.findViewById(R.id.item_long_description)).setText(Jsoup.parse(showDto.getSummary()).text());
                    if(showDto.getImage()!=null && showDto.getImage().size()>0)
                        Picasso.with(getContext()).load(showDto.getImage().values().iterator().next()).into((ImageView)view.findViewById(R.id.item_image));
                    else
                        Picasso.with(getContext()).load(R.mipmap.ic_launcher).into((ImageView)view.findViewById(R.id.item_image));
                  //  List<CastDto> cast = showDto.getEmbedded().getCast();
                   // for(int i=0;i<cast.size();i++)
                        list.addAll(showDto.getEmbedded().getCast());
                    setCastListAdapter();
                    if(showDto.getLinks().get("nextepisode")!=null && showDto.getLinks().get("nextepisode").href.length()>0)
                        getNextEpisode(showDto.getLinks().get("nextepisode").href, view);
                    else{
                        ((TextView)view.findViewById(R.id.next_episode_description)).setText(R.string.no_episodes_in_future);
                    }
                }
            }

            @Override
            public void onFailure(Call<ShowDto> call, Throwable t) {
                Log.e("ERR",t.toString());
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.show_cast_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
       // initData2();




      //  initData();

        return view;
    }

    private void getNextEpisode(String url, final View view){
        ApiSettings.urlApi.getResponse(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body()!=null) {
                    episode = new Gson().fromJson(response.body().charStream(), EpisodeDto.class);
                    ((TextView)view.findViewById(R.id.next_episode)).setText("Episode "+episode.getSeason()+"x"+episode.getNumber());
                    ((TextView)view.findViewById(R.id.next_episode_description)).setText(Jsoup.parse(episode.getSummary()).text());
                    ((TextView)view.findViewById(R.id.next_episode_time)).setText(episode.getAirdate()+", "+episode.getAirtime());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERR",t.toString());
            }
        });
    }
    private void setCastListAdapter(){

        castListAdapter = new CastListAdapter(list, getActivity(), getFragmentManager());
        recyclerView.setAdapter(castListAdapter);
    }
}
