package serializerteam.serializer.model.showList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import serializerteam.serializer.R;
import serializerteam.serializer.ShowDetailsFragment;
import serializerteam.serializer.dto.ShowDto;


public class ShowListAdapter extends RecyclerView.Adapter<ShowListViewHolder> {
    private List<ShowDto> list;
    private Context mContext;
    private FragmentManager mFragmentManager;

    public ShowListAdapter(List<ShowDto> list, Context mContext, FragmentManager mFragmentManager) {
        this.list = list;
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    public ShowListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShowListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_list_item, null));
    }

    @Override
    public void onBindViewHolder(final ShowListViewHolder holder, final int position) {
        final ShowDto showListItem = list.get(position);

        holder.mTitleView.setText(showListItem.getName());
        holder.mDescriptionView.setText(showListItem.getGenres().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDetailsFragment a = new ShowDetailsFragment();
                Bundle args=new Bundle();
                args.putInt("id",showListItem.getId());
                a.setArguments(args);
                mFragmentManager.beginTransaction().addToBackStack("details")
                        .replace(R.id.content_frame,a)
                        .commit();
            }
        });

        setShoppingListItemImage(holder, showListItem);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    private void setShoppingListItemImage(ShowListViewHolder holder, ShowDto showListItem) {
        String imageUrl = showListItem.getImage().values().iterator().next();
        if(!imageUrl.isEmpty()) {
            Picasso.with(mContext).load(imageUrl).into(holder.mImageView);
        }
        else {
            Picasso.with(mContext).load(R.mipmap.ic_launcher).into(holder.mImageView);
        }
    }
}
