package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.UpComing;

import java.util.List;

public class UpComingAdapter extends RecyclerView.Adapter<UpComingAdapter.CardViewHolder> {

    private Context context;
    private List<UpComing.Results> listUpComing;
    private List<UpComing.Results> getListUpComing() {
        return listUpComing;
    }

    public void setListUpComing(List<UpComing.Results> listUpComing) {
        this.listUpComing = listUpComing;
    }

    public UpComingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UpComingAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_upcoming, parent, false);
        return new UpComingAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingAdapter.CardViewHolder holder, int position) {
        final UpComing.Results results = getListUpComing().get(position);
        holder.lbl_title.setText(results.getTitle());
        holder.lbl_overview.setText(results.getOverview());
        holder.lbl_release_date.setText(results.getRelease_date());
        Glide.with(context)
                .load(Const.IMG_URL + results.getPoster_path())
                .into(holder.img_poster);
    }

    @Override
    public int getItemCount(){
        return getListUpComing().size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        ImageView img_poster;
        TextView lbl_title, lbl_overview, lbl_release_date;
        CardView cv;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_poster_card_upcoming);
            lbl_title = itemView.findViewById(R.id.lbl_title_card_upcoming);
            lbl_overview = itemView.findViewById(R.id.lbl_overview_upcoming);
            lbl_release_date = itemView.findViewById(R.id.lbl_releasedate_card_upcoming);
            cv = itemView.findViewById(R.id.cv_card_nowplaying);
        }
    }
}
