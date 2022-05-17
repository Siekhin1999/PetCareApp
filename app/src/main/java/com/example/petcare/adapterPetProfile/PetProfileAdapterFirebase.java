package com.example.petcare.adapterPetProfile;

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
import com.example.petcare.PetDiaryFirebase;
import com.example.petcare.Profile2ndPage;
import com.example.petcare.R;
import com.example.petcare.UserDataFirebase;
import com.example.petcare.adapterPetDiary.PetDiaryAdapterFirebase;

import java.util.ArrayList;

public class PetProfileAdapterFirebase extends RecyclerView.Adapter<PetProfileAdapterFirebase.ViewHolder>{
    private static final String  Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<UserDataFirebase> userPetList;

    public PetProfileAdapterFirebase(Context mContext, ArrayList<UserDataFirebase> userPetList) {
        this.mContext = mContext;
        this.userPetList = userPetList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_petprofile,parent,false);

        return new PetProfileAdapterFirebase.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get data
        UserDataFirebase userDataFirebase = userPetList.get(position);
        final String petName = userDataFirebase.getPetName();
        final String petAge = userDataFirebase.getPetAge();
        final String petGender = userDataFirebase.getPetGender();
        final String username = userDataFirebase.getUserName();
        final String email = userDataFirebase.getEmail();
        final String userId = userDataFirebase.getUserId();
        String image = userDataFirebase.getImage();

        //set data
        holder.tv_pet_name.setText(petName);
        Glide.with(mContext)
                .load(userPetList.get(position).getImage())
                .into(holder.img_pet_profile);

    }

    @Override
    public int getItemCount() {
        return userPetList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageView img_pet_profile;
        TextView tv_pet_name;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_container);
            tv_pet_name = itemView.findViewById(R.id.tv_pet_name);
            img_pet_profile = itemView.findViewById(R.id.img_pet_profile);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Pet: " + userPetList.get(getAdapterPosition()).getPetName(),Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext(), Profile2ndPage.class);
            intent.putExtra("image", userPetList.get(getAdapterPosition()).getImage());
            intent.putExtra("petname", userPetList.get(getAdapterPosition()).getPetName());
            intent.putExtra("petage", userPetList.get(getAdapterPosition()).getPetAge());
            intent.putExtra("petgender", userPetList.get(getAdapterPosition()).getPetGender());
            intent.putExtra("name", userPetList.get(getAdapterPosition()).getUserName());
            intent.putExtra("email", userPetList.get(getAdapterPosition()).getEmail());
            intent.putExtra("uid", userPetList.get(getAdapterPosition()).getUserId());
            view.getContext().startActivity(intent);

        }
    }
}
