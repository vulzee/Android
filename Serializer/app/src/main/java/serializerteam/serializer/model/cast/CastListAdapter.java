package serializerteam.serializer.model.cast;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import serializerteam.serializer.R;
import serializerteam.serializer.dto.CastDto;


public class CastListAdapter extends RecyclerView.Adapter<CastListViewHolder> {
    private List<CastDto> list;
    private Context mContext;
    private FragmentManager mFragmentManager;

    public CastListAdapter(List<CastDto> list, Context mContext, FragmentManager mFragmentManager) {
        this.list = list;
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    public CastListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CastListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cast_list_item, null));
    }

    @Override
    public void onBindViewHolder(final CastListViewHolder holder, final int position) {
        final CastDto castListItem = list.get(position);

        holder.mTitleView.setText(castListItem.getCharacter().getName());
        holder.mDescriptionView.setText(castListItem.getPerson().getName());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFragmentManager.beginTransaction().addToBackStack("details")
//                        .replace(R.id.content_frame, new ShowDetailsFragment())
//                        .commit();
//            }
//        });

        setShoppingListItemImage(holder, castListItem);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    private void setShoppingListItemImage(CastListViewHolder holder, CastDto castListItem) {
        if(castListItem.getPerson().getImage()!=null && castListItem.getPerson().getImage().size()>0) {
            Picasso.with(mContext).load(castListItem.getPerson().getImage().values().iterator().next()).into(holder.mImageView);
        }
        else {
            Picasso.with(mContext).load(R.mipmap.ic_launcher).into(holder.mImageView);
        }
    }
}
