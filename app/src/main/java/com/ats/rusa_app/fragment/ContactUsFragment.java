package com.ats.rusa_app.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ats.rusa_app.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment implements View.OnClickListener {
    public ImageView iv_showLocation;
    public Button btn_send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contact_us, container, false);

        iv_showLocation=(ImageView) view.findViewById(R.id.iv_showLocation);
        btn_send=(Button)view.findViewById(R.id.btn_send);

        iv_showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude=20.007357;
                double longitude=73.792992;
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onClick(View v) {
//        if(v.getId()==R.id.tv_showLocation)
//        {
//            double latitude=20.007357;
//            double longitude=73.792992;
//            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//            startActivity(intent);
//        }
    }
}
