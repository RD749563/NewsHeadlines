package com.example.newsheadlines.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newsheadlines.Activity.MainActivity;
import com.example.newsheadlines.Activity.WebActivity;
import com.example.newsheadlines.R;
import com.example.newsheadlines.models.Article;

import java.util.ArrayList;
import java.util.List;

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
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_adapter,viewGroup,false);
        return new Mainarticleadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Mainarticleadapter.ViewHolder viewHolder, int i) {

        viewHolder.titletext.setText(marticlelist.get(i).getTitle());
        viewHolder.descriptiontext.setText(marticlelist.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return marticlelist.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titletext;
        private TextView descriptiontext;

        ViewHolder(View itemview) {
            super(itemview);
            titletext = itemview.findViewById(R.id.title);
            descriptiontext = itemview.findViewById(R.id.description);
            itemview.setOnClickListener(this);
        }
        @Override
            public void onClick(View view){
            String URL=marticlelist.get(getAdapterPosition()).getUrl();
            Intent intent=new Intent(mcontext,WebActivity.class);
            intent.putExtra("url",URL);
            mcontext.startActivity(intent);

        }
    }
}
