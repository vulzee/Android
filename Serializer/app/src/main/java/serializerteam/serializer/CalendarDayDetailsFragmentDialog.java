package serializerteam.serializer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serializerteam.serializer.api.ApiSettings;
import serializerteam.serializer.api.UsersApi;
import serializerteam.serializer.dto.EpisodeDto;
import serializerteam.serializer.dto.ShowDto;
import serializerteam.serializer.model.episode.EpisodeListAdapter;
import serializerteam.serializer.model.showList.ShowListAdapter;

import static android.content.Context.MODE_PRIVATE;

public class CalendarDayDetailsFragmentDialog extends DialogFragment {

    private RecyclerView recyclerView;
    private static ArrayList<EpisodeDto> list = new ArrayList<>();
  //  int[] myFavouriteShows;
    ArrayList<ShowDto> shows;
    int episodesNo=0;
    String date="";
    @Override
    public void setArguments(Bundle args) {
        date=args.getString("date");
        super.setArguments(args);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragment_calendar_day_details_dialog, null);

        builder.setView(v)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CalendarDayDetailsFragmentDialog.this.getDialog().cancel();
                    }
                });

//        ((TextView)v.findViewById(R.id.item_title)).setText("Some show");
//        ((TextView)v.findViewById(R.id.item_description)).setText("Description");
//        ((TextView)v.findViewById(R.id.show_time)).setText("16:30");

        recyclerView = (RecyclerView) v.findViewById(R.id.episodes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
        return builder.create();
    }

    private void initData() {
       // ApiSettings.usersApi.getUserShows(
     //           getActivity().getSharedPreferences("serializer", MODE_PRIVATE).getString("userId", null)
     //   ).enqueue(new Callback<int[]>() {
        //    @Override
        //    public void onResponse(Call<int[]> call, Response<int[]> response) {
               // myFavouriteShows = new int[response.body().length];
               // System.arraycopy(response.body(), 0, myFavouriteShows, 0, response.body().length);
                list.clear();
                shows = MyShowsFragment.getShowsList();
                //Call<ShowDto>[] tasks = new Call[shows.size()];

                for (int i = 0; i <shows.size(); i++) {
                    if (shows.get(i).getLinks().get("nextepisode") != null && shows.get(i).getLinks().get("nextepisode").href.length() > 0) {
                        getNextEpisode( i);
                        episodesNo++;
                    }
                }
            }


//            @Override
//            public void onFailure(Call<int[]> call, Throwable t) {
//                Log.e("ERR", t.getMessage());
//                Toast.makeText(getActivity(), "Couldn't get episodes", Toast.LENGTH_SHORT).show();
//            }
//        });

    private void setShowListAdapter() {
       /* if (list.size() == 0)
            view.findViewById(R.id.no_shows_found).setVisibility(View.VISIBLE);
        else
            view.findViewById(R.id.no_shows_found).setVisibility(View.GONE);*/

        EpisodeListAdapter episodeListAdapter = new EpisodeListAdapter(list, getActivity());
        //showListAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(episodeListAdapter);
    }


    private void getNextEpisode(final int showId) {
        ApiSettings.episodeApi.getEpisodeByDate(shows.get(showId).getId(),date).enqueue(new Callback<EpisodeDto[]>() {
            @Override
            public void onResponse(Call<EpisodeDto[]> call, Response<EpisodeDto[]> response) {
                if (response.body() != null) {
                    EpisodeDto[] e=response.body();//new Gson().fromJson(response.body().charStream(), EpisodeDto.class);
                    if(shows.get(showId).getImage()!=null) {
                        String imageUrl = shows.get(showId).getImage().values().iterator().next();
                        for (int m = 0; m < e.length; m++) {
                            if (!imageUrl.isEmpty()) {
                                HashMap<String, String> image = e[m].getImage();
                                if (image == null)
                                    image = new HashMap<String, String>();
                                image.put("show", imageUrl);
                                e[m].setImage(image);
                            }


                        if (e[m].getAirdate().equals(date))
                            list.add(e[m]);
                        }
                    }
                }
                episodesNo--;
                if(episodesNo==0)
                    setShowListAdapter();
            }

            @Override
            public void onFailure(Call<EpisodeDto[]> call, Throwable t) {
                Log.e("ERR", t.toString());
                episodesNo--;
                if(episodesNo==0)
                    setShowListAdapter();
            }
        });
    }

}
