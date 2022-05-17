package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.petcare.adapterDogTips.DogTipsAdapterFirebase;
import com.example.petcare.adapterPetDiary.PetDiaryAdapterFirebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiaryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiaryFragment newInstance(String param1, String param2) {
        DiaryFragment fragment = new DiaryFragment();
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

    DatabaseReference reference;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    RecyclerView petDiaryRecycler;
    PetDiaryFirebase petDiaryFirebase;
    ArrayList<PetDiaryFirebase> diaryList;
    PetDiaryAdapterFirebase petDiaryAdapterFirebase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        TextView tv_title = (TextView)view.findViewById(R.id.tv_petdiary);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fbtn_adddiary);
        RelativeLayout petDiaryView = (RelativeLayout)view.findViewById(R.id.petDiaryView);
        petDiaryRecycler = (RecyclerView)view.findViewById(R.id.petDiaryRecycler);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Diary").child(fAuth.getUid());

        //for pet diary recyclerview
        diaryList = new ArrayList<>();
        GetDiaryDataFromFirebase();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Diary3rdPage.class);
                startActivity(intent);
            }
        });

        return view;
    }

    //retrieve diary data from database
    private void GetDiaryDataFromFirebase() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear list at start
                diaryList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
//                    petDiaryFirebase = ds.getValue(PetDiaryFirebase.class);
//                    diaryList.add(petDiaryFirebase);

                    PetDiaryFirebase petDiaryFirebase = new PetDiaryFirebase();

                    petDiaryFirebase.setPetName(ds.child("petname").getValue().toString());
                    petDiaryFirebase.setTime(ds.child("time").getValue().toString());
                    petDiaryFirebase.setDate(ds.child("date").getValue().toString());
                    petDiaryFirebase.setFoodIntake(ds.child("foodIntake").getValue().toString());
                    petDiaryFirebase.setWaterIntake(ds.child("waterIntake").getValue().toString());
                    petDiaryFirebase.setOutdoor(ds.child("outdoor").getValue().toString());
                    petDiaryFirebase.setHealth(ds.child("health").getValue().toString());
                    diaryList.add(petDiaryFirebase);
                }

                //setup adapter
                petDiaryAdapterFirebase = new PetDiaryAdapterFirebase(getActivity(),diaryList);
                //set adapter to recyclerview
                petDiaryRecycler.setAdapter(petDiaryAdapterFirebase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}