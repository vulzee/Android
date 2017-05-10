package serializerteam.serializer.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import serializerteam.serializer.R;
import serializerteam.serializer.dto.ShowDto;
import serializerteam.serializer.model.showList.ShowListAdapter;
import serializerteam.serializer.model.showList.ShowListItem;

public class SearchResultsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ShowDto> list;
    private ShowListAdapter showListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.search_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        initData();

        return view;
    }

    private void initData() {
        list = new ArrayList<>();
        //list.add(0, new ShowDto());

        showListAdapter = new ShowListAdapter(list, getActivity());
        recyclerView.setAdapter(showListAdapter);
    }
}
