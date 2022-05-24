package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.petcare.adapterPetDiary.PetDiaryAdapterFirebase;
import com.example.petcare.adapterPetProfile.PetProfileAdapterFirebase;
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
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    DatabaseReference reference, mref;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    GridLayoutManager gridLayoutManager;
    RecyclerView petProfileRecycler;
    CardView btnProfilePet;
    ImageView img3;
    UserDataFirebase userDataFirebase;
    ArrayList<UserDataFirebase> userPetList;
    PetProfileAdapterFirebase petProfileAdapterFirebase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tv_title = (TextView)view.findViewById(R.id.tv_petprofile);
        TextView tvpetname = view.findViewById(R.id.tv_profilepetname);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fbtn_addpet);
        RelativeLayout petProfileView = (RelativeLayout)view.findViewById(R.id.petProfileView);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        petProfileRecycler = (RecyclerView)view.findViewById(R.id.petProfileRecycler);
        btnProfilePet = view.findViewById(R.id.btn_petProfile);
        img3 = view.findViewById(R.id.img_pet3);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        mref = FirebaseDatabase.getInstance().getReference("UserData");
        reference = FirebaseDatabase.getInstance().getReference("UserData");

        //for pet profile recyclerview
        userPetList = new ArrayList<>();
        GetPetDataFromFirebase();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(fAuth.getUid()).child("AddedPet").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //check if pet profile child less than 2
                        long num = snapshot.getChildrenCount();
                        if ( num < 2){
                            Intent intent = new Intent(getActivity(),AddPetProfile.class);
                            startActivity(intent);
                        }
                        else if (num >= 2){
                            //if pet profile child more than 2
                            Toast.makeText(getActivity(), "Only can add 2 pets", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnProfilePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Profile2ndPage.class);
                startActivity(intent);
            }
        });

        //for 1st acc pet
        mref.child(fAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getActivity() == null) {
                    return;
                }
                tvpetname.setText(dataSnapshot.child("petname").getValue().toString());
                String image = (String) dataSnapshot.child("image").getValue();
                Glide.with(getActivity()).load(image).into(img3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    //for added pet
    private void GetPetDataFromFirebase() {
        Query query = reference.child(fAuth.getUid()).child("AddedPet");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear list at start
                userPetList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    UserDataFirebase userDataFirebase = new UserDataFirebase();

                    userDataFirebase.setPetPName((String) ds.child("newPetname").getValue());
                    userDataFirebase.setPetAge((String) ds.child("newPetage").getValue());
                    userDataFirebase.setPetGender((String) ds.child("newPetType").getValue());
//                    userDataFirebase.setUserName("" + ds.child("name").getValue().toString());
//                    userDataFirebase.setEmail("" + ds.child("email").getValue().toString());
                    userDataFirebase.setUserId((String) ds.child("uid").getValue());
                    userDataFirebase.setImage((String) ds.child("image").getValue());
                    userPetList.add(userDataFirebase);
                }
                //setup adapter
                petProfileAdapterFirebase = new PetProfileAdapterFirebase(getActivity(),userPetList);
                //set adapter to recyclerview
                //petProfileRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                petProfileRecycler.setAdapter(petProfileAdapterFirebase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}