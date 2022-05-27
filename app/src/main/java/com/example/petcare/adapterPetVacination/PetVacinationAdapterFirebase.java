package com.example.petcare.adapterPetVacination;

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

import com.example.petcare.PetVaccineFirebase;
import com.example.petcare.R;
import com.example.petcare.VaccineDetailActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PetVacinationAdapterFirebase extends RecyclerView.Adapter<PetVacinationAdapterFirebase.ViewHolder> {
    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<PetVaccineFirebase> vaccineList;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Vaccine");
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser = fAuth.getCurrentUser();

    public PetVacinationAdapterFirebase(Context mContext, ArrayList<PetVaccineFirebase> vaccineList) {
        this.mContext = mContext;
        this.vaccineList = vaccineList;
        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Vaccine");

    }

    @NonNull
    @Override
    public PetVacinationAdapterFirebase.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_pet_vaccine,parent,false);

        return new PetVacinationAdapterFirebase.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetVacinationAdapterFirebase.ViewHolder holder, int position) {
        //get data
        PetVaccineFirebase petVaccineFirebase = vaccineList.get(position);
        final String petName = petVaccineFirebase.getPetName();
        //final String time = petVaccineFirebase.getTime();
        final String date = petVaccineFirebase.getDate();
        final String vaccineIntake = petVaccineFirebase.getVaccineIntake();
        //final String notes = petVaccineFirebase.getNote();
        final String cared = petVaccineFirebase.getCared();
        final String vaccineId = petVaccineFirebase.getVaccineId();

        //set data
        holder.tv_vPetname.setText(petName);
        holder.tv_vaccineintake.setText(vaccineIntake);
        holder.tv_caredby.setText(cared);
        holder.tv_date.setText(date);

        holder.imgvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.tv_vPetname.getContext());
                alertDialog.setTitle("Delete Diary");
                alertDialog.setMessage("Are you sure want to delete?");
                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reference.child(fAuth.getCurrentUser().getUid()).child(vaccineList.get(position).getVaccineId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "Vaccination Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext, "Failed to delete vaccination", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.tv_vPetname.getContext(),"Canceled",Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return vaccineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_vPetname, tv_vaccineintake, tv_caredby, tv_date;
        ImageView imgvDelete, imgUpdate;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_container);
            tv_vPetname = itemView.findViewById(R.id.tv_vPetname);
            tv_vaccineintake = itemView.findViewById(R.id.tv_vaccine_take);
            tv_caredby = itemView.findViewById(R.id.tv_cared_by);
            tv_date = itemView.findViewById(R.id.tvdate);
            imgvDelete = itemView.findViewById(R.id.imgvdelete);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Vaccine: " + vaccineList.get(getAdapterPosition()).getPetName(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(), VaccineDetailActivity.class);

            intent.putExtra("petname", vaccineList.get(getAdapterPosition()).getPetName());
            intent.putExtra("time", vaccineList.get(getAdapterPosition()).getTime());
            intent.putExtra("date", vaccineList.get(getAdapterPosition()).getDate());
            intent.putExtra("vaccineIntake", vaccineList.get(getAdapterPosition()).getVaccineIntake());
            intent.putExtra("cared", vaccineList.get(getAdapterPosition()).getCared());
            intent.putExtra("note", vaccineList.get(getAdapterPosition()).getNote());
            intent.putExtra("vaccineid", vaccineList.get(getAdapterPosition()).getVaccineId());
            view.getContext().startActivity(intent);

        }
    }
}
