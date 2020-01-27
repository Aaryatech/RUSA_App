package com.ats.rusa_app.activity;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Info;
import com.ats.rusa_app.model.Institute;
import com.ats.rusa_app.model.Reg;
import com.ats.rusa_app.model.University;
import com.ats.rusa_app.util.CommonDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.rusa_app.constants.Constants.authHeader;

public class IndividualRegActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText edName, edEmail, edAlterEmail, edCode, edDesg, edDept, edMobile;
    public Button btnRegister;
    public Spinner spUniversity, spInstitute;
    long dateMillis;
    int yyyy, mm, dd;
    boolean isValidEmail = false, isValidMob = false;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private BroadcastReceiver receiver;
    ArrayList<String> typeNameArray = new ArrayList<>();

    ArrayList<String> uniNameList = new ArrayList<>();
    ArrayList<Integer> uniIdList = new ArrayList<>();

    ArrayList<String> instNameList = new ArrayList<>();
    ArrayList<Institute> instList = new ArrayList<>();
    ArrayList<Integer> instIdList = new ArrayList<>();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_reg);
        setTitle(""+getResources().getString(R.string.app_name));

        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        edAlterEmail = findViewById(R.id.edAlterEmail);
        edCode = findViewById(R.id.edCode);
        edDesg = findViewById(R.id.edDesg);
        edDept = findViewById(R.id.edDept);
        edMobile = findViewById(R.id.edMobile);

        btnRegister = findViewById(R.id.btnRegister);

        spUniversity = findViewById(R.id.spUniversity);
        spInstitute = findViewById(R.id.spInstitute);

        btnRegister.setOnClickListener(this);

        getUniversityList();

        spUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    getInstituteList(uniIdList.get(position));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spInstitute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    edCode.setText("" + instList.get(position - 1).getAisheCode());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {

            String strCode = edCode.getText().toString().trim();
            String strName = edName.getText().toString().trim();
            String strDesg = edDesg.getText().toString().trim();
            String strDept = edDept.getText().toString().trim();
            String strMobile = edMobile.getText().toString().trim();
            String strEmail = edEmail.getText().toString().trim();
            String strAlterEmail = edAlterEmail.getText().toString().trim();

            if (spUniversity.getSelectedItemPosition() == 0) {
                TextView viewUni = (TextView) spUniversity.getSelectedView();
                viewUni.setError("required");

            } else if (spInstitute.getSelectedItemPosition() == 0) {

                TextView viewUni = (TextView) spUniversity.getSelectedView();
                viewUni.setError(null);

                TextView viewInst = (TextView) spInstitute.getSelectedView();
                viewInst.setError("required");
            } else if (strCode.isEmpty()) {

                TextView viewInst = (TextView) spInstitute.getSelectedView();
                viewInst.setError(null);

                edCode.setError("required");
            } else if (strName.isEmpty()) {

                edCode.setError(null);

                edName.setError("required");
            } else if (strDesg.isEmpty()) {

                edName.setError(null);

                edDesg.setError("required");
            } else if (strDept.isEmpty()) {

                edDesg.setError(null);

                edDept.setError("required");
            } else if (strMobile.isEmpty()) {

                edDept.setError(null);

                edMobile.setError("required");
            } else if (strMobile.length() != 10) {

                edDept.setError(null);

                edMobile.setError("required 10 digits");
            } else if (strMobile.isEmpty()) {

                edDept.setError(null);

                edMobile.setError("required");
            } else if (strEmail.isEmpty()) {

                edMobile.setError(null);

                edEmail.setError("required");
            } else if (!isValidEmailAddress(strEmail)) {

                edMobile.setError(null);

                edEmail.setError("invalid email address");
            } else if (!strAlterEmail.isEmpty()) {

                edEmail.setError(null);

                if (!isValidEmailAddress(strAlterEmail)) {
                    edAlterEmail.setError("invalid email address");
                } else {
                    edAlterEmail.setError(null);
                }
            } else {

                String uniqueId = UUID.randomUUID().toString();

                int instId = instIdList.get(spInstitute.getSelectedItemPosition());
                int uniId = uniIdList.get(spUniversity.getSelectedItemPosition());

                Reg registration = new Reg(0, uniqueId, 1, strEmail, strAlterEmail, "0", strName, strCode, String.valueOf(instId), String.valueOf(uniId), strDesg, strDept, strMobile, "", null, "", "", "Android", 0, 1, sdf.format(System.currentTimeMillis()), null, 0, 0, 0, "", "", "", 0, "", 0, 0);
                //Log.e("Registration", "--------------" + registration);
//                    getRegistration(registration);

                getCheckUniqueFieldMobile(strMobile, strEmail, registration);


            }


        }
    }

    private void getUniversityList() {

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(IndividualRegActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<University>> listCall = Constants.myInterface.getUniversityList(authHeader);
            listCall.enqueue(new Callback<ArrayList<University>>() {
                @Override
                public void onResponse(Call<ArrayList<University>> call, Response<ArrayList<University>> response) {
                    try {
                        uniIdList.clear();
                        uniNameList.clear();

                        uniIdList.add(0);
                        uniNameList.add("Select University");

                        instList.clear();
                        instNameList.clear();
                        instIdList.clear();

                        instIdList.add(0);
                        instNameList.add("Select Institute");

                        edCode.setText("");

                        //Log.e("RESPONSE ", "-----------------------------" + response.body());

                        if (response.body() != null) {

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    uniIdList.add(response.body().get(i).getUniId());
                                    uniNameList.add(response.body().get(i).getUniName());
                                }
                            }
                        }

                        ArrayAdapter<String> uniAdapter = new ArrayAdapter<>(IndividualRegActivity.this, R.layout.spinner_item, uniNameList);
                        spUniversity.setAdapter(uniAdapter);

                        ArrayAdapter<String> instAdapter = new ArrayAdapter<>(IndividualRegActivity.this, R.layout.spinner_item, instNameList);
                        spInstitute.setAdapter(instAdapter);

                        commonDialog.dismiss();
                        // }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                       // e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<University>> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                   // t.printStackTrace();

                }
            });
        }

    }

    private void getInstituteList(int uniId) {

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(IndividualRegActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Institute>> listCall = Constants.myInterface.getInstituteList(uniId,authHeader);
            listCall.enqueue(new Callback<ArrayList<Institute>>() {
                @Override
                public void onResponse(Call<ArrayList<Institute>> call, Response<ArrayList<Institute>> response) {
                    try {
                        instList.clear();
                        instNameList.clear();
                        instIdList.clear();

                        instIdList.add(0);
                        instNameList.add("Select Institute");

                        //Log.e("RESPONSE ", "-----------------------------" + response.body());

                        if (response.body() != null) {

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    instList.add(response.body().get(i));
                                    instIdList.add(response.body().get(i).getMhInstId());
                                    instNameList.add(response.body().get(i).getInsName());
                                }
                            }
                        }

                        ArrayAdapter<String> instAdapter = new ArrayAdapter<>(IndividualRegActivity.this, R.layout.spinner_item, instNameList);
                        spInstitute.setAdapter(instAdapter);

                        commonDialog.dismiss();
                        // }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                       // e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Institute>> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                   // t.printStackTrace();

                }
            });
        }

    }


    private void getCheckUniqueFieldMobile(String inputValue, final String email, final Reg reg) {
        //Log.e("PARAMETERS : ", "        INPUT VALUE : " + inputValue + "      VALUE TYPE : " + 1 + "      PRIMARY KEY : " + 0);

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(IndividualRegActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.getCheckUniqueField(inputValue, 1, 0,authHeader);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        //Log.e("RESPONCE UNICE", "-----------------------------" + response.body());

                        if (response.body() == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(IndividualRegActivity.this, R.style.AlertDialogTheme);
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
                                edMobile.setError("Duplicate MOB Number");
                                AlertDialog.Builder builder = new AlertDialog.Builder(IndividualRegActivity.this, R.style.AlertDialogTheme);
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
                                edMobile.setError(null);
                                getCheckUniqueFieldEmail(email, reg);
                            }
                        }
                        commonDialog.dismiss();
                        // }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                       // e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(IndividualRegActivity.this, R.style.AlertDialogTheme);
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
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                  //  t.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(IndividualRegActivity.this, R.style.AlertDialogTheme);
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
        //Log.e("PARAMETERS : ", "        INPUT VALUE : " + inputValue + "      VALUE TYPE : " + 2 + "      PRIMARY KEY : " + 0);

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(IndividualRegActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.getCheckUniqueField(inputValue, 2, 0,authHeader);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        //Log.e("RESPONCE UNICE", "-----------------------------" + response.body());

                        if (response.body() == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(IndividualRegActivity.this, R.style.AlertDialogTheme);
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
                                edEmail.setError("Duplicate Email Address");
                                AlertDialog.Builder builder = new AlertDialog.Builder(IndividualRegActivity.this, R.style.AlertDialogTheme);
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
                                edEmail.setError(null);
                                getRegistration(reg);
                            }
                        }
                        commonDialog.dismiss();
                        // }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                      //  e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(IndividualRegActivity.this, R.style.AlertDialogTheme);
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
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                  //  t.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(IndividualRegActivity.this, R.style.AlertDialogTheme);
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


    private void getRegistration(Reg registration) {

        if (Constants.isOnline(getApplicationContext())) {
            //Log.e("PARAMETER : ", "---------------- REGISTRATION : " + registration);

            final CommonDialog commonDialog = new CommonDialog(IndividualRegActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Reg> listCall = Constants.myInterface.saveRegistration(registration,authHeader);
            listCall.enqueue(new Callback<Reg>() {
                @Override
                public void onResponse(Call<Reg> call, Response<Reg> response) {
                    try {
                        if (response.body() != null) {
                            Reg model = response.body();

                            String smsCode = model.getSmsCode();
                            Gson gson = new Gson();
                            String json = gson.toJson(model);

                            //Log.e("SAVE REGISTRATION", "-----------------------------" + response.body());
                            //Log.e("SAVE REGISTRATION MODEL", "-----------------------------" + model);

                            Intent intent = new Intent(IndividualRegActivity.this, OTPVerificationActivity.class);
                            intent.putExtra("code", smsCode);
                            Bundle args = new Bundle();
                            args.putString("model", json);
                            intent.putExtra("model", json);
                            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----REG------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                      //  e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Reg> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                  //  t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(IndividualRegActivity.this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
