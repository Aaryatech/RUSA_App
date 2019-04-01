package com.ats.rusa_app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.UpcomingEvent;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.ats.rusa_app.util.PermissionsUtil;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingEventDetailFragment extends Fragment implements View.OnClickListener  {
UpcomingEvent upcomingEvent;
ImageView imageView;
TextView tv_eventName,tv_eventVenu,tv_eventDate;
Button btn_apply;
Login loginUser;
private static final int RESULT_OK=100;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_upcoming_event_detail, container, false);

        imageView=(ImageView)view.findViewById(R.id.iv_baner);
        tv_eventName=(TextView)view.findViewById(R.id.tvEventName);
        tv_eventVenu=(TextView)view.findViewById(R.id.tvEventVenu);
        tv_eventDate=(TextView)view.findViewById(R.id.tvEventDate);
        btn_apply=(Button)view.findViewById(R.id.btn_apply);

        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
        }

        String upcomingStr = getArguments().getString("model");
        Gson gson = new Gson();
        upcomingEvent = gson.fromJson(upcomingStr, UpcomingEvent.class);
        Log.e("responce","-----------------------"+upcomingEvent);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("LOGIN_ACTIVITY : ", "--------USER-------" + loginUser);

        tv_eventName.setText(upcomingEvent.getHeading());
        tv_eventVenu.setText(""+upcomingEvent.getEventLocation());
        tv_eventDate.setText(""+upcomingEvent.getEventDateFrom());
//        String imageUri =""+upcomingEvent.getFeaturedImage();
//        Log.e("URI", "-----------" + imageUri);
//        Picasso.with(getContext()).load(imageUri).into(imageView);

        return view;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_apply)
        {
            if(loginUser.getIsActive()==1 && loginUser.getDelStatus()==1 && loginUser.getEmailVerified()==1)
            {
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.content_frame, new EventRegistartionFragment(), "HomeFragment");
//                ft.commit();
            }else{
                Toast.makeText(getActivity(), "Not Apply For This Event", Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId()==R.id.btn_upload)
        {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("*/*");
//            startActivityForResult(intent, 7);
            slectImage();
        }
    }

    private void slectImage() {
    }


}
