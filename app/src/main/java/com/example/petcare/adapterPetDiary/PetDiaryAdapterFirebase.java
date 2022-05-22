package com.example.petcare.adapterPetDiary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.petcare.DiaryDetailActivity;
import com.example.petcare.DiaryFragment;
import com.example.petcare.DiaryUpdate;
import com.example.petcare.DogTipsDetailActivity;
import com.example.petcare.DogTipsFirebase;
import com.example.petcare.PetDiaryFirebase;
import com.example.petcare.R;
import com.example.petcare.adapterDogTips.DogTipsAdapterFirebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PetDiaryAdapterFirebase extends RecyclerView.Adapter<PetDiaryAdapterFirebase.ViewHolder> {
    private static final String  Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<PetDiaryFirebase> diaryList;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Diary");
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser = fAuth.getCurrentUser();
//    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance().getReference("Diary");

    public PetDiaryAdapterFirebase(Context mContext, ArrayList<PetDiaryFirebase> diaryList) {
        this.mContext = mContext;
        this.diaryList = diaryList;
        fAuth = FirebaseAuth.getInstance();
//        fUser = fAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Diary");
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
        final String date = petDiaryFirebase.getDate();
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

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.tv_dPetname.getContext());
                alertDialog.setTitle("Delete Diary");
                alertDialog.setMessage("Are you sure want to delete?");
                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        reference.child(fAuth.getCurrentUser().getUid()).child(diaryList.get(position).getDiaryId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "Diary Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext, "Failed to delete diary", Toast.LENGTH_SHORT).show();
                            }
                        });

//                        reference.child(fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for (DataSnapshot ds : dataSnapshot.getChildren()){
//                                    ds.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Toast.makeText(mContext, "Diary Deleted", Toast.LENGTH_SHORT).show();
//                                            //notifyItemRemoved(position);
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(mContext, "Failed to delete diary", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            }

//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {

//                            }
//                        });
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.tv_dPetname.getContext(),"Canceled",Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_dPetname, tv_foodintake, tv_waterintake, tv_date;
        ImageView imgDelete, imgUpdate;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_container);
            tv_dPetname = itemView.findViewById(R.id.tv_dPetname);
            tv_foodintake = itemView.findViewById(R.id.tv_food_intake);
            tv_waterintake = itemView.findViewById(R.id.tv_water_intake);
            tv_date = itemView.findViewById(R.id.tvdate);
            imgDelete = itemView.findViewById(R.id.imgdelete);
            imgUpdate = itemView.findViewById(R.id.imgedit);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Diary: " + diaryList.get(getAdapterPosition()).getPetName(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(), DiaryDetailActivity.class);
            intent.putExtra("petname", diaryList.get(getAdapterPosition()).getPetName());
            intent.putExtra("time", diaryList.get(getAdapterPosition()).getTime());
            intent.putExtra("date", diaryList.get(getAdapterPosition()).getDate());
            intent.putExtra("fooidIntake", diaryList.get(getAdapterPosition()).getFoodIntake());
            intent.putExtra("waterIntake", diaryList.get(getAdapterPosition()).getWaterIntake());
            intent.putExtra("outdoor", diaryList.get(getAdapterPosition()).getOutdoor());
            intent.putExtra("health", diaryList.get(getAdapterPosition()).getHealth());
            intent.putExtra("diaryid", diaryList.get(getAdapterPosition()).getDiaryId());
            view.getContext().startActivity(intent);

        }
    }
}
