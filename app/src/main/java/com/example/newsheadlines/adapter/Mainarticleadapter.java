package com.example.newsheadlines.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsheadlines.Activity.WebActivity;
import com.example.newsheadlines.R;
import com.example.newsheadlines.models.Article;

import java.util.ArrayList;

public class Mainarticleadapter extends RecyclerView.Adapter<Mainarticleadapter.ViewHolder> {



    private ArrayList<Article> marticlelist;
    private Context mcontext;

    public Mainarticleadapter(Context mcontext, ArrayList<Article> marticleslist) {
        this.marticlelist=marticleslist;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public Mainarticleadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
        return new Mainarticleadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Mainarticleadapter.ViewHolder viewHolder, int i) {

        viewHolder.titletext.setText(marticlelist.get(i).getTitle());
        Glide.with(mcontext)
                .load(marticlelist.get(i).getUrlToImage())
                .thumbnail(0.1f)
                .centerCrop()
                .error(R.drawable.ic_placeholder)
                .into(viewHolder.imgview);

        Animation animation= AnimationUtils.loadAnimation(mcontext,R.anim.item_fall_down);
        viewHolder.cardView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return marticlelist.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titletext;
        private ImageView imgview;
        private CardView cardView;

        ViewHolder(View itemview) {
            super(itemview);
            titletext = itemview.findViewById(R.id.title);
            imgview=itemview.findViewById(R.id.img_card_main);
            cardView=itemview.findViewById(R.id.card_row);
            itemview.setOnClickListener(this);
        }
        @Override
            public void onClick(View view){
            String URL=marticlelist.get(getAdapterPosition()).getUrl();
            Intent intent=new Intent(mcontext,WebActivity.class);
            intent.putExtra("url",URL);
            mcontext.startActivity(intent);
            ((Activity)mcontext).overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);
        }
    }
}
