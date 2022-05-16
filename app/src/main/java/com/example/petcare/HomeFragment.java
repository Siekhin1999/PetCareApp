package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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
        ImageView imgPet = view.findViewById(R.id.img_pet2);
        TextView tvDate = view.findViewById(R.id.tv_date);
        TextView tvEat = view.findViewById(R.id.tv_eat);
        TextView tvWater = view.findViewById(R.id.tv_water);
        TextView tvPark = view.findViewById(R.id.tv_park);

        imgTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TipsFragment.class);
                startActivity(intent);
            }
        });

        imgDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), DiaryFragment.class);
                startActivity(intent2);
            }
        });

        imgTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getActivity(), TrainingFragment.class);
                startActivity(intent3);
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

        //show diary history






        // Inflate the layout for this fragment
        return view;
    }
}