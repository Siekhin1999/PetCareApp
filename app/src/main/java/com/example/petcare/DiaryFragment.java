package com.example.petcare;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.petcare.adapterDogTips.DogTipsAdapterFirebase;
import com.example.petcare.adapterPetDiary.PetDiaryAdapterFirebase;
import com.example.petcare.adapterPetVacination.PetVacinationAdapterFirebase;
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
import java.util.Calendar;

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

    DatabaseReference reference, reference2;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    RecyclerView petDiaryRecycler, petVaciRecycler;
    PetDiaryFirebase petDiaryFirebase;
    ArrayList<PetDiaryFirebase> diaryList;
    ArrayList<PetVaccineFirebase> vaccineList;
    PetDiaryAdapterFirebase petDiaryAdapterFirebase;
    PetVacinationAdapterFirebase petVacinationAdapterFirebase;
    private int notificationId = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        TextView tv_tab_diary = (TextView)view.findViewById(R.id.tv_tab_diary);
        TextView tv_tab_vaci = (TextView)view.findViewById(R.id.tv_tab_vaci);
        TextView tv_tab_remind = (TextView)view.findViewById(R.id.tv_tab_reminder);
        TextView tv_title = (TextView)view.findViewById(R.id.tv_petdiary);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fbtn_adddiary);
        FloatingActionButton fbtn_addvacci = view.findViewById(R.id.fbtn_addvaci);
        RelativeLayout petDiaryView = (RelativeLayout)view.findViewById(R.id.petDiaryView);
        RelativeLayout petVaciView = (RelativeLayout)view.findViewById(R.id.petVaciView);
        RelativeLayout petRemindView = (RelativeLayout)view.findViewById(R.id.petRemindView);
        petDiaryRecycler = (RecyclerView)view.findViewById(R.id.petDiaryRecycler);
        petVaciRecycler = (RecyclerView)view.findViewById(R.id.petVaciRecycler);
        EditText editText = view.findViewById(R.id.et_task);
        TimePicker timePicker = view.findViewById(R.id.tp_settime);
        Button btn_set = (Button) view.findViewById(R.id.btn_set);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Diary").child(fAuth.getUid());
        reference2 = FirebaseDatabase.getInstance().getReference("Vaccine").child(fAuth.getUid());

        //for pet diary recyclerview
        diaryList = new ArrayList<>();
        GetDiaryDataFromFirebase();

        //for pet vaccine recyclerview
        vaccineList = new ArrayList<>();
        GetVaciDataFromFirebase();

        tv_tab_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petDiaryView.setVisibility(View.VISIBLE);
                petVaciView.setVisibility(View.GONE);
                petRemindView.setVisibility(View.GONE);

                tv_tab_diary.setBackgroundResource(R.drawable.shape_rect_2);
                tv_tab_vaci.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                tv_tab_remind.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        tv_tab_vaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petVaciView.setVisibility(View.VISIBLE);
                petDiaryView.setVisibility(View.GONE);
                petRemindView.setVisibility(View.GONE);

                tv_tab_vaci.setBackgroundResource(R.drawable.shape_rect_2);
                tv_tab_diary.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                tv_tab_remind.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        tv_tab_remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petRemindView.setVisibility(View.VISIBLE);
                petDiaryView.setVisibility(View.GONE);
                petVaciView.setVisibility(View.GONE);

                tv_tab_remind.setBackgroundResource(R.drawable.shape_rect_2);
                tv_tab_diary.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                tv_tab_vaci.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Diary3rdPage.class);
                startActivity(intent);
            }
        });

        fbtn_addvacci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),DiaryAddVaccinePage.class);
                startActivity(intent);
            }
        });

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText editText = view.findViewById(R.id.et_task);
                //String task = editText.getText().toString();
                //TimePicker timePicker = view.findViewById(R.id.tp_settime);

                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                intent.putExtra("notificationId", notificationId);
                intent.putExtra("todo", editText.getText().toString());

                PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(),0,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager alarm = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                editText.getText().clear();
                Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT).show();
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

                    diaryList.add(new PetDiaryFirebase(ds.child("petname").getValue().toString(),
                            ds.child("time").getValue().toString(),
                            ds.child("date").getValue().toString(),
                            ds.child("foodIntake").getValue().toString(),
                            ds.child("waterIntake").getValue().toString(),
                            ds.child("outdoor").getValue().toString(),
                            ds.child("health").getValue().toString(),
                            ds.getKey()));
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

    //retrieve vaccine data from database
    private void GetVaciDataFromFirebase() {
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear list at start
                vaccineList.clear();

                for (DataSnapshot ds : snapshot.getChildren()){

                    vaccineList.add(new PetVaccineFirebase(ds.child("petname").getValue().toString(),
                            ds.child("time").getValue().toString(),
                            ds.child("date").getValue().toString(),
                            ds.child("vaccineIntake").getValue().toString(),
                            ds.child("cared").getValue().toString(),
                            ds.child("notes").getValue().toString(),
                            ds.getKey()));
                }

                //setup adapter
                petVacinationAdapterFirebase = new PetVacinationAdapterFirebase(getActivity(),vaccineList);
                //set adapter to recyclerview
                petVaciRecycler.setAdapter(petVacinationAdapterFirebase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //for reminder
    private void setAlarm() {


    }
}