package serializerteam.serializer;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.EpisodeDto;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import serializerteam.serializer.model.cast.CastListAdapter;
import serializerteam.serializer.model.cast.CastListItem;

import static android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM;

public class ShowDetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CastListItem> list;
    private CastListAdapter castListAdapter;
    private EpisodeDto episode;
    private String seriesName;
    private String summary;
    private String image;
    private String genre;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_details, container, false);

        initData2();
        ((TextView)view.findViewById(R.id.item_title)).setText(seriesName);
        ((TextView)view.findViewById(R.id.item_genre)).setText(genre);
        ((TextView)view.findViewById(R.id.item_long_description)).setText(summary);
        ((TextView)view.findViewById(R.id.next_episode)).setText("Episode "+episode.getSeason()+"x"+episode.getNumber());
        ((TextView)view.findViewById(R.id.next_episode_description)).setText(episode.getSummary());
        ((TextView)view.findViewById(R.id.next_episode_time)).setText(episode.getAirdate()+", "+episode.getAirtime());
        //Picasso.with(getContext()).load(R.mipmap.ic_launcher).into((ImageView)view.findViewById(R.id.item_image));

        recyclerView = (RecyclerView) view.findViewById(R.id.show_cast_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.side_nav_bar, getActivity().getTheme()));
        getActivity().setTheme(R.style.AppTheme_NoActionBar);
        initData();
        toolbar.hide();


    Target showPic = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ((RelativeLayout) view.findViewById(R.id.show_pic)).setBackground(new BitmapDrawable(getResources(), bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            ((RelativeLayout) view.findViewById(R.id.show_pic)).setBackground(getResources().getDrawable(R.drawable.side_nav_bar, getActivity().getTheme()));
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
     Picasso.with(getContext()).load(R.mipmap.ic_launcher).into(showPic);

    return view;
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(0, new CastListItem(0, "Super star", "Super character", ""));
        list.add(1, new CastListItem(0, "Super star", "Super character", ""));
        list.add(2, new CastListItem(0, "Super star", "Super character", ""));
        castListAdapter = new CastListAdapter(list, getActivity(), getFragmentManager());
        recyclerView.setAdapter(castListAdapter);

    }

    private void initData2(){
        episode=new EpisodeDto();
        episode.setAirdate("2017-04-21");
        episode.setAirtime("21:00");
        episode.setSummary("cos o serialu");
        episode.setSeason(4);
        episode.setNumber(8);

        seriesName="Serial o nazwie";
        summary="Serial o abrdzo piekny koniu szukajacym owsa.";
        image="";
        genre="Akcja";
    }
}
