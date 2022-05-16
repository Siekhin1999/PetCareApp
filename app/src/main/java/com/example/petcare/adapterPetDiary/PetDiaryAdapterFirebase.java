package com.example.petcare.adapterPetDiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.DogTipsFirebase;
import com.example.petcare.PetDiaryFirebase;
import com.example.petcare.R;
import com.example.petcare.adapterDogTips.DogTipsAdapterFirebase;

import java.util.ArrayList;

public class PetDiaryAdapterFirebase extends RecyclerView.Adapter<PetDiaryAdapterFirebase.ViewHolder> {
    private static final String  Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<PetDiaryFirebase> diaryList;

    public PetDiaryAdapterFirebase(Context mContext, ArrayList<PetDiaryFirebase> diaryList) {
        this.mContext = mContext;
        this.diaryList = diaryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_diary,parent,false);

        return new PetDiaryAdapterFirebase.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetDiaryAdapterFirebase.ViewHolder holder, int position) {
        //get data
        PetDiaryFirebase petDiaryFirebase = diaryList.get(position);
        final String petName = petDiaryFirebase.getPetName();
        final String time = petDiaryFirebase.getTime();
        final String date = petDiaryFirebase.getTime();
        final String foodIntake = petDiaryFirebase.getFoodIntake();
        final String waterIntake = petDiaryFirebase.getWaterIntake();
        final String outdoor = petDiaryFirebase.getOutdoor();
        final String health = petDiaryFirebase.getHealth();
        final String diaryId = petDiaryFirebase.getDiaryId();

        //set data
        holder.tv_dPetname.setText(petName);
        holder.tv_foodintake.setText(foodIntake);
        holder.tv_waterintake.setText(waterIntake);
        holder.tv_date.setText(date);

    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_dPetname, tv_foodintake, tv_waterintake, tv_date;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_container);
            tv_dPetname = itemView.findViewById(R.id.tv_dPetname);
            tv_foodintake = itemView.findViewById(R.id.tv_food_intake);
            tv_waterintake = itemView.findViewById(R.id.tv_water_intake);
            tv_date = itemView.findViewById(R.id.tvdate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Diary: " + diaryList.get(getAdapterPosition()).getDiaryId(),Toast.LENGTH_SHORT).show();

        }
    }
}
