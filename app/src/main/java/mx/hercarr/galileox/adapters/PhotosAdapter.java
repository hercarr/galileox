package mx.hercarr.galileox.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.hercarr.galileox.R;
import mx.hercarr.galileox.model.Photo;
import mx.hercarr.galileox.util.ImageLoaderUtil;

/**
 * Created by hercarr on 7/13/16.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    private Context context;
    private List<Photo> photos;

    public PhotosAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.bindPhoto(photos.get(position));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void reload(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    protected class PhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgPhoto)
        ImageView imgPhoto;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindPhoto(Photo photo) {
            ImageLoaderUtil.loadImage(context, imgPhoto, photo.getImageUrl());
        }

    }

}