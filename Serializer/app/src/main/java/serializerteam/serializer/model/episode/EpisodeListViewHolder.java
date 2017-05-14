package serializerteam.serializer.model.episode;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import serializerteam.serializer.R;

public class EpisodeListViewHolder extends RecyclerView.ViewHolder {
    TextView mTitleView;
    TextView mDescriptionView;
    ImageView mImageView;
    TextView mShowTime;


    public EpisodeListViewHolder(View itemView) {
        super(itemView);
        this.mTitleView = (TextView) itemView.findViewById(R.id.item_title);
        this.mDescriptionView = (TextView) itemView.findViewById(R.id.item_description);
        this.mImageView = (ImageView) itemView.findViewById(R.id.item_image);
        this.mShowTime = (TextView) itemView.findViewById(R.id.show_time);
    }

}
