package com.ats.rusa_app.fragment;


import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.ContactUs;
import com.ats.rusa_app.util.CommonDialog;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.WIFI_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment implements View.OnClickListener {
    public ImageView iv_showLocation;
    public Button btn_send;
    public RadioGroup rg;
    public EditText ed_name,ed_email,ed_phoneNo,ed_msg;
    public RadioButton rb_query,rb_feedback,rb_meassage,radioButton;
    String selectedtext;
    String ip;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contact_us, container, false);

        iv_showLocation=(ImageView) view.findViewById(R.id.iv_showLocation);
        btn_send=(Button)view.findViewById(R.id.btn_send);
        ed_name=(EditText)view.findViewById(R.id.edName);
        ed_email=(EditText)view.findViewById(R.id.edEmail);
        ed_phoneNo=(EditText)view.findViewById(R.id.edPhoneNo);
        ed_msg=(EditText)view.findViewById(R.id.edMsg);
        rg=(RadioGroup)view.findViewById(R.id.rg);
        rb_query=(RadioButton)view.findViewById(R.id.rbQuery);
        rb_feedback=(RadioButton)view.findViewById(R.id.rbFeedback);
        rb_meassage=(RadioButton)view.findViewById(R.id.rbMsg);

        WifiManager wm = (WifiManager) getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
         ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        Log.e("IP Address","-----------------"+ip);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int idx = group.indexOfChild(radioButton);
                RadioButton r = (RadioButton) group.getChildAt(idx);
                 selectedtext = r.getText().toString();
                Log.e("Log Radio", "----------" + r.getText());
                Log.e("Log Radio1", "----------" + selectedtext);

            }
        });

        iv_showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude=19.109894; //20.007357
                double longitude=72.884028; //73.792992
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        btn_send.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_send)
        {
           String srtName,strEmail,strPhoneNo,strMsg;
            boolean isValidEmail = false,isValidName = false, isValidPhoneNo = false, isValidMsg = false;
           srtName=ed_name.getText().toString();
           strEmail=ed_email.getText().toString();
           strPhoneNo=ed_phoneNo.getText().toString();
           strMsg=ed_msg.getText().toString();

           if (strEmail.isEmpty()) {
                ed_email.setError("required");
            }  else {
                ed_email.setError(null);
                isValidEmail = true;
            }
            if (srtName.isEmpty()) {
                ed_name.setError("required");
            }  else {
                ed_name.setError(null);
                isValidName = true;
            }
            if (strPhoneNo.isEmpty()) {
                ed_phoneNo.setError("required");
            } else if (strPhoneNo.length() != 10) {
                ed_phoneNo.setError("required 10 digits");
            } else if (strPhoneNo.equalsIgnoreCase("0000000000")) {
                ed_phoneNo.setError("invalid number");
            }else {
                ed_phoneNo.setError(null);
                isValidPhoneNo = true;
            }
            if (strMsg.isEmpty()) {
                ed_msg.setError("required");
            }  else {
                ed_msg.setError(null);
                isValidMsg = true;
            }
            if (isValidName && isValidEmail  && isValidPhoneNo && isValidMsg)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                ContactUs contactUs=new ContactUs(0,srtName,strEmail,strPhoneNo,strMsg,"","",sdf.format(System.currentTimeMillis()),1,"",0,1,ip,0,0,selectedtext,"");
                getContact(contactUs);
                ed_name.setText("");
                ed_email.setText("");
                ed_phoneNo.setText("");
                ed_msg.setText("");
            }

        }
    }
    private void getContact(ContactUs contactUs) {
        if (Constants.isOnline(getActivity())) {
            Log.e("PARAMETER : ", "---------------- CONTACT : " + contactUs);

            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ContactUs> listCall = Constants.myInterface.saveContactUs(contactUs);
            listCall.enqueue(new Callback<ContactUs>() {
                @Override
                public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                    try {
                        if (response.body() != null) {

                            ContactUs model=response.body();

                            Log.e("CONTACT US","-----------------------------"+response.body());
                            Log.e("CONTACT US MODEL","-----------------------------"+model);
                            Toast.makeText(getContext(), "Your Record has being save", Toast.LENGTH_SHORT).show();

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ContactUs> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}
