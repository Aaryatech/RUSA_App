package com.ats.rusa_app.fragment;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.MainActivity;
import com.ats.rusa_app.activity.OTPVerificationActivity;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.Reg;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener {
    public EditText ed_Name,ed_email,ed_alterEmail,ed_clgName,ed_aisheCode,ed_designationPerson,ed_nameDept,ed_nameAuthePerson,ed_DOB,ed_univercityAff,ed_mobile,ed_type;
    public Button btn_registration;
    public Spinner spType;
    String spinnerPosition;
    long dateMillis;
    Login loginUser;
    int yyyy, mm, dd;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private BroadcastReceiver receiver;
    ArrayList<String> typeNameArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ed_Name=(EditText)view.findViewById(R.id.ed_name);
        ed_email=(EditText)view.findViewById(R.id.ed_email);
        ed_alterEmail=(EditText)view.findViewById(R.id.ed_alterEmail);
        ed_clgName=(EditText)view.findViewById(R.id.ed_clgName);
        ed_aisheCode=(EditText)view.findViewById(R.id.ed_AISHE_code);
        ed_designationPerson=(EditText)view.findViewById(R.id.ed_designationPerson);
        ed_nameDept=(EditText)view.findViewById(R.id.ed_nameDept);
        ed_nameAuthePerson=(EditText)view.findViewById(R.id.ed_nameAuthPerson);
        ed_DOB=(EditText)view.findViewById(R.id.ed_DOB);
        ed_mobile=(EditText)view.findViewById(R.id.ed_mobileNo);
        ed_univercityAff=(EditText)view.findViewById(R.id.ed_univercityAffil);
        ed_type=(EditText)view.findViewById(R.id.ed_type);
        btn_registration=(Button)view.findViewById(R.id.btn_registration);

        btn_registration.setOnClickListener(this);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        ed_email.setText(loginUser.getEmails());
        ed_alterEmail.setText(loginUser.getAlternateEmail());
        ed_Name.setText(loginUser.getName());
        ed_aisheCode.setText(loginUser.getAisheCode());
        ed_clgName.setText(loginUser.getCollegeName());
        ed_univercityAff.setText(loginUser.getUnversityName());
        ed_designationPerson.setText(loginUser.getDesignationName());
        ed_nameDept.setText(loginUser.getDepartmentName());
        ed_mobile.setText(loginUser.getMobileNumber());
        ed_nameAuthePerson.setText(loginUser.getAuthorizedPerson());


        if(loginUser.getUserType()==1)
        {
            ed_type.setText("Individual");
            ed_Name.setVisibility(View.VISIBLE);
            ed_Name.setHint(getString(R.string.str_full_name));
            ed_email.setVisibility(View.VISIBLE);
            ed_alterEmail.setVisibility(View.VISIBLE);
            ed_clgName.setVisibility(View.VISIBLE);
            ed_aisheCode.setVisibility(View.GONE);
            ed_designationPerson.setVisibility(View.VISIBLE);
            ed_nameDept.setVisibility(View.VISIBLE);
            ed_nameAuthePerson.setVisibility(View.GONE);
            ed_DOB.setVisibility(View.GONE);
            ed_mobile.setVisibility(View.VISIBLE);
            ed_univercityAff.setVisibility(View.VISIBLE);

        }else if(loginUser.getUserType()==2)
        {
            ed_type.setText("Colleges");
            ed_Name.setVisibility(View.VISIBLE);
            ed_Name.setHint(getString(R.string.str_institute_name));
            ed_email.setVisibility(View.VISIBLE);
            ed_alterEmail.setVisibility(View.VISIBLE);
            ed_clgName.setVisibility(View.GONE);
            ed_aisheCode.setVisibility(View.VISIBLE);
            ed_designationPerson.setVisibility(View.VISIBLE);
            ed_nameDept.setVisibility(View.VISIBLE);
            ed_nameAuthePerson.setVisibility(View.VISIBLE);
            ed_DOB.setVisibility(View.GONE);
            ed_mobile.setVisibility(View.VISIBLE);
            ed_univercityAff.setVisibility(View.VISIBLE);

        }else if(loginUser.getUserType()==3)
        {
            ed_type.setText("University");
            ed_Name.setVisibility(View.VISIBLE);
            ed_Name.setHint(getString(R.string.str_university_name));
            ed_email.setVisibility(View.VISIBLE);
            ed_alterEmail.setVisibility(View.VISIBLE);
            ed_clgName.setVisibility(View.GONE);
            ed_aisheCode.setVisibility(View.VISIBLE);
            ed_designationPerson.setVisibility(View.VISIBLE);
            ed_nameDept.setVisibility(View.VISIBLE);
            ed_nameAuthePerson.setVisibility(View.VISIBLE);
            ed_DOB.setVisibility(View.GONE);
            ed_mobile.setVisibility(View.VISIBLE);
            ed_univercityAff.setVisibility(View.GONE);
        }
       // ed_DOB.setText((Integer) loginUser.getDob());

//        typeNameArray.add("Select Type");
//        typeNameArray.add("Individual");
//        typeNameArray.add("Colleges");
//        typeNameArray.add("University");
//
//        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_item, typeNameArray);
//        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
//        // android.R.layout.simple_spinner_dropdown_item
//        spType.setAdapter(spinnerAdapter);
//
////        int position = spinnerAdapter.getPosition("KY");
////        spType.setSelection(position);
//
//        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                spinnerPosition = typeNameArray.get(position);
//                if(spinnerPosition.equals("Individual"))
//                {
//                    ed_Name.setVisibility(View.VISIBLE);
//                    ed_Name.setHint(getString(R.string.str_full_name));
//                    ed_email.setVisibility(View.VISIBLE);
//                    ed_alterEmail.setVisibility(View.VISIBLE);
//                    ed_clgName.setVisibility(View.VISIBLE);
//                    ed_aisheCode.setVisibility(View.GONE);
//                    ed_designationPerson.setVisibility(View.GONE);
//                    ed_nameDept.setVisibility(View.VISIBLE);
//                    ed_nameAuthePerson.setVisibility(View.VISIBLE);
//                    ed_DOB.setVisibility(View.VISIBLE);
//                    ed_mobile.setVisibility(View.VISIBLE);
//                    ed_univercityAff.setVisibility(View.VISIBLE);
//                }else if(spinnerPosition.equals("Colleges"))
//                {
//                    ed_Name.setVisibility(View.VISIBLE);
//                    ed_Name.setHint(getString(R.string.str_institute_name));
//                    ed_email.setVisibility(View.VISIBLE);
//                    ed_alterEmail.setVisibility(View.VISIBLE);
//                    ed_clgName.setVisibility(View.GONE);
//                    ed_aisheCode.setVisibility(View.VISIBLE);
//                    ed_designationPerson.setVisibility(View.GONE);
//                    ed_nameDept.setVisibility(View.VISIBLE);
//                    ed_nameAuthePerson.setVisibility(View.VISIBLE);
//                    ed_DOB.setVisibility(View.GONE);
//                    ed_mobile.setVisibility(View.VISIBLE);
//                    ed_univercityAff.setVisibility(View.VISIBLE);
//                }else if(spinnerPosition.equals("University"))
//                {
//                    ed_Name.setVisibility(View.VISIBLE);
//                    ed_Name.setHint(getString(R.string.str_university_name));
//                    ed_email.setVisibility(View.VISIBLE);
//                    ed_alterEmail.setVisibility(View.VISIBLE);
//                    ed_clgName.setVisibility(View.GONE);
//                    ed_aisheCode.setVisibility(View.VISIBLE);
//                    ed_designationPerson.setVisibility(View.VISIBLE);
//                    ed_nameDept.setVisibility(View.VISIBLE);
//                    ed_nameAuthePerson.setVisibility(View.VISIBLE);
//                    ed_DOB.setVisibility(View.GONE);
//                    ed_mobile.setVisibility(View.VISIBLE);
//                    ed_univercityAff.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        return view;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_registration) {

            if(loginUser.getUserType()==1)
            {
                Log.e("SpinnerInd","-----------------"+loginUser.getUserType());
                boolean isValidEmail = false, isValidClgName = false,isValidUniverAff = false,isValidName = false,  isValidNameDept = false, isValidNameAuthPer = false, isDesignationPer=false, isValidDob = false,isValidMob=false;
                String email,alt_email,fullName,clgName,univercityAff,nameDept,nameAuthPer,designationPer,dob,mob;
                fullName = ed_Name.getText().toString().trim();
                email = ed_email.getText().toString().trim();
                alt_email = ed_alterEmail.getText().toString().trim();
                clgName = ed_clgName.getText().toString().trim();
                univercityAff = ed_univercityAff.getText().toString().trim();
                nameDept = ed_nameDept.getText().toString().trim();
                nameAuthPer = ed_nameAuthePerson.getText().toString().trim();
                designationPer = ed_designationPerson.getText().toString().trim();
                dob = ed_DOB.getText().toString().trim();
                mob = ed_mobile.getText().toString().trim();

                String uniqueId = UUID.randomUUID().toString();

                Log.e("Email","----------------------"+email);
                Log.e("alt_email","----------------------"+alt_email);
                Log.e("clgName","----------------------"+clgName);
                Log.e("univercityAff","----------------------"+univercityAff);
                Log.e("nameDept","----------------------"+nameDept);
                Log.e("nameAuthPer","----------------------"+nameAuthPer);
                Log.e("uniqueId","----------------------"+uniqueId);

                if (fullName.isEmpty()) {
                    ed_Name.setError("required");
                }  else {
                    ed_Name.setError(null);
                    isValidName = true;
                }

                if (!email.isEmpty()) {
                    if (!isValidEmailAddress(email)) {
                        ed_email.setError("invalid email");
                    } else {
                        ed_email.setError(null);
                        isValidEmail = true;
                    }
                } else {
                    isValidEmail = true;
                }
                if (clgName.isEmpty()) {
                    ed_clgName.setError("required");
                }  else {
                    ed_clgName.setError(null);
                    isValidClgName = true;
                }
                if (univercityAff.isEmpty()) {
                    ed_univercityAff.setError("required");
                }  else {
                    ed_univercityAff.setError(null);
                    isValidUniverAff = true;
                }
                if (nameDept.isEmpty()) {
                    ed_nameDept.setError("required");
                }  else {
                    ed_nameDept.setError(null);
                    isValidNameDept = true;
                }
//                if (nameAuthPer.isEmpty()) {
//                    ed_nameAuthePerson.setError("required");
//                }  else {
//                    ed_nameAuthePerson.setError(null);
//                    isValidNameAuthPer = true;
//                }
                if (designationPer.isEmpty()) {
                    ed_designationPerson.setError("required");
                }  else {
                    ed_designationPerson.setError(null);
                    isDesignationPer = true;
                }
                if (dob.isEmpty()) {
                    ed_DOB.setError("required");
                }  else {
                    ed_DOB.setError(null);
                    isValidDob = true;
                }

                if (mob.isEmpty()) {
                    ed_mobile.setError("required");
                } else if (mob.length() != 10) {
                    ed_mobile.setError("required 10 digits");
                } else if (mob.equalsIgnoreCase("0000000000")) {
                    ed_mobile.setError("invalid number");
                }else {
                    ed_mobile.setError(null);
                    isValidMob = true;
                }

                if (isValidName && isValidEmail  && isValidNameDept && isDesignationPer && isValidClgName && isValidUniverAff  && isValidMob ) {
                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    Reg registration = new Reg(loginUser.getRegId(), loginUser.getUserUuid(), loginUser.getUserType(), loginUser.getEmails(), alt_email, loginUser.getUserPassword(), fullName, "", clgName, univercityAff, designationPer, nameDept, loginUser.getMobileNumber(), loginUser.getAuthorizedPerson(), null, "", "", loginUser.getRegisterVia(), loginUser.getIsActive(), loginUser.getDelStatus(), loginUser.getAddDate(), sdf.format(System.currentTimeMillis()), loginUser.getEditByUserId(), loginUser.getExInt1(), loginUser.getExInt2(), email, mob, "", loginUser.getEmailVerified(), "", loginUser.getSmsVerified(), loginUser.getEditByAdminuserId());
                    Log.e("Registration", "--------------" + registration);
                    getRegistration(registration);

                    ed_Name.setText("");
                    ed_email.setText("");
                    ed_alterEmail.setText("");
                    ed_clgName.setText("");
                    ed_univercityAff.setText("");
                    ed_nameDept.setText("");
                    ed_DOB.setText("");
                    ed_mobile.setText("");
                    ed_nameAuthePerson.setText("");
                }

            }
            if(loginUser.getUserType()==2)
            {
                Log.e("Spinnerclg","-----------------"+loginUser.getUserType());
                boolean isValidEmail = false,isValidUniverAff = false,isValidName = false, isValidDesigPerson = false, isValidNameDept = false, isValidNameAuthPer = false,isValidMob=false;
                String email,alt_email,institudeName,univercityAff,nameDept,nameAuthPer,designPerName,AISHECode,mob;
                institudeName = ed_Name.getText().toString().trim();
                email = ed_email.getText().toString().trim();
                alt_email = ed_alterEmail.getText().toString().trim();
                designPerName = ed_designationPerson.getText().toString().trim();
                univercityAff = ed_univercityAff.getText().toString().trim();
                nameDept = ed_nameDept.getText().toString().trim();
                nameAuthPer = ed_nameAuthePerson.getText().toString().trim();
                AISHECode = ed_aisheCode.getText().toString().trim();
                mob = ed_mobile.getText().toString().trim();
                String uniqueId = UUID.randomUUID().toString();
                if (institudeName.isEmpty()) {
                    ed_Name.setError("required");
                }  else {
                    ed_Name.setError(null);
                    isValidName = true;
                }
//                if (email.isEmpty()) {
//                    ed_email.setError("required");
//                }  else {
//                    ed_email.setError(null);
//                    isValidEmail = true;
//                }
                if (!email.isEmpty()) {
                    if (!isValidEmailAddress(email)) {
                        ed_email.setError("invalid email");
                    } else {
                        ed_email.setError(null);
                        isValidEmail = true;
                    }
                } else {
                    isValidEmail = true;
                }

                if (univercityAff.isEmpty()) {
                    ed_univercityAff.setError("required");
                }  else {
                    ed_univercityAff.setError(null);
                    isValidUniverAff = true;
                }
                if (designPerName.isEmpty()) {
                    ed_designationPerson.setError("required");
                }  else {
                    ed_designationPerson.setError(null);
                    isValidDesigPerson = true;
                }
                if (nameDept.isEmpty()) {
                    ed_nameDept.setError("required");
                }  else {
                    ed_nameDept.setError(null);
                    isValidNameDept = true;
                }
                if (nameAuthPer.isEmpty()) {
                    ed_nameAuthePerson.setError("required");
                }  else {
                    ed_nameAuthePerson.setError(null);
                    isValidNameAuthPer = true;
                }

//                if (mob.isEmpty()) {
//                    ed_mobile.setError("required");
//                }  else {
//                    ed_mobile.setError(null);
//                    isValidMob=true;
//                }

                if (mob.isEmpty()) {
                    ed_mobile.setError("required");
                } else if (mob.length() != 10) {
                    ed_mobile.setError("required 10 digits");
                } else if (mob.equalsIgnoreCase("0000000000")) {
                    ed_mobile.setError("invalid number");
                }else {
                    ed_mobile.setError(null);
                    isValidMob = true;
                }

                if (isValidName && isValidEmail  && isValidNameDept && isValidNameAuthPer  && isValidUniverAff && isValidDesigPerson && isValidMob ) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    if(!loginUser.getMobileNumber().equals(mob) && !loginUser.getEmails().equals(email)) {
                        Reg registration = new Reg(loginUser.getRegId(), loginUser.getUserUuid(), loginUser.getUserType(), loginUser.getEmails(), alt_email, loginUser.getUserPassword(), institudeName, AISHECode, "", univercityAff, designPerName, nameDept, loginUser.getMobileNumber(), nameAuthPer, null, "", "", loginUser.getRegisterVia(), loginUser.getIsActive(), loginUser.getDelStatus(), loginUser.getAddDate(),  sdf.format(System.currentTimeMillis()), loginUser.getEditByUserId(), loginUser.getExInt1(), loginUser.getExInt2(), email, mob, "", loginUser.getEmailVerified(), "", loginUser.getSmsVerified(), loginUser.getEditByAdminuserId());
                        getRegistration(registration);
                    }
                    ed_Name.setText("");
                    ed_email.setText("");
                    ed_alterEmail.setText("");
                    ed_designationPerson.setText("");
                    ed_univercityAff.setText("");
                    ed_nameDept.setText("");
                    ed_aisheCode.setText("");
                    ed_mobile.setText("");
                    ed_nameAuthePerson.setText("");
                }
            }
            if(loginUser.getUserType()==3)
            {
                Log.e("SpinnerUni","-----------------"+loginUser.getUserType());
                boolean isValidEmail = false,isValidName = false, isValidDesigPerson = false, isValidNameDept = false, isValidNameAuthPer = false,isValidMob=false;
                String email,alt_email,UnivercityName,nameDept,nameAuthPer,designPerName,AISHECode,mob;
                UnivercityName = ed_Name.getText().toString().trim();
                email = ed_email.getText().toString().trim();
                alt_email = ed_alterEmail.getText().toString().trim();
                designPerName = ed_designationPerson.getText().toString().trim();
                nameDept = ed_nameDept.getText().toString().trim();
                nameAuthPer = ed_nameAuthePerson.getText().toString().trim();
                AISHECode = ed_aisheCode.getText().toString().trim();
                mob = ed_mobile.getText().toString().trim();
                String uniqueId = UUID.randomUUID().toString();

                if (UnivercityName.isEmpty()) {
                    ed_Name.setError("required");
                }  else {
                    ed_Name.setError(null);
                    isValidName = true;
                }
//                if (email.isEmpty()) {
//                    ed_email.setError("required");
//                }  else {
//                    ed_email.setError(null);
//                    isValidEmail = true;
//                }

                if (!email.isEmpty()) {
                    if (!isValidEmailAddress(email)) {
                        ed_email.setError("invalid email");
                    } else {
                        ed_email.setError(null);
                        isValidEmail = true;
                    }
                } else {
                    isValidEmail = true;
                }

                if (designPerName.isEmpty()) {
                    ed_designationPerson.setError("required");
                }  else {
                    ed_designationPerson.setError(null);
                    isValidDesigPerson = true;
                }
                if (nameDept.isEmpty()) {
                    ed_nameDept.setError("required");
                }  else {
                    ed_nameDept.setError(null);
                    isValidNameDept = true;
                }
                if (nameAuthPer.isEmpty()) {
                    ed_nameAuthePerson.setError("required");
                }  else {
                    ed_nameAuthePerson.setError(null);
                    isValidNameAuthPer = true;
                }
//                if (mob.isEmpty()) {
//                    ed_mobile.setError("required");
//                }  else {
//                    ed_mobile.setError(null);
//                    isValidMob=true;
//                }

                if (mob.isEmpty()) {
                    ed_mobile.setError("required");
                } else if (mob.length() != 10) {
                    ed_mobile.setError("required 10 digits");
                } else if (mob.equalsIgnoreCase("0000000000")) {
                    ed_mobile.setError("invalid number");
                }else {
                    ed_mobile.setError(null);
                    isValidMob = true;
                }

                if (isValidName && isValidEmail  && isValidNameDept && isValidNameAuthPer   && isValidDesigPerson && isValidMob) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Reg registration = new Reg(loginUser.getRegId(), loginUser.getUserUuid(), loginUser.getUserType(),loginUser.getEmails(),alt_email,loginUser.getUserPassword(),UnivercityName,AISHECode,"","","",nameDept,loginUser.getMobileNumber(),nameAuthPer,null,"","",loginUser.getRegisterVia(),loginUser.getIsActive(),loginUser.getDelStatus(),loginUser.getAddDate(), sdf.format(System.currentTimeMillis()),loginUser.getEditByUserId(),loginUser.getExInt1(),loginUser.getExInt2(),email,mob,"",loginUser.getEmailVerified(),"",loginUser.getSmsVerified(),loginUser.getEditByAdminuserId());
                    getRegistration(registration);

                    ed_Name.setText("");
                    ed_email.setText("");
                    ed_alterEmail.setText("");
                    ed_designationPerson.setText("");
                    ed_nameDept.setText("");
                    ed_mobile.setText("");
                    ed_aisheCode.setText("");
                    ed_nameAuthePerson.setText("");
                }
            }
        }


    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
//    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//            yyyy = year;
//            mm = month + 1;
//            dd = dayOfMonth;
//            ed_DOB.setText(dd + "-" + mm + "-" + yyyy);
//            //tvDate.setText(yyyy + "-" + mm + "-" + dd);
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(yyyy, mm - 1, dd);
//            calendar.set(Calendar.MILLISECOND, 0);
//            calendar.set(Calendar.SECOND, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.HOUR, 0);
//            dateMillis = calendar.getTimeInMillis();
//        }
//    };
    private void getRegistration(Reg registration) {

        if (Constants.isOnline(getActivity())) {
            Log.e("PARAMETER : ", "---------------- REGISTRATION : " + registration);

            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Reg> listCall = Constants.myInterface.editProfile(registration);
            listCall.enqueue(new Callback<Reg>() {
                @Override
                public void onResponse(Call<Reg> call, Response<Reg> response) {
                    try {
                        if (response.body() != null) {
                            Reg model=response.body();
                            String smsCode=model.getSmsCode();
                            Gson gson = new Gson();
                            String json = gson.toJson(model);

                            Log.e("Edit Profile","-----------------------------"+response.body());
                            Log.e("Edit Profile","-----------------------------"+model);

                            if(!model.getMobileNumber().equals(model.getExVar2()))
                            {
                                Intent intent1=new Intent(getActivity(), OTPVerificationActivity.class);
                                startActivity(intent1);
                                commonDialog.dismiss();
                            }else {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("code", smsCode);
                                Bundle args = new Bundle();
                                args.putString("model", json);
                                intent.putExtra("model", json);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                commonDialog.dismiss();
                            }

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
                public void onFailure(Call<Reg> call, Throwable t) {
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
