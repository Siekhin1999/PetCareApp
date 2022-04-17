package com.example.petcare.adapterDogTips;

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
import com.example.petcare.DogTipsDetailActivity;
import com.example.petcare.DogTipsFirebase;
import com.example.petcare.R;

import java.util.ArrayList;

public class DogTipsAdapterFirebase extends RecyclerView.Adapter<DogTipsAdapterFirebase.ViewHolder>{
    private static final String  Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<DogTipsFirebase> dogTipsList;

    public DogTipsAdapterFirebase(Context mContext, ArrayList<DogTipsFirebase> dogTipsList) {
        this.mContext = mContext;
        this.dogTipsList = dogTipsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        TextView description;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_container);
            imageView = itemView.findViewById(R.id.image_dog_tips);
            textView = itemView.findViewById(R.id.tv_dog_tips);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Tips: " + dogTipsList.get(getAdapterPosition()).getTitle(),Toast.LENGTH_SHORT).show();

/*          Intent intent = new Intent(view.getContext(), DogTipsDetailActivity.class);
            intent.putExtra("tipsName", tipsList.get(getAdapterPosition()).getTitle());
            intent.putExtra("tipsDetail", tipsList.get(getAdapterPosition()).getDescription());
            intent.putExtra("image", tipsList.get(getAdapterPosition()).getImage());
            intent.putExtra("tipsId", tipsList.get(getAdapterPosition()).getTipsId());
            view.getContext().startActivity(intent);*/
        }
    }

    @NonNull
    @Override
    public DogTipsAdapterFirebase.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_dog_tips,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogTipsAdapterFirebase.ViewHolder holder, int position) {
        holder.textView.setText(dogTipsList.get(position).getTitle());
        Glide.with(mContext)
                .load(dogTipsList.get(position).getImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return dogTipsList.size();
    }


}
