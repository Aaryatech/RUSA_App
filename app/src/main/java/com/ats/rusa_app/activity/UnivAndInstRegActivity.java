package com.ats.rusa_app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Info;
import com.ats.rusa_app.model.Institute;
import com.ats.rusa_app.model.Reg;
import com.ats.rusa_app.util.CommonDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.rusa_app.constants.Constants.authHeader;

public class UnivAndInstRegActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText edName, edEmail, edAlterEmail, edCode, edDesg, edDept, edMobile, edUniName, edInstName;
    public Button btnRegister;
    public Spinner spUniversity, spInstitute;
    public ImageView ivSearch;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    int userType = 0, isReg = 0, instId = 0, uniId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_univ_and_inst_reg);

        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        edAlterEmail = findViewById(R.id.edAlterEmail);
        edCode = findViewById(R.id.edCode);
        edDesg = findViewById(R.id.edDesg);
        edDept = findViewById(R.id.edDept);
        edMobile = findViewById(R.id.edMobile);
        ivSearch = findViewById(R.id.ivSearch);

        edUniName = findViewById(R.id.edUniName);
        edInstName = findViewById(R.id.edInstName);

        btnRegister = findViewById(R.id.btnRegister);

        spUniversity = findViewById(R.id.spUniversity);
        spInstitute = findViewById(R.id.spInstitute);

        btnRegister.setOnClickListener(this);
        ivSearch.setOnClickListener(this);

        userType = getIntent().getIntExtra("type", 2);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivSearch) {

            String strCode = edCode.getText().toString().trim();

            if (strCode.isEmpty()) {

                edCode.setError("required");

            } else {

                edCode.setError(null);
                getDataByAisheCode(strCode);

            }


        } else if (v.getId() == R.id.btnRegister) {

            int flag=0;
            String strCode = edCode.getText().toString().trim();
            String strName = edName.getText().toString().trim();
            String strDesg = edDesg.getText().toString().trim();
            String strDept = edDept.getText().toString().trim();
            String strMobile = edMobile.getText().toString().trim();
            String strEmail = edEmail.getText().toString().trim();
            String strAlterEmail = edAlterEmail.getText().toString().trim();
            String strUni = edUniName.getText().toString().trim();
            String strInst = edInstName.getText().toString().trim();

            if (strCode.isEmpty()) {

                edCode.setError("required");

            } else if (strUni.isEmpty()) {

                edCode.setError(null);
                edUniName.setError("required");

            } else if (strInst.isEmpty()) {

                edUniName.setError(null);
                edInstName.setError("required");

            } else if (strName.isEmpty()) {

                edInstName.setError(null);
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
                    flag = 0;
                }
            } else {
                    edAlterEmail.setError(null);
                    flag=1;

                    edCode.setError(null);
                    edUniName.setError(null);
                    edInstName.setError(null);
                    edName.setError(null);
                    edDept.setError(null);
                    edDesg.setError(null);
                    edMobile.setError(null);
                    edEmail.setError(null);
                    edAlterEmail.setError(null);

                    String uniqueId = UUID.randomUUID().toString();

                    Log.e("Success","-----------------------------------------------");

                    Reg registration = new Reg(0, uniqueId, userType, strEmail, strAlterEmail, "0", strInst, strCode, String.valueOf(instId), String.valueOf(uniId), strDesg, strDept, strMobile, "", null, "", "", "Android", 0, 1, sdf.format(System.currentTimeMillis()), null, 0, 0, 0, "", "", "", 0, "", 0, 0);
                    //Log.e("Registration", "--------------" + registration);
//                    getRegistration(registration);

                    if (isReg == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle("Alert");
                        builder.setMessage("Institute Registered!");
                        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Log.e("Success","-------------------11111111111111----------------------------");
                         getDataByAisheCodeReg(strCode,strMobile,strEmail,registration);
                        //getCheckUniqueFieldMobile(strMobile, strEmail, registration);
                    }

                }
           // }
//            else {

//                edCode.setError(null);
//                edUniName.setError(null);
//                edInstName.setError(null);
//                edName.setError(null);
//                edDept.setError(null);
//                edDesg.setError(null);
//                edMobile.setError(null);
//                edEmail.setError(null);
//                edAlterEmail.setError(null);
//
//                String uniqueId = UUID.randomUUID().toString();
//
//                Log.e("Success","-----------------------------------------------");
//
//                Reg registration = new Reg(0, uniqueId, userType, strEmail, strAlterEmail, "0", strInst, strCode, String.valueOf(instId), String.valueOf(uniId), strDesg, strDept, strMobile, "", null, "", "", "Android", 0, 1, sdf.format(System.currentTimeMillis()), null, 0, 0, 0, "", "", "", 0, "", 0, 0);
//                //Log.e("Registration", "--------------" + registration);
////                    getRegistration(registration);
//
//                if (isReg == 1) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
//                    builder.setTitle("Alert");
//                    builder.setMessage("Institute Registered!");
//                    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                } else {
//                    Log.e("Success","-------------------11111111111111----------------------------");
//                  //  getDataByAisheCodeReg(strCode,strMobile,strEmail,registration);
//                    getCheckUniqueFieldMobile(strMobile, strEmail, registration);
//                }


//            }


        }
    }

    private void getDataByAisheCode(String code) {

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(UnivAndInstRegActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Institute> listCall = Constants.myInterface.getInstituteInfoByCode(code,authHeader);
            listCall.enqueue(new Callback<Institute>() {
                @Override
                public void onResponse(Call<Institute> call, Response<Institute> response) {
                    try {

                        //Log.e("RESPONSE ", "-----------------------------" + response.body());

                        if (response.body() != null) {

                            if(response.body().getInsName()==null){
                                edInstName.setText("");
                            }else{
                                edInstName.setText("" + response.body().getInsName());
                            }

                            if(response.body().getUniName()==null){
                                edUniName.setText("");
                            }else{
                                edUniName.setText("" + response.body().getUniName());
                            }

                            isReg = response.body().getYesNo();
                            instId = response.body().getMhInstId();
                            uniId = response.body().getAffUniversity();

                            if (response.body().getYesNo() == 1) {
                                edCode.setError("Institute Registered");
                            } else {
                                edCode.setError(null);
                            }
                        }


                        commonDialog.dismiss();
                        // }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                       // e.printStackTrace();
                        edUniName.setText("");
                        edInstName.setText("");

                        AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle("Alert");
                        builder.setMessage("Invalid AISHE Code");
                        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
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
                public void onFailure(Call<Institute> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                   // t.printStackTrace();
                    edUniName.setText("");
                    edInstName.setText("");

                    AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle("Alert");
                    builder.setMessage("Invalid AISHE Code");
                    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        }

    }


    private void getDataByAisheCodeReg(String code, final String inputValue, final String email,final Reg reg) {

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(UnivAndInstRegActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Institute> listCall = Constants.myInterface.getInstituteInfoByCode(code,authHeader);
            listCall.enqueue(new Callback<Institute>() {
                @Override
                public void onResponse(Call<Institute> call, Response<Institute> response) {
                    try {

                        Log.e("RESPONSE ", "-----------------------------" + response.body());

                        if (response.body() != null) {

                            if (response.body().getMhInstId() > 0) {
                                if (response.body().getInsName() == null) {
                                    edInstName.setText("");
                                } else {
                                    edInstName.setText("" + response.body().getInsName());
                                }

                                if (response.body().getUniName() == null) {
                                    edUniName.setText("");
                                } else {
                                    edUniName.setText("" + response.body().getUniName());
                                }

                                isReg = response.body().getYesNo();
                                instId = response.body().getMhInstId();
                                uniId = response.body().getAffUniversity();

                                if (response.body().getYesNo() == 1) {
                                    edCode.setError("Institute Registered");
                                } else {
                                    edCode.setError(null);
                                }

                                getCheckUniqueFieldMobile(inputValue, email, reg);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
                                builder.setTitle("Alert");
                                builder.setMessage("Invalid AISHE Code");
                                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }


                        commonDialog.dismiss();
                        // }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                        // e.printStackTrace();
                        edUniName.setText("");
                        edInstName.setText("");

                        AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle("Alert");
                        builder.setMessage("Invalid AISHE Code");
                        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
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
                public void onFailure(Call<Institute> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                    // t.printStackTrace();
                    edUniName.setText("");
                    edInstName.setText("");

                    AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle("Alert");
                    builder.setMessage("Invalid AISHE Code");
                    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        }

    }




    private void getCheckUniqueFieldMobile(String inputValue, final String email, final Reg reg) {
        //Log.e("PARAMETERS : ", "        INPUT VALUE : " + inputValue + "      VALUE TYPE : " + 1 + "      PRIMARY KEY : " + 0);

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(UnivAndInstRegActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.getCheckUniqueField(inputValue, 1, 0,authHeader);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        Log.e("RESPONCE UNICE", "-----------------------------" + response.body());

                        if (response.body() == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
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
                   // t.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
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
            final CommonDialog commonDialog = new CommonDialog(UnivAndInstRegActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.getCheckUniqueField(inputValue, 2, 0,authHeader);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        Log.e("RESPONCE UNICE", "--------EMAIL---------------------" + response.body());

                        if (response.body() == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
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
                       // e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
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
                   // t.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(UnivAndInstRegActivity.this, R.style.AlertDialogTheme);
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
            Log.e("PARAMETER : ", "---------------- REGISTRATION : " + registration);

            final CommonDialog commonDialog = new CommonDialog(UnivAndInstRegActivity.this, "Loading", "Please Wait...");
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

                            Intent intent = new Intent(UnivAndInstRegActivity.this, OTPVerificationActivity.class);
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
                       // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Reg> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                   // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(UnivAndInstRegActivity.this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
