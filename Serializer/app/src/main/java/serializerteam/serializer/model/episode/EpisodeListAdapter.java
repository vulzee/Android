package serializerteam.serializer.model.episode;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import serializerteam.serializer.R;
import serializerteam.serializer.dto.CastDto;
import serializerteam.serializer.dto.EpisodeDto;
import serializerteam.serializer.model.cast.CastListViewHolder;


public class EpisodeListAdapter extends RecyclerView.Adapter<EpisodeListViewHolder> {
    private List<EpisodeDto> list;
    private Context mContext;

    public EpisodeListAdapter(List<EpisodeDto> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public EpisodeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EpisodeListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_episode_list_item, null));
    }

    @Override
    public void onBindViewHolder(final EpisodeListViewHolder holder, final int position) {
        final EpisodeDto episodeListItem = list.get(position);

        holder.mTitleView.setText(episodeListItem.getName());
        holder.mShowTime.setText(episodeListItem.getAirtime());
        //holder.mDescriptionView.setText(castListItem.getPerson().getName());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFragmentManager.beginTransaction().addToBackStack("details")
//                        .replace(R.id.content_frame, new ShowDetailsFragment())
//                        .commit();
//            }
//        });

        setShoppingListItemImage(holder, episodeListItem);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    private void setShoppingListItemImage(EpisodeListViewHolder holder, EpisodeDto episodeListItem) {
        if(episodeListItem.getImage()!=null && episodeListItem.getImage().containsKey("show")) {
            Picasso.with(mContext).load(episodeListItem.getImage().get("show")).into(holder.mImageView);
        }
        else {
            Picasso.with(mContext).load(R.mipmap.ic_launcher).into(holder.mImageView);
        }
    }
}
