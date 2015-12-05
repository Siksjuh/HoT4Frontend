package com.example.kakatin.kakatinhelmet;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class TestFragment extends Fragment {

    private Button testButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        testButton = (Button) view.findViewById(R.id.notification_button);
        testButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v){
                String tittle = "Nakki";
                String subject = "Munakas";
                String body = "jeejee";
                NotificationManager notif = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify = new Notification(R.drawable.notification_template_icon_bg, tittle, System.currentTimeMillis());
                PendingIntent pending = PendingIntent.getActivity(getActivity().getApplicationContext(), 0, new Intent(),0);

                notify.setLatestEventInfo(getActivity().getApplicationContext(),subject,body,pending);
                notif.notify(0,notify);
            }
        });
        return view;
    }

    public static TestFragment newInstance(){
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }




}
