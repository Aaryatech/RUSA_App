package com.ats.rusa_app.activity;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Reg;
import com.ats.rusa_app.util.CommonDialog;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    public EditText ed_Name,ed_email,ed_alterEmail,ed_clgName,ed_aisheCode,ed_designationPerson,ed_nameDept,ed_nameAuthePerson,ed_DOB,ed_univercityAff,ed_mobile;
    public Button btn_registration;
    public Spinner spType;
    public TextView tv_signIn;
    String spinnerPosition;
    long dateMillis;
    int yyyy, mm, dd;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private BroadcastReceiver receiver;
    ArrayList<String> typeNameArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ed_Name=(EditText)findViewById(R.id.ed_name);
        ed_email=(EditText)findViewById(R.id.ed_email);
        ed_alterEmail=(EditText)findViewById(R.id.ed_alterEmail);
        ed_clgName=(EditText)findViewById(R.id.ed_clgName);
        ed_aisheCode=(EditText)findViewById(R.id.ed_AISHE_code);
        ed_designationPerson=(EditText)findViewById(R.id.ed_designationPerson);
        ed_nameDept=(EditText)findViewById(R.id.ed_nameDept);
        ed_nameAuthePerson=(EditText)findViewById(R.id.ed_nameAuthPerson);
        ed_DOB=(EditText)findViewById(R.id.ed_DOB);
        ed_mobile=(EditText)findViewById(R.id.ed_mobileNo);
        ed_univercityAff=(EditText)findViewById(R.id.ed_univercityAffil);
        spType=(Spinner) findViewById(R.id.spType);
        tv_signIn=(TextView)findViewById(R.id.tv_signIn);
        btn_registration=(Button)findViewById(R.id.btn_registration);

        tv_signIn.setOnClickListener(this);
        btn_registration.setOnClickListener(this);
        ed_DOB.setOnClickListener(this);

        typeNameArray.add("Select Type");
        typeNameArray.add("Individual");
        typeNameArray.add("Colleges");
        typeNameArray.add("University");

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item, typeNameArray);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
       // android.R.layout.simple_spinner_dropdown_item
        spType.setAdapter(spinnerAdapter);

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 spinnerPosition = typeNameArray.get(position);
                if(spinnerPosition.equals("Individual"))
                {
                    ed_Name.setVisibility(View.VISIBLE);
                    ed_Name.setHint(getString(R.string.str_full_name));
                    ed_email.setVisibility(View.VISIBLE);
                    ed_alterEmail.setVisibility(View.VISIBLE);
                    ed_clgName.setVisibility(View.VISIBLE);
                    ed_aisheCode.setVisibility(View.GONE);
                    ed_designationPerson.setVisibility(View.GONE);
                    ed_nameDept.setVisibility(View.VISIBLE);
                    ed_nameAuthePerson.setVisibility(View.VISIBLE);
                    ed_DOB.setVisibility(View.VISIBLE);
                    ed_mobile.setVisibility(View.VISIBLE);
                    ed_univercityAff.setVisibility(View.VISIBLE);
                }else if(spinnerPosition.equals("Colleges"))
                {
                    ed_Name.setVisibility(View.VISIBLE);
                    ed_Name.setHint(getString(R.string.str_institute_name));
                    ed_email.setVisibility(View.VISIBLE);
                    ed_alterEmail.setVisibility(View.VISIBLE);
                    ed_clgName.setVisibility(View.GONE);
                    ed_aisheCode.setVisibility(View.VISIBLE);
                    ed_designationPerson.setVisibility(View.GONE);
                    ed_nameDept.setVisibility(View.VISIBLE);
                    ed_nameAuthePerson.setVisibility(View.VISIBLE);
                    ed_DOB.setVisibility(View.GONE);
                    ed_mobile.setVisibility(View.VISIBLE);
                    ed_univercityAff.setVisibility(View.VISIBLE);
                }else if(spinnerPosition.equals("University"))
                {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_registration) {

            if(spinnerPosition.equals("Individual"))
                {
                    Log.e("SpinnerInd","-----------------"+spinnerPosition);
                    boolean isValidEmail = false, isValidClgName = false,isValidUniverAff = false,isValidName = false,  isValidNameDept = false, isValidNameAuthPer = false, isValidDob = false,isValidMob=false;
                    String email,alt_email,fullName,clgName,univercityAff,nameDept,nameAuthPer,dob,mob;
                    fullName = ed_Name.getText().toString().trim();
                    email = ed_email.getText().toString().trim();
                    alt_email = ed_alterEmail.getText().toString().trim();
                    clgName = ed_clgName.getText().toString().trim();
                    univercityAff = ed_univercityAff.getText().toString().trim();
                    nameDept = ed_nameDept.getText().toString().trim();
                    nameAuthPer = ed_nameAuthePerson.getText().toString().trim();
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
//                    if (email.isEmpty()) {
//                        ed_email.setError("required");
//                    }  else {
//                        ed_email.setError(null);
//                        isValidEmail = true;
//                    }
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
                    if (nameAuthPer.isEmpty()) {
                        ed_nameAuthePerson.setError("required");
                    }  else {
                        ed_nameAuthePerson.setError(null);
                        isValidNameAuthPer = true;
                    }
                    if (dob.isEmpty()) {
                        ed_DOB.setError("required");
                    }  else {
                        ed_DOB.setError(null);
                        isValidDob = true;
                    }
                    if (mob.isEmpty()) {
                        ed_mobile.setError("required");
                    }  else {
                        ed_mobile.setError(null);
                        isValidMob = true;

                    }

                    if (isValidName && isValidEmail  && isValidNameDept && isValidNameAuthPer && isValidClgName && isValidUniverAff && isValidDob && isValidMob ) {
                        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

                        Date ToDOB = null;
                        try {
                            ToDOB = formatter1.parse(dob);//catch exception
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        String DOB = null;
                        try {
                            DOB = formatter2.format(ToDOB);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                        Reg registration = new Reg(0, uniqueId, 1, email, alt_email, "", fullName, "", clgName, univercityAff, "", nameDept, mob, nameAuthPer, DOB, "", "", "Android", 1, 1, sdf.format(System.currentTimeMillis()), sdf.format(System.currentTimeMillis()), 0, 0, 0, "", "", "", 0, "", 0, 0);
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
                if(spinnerPosition.equals("Colleges"))
                {
                    Log.e("Spinnerclg","-----------------"+spinnerPosition);
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
//                    if (email.isEmpty()) {
//                        ed_email.setError("required");
//                    }  else {
//                        ed_email.setError(null);
//                        isValidEmail = true;
//                    }
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
                    if (mob.isEmpty()) {
                        ed_mobile.setError("required");
                    }  else {
                        ed_mobile.setError(null);
                        isValidMob=true;
                    }
                    if (isValidName && isValidEmail  && isValidNameDept && isValidNameAuthPer  && isValidUniverAff && isValidDesigPerson && isValidMob ) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    Reg registration = new Reg(0, uniqueId, 2,email,alt_email,"",institudeName,AISHECode,"",univercityAff,"",nameDept,mob,nameAuthPer,null,"","","Android",1,1,sdf.format(System.currentTimeMillis()),null,0,0,0,"","","",0,"",0,0);
                        getRegistration(registration);

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
                if(spinnerPosition.equals("University"))
                {
                    Log.e("SpinnerUni","-----------------"+spinnerPosition);
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
//                    if (email.isEmpty()) {
//                        ed_email.setError("required");
//                    }  else {
//                        ed_email.setError(null);
//                        isValidEmail = true;
//                    }

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
                    if (mob.isEmpty()) {
                        ed_mobile.setError("required");
                    }  else {
                        ed_mobile.setError(null);
                        isValidMob=true;
                    }
                    if (isValidName && isValidEmail  && isValidNameDept && isValidNameAuthPer   && isValidDesigPerson && isValidMob) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Reg registration = new Reg(0, uniqueId, 3,email,alt_email,"",UnivercityName,AISHECode,"","","",nameDept,mob,nameAuthPer,null,"","","Android",1,1,sdf.format(System.currentTimeMillis()),null,0,0,0,"","","",0,"",0,0);
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
        }else if(v.getId()==R.id.tv_signIn)
        {
            Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.ed_DOB)
        {
                int yr, mn, dy;
                if (dateMillis > 0) {
                    Calendar purchaseCal = Calendar.getInstance();
                    purchaseCal.setTimeInMillis(dateMillis);
                    yr = purchaseCal.get(Calendar.YEAR);
                    mn = purchaseCal.get(Calendar.MONTH);
                    dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                } else {
                    Calendar purchaseCal = Calendar.getInstance();
                    yr = purchaseCal.get(Calendar.YEAR);
                    mn = purchaseCal.get(Calendar.MONTH);
                    dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
                }
                DatePickerDialog dialog = new DatePickerDialog(RegistrationActivity.this, dateListener, yr, mn, dy);
                dialog.show();

            }
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            ed_DOB.setText(dd + "-" + mm + "-" + yyyy);
            //tvDate.setText(yyyy + "-" + mm + "-" + dd);

            Calendar calendar = Calendar.getInstance();
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            dateMillis = calendar.getTimeInMillis();
        }
    };
    private void getRegistration(Reg registration) {

        if (Constants.isOnline(getApplicationContext())) {
            Log.e("PARAMETER : ", "---------------- REGISTRATION : " + registration);

            final CommonDialog commonDialog = new CommonDialog(RegistrationActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Reg> listCall = Constants.myInterface.saveRegistration(registration);
            listCall.enqueue(new Callback<Reg>() {
                @Override
                public void onResponse(Call<Reg> call, Response<Reg> response) {
                    try {
                        if (response.body() != null) {
                            Reg model=response.body();
                            String smsCode=model.getSmsCode();
                            Gson gson = new Gson();
                            String json = gson.toJson(model);

                            Log.e("SAVE REGISTRATION","-----------------------------"+response.body());
                            Log.e("SAVE REGISTRATION MODEL","-----------------------------"+model);
                            Intent intent=new Intent(RegistrationActivity.this,OTPVerificationActivity.class);
                            intent.putExtra("code", smsCode);
                            Bundle args = new Bundle();
                            args.putString("model", json);
                            intent.putExtra("model", json);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
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
                public void onFailure(Call<Reg> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }


}
