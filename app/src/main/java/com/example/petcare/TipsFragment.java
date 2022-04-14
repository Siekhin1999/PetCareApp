package com.example.petcare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tips, container, false);

        TextView tv_tab_dog = (TextView)view.findViewById(R.id.tv_tab_dog);
        TextView tv_tab_cat = (TextView)view.findViewById(R.id.tv_tab_cat);
        RelativeLayout dogtipsView = (RelativeLayout)view.findViewById(R.id.dogtipsView);
        RelativeLayout cattipsView = (RelativeLayout)view.findViewById(R.id.cattipsView);
        RecyclerView dogtipsRecycler = (RecyclerView)view.findViewById(R.id.dogtipsRecycler);
        RecyclerView cattipsRecycler = (RecyclerView)view.findViewById(R.id.cattipsRecycler);



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
}