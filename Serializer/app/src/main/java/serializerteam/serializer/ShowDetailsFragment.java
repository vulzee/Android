package serializerteam.serializer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.EpisodeDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import serializerteam.serializer.castModel.CastListAdapter;
import serializerteam.serializer.castModel.CastListItem;

public class ShowDetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CastListItem> list;
    private CastListAdapter castListAdapter;
    private EpisodeDto episode;
    private String seriesName;
    private String summary;
    private String image;
    private String genre;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_details, container, false);

        initData2();
        ((TextView)view.findViewById(R.id.item_title)).setText(seriesName);
        ((TextView)view.findViewById(R.id.item_genre)).setText(genre);
        ((TextView)view.findViewById(R.id.item_long_description)).setText(summary);
        ((TextView)view.findViewById(R.id.next_episode)).setText("Episode "+episode.getSeason()+"x"+episode.getNumber());
        ((TextView)view.findViewById(R.id.next_episode_description)).setText(episode.getSummary());
        ((TextView)view.findViewById(R.id.next_episode_time)).setText(episode.getAirdate()+", "+episode.getAirtime());
        Picasso.with(getContext()).load(R.mipmap.ic_launcher).into((ImageView)view.findViewById(R.id.item_image));
        recyclerView = (RecyclerView) view.findViewById(R.id.show_cast_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        initData();

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
