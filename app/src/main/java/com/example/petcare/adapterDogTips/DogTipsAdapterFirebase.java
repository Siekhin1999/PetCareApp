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



    @NonNull
    @Override
    public DogTipsAdapterFirebase.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_dog_tips,parent,false);

        return new DogTipsAdapterFirebase.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogTipsAdapterFirebase.ViewHolder holder, int position) {
        //get data
        DogTipsFirebase dogTipsFirebase = dogTipsList.get(position);
        final String title = dogTipsFirebase.getTitle();
        final String description = dogTipsFirebase.getDescription();
        String image = dogTipsFirebase.getImage();
        final String uid = dogTipsFirebase.getTipsId();

        //set data
        holder.tv_dog_tips.setText(title);
        Glide.with(mContext)
                .load(dogTipsList.get(position).getImage())
                .into(holder.img_dog_tips);


//        holder.tv_dog_tips.setText(dogTipsList.get(position).getTitle());
//        Glide.with(mContext)
//                .load(dogTipsList.get(position).getImage())
 //               .into(holder.img_dog_tips);

    }

    @Override
    public int getItemCount() {
        return dogTipsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img_dog_tips;
        TextView tv_dog_tips;
        TextView description;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_container);
            img_dog_tips = itemView.findViewById(R.id.image_dog_tips);
            tv_dog_tips = itemView.findViewById(R.id.tv_dog_tips);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Tips: " + dogTipsList.get(getAdapterPosition()).getTitle(),Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext(), DogTipsDetailActivity.class);
            intent.putExtra("tipsName", dogTipsList.get(getAdapterPosition()).getTitle());
            intent.putExtra("tipsDetail", dogTipsList.get(getAdapterPosition()).getDescription());
            intent.putExtra("image", dogTipsList.get(getAdapterPosition()).getImage());
            intent.putExtra("tipsId", dogTipsList.get(getAdapterPosition()).getTipsId());
            view.getContext().startActivity(intent);
        }
    }


}
