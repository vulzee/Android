package serializerteam.serializer.model.showList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import serializerteam.serializer.R;

public class ShowListViewHolder extends RecyclerView.ViewHolder {
    TextView mTitleView;
    TextView mDescriptionView;
    ImageView mImageView;


    public ShowListViewHolder(View itemView) {
        super(itemView);
        this.mTitleView = (TextView) itemView.findViewById(R.id.item_title);
        this.mDescriptionView = (TextView) itemView.findViewById(R.id.item_description);
        this.mImageView = (ImageView) itemView.findViewById(R.id.item_image);

    }


}
