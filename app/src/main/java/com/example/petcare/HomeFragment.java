package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    TextView tvPetname,tvDate, tvEat,tvWater,tvPark;
    ImageView imgPet;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseDatabase database;
    FirebaseStorage fStorage;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        TextView dashboard = (TextView) view.findViewById(R.id.tv_dashboard);
        CardView cardTips = view.findViewById(R.id.btn_changephoto);
        CardView cardDiary = view.findViewById(R.id.btn_diary);
        CardView cardTraining = view.findViewById(R.id.btn_training);
        ImageView imgTips = view.findViewById(R.id.img_tips);
        ImageView imgDiary = view.findViewById(R.id.img_diary);
        ImageView imgTraining = view.findViewById(R.id.imgtraining);
        ImageView imgLogout = view.findViewById(R.id.btn_imglogout);

        CardView cardView = view.findViewById(R.id.cv_hDiary);
        imgPet = view.findViewById(R.id.img_pet2);
        tvPetname = view.findViewById(R.id.tv_dpetname);
        tvDate = view.findViewById(R.id.tv_date);
        tvEat = view.findViewById(R.id.tv_eat);
        tvWater = view.findViewById(R.id.tv_water);
        tvPark = view.findViewById(R.id.tv_park);

        imgTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), TipsFragment.class);
//                startActivity(intent);
                Fragment fragment = new TipsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).commit();
            }
        });

        imgDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent2 = new Intent(getActivity(), DiaryFragment.class);
//                startActivity(intent2);
                Fragment fragment2 = new DiaryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment2).commit();
            }
        });

        imgTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent3 = new Intent(getActivity(), TrainingFragment.class);
//                startActivity(intent3);
                Fragment fragment3 = new TrainingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment3).commit();
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "You've Successfully Logout!", Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(getActivity(), Login.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent4);
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Diary");

        //show diary history
        ShowDiaryHistory();

//        reference.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String date = "" + dataSnapshot.child("date").getValue().toString();
//                String food = "" + dataSnapshot.child("foodIntake").getValue().toString();
//                String water = "" + dataSnapshot.child("waterIntake").getValue().toString();
//                String outdoor = "" + dataSnapshot.child("outdoor").getValue().toString();

//                tvDate.setText(date);
//                tvEat.setText(food);
//                tvWater.setText(water);
//                tvPark.setText(outdoor);
//            }
//                tvEat.setText("" +dataSnapshot.child("foodIntake").getValue().toString());
//                tvWater.setText("" +dataSnapshot.child("waterIntake").getValue().toString());
//                tvPark.setText("" +dataSnapshot.child("outdoor").getValue().toString());
//                String image = (String) dataSnapshot.child("image").getValue();
//                Glide.with(getActivity()).load(image).into(imgPet);


//            @Override
//           public void onCancelled(@NonNull DatabaseError databaseError) {
//                throw databaseError.toException();
//            }
//        });


        // Inflate the layout for this fragment
        return view;
    }

    //show diary history
    private void ShowDiaryHistory() {
        Query query = reference.child(fAuth.getUid()).orderByKey().limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String name = "" + ds.child("petname").getValue().toString();
                    String date = "" + ds.child("date").getValue().toString();
                    String food = "" + ds.child("foodIntake").getValue().toString();
                    String water = "" + ds.child("waterIntake").getValue().toString();
                    String outdoor = "" + ds.child("outdoor").getValue().toString();
//                    String image = (String) dataSnapshot.child("image").getValue();
//                    Glide.with(getActivity()).load(image).into(imgPet);

                    tvPetname.setText(name);
                    tvDate.setText(date);
                    tvEat.setText(food);
                    tvWater.setText(water);
                    tvPark.setText(outdoor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}