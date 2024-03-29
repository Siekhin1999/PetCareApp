package com.example.petcare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.petcare.adapetCatTips.CatTipsAdapterFirebase;
import com.example.petcare.adapterDogTips.DogTipsAdapterFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TipsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TipsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Object DogTipsAdapterFirebase;
    private Object CatTipsAdapterFirebase;

    public TipsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TipsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TipsFragment newInstance(String param1, String param2) {
        TipsFragment fragment = new TipsFragment();
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
    ArrayList<DogTipsFirebase> dogTipsList;
    ArrayList<CatTipsFirebase> catTipsList;
    LinearLayoutManager linearLayoutManager;
    RecyclerView dogtipsRecycler, cattipsRecycler;
    DogTipsAdapterFirebase dogTipsAdapterFirebase;
    CatTipsAdapterFirebase catTipsAdapterFirebase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tips, container, false);

        TextView tv_tab_dog = (TextView)view.findViewById(R.id.tv_tab_dog);
        TextView tv_tab_cat = (TextView)view.findViewById(R.id.tv_tab_cat);
        RelativeLayout dogtipsView = (RelativeLayout)view.findViewById(R.id.dogtipsView);
        RelativeLayout cattipsView = (RelativeLayout)view.findViewById(R.id.cattipsView);
        dogtipsRecycler = (RecyclerView)view.findViewById(R.id.dogtipsRecycler);
        cattipsRecycler = (RecyclerView)view.findViewById(R.id.cattipsRecycler);
//        linearLayoutManager = new LinearLayoutManager(this);

        reference = FirebaseDatabase.getInstance().getReference();

        //for dog tips recyclerview
        dogTipsList = new ArrayList<>();
//      ClearDogAll();
        GetDogDataFromFirebase();

        //for cat tips recyclerview
        catTipsList = new ArrayList<>();
        GetCatDataFromFirebase();


        tv_tab_dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogtipsView.setVisibility(View.VISIBLE);
                cattipsView.setVisibility(View.GONE);

                tv_tab_dog.setBackgroundResource(R.drawable.shape_rect_2);
                tv_tab_cat.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            }
        });

        tv_tab_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cattipsView.setVisibility(View.VISIBLE);
                dogtipsView.setVisibility(View.GONE);

                tv_tab_cat.setBackgroundResource(R.drawable.shape_rect_2);
                tv_tab_dog.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            }
        });

        return view;
    }



    //for dog recycler view
    private void GetDogDataFromFirebase() {
        Query query = reference.child("DogCareTips");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear list at start
                dogTipsList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    DogTipsFirebase dogTipsFirebase = new DogTipsFirebase();

                    dogTipsFirebase.setImage(ds.child("Image").getValue().toString());
                    dogTipsFirebase.setTitle(ds.child("Title").getValue().toString());
                    dogTipsFirebase.setDescription(ds.child("Description").getValue().toString());
                    dogTipsFirebase.setTipsId(ds.child("tipsId").getValue().toString());
                    dogTipsList.add(dogTipsFirebase);
                }

                //setup adapter
                dogTipsAdapterFirebase = new DogTipsAdapterFirebase(getActivity(),dogTipsList);

                //set adapter to recyclerview
                //dogtipsRecycler.setLayoutManager(linearLayoutManager);
                dogtipsRecycler.setAdapter(dogTipsAdapterFirebase);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //for cat recycler view
    private void GetCatDataFromFirebase() {
        Query query = reference.child("CatCareTips");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear list at start
                catTipsList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    CatTipsFirebase catTipsFirebase = new CatTipsFirebase();

                    catTipsFirebase.setImage(ds.child("Image").getValue().toString());
                    catTipsFirebase.setTitle(ds.child("Title").getValue().toString());
                    catTipsFirebase.setDescription(ds.child("Description").getValue().toString());
                    catTipsFirebase.setTipsId(ds.child("tipsId").getValue().toString());
                    catTipsList.add(catTipsFirebase);
                }

                //setup adapter
                catTipsAdapterFirebase = new CatTipsAdapterFirebase(getActivity(),catTipsList);

                //set adapter to recyclerview
                //dogtipsRecycler.setLayoutManager(linearLayoutManager);
                cattipsRecycler.setAdapter(catTipsAdapterFirebase);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

/*    private void ClearDogAll() {
        if (dogTipsList != null) {
            dogTipsList.clear();

            if(DogTipsAdapterFirebase != null){
                DogTipsAdapterFirebase.notifyDataSetChanged();
            }
        }
        dogTipsList = new ArrayList<>();
    }*/
}