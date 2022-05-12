package com.example.petcare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.petcare.adapetCatTips.CatTipsAdapterFirebase;
import com.example.petcare.adapterCatTrainings.CatTrainingAdapterFirebase;
import com.example.petcare.adapterDogTips.DogTipsAdapterFirebase;
import com.example.petcare.adapterDogTrainings.DogTrainingAdapterFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrainingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrainingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrainingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrainingFragment newInstance(String param1, String param2) {
        TrainingFragment fragment = new TrainingFragment();
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
    ArrayList<DogTrainingFirebase> dogTrainingList;
    ArrayList<CatTrainingFirebase> catTrainingList;
    LinearLayoutManager linearLayoutManager;
    RecyclerView dogTrngRecycler, catTrngRecycler;
    DogTrainingAdapterFirebase dogTrainingAdapterFirebase;
    CatTrainingAdapterFirebase catTrainingAdapterFirebase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training, container, false);

        TextView tv_tab_dog = (TextView)view.findViewById(R.id.tv_tab_dog);
        TextView tv_tab_cat = (TextView)view.findViewById(R.id.tv_tab_cat);
        RelativeLayout dogTrngView = (RelativeLayout)view.findViewById(R.id.dogTrainingView);
        RelativeLayout catTrngView = (RelativeLayout)view.findViewById(R.id.catTrainingView);
        dogTrngRecycler = (RecyclerView)view.findViewById(R.id.dogTrainingRecycler);
        catTrngRecycler = (RecyclerView)view.findViewById(R.id.catTrainingRecycler);

        reference = FirebaseDatabase.getInstance().getReference();

        //for dog training recyclerview
        dogTrainingList = new ArrayList<>();
        GetDogDataFromFirebase();

        //for cat training recyclerview
        catTrainingList = new ArrayList<>();
        GetCatDataFromFirebase();

        tv_tab_dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogTrngView.setVisibility(View.VISIBLE);
                catTrngView.setVisibility(View.GONE);

                tv_tab_dog.setBackgroundResource(R.drawable.shape_rect_2);
                tv_tab_cat.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            }
        });

        tv_tab_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catTrngView.setVisibility(View.VISIBLE);
                dogTrngView.setVisibility(View.GONE);

                tv_tab_cat.setBackgroundResource(R.drawable.shape_rect_2);
                tv_tab_dog.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            }
        });

        return view;
    }

    //for dog training recyclerview
    private void GetDogDataFromFirebase() {
        Query query = reference.child("DogTraining");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear list at start
                dogTrainingList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    DogTrainingFirebase dogTrainingFirebase = new DogTrainingFirebase();

                    dogTrainingFirebase.setImage(ds.child("Image").getValue().toString());
                    dogTrainingFirebase.setTitle(ds.child("Title").getValue().toString());
                    dogTrainingFirebase.setDescription(ds.child("Description").getValue().toString());
                    dogTrainingFirebase.setVideo(ds.child("Video").getValue().toString());
                    dogTrainingFirebase.setLinkUrl(ds.child("Link").getValue().toString());
                    dogTrainingFirebase.setTrainingId(ds.child("trainingId").getValue().toString());
                    dogTrainingList.add(dogTrainingFirebase);
                }

                //setup adapter
                dogTrainingAdapterFirebase = new DogTrainingAdapterFirebase(getActivity(),dogTrainingList);

                //set adapter to recyclerview
                dogTrngRecycler.setAdapter(dogTrainingAdapterFirebase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //for cat training recyclerview
    private void GetCatDataFromFirebase() {
        Query query = reference.child("CatTraining");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear list at start
                catTrainingList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    CatTrainingFirebase catTrainingFirebase = new CatTrainingFirebase();

                    catTrainingFirebase.setImage(ds.child("Image").getValue().toString());
                    catTrainingFirebase.setTitle(ds.child("Title").getValue().toString());
                    catTrainingFirebase.setDescription(ds.child("Description").getValue().toString());
                    catTrainingFirebase.setVideo(ds.child("Video").getValue().toString());
                    catTrainingFirebase.setLinkUrl(ds.child("Link").getValue().toString());
                    catTrainingFirebase.setTrainingId(ds.child("trainingId").getValue().toString());
                    catTrainingList.add(catTrainingFirebase);
                }

                //setup adapter
                catTrainingAdapterFirebase = new CatTrainingAdapterFirebase(getActivity(),catTrainingList);

                //set adapter to recyclerview
                catTrngRecycler.setAdapter(catTrainingAdapterFirebase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}