package com.example.petcare.adapetCatTips;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petcare.CatTipsDetailActivity;
import com.example.petcare.CatTipsFirebase;
import com.example.petcare.DogTipsFirebase;
import com.example.petcare.R;
import com.example.petcare.adapterDogTips.DogTipsAdapterFirebase;

import java.util.ArrayList;

public class CatTipsAdapterFirebase extends RecyclerView.Adapter<CatTipsAdapterFirebase.ViewHolder>{
    private static final String  Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<CatTipsFirebase> catTipsList;

    public CatTipsAdapterFirebase(Context mContext, ArrayList<CatTipsFirebase> catTipsList) {
        this.mContext = mContext;
        this.catTipsList = catTipsList;
    }


    @NonNull
    @Override
    public CatTipsAdapterFirebase.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_cat_tips,parent,false);

        return new CatTipsAdapterFirebase.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CatTipsAdapterFirebase.ViewHolder holder, int position) {
        //get data
        CatTipsFirebase catTipsFirebase = catTipsList.get(position);
        final String title = catTipsFirebase.getTitle();
        final String description = catTipsFirebase.getDescription();
        String image = catTipsFirebase.getImage();
        final String uid = catTipsFirebase.getTipsId();

        //set data
        holder.tv_cat_tips.setText(title);
        Glide.with(mContext)
                .load(catTipsList.get(position).getImage())
                .into(holder.img_cat_tips);

    }

    @Override
    public int getItemCount() {
        return catTipsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_cat_tips;
        TextView tv_cat_tips;
        TextView description;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_container);
            img_cat_tips = itemView.findViewById(R.id.image_cat_tips);
            tv_cat_tips = itemView.findViewById(R.id.tv_cat_tips);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Tips: " + catTipsList.get(getAdapterPosition()).getTitle(),Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext(), CatTipsDetailActivity.class);
            intent.putExtra("tipsName", catTipsList.get(getAdapterPosition()).getTitle());
            intent.putExtra("tipsDetail", catTipsList.get(getAdapterPosition()).getDescription());
            intent.putExtra("image", catTipsList.get(getAdapterPosition()).getImage());
            intent.putExtra("tipsId", catTipsList.get(getAdapterPosition()).getTipsId());
            view.getContext().startActivity(intent);
        }
    }
}
