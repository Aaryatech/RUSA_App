package com.ats.rusa_app.activity;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.ats.rusa_app.model.Info;
import com.ats.rusa_app.model.Reg;
import com.ats.rusa_app.util.CommonDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText ed_Name, ed_email, ed_alterEmail, ed_clgName, ed_aisheCode, ed_designationPerson, ed_nameDept, ed_nameAuthePerson, ed_DOB, ed_univercityAff, ed_mobile;
    public Button btn_registration;
    public Spinner spType;
    public TextView tv_signIn;
    String spinnerPosition;
    long dateMillis;
    int yyyy, mm, dd;
    boolean isValidEmail = false, isValidMob = false;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private BroadcastReceiver receiver;
    ArrayList<String> typeNameArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ed_Name = (EditText) findViewById(R.id.ed_name);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_alterEmail = (EditText) findViewById(R.id.ed_alterEmail);
        ed_clgName = (EditText) findViewById(R.id.ed_clgName);
        ed_aisheCode = (EditText) findViewById(R.id.ed_AISHE_code);
        ed_designationPerson = (EditText) findViewById(R.id.ed_designationPerson);
        ed_nameDept = (EditText) findViewById(R.id.ed_nameDept);
        ed_nameAuthePerson = (EditText) findViewById(R.id.ed_nameAuthPerson);
        ed_DOB = (EditText) findViewById(R.id.ed_DOB);
        ed_mobile = (EditText) findViewById(R.id.ed_mobileNo);
        ed_univercityAff = (EditText) findViewById(R.id.ed_univercityAffil);
        spType = (Spinner) findViewById(R.id.spType);
        tv_signIn = (TextView) findViewById(R.id.tv_signIn);
        btn_registration = (Button) findViewById(R.id.btn_registration);

        tv_signIn.setOnClickListener(this);
        btn_registration.setOnClickListener(this);
        ed_DOB.setOnClickListener(this);

        typeNameArray.add("Select Type");
        typeNameArray.add("Individual");
        typeNameArray.add("Colleges");
        typeNameArray.add("University");

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, typeNameArray);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        // android.R.layout.simple_spinner_dropdown_item
        spType.setAdapter(spinnerAdapter);

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition = typeNameArray.get(position);
                if (spinnerPosition.equals("Individual")) {
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

                    ed_Name.setText("");
                    ed_email.setText("");
                    ed_alterEmail.setText("");
                    ed_clgName.setText("");
                    ed_univercityAff.setText("");
                    ed_nameDept.setText("");
                    ed_DOB.setText("");
                    ed_designationPerson.setText("");
                    ed_mobile.setText("");
                    ed_nameAuthePerson.setText("");
                } else if (spinnerPosition.equals("Colleges")) {
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

                    ed_Name.setText("");
                    ed_email.setText("");
                    ed_alterEmail.setText("");
                    ed_designationPerson.setText("");
                    ed_univercityAff.setText("");
                    ed_nameDept.setText("");
                    ed_designationPerson.setText("");
                    ed_aisheCode.setText("");
                    ed_mobile.setText("");
                    ed_nameAuthePerson.setText("");
                } else if (spinnerPosition.equals("University")) {
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_registration) {

            if (spinnerPosition.equals("Individual")) {
                Log.e("SpinnerInd", "-----------------" + spinnerPosition);
                boolean isValidClgName = false, isValidUniverAff = false, isValidName = false, isValidNameDept = false, isValidNameAuthPer = false, isValidDesigPerson = false;
                String email, alt_email, fullName, clgName, univercityAff, nameDept, nameAuthPer, dob, mob, designPerName;
                fullName = ed_Name.getText().toString().trim();
                email = ed_email.getText().toString().trim();
                alt_email = ed_alterEmail.getText().toString().trim();
                clgName = ed_clgName.getText().toString().trim();
                univercityAff = ed_univercityAff.getText().toString().trim();
                nameDept = ed_nameDept.getText().toString().trim();
                nameAuthPer = ed_nameAuthePerson.getText().toString().trim();
                designPerName = ed_designationPerson.getText().toString().trim();
                mob = ed_mobile.getText().toString().trim();

                String uniqueId = UUID.randomUUID().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Log.e("Email", "----------------------" + email);
                Log.e("alt_email", "----------------------" + alt_email);
                Log.e("clgName", "----------------------" + clgName);
                Log.e("univercityAff", "----------------------" + univercityAff);
                Log.e("nameDept", "----------------------" + nameDept);
                Log.e("nameAuthPer", "----------------------" + nameAuthPer);
                Log.e("uniqueId", "----------------------" + uniqueId);


                if (fullName.isEmpty()) {
                    ed_Name.setError("required");
                } else {
                    ed_Name.setError(null);
                    isValidName = true;
                }
                if (email.isEmpty()) {
                    ed_email.setError("required");
                } else if (!isValidEmailAddress(email)) {
                    ed_email.setError("invalid email");
                } else {
                    ed_email.setError(null);
                    isValidEmail = true;
                }
                if (clgName.isEmpty()) {
                    ed_clgName.setError("required");
                } else {
                    ed_clgName.setError(null);
                    isValidClgName = true;
                }
                if (univercityAff.isEmpty()) {
                    ed_univercityAff.setError("required");
                } else {
                    ed_univercityAff.setError(null);
                    isValidUniverAff = true;
                }
                if (nameDept.isEmpty()) {
                    ed_nameDept.setError("required");
                } else {
                    ed_nameDept.setError(null);
                    isValidNameDept = true;
                }
                if (designPerName.isEmpty()) {
                    ed_designationPerson.setError("required");
                } else {
                    ed_designationPerson.setError(null);
                    isValidDesigPerson = true;
                }

                if (mob.isEmpty()) {
                    ed_mobile.setError("required");
                } else if (mob.length() != 10) {
                    ed_mobile.setError("required 10 digits");
                } else if (mob.equalsIgnoreCase("0000000000")) {
                    ed_mobile.setError("invalid number");
                } else {
                    ed_mobile.setError(null);
                    isValidMob = true;
                }

                if (isValidName && isValidEmail && isValidNameDept && isValidClgName && isValidUniverAff && isValidMob && isValidDesigPerson) {
                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

                    Reg registration = new Reg(0, uniqueId, 1, email, alt_email, "0", fullName, "", clgName, univercityAff, designPerName, nameDept, mob, "", null, "", "", "Android", 0, 1, sdf.format(System.currentTimeMillis()), null, 0, 0, 0, "", "", "", 0, "", 0, 0);
                    Log.e("Registration", "--------------" + registration);
//                    getRegistration(registration);

                    getCheckUniqueFieldMobile(mob, email, registration);


                }

            }
            if (spinnerPosition.equals("Colleges")) {
                Log.e("Spinnerclg", "-----------------" + spinnerPosition);
                boolean isValidUniverAff = false, isValidName = false, isValidDesigPerson = false, isValidNameDept = false, isValidNameAuthPer = false;
                String email, alt_email, institudeName, univercityAff, nameDept, nameAuthPer, designPerName, AISHECode, mob;
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
                } else {
                    ed_Name.setError(null);
                    isValidName = true;
                }
                if (email.isEmpty()) {
                    ed_email.setError("required");
                } else if (!isValidEmailAddress(email)) {
                    ed_email.setError("invalid email");
                } else {
                    ed_email.setError(null);
                    isValidEmail = true;
                }


                if (univercityAff.isEmpty()) {
                    ed_univercityAff.setError("required");
                } else {
                    ed_univercityAff.setError(null);
                    isValidUniverAff = true;
                }
                if (designPerName.isEmpty()) {
                    ed_designationPerson.setError("required");
                } else {
                    ed_designationPerson.setError(null);
                    isValidDesigPerson = true;
                }
                if (nameDept.isEmpty()) {
                    ed_nameDept.setError("required");
                } else {
                    ed_nameDept.setError(null);
                    isValidNameDept = true;
                }
                if (nameAuthPer.isEmpty()) {
                    ed_nameAuthePerson.setError("required");
                } else {
                    ed_nameAuthePerson.setError(null);
                    isValidNameAuthPer = true;
                }

                if (mob.isEmpty()) {
                    ed_mobile.setError("required");
                } else if (mob.length() != 10) {
                    ed_mobile.setError("required 10 digits");
                } else if (mob.equalsIgnoreCase("0000000000")) {
                    ed_mobile.setError("invalid number");
                } else {
                    ed_mobile.setError(null);
                    isValidMob = true;
                }

                if (isValidName && isValidEmail && isValidNameDept && isValidNameAuthPer && isValidUniverAff && isValidDesigPerson && isValidMob) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    Reg registration = new Reg(0, uniqueId, 2, email, alt_email, "0", institudeName, AISHECode, "", univercityAff, designPerName, nameDept, mob, nameAuthPer, null, "", "", "Android", 0, 1, sdf.format(System.currentTimeMillis()), null, 0, 0, 0, "", "", "", 0, "", 0, 0);
                    // getRegistration(registration);

                    getCheckUniqueFieldMobile(mob, email, registration);

                }
            }
            if (spinnerPosition.equals("University")) {
                Log.e("SpinnerUni", "-----------------" + spinnerPosition);
                boolean isValidName = false, isValidDesigPerson = false, isValidNameDept = false, isValidNameAuthPer = false;
                String email, alt_email, UnivercityName, nameDept, nameAuthPer, designPerName, AISHECode, mob;
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
                } else {
                    ed_Name.setError(null);
                    isValidName = true;
                }
                if (email.isEmpty()) {
                    ed_email.setError("required");
                } else if (!isValidEmailAddress(email)) {
                    ed_email.setError("invalid email");
                } else {
                    ed_email.setError(null);
                    isValidEmail = true;
                }


                if (designPerName.isEmpty()) {
                    ed_designationPerson.setError("required");
                } else {
                    ed_designationPerson.setError(null);
                    isValidDesigPerson = true;
                }
                if (nameDept.isEmpty()) {
                    ed_nameDept.setError("required");
                } else {
                    ed_nameDept.setError(null);
                    isValidNameDept = true;
                }
                if (nameAuthPer.isEmpty()) {
                    ed_nameAuthePerson.setError("required");
                } else {
                    ed_nameAuthePerson.setError(null);
                    isValidNameAuthPer = true;
                }

                if (mob.isEmpty()) {
                    ed_mobile.setError("required");
                } else if (mob.length() != 10) {
                    ed_mobile.setError("required 10 digits");
                } else if (mob.equalsIgnoreCase("0000000000")) {
                    ed_mobile.setError("invalid number");
                } else {
                    ed_mobile.setError(null);
                    isValidMob = true;
                }

                if (isValidName && isValidEmail && isValidNameDept && isValidNameAuthPer && isValidDesigPerson && isValidMob) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Reg registration = new Reg(0, uniqueId, 3, email, alt_email, "0", UnivercityName, AISHECode, "", "", "", nameDept, mob, nameAuthPer, null, "", "", "Android", 0, 1, sdf.format(System.currentTimeMillis()), null, 0, 0, 0, "", "", "", 0, "", 0, 0);
                    // getRegistration(registration);

                    getCheckUniqueFieldMobile(mob, email, registration);



                }
            }
        } else if (v.getId() == R.id.tv_signIn) {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.ed_DOB) {
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

    private void getCheckUniqueFieldMobile(String inputValue, final String email, final Reg reg) {
        Log.e("PARAMETERS : ", "        INPUT VALUE : " + inputValue + "      VALUE TYPE : " + 1 + "      PRIMARY KEY : " + 0);

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(RegistrationActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.getCheckUniqueField(inputValue, 1, 0);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        Log.e("RESPONCE UNICE", "-----------------------------" + response.body());

                        if (response.body() == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this, R.style.AlertDialogTheme);
                            builder.setTitle("Error");
                            builder.setMessage("something went wrong, please try again later");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            if (response.body().getError()) {
                                ed_mobile.setError("Duplicate MOB Number");
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this, R.style.AlertDialogTheme);
                                builder.setTitle("Caution");
                                builder.setMessage("Mobile number already exists, please enter other number");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                ed_mobile.setError(null);
                                getCheckUniqueFieldEmail(email, reg);
                            }
                        }
                        commonDialog.dismiss();
                        // }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle("Error");
                        builder.setMessage("something went wrong, please try again later");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle("Error");
                    builder.setMessage("something went wrong, please try again later");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }


    private void getCheckUniqueFieldEmail(String inputValue, final Reg reg) {
        Log.e("PARAMETERS : ", "        INPUT VALUE : " + inputValue + "      VALUE TYPE : " + 2 + "      PRIMARY KEY : " + 0);

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(RegistrationActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.getCheckUniqueField(inputValue, 2, 0);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        Log.e("RESPONCE UNICE", "-----------------------------" + response.body());

                        if (response.body() == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this, R.style.AlertDialogTheme);
                            builder.setTitle("Error");
                            builder.setMessage("something went wrong, please try again later");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            if (response.body().getError()) {
                                ed_email.setError("Duplicate Email Address");
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this, R.style.AlertDialogTheme);
                                builder.setTitle("Caution");
                                builder.setMessage("Email address already exists, please enter other email address");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                ed_email.setError(null);
                                getRegistration(reg);
                            }
                        }
                        commonDialog.dismiss();
                        // }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle("Error");
                        builder.setMessage("something went wrong, please try again later");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle("Error");
                    builder.setMessage("something went wrong, please try again later");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
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
                            Reg model = response.body();
                            String smsCode = model.getSmsCode();
                            Gson gson = new Gson();
                            String json = gson.toJson(model);

                            Log.e("SAVE REGISTRATION", "-----------------------------" + response.body());
                            Log.e("SAVE REGISTRATION MODEL", "-----------------------------" + model);
                            Intent intent = new Intent(RegistrationActivity.this, OTPVerificationActivity.class);
                            intent.putExtra("code", smsCode);
                            Bundle args = new Bundle();
                            args.putString("model", json);
                            intent.putExtra("model", json);
                            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----REG------");
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
