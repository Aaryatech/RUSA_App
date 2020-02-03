package com.ats.rusa_app.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.NewReg;
import com.ats.rusa_app.model.PreviousRecord;
import com.ats.rusa_app.model.Reg;
import com.ats.rusa_app.sqlite.DatabaseHandler;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.rusa_app.constants.Constants.authHeader;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener {
    public EditText edName, edInst, edUni, edCode, edDesg, edDept, edMobile, edEmail, edAlterEmail, edAuthName;
    public Button btn_registration;
    public TextInputLayout tilName, tilAuthName;
    Login loginUser;
    public TextView tvHead;
    Reg RegModel;
    DatabaseHandler dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        edName = view.findViewById(R.id.edName);
        edInst = view.findViewById(R.id.edInst);
        edUni = view.findViewById(R.id.edUni);
        edCode = view.findViewById(R.id.edCode);
        edDesg = view.findViewById(R.id.edDesg);
        edDept = view.findViewById(R.id.edDept);
        edMobile = view.findViewById(R.id.edMobile);
        edEmail = view.findViewById(R.id.edEmail);
        edAlterEmail = view.findViewById(R.id.edAlterEmail);
        btn_registration = view.findViewById(R.id.btn_registration);
        edAuthName = view.findViewById(R.id.edAuthName);
        tvHead = view.findViewById(R.id.tvHead);

        tilAuthName = view.findViewById(R.id.tilAuthName);
        tilName = view.findViewById(R.id.tilName);

        dbHelper=new DatabaseHandler(getActivity());

        btn_registration.setOnClickListener(this);

//        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
//        Gson gson = new Gson();
//        loginUser = gson.fromJson(userStr, Login.class);


        try {
            loginUser = dbHelper.getLoginData();
            //Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        }catch (Exception e)
        {
            //e.printStackTrace();
        }

        //Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);


        if (loginUser != null) {

            getRegDetailById(loginUser.getRegId());
            getRegById(loginUser.getRegId());

            if (loginUser.getUserType() == 1) {
                tilAuthName.setVisibility(View.GONE);
                tilName.setVisibility(View.VISIBLE);
            } else {
                tilAuthName.setVisibility(View.VISIBLE);
                tilName.setVisibility(View.GONE);
            }

            edName.setText("" + loginUser.getName());
            edCode.setText("" + loginUser.getAisheCode());
            edUni.setText("" + loginUser.getUnversityName());
            edInst.setText("" + loginUser.getCollegeName());
            edEmail.setText("" + loginUser.getEmails());
            edDesg.setText("" + loginUser.getDesignationName());
            edDept.setText("" + loginUser.getDepartmentName());
            edMobile.setText("" + loginUser.getMobileNumber());
            edAlterEmail.setText("" + loginUser.getAlternateEmail());
            edAuthName.setText("" + loginUser.getAuthorizedPerson());


        }

        return view;
    }

    private void getRegDetailById(Integer regId) {
        if (Constants.isOnline(getActivity())) {
            //Log.e("PARAMETER : ", "---------------- regId : " + regId);

            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;


            Call<NewReg> listCall = Constants.myInterface.getRegUserDetailbyRegId(regId,token, authHeader);
            listCall.enqueue(new Callback<NewReg>() {
                @Override
                public void onResponse(Call<NewReg> call, Response<NewReg> response) {
                    try {
                        if (response.body() != null) {

                            if(!response.body().getError()) {
                                NewReg newRegModel = response.body();

                                if (newRegModel.getUserType() == 1) {
                                    tvHead.setText("Edit Profile (Individual)");
                                    tilAuthName.setVisibility(View.GONE);
                                    tilName.setVisibility(View.VISIBLE);
                                } else {

                                    if (newRegModel.getUserType() == 2) {
                                        tvHead.setText("Edit Profile (Institute)");
                                    } else if (newRegModel.getUserType() == 1) {
                                        tvHead.setText("Edit Profile (University)");
                                    }
                                    tilAuthName.setVisibility(View.VISIBLE);
                                    tilName.setVisibility(View.GONE);
                                }

                                edName.setText("" + newRegModel.getName());
                                edCode.setText("" + newRegModel.getAisheCode());
                                edUni.setText("" + newRegModel.getUniName());
                                edInst.setText("" + newRegModel.getInstName());
                                edEmail.setText("" + newRegModel.getEmails());
                                edDesg.setText("" + newRegModel.getDesignationName());
                                edDept.setText("" + newRegModel.getDepartmentName());
                                edMobile.setText("" + newRegModel.getMobileNumber());
                                edAlterEmail.setText("" + newRegModel.getAlternateEmail());
                                edAuthName.setText("" + newRegModel.getAuthorizedPerson());

                                //Log.e("Register By id", "-----------------------------" + newRegModel);
                            }else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setTitle("Alert");
                                builder.setMessage("" + response.body().getMsg());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                        // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<NewReg> call, Throwable t) {
                    commonDialog.dismiss();
                    ////Log.e("onFailure : ", "-----------" + t.getMessage());
                    // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getRegById(Integer regId) {
        if (Constants.isOnline(getActivity())) {
            //Log.e("PARAMETER : ", "---------------- regId : " + regId);

            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;

            Call<Reg> listCall = Constants.myInterface.getRegUserbyRegId(regId,token, authHeader);
            listCall.enqueue(new Callback<Reg>() {
                @Override
                public void onResponse(Call<Reg> call, Response<Reg> response) {
                    try {
                        if (response.body() != null) {

                            if(!response.body().getError()) {
                                RegModel = response.body();
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setTitle("Alert");
                                builder.setMessage("" + response.body().getMsg());
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }

                            //Log.e("Register By id", "-----------------------------" + RegModel);
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----------");
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
                    // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_registration) {

            //Individual
            if (loginUser.getUserType() == 1) {
                //Log.e("SpinnerInd", "-----------------" + loginUser.getUserType());
                boolean isValidAlterEmail = false, isValidNameDept = false, isDesignationPer = false, isValidMob = false;
                String alt_email, nameDept, designationPer, mob;
                alt_email = edAlterEmail.getText().toString().trim();
                nameDept = edDept.getText().toString().trim();
                designationPer = edDesg.getText().toString().trim();
                mob = edMobile.getText().toString().trim();

                if (designationPer.isEmpty()) {
                    edDesg.setError("required");
                } else {
                    edDesg.setError(null);
                    isDesignationPer = true;
                }

                if (nameDept.isEmpty()) {
                    edDept.setError("required");
                } else {
                    edDept.setError(null);
                    isValidNameDept = true;
                }

                if (mob.isEmpty()) {
                    edMobile.setError("required");
                } else if (mob.length() != 10) {
                    edMobile.setError("required 10 digits");
                } else if (mob.equalsIgnoreCase("0000000000")) {
                    edMobile.setError("invalid number");
                } else {
                    edMobile.setError(null);
                    isValidMob = true;
                }

                if (!alt_email.isEmpty()) {
                    if (!isValidEmailAddress(alt_email)) {
                        isValidAlterEmail = false;
                        edAlterEmail.setError("invalid Email ID");
                    } else {
                        edAlterEmail.setError(null);
                        isValidAlterEmail = true;
                    }

                } else {
                    edAlterEmail.setError(null);
                    isValidAlterEmail = true;
                }

                if (isValidNameDept && isDesignationPer && isValidMob && isValidAlterEmail) {

                    getPreviousRecord(loginUser.getRegId(), RegModel);

                }

            }
            if (loginUser.getUserType() == 2) {//Institute

                boolean isValidDesigPerson = false, isValidNameDept = false, isValidNameAuthPer = false, isValidMob = false, isValidAltEmail = false;
                String nameDept, nameAuthPer, designPerName, altEmail, strMobile;

                designPerName = edDesg.getText().toString().trim();
                nameDept = edDept.getText().toString().trim();
                nameAuthPer = edAuthName.getText().toString().trim();
                strMobile = edMobile.getText().toString().trim();
                altEmail = edAlterEmail.getText().toString().trim();

                if (nameAuthPer.isEmpty()) {
                    edAuthName.setError("required");
                } else {
                    edAuthName.setError(null);
                    isValidNameAuthPer = true;
                }

                if (designPerName.isEmpty()) {
                    edDesg.setError("required");
                } else {
                    edDesg.setError(null);
                    isValidDesigPerson = true;
                }

                if (nameDept.isEmpty()) {
                    edDept.setError("required");
                } else {
                    edDept.setError(null);
                    isValidNameDept = true;
                }

                if (strMobile.isEmpty()) {
                    edMobile.setError("required");
                } else if (strMobile.length() != 10) {
                    edMobile.setError("required 10 digits");
                } else if (strMobile.equalsIgnoreCase("0000000000")) {
                    edMobile.setError("invalid number");
                } else {
                    edMobile.setError(null);
                    isValidMob = true;
                }

                if (!altEmail.isEmpty()) {
                    if (!isValidEmailAddress(altEmail)) {
                        isValidAltEmail = false;
                        edAlterEmail.setError("invalid Email ID");
                    } else {
                        edAlterEmail.setError(null);
                        isValidAltEmail = true;
                    }

                } else {
                    edAlterEmail.setError(null);
                    isValidAltEmail = true;
                }

                if (isValidNameDept && isValidNameAuthPer && isValidDesigPerson && isValidMob && isValidAltEmail) {

                    getPreviousRecord(loginUser.getRegId(), RegModel);

                }
            }
            if (loginUser.getUserType() == 3) {//University

                boolean isValidDesigPerson = false, isValidNameDept = false, isValidNameAuthPer = false, isValidMob = false, isValidAltEmail = false;
                String nameDept, nameAuthPer, designPerName, altEmail, strMobile;

                designPerName = edDesg.getText().toString().trim();
                nameDept = edDept.getText().toString().trim();
                nameAuthPer = edAuthName.getText().toString().trim();
                strMobile = edMobile.getText().toString().trim();
                altEmail = edAlterEmail.getText().toString().trim();

                if (nameAuthPer.isEmpty()) {
                    edAuthName.setError("required");
                } else {
                    edAuthName.setError(null);
                    isValidNameAuthPer = true;
                }

                if (designPerName.isEmpty()) {
                    edDesg.setError("required");
                } else {
                    edDesg.setError(null);
                    isValidDesigPerson = true;
                }

                if (nameDept.isEmpty()) {
                    edDept.setError("required");
                } else {
                    edDept.setError(null);
                    isValidNameDept = true;
                }

                if (strMobile.isEmpty()) {
                    edMobile.setError("required");
                } else if (strMobile.length() != 10) {
                    edMobile.setError("required 10 digits");
                } else if (strMobile.equalsIgnoreCase("0000000000")) {
                    edMobile.setError("invalid number");
                } else {
                    edMobile.setError(null);
                    isValidMob = true;
                }

                if (!altEmail.isEmpty()) {
                    if (!isValidEmailAddress(altEmail)) {
                        isValidAltEmail = false;
                        edAlterEmail.setError("invalid Email ID");
                    } else {
                        edAlterEmail.setError(null);
                        isValidAltEmail = true;
                    }

                } else {
                    edAlterEmail.setError(null);
                    isValidAltEmail = true;
                }

                if (isValidNameDept && isValidNameAuthPer && isValidDesigPerson && isValidMob && isValidAltEmail) {

                    getPreviousRecord(loginUser.getRegId(), RegModel);

                }


            }
        }


    }

    private void getPreviousRecord(Integer regId, final Reg registration) {

        //Log.e("PARAMETERS : ", "        REG ID : " + regId + "      REGISTARION BIN : " + registration);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;

           // Log.e("PARAMETERS : ", "        REG ID : " + regId + " TOKEN : " + token + "        REGISTARION BIN : " + registration);
//            ParameterModel model=new ParameterModel();
//            model.setRegId(regId);

            Call<PreviousRecord> listCall = Constants.myInterface.getPrevRecordByRegId(regId, token, authHeader);
            listCall.enqueue(new Callback<PreviousRecord>() {
                @Override
                public void onResponse(Call<PreviousRecord> call, Response<PreviousRecord> response) {
                    try {
                       // Log.e("Record", "-------xxxx----------------------" + response.body());
                        PreviousRecord previousRecord = response.body();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (response.body() != null) {
                            if (response.body().getError()) {
//                                getRegistration(registration);

                                ObjectMapper objectMapper = new ObjectMapper();
                                String jsonStr = objectMapper.writeValueAsString(registration);
                                //Log.e("JSON STRING", "-------------------" + jsonStr);
                                previousRecord.setRecord(jsonStr);

                                PreviousRecord previousRecord1 = new PreviousRecord(previousRecord.getPrevId(), RegModel.getRegId(), previousRecord.getRecord(), sdf.format(System.currentTimeMillis()), previousRecord.getExtraVar1());

                                //Log.e("PREV REC : ", "-************************************************************- TRUE");
                                // getSavePreviousRecord(previousRecord1);

                                if (response.body().getMessage().equalsIgnoreCase("unauthorized User")) {

                                    // Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                    builder.setTitle("Alert");
                                    builder.setMessage("" + response.body().getMessage());
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                } else {
                                    getSavePreviousRecord(previousRecord1);
                                }

                            } else {

                                JSONObject jsonObject = new JSONObject(response.body().getRecord().toString());
                                int regId = jsonObject.getInt("regId");
                                String userUuid = jsonObject.getString("userUuid");
                                int userType = jsonObject.getInt("userType");
                                String emails = jsonObject.getString("emails");
                                String alternateEmail = jsonObject.getString("alternateEmail");
                                String userPassword = jsonObject.getString("userPassword");
                                String name = jsonObject.getString("name");
                                String aisheCode = jsonObject.getString("aisheCode");
                                String collegeName = jsonObject.getString("collegeName");
                                String unversityName = jsonObject.getString("unversityName");
                                String designationName = jsonObject.getString("designationName");
                                String departmentName = jsonObject.getString("departmentName");
                                String mobileNumber = jsonObject.getString("mobileNumber");
                                String authorizedPerson = jsonObject.getString("authorizedPerson");
                                String dob = jsonObject.getString("dob");
                                String imageName = jsonObject.getString("imageName");
                                String tokenId = jsonObject.getString("tokenId");
                                String registerVia = jsonObject.getString("registerVia");
                                int isActive = jsonObject.getInt("isActive");
                                int delStatus = jsonObject.getInt("delStatus");
                                String addDate = jsonObject.getString("addDate");
                                String editDate = jsonObject.getString("editDate");
                                int editByUserId = jsonObject.getInt("editByUserId");
                                int exInt1 = jsonObject.getInt("exInt1");
                                int exInt2 = jsonObject.getInt("exInt2");
                                String exVar1 = jsonObject.getString("exVar1");
                                String exVar2 = jsonObject.getString("exVar2");
                                String emailCode = jsonObject.getString("emailCode");
                                int emailVerified = jsonObject.getInt("emailVerified");
                                String smsCode = jsonObject.getString("smsCode");
                                int smsVerified = jsonObject.getInt("smsVerified");
                                int editByAdminuserId = jsonObject.getInt("editByAdminuserId");
//                                String msg=jsonObject.getString("msg");
//                                String error=jsonObject.getString("error");

                                Reg regPrevious = new Reg(regId, userUuid, userType, emails, alternateEmail, userPassword, name, aisheCode, collegeName, unversityName, designationName, departmentName, mobileNumber, authorizedPerson, dob, imageName, tokenId, registerVia, isActive, delStatus, addDate, editDate, editByUserId, exInt1, exInt2, exVar1, exVar2, emailCode, emailVerified, smsCode, smsVerified, editByAdminuserId);
                                //Log.e("JSON STRING", "-------------------" + regPrevious);

                                String email, alt_email, clgName, univercityAff, nameDept, nameAuthPer, designationPer, mob;
                                email = edEmail.getText().toString().trim();
                                alt_email = edAlterEmail.getText().toString().trim();
                                clgName = edInst.getText().toString().trim();
                                univercityAff = edUni.getText().toString().trim();
                                nameDept = edDept.getText().toString().trim();
                                nameAuthPer = edAuthName.getText().toString().trim();
                                designationPer = edDesg.getText().toString().trim();
                                mob = edMobile.getText().toString().trim();

                                //Log.e("email", "------------------" + email);
                                //Log.e("clgName", "------------------" + clgName);
                                //Log.e("univercityAff", "------------------" + univercityAff);
                                //Log.e("nameDept", "------------------" + nameDept);
                                //Log.e("nameAuthPer", "------------------" + nameAuthPer);
                                //Log.e("designationPer", "------------------" + designationPer);

                                if (loginUser.getUserType() == 1) {

                                    boolean valChange = false;

                                    if (!RegModel.getDesignationName().equalsIgnoreCase(designationPer)) {
                                        regPrevious.setDesignationName(RegModel.getDesignationName());
                                        valChange = true;
                                    } else if (!RegModel.getDepartmentName().equalsIgnoreCase(nameDept)) {
                                        regPrevious.setDepartmentName(RegModel.getDepartmentName());
                                        valChange = true;
                                    } else if (!RegModel.getMobileNumber().equalsIgnoreCase(mob)) {
                                        regPrevious.setMobileNumber(RegModel.getMobileNumber());
                                        valChange = true;
                                    } else if (!RegModel.getAlternateEmail().equalsIgnoreCase(alt_email)) {
                                        regPrevious.setAlternateEmail(RegModel.getAlternateEmail());
                                        valChange = true;
                                    }

                                    if (valChange) {

                                        ObjectMapper objectMapper = new ObjectMapper();
                                        String jsonStr = objectMapper.writeValueAsString(regPrevious);
                                        //Log.e("JSON STRING", "-------regPrevious------------" + jsonStr);
                                        previousRecord.setRecord(jsonStr);

                                        PreviousRecord previousRecord1 = new PreviousRecord(previousRecord.getPrevId(), previousRecord.getRegId(), previousRecord.getRecord(), sdf.format(System.currentTimeMillis()), previousRecord.getExtraVar1());

                                        //Log.e("PREV REC : ", "-************************************************************- FALSE ----- 1");
                                        getSavePreviousRecord(previousRecord1);


                                    } else {
                                        saveRegData();
                                    }


                                } else if (loginUser.getUserType() == 2) {

                                    boolean valChange = false;

                                    if (!RegModel.getDesignationName().equalsIgnoreCase(designationPer)) {
                                        regPrevious.setDesignationName(RegModel.getDesignationName());
                                        valChange = true;
                                    } else if (!RegModel.getDepartmentName().equalsIgnoreCase(nameDept)) {
                                        regPrevious.setDepartmentName(RegModel.getDepartmentName());
                                        valChange = true;
                                    } else if (!RegModel.getMobileNumber().equalsIgnoreCase(mob)) {
                                        regPrevious.setMobileNumber(RegModel.getMobileNumber());
                                        valChange = true;
                                    } else if (!RegModel.getAlternateEmail().equalsIgnoreCase(alt_email)) {
                                        regPrevious.setAlternateEmail(RegModel.getAlternateEmail());
                                        valChange = true;
                                    }

                                    if (valChange) {

                                        ObjectMapper objectMapper = new ObjectMapper();
                                        String jsonStr = objectMapper.writeValueAsString(regPrevious);
                                        //Log.e("JSON STRING", "-------regPrevious------------" + jsonStr);
                                        previousRecord.setRecord(jsonStr);

                                        PreviousRecord previousRecord1 = new PreviousRecord(previousRecord.getPrevId(), previousRecord.getRegId(), previousRecord.getRecord(), sdf.format(System.currentTimeMillis()), previousRecord.getExtraVar1());

                                        //Log.e("PREV REC : ", "-************************************************************- FALSE ----- 2");
                                        getSavePreviousRecord(previousRecord1);

                                    } else {
                                        saveRegData();
                                    }


                                } else if (loginUser.getUserType() == 3) {

                                    boolean valChange = false;

                                    if (!RegModel.getDesignationName().equalsIgnoreCase(designationPer)) {
                                        regPrevious.setDesignationName(RegModel.getDesignationName());
                                        valChange = true;
                                    } else if (!RegModel.getDepartmentName().equalsIgnoreCase(nameDept)) {
                                        regPrevious.setDepartmentName(RegModel.getDepartmentName());
                                        valChange = true;
                                    } else if (!RegModel.getMobileNumber().equalsIgnoreCase(mob)) {
                                        regPrevious.setMobileNumber(RegModel.getMobileNumber());
                                        valChange = true;
                                    } else if (!RegModel.getAlternateEmail().equalsIgnoreCase(alt_email)) {
                                        regPrevious.setAlternateEmail(RegModel.getAlternateEmail());
                                        valChange = true;
                                    }

                                    if (valChange) {

                                        ObjectMapper objectMapper = new ObjectMapper();
                                        String jsonStr = objectMapper.writeValueAsString(regPrevious);
                                        //Log.e("JSON STRING", "-------regPrevious------------" + jsonStr);
                                        previousRecord.setRecord(jsonStr);

                                        PreviousRecord previousRecord1 = new PreviousRecord(previousRecord.getPrevId(), previousRecord.getRegId(), previousRecord.getRecord(), sdf.format(System.currentTimeMillis()), previousRecord.getExtraVar1());

                                        //Log.e("PREV REC : ", "-************************************************************- FALSE ----- 3");
                                        getSavePreviousRecord(previousRecord1);

                                    } else {
                                        saveRegData();
                                    }


                                }

                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                        commonDialog.dismiss();

                        // }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----RECORD1------" + e.getMessage());
                        // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<PreviousRecord> call, Throwable t) {
                    Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "------RECORD-----" + t.getMessage());
                    // t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getSavePreviousRecord(PreviousRecord previousRecord) {
        if (Constants.isOnline(getActivity())) {
            //Log.e("PARAMETER : ", "---------------- SAVE PREVIOUS : " + previousRecord);

            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;

            Call<PreviousRecord> listCall = Constants.myInterface.savePreviousRecord(previousRecord,token, authHeader);
            listCall.enqueue(new Callback<PreviousRecord>() {
                @Override
                public void onResponse(Call<PreviousRecord> call, Response<PreviousRecord> response) {
                    try {
                        if (response.body() != null) {
                            PreviousRecord model = response.body();
                            //Log.e("Save prev Record", "-----------------------------" + model);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                            if (loginUser.getUserType() == 1) {
                                String alt_email, nameDept, designationPer, mob;

                                alt_email = edAlterEmail.getText().toString().trim();
                                nameDept = edDept.getText().toString().trim();
                                designationPer = edDesg.getText().toString().trim();
                                mob = edMobile.getText().toString().trim();


                                Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 1, RegModel.getEmails(), alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), RegModel.getCollegeName(), RegModel.getUnversityName(), designationPer, nameDept, mob, RegModel.getAuthorizedPerson(), RegModel.getDob(), RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
                                getRegistration(reg);

                            } else if (loginUser.getUserType() == 2) {

                                String alt_email, nameDept, nameAuthPer, designPerName, mob;


                                alt_email = edAlterEmail.getText().toString().trim();
                                designPerName = edDesg.getText().toString().trim();
                                nameDept = edDept.getText().toString().trim();
                                nameAuthPer = edAuthName.getText().toString().trim();
                                mob = edMobile.getText().toString().trim();

                                Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 2, RegModel.getEmails(), alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), RegModel.getCollegeName(), RegModel.getUnversityName(), designPerName, nameDept, mob, nameAuthPer, RegModel.getDob(), RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
                                getRegistration(reg);


                            } else if (loginUser.getUserType() == 3) {

                                String alt_email, nameDept, nameAuthPer, designPerName, mob;


                                alt_email = edAlterEmail.getText().toString().trim();
                                designPerName = edDesg.getText().toString().trim();
                                nameDept = edDept.getText().toString().trim();
                                nameAuthPer = edAuthName.getText().toString().trim();
                                mob = edMobile.getText().toString().trim();

                                Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 3, RegModel.getEmails(), alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), RegModel.getCollegeName(), RegModel.getUnversityName(), designPerName, nameDept, mob, nameAuthPer, RegModel.getDob(), RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
                                getRegistration(reg);

                            }


                            commonDialog.dismiss();

                        } else {
                            Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                        // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<PreviousRecord> call, Throwable t) {
                    Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                    // t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveRegData() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (loginUser.getUserType() == 1) {

            String alt_email, nameDept, designationPer, mob;

            alt_email = edAlterEmail.getText().toString().trim();
            nameDept = edDept.getText().toString().trim();
            designationPer = edDesg.getText().toString().trim();
            mob = edMobile.getText().toString().trim();

            Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 1, RegModel.getEmails(), alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), RegModel.getCollegeName(), RegModel.getUnversityName(), designationPer, nameDept, mob, RegModel.getAuthorizedPerson(), RegModel.getDob(), RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
            getRegistration(reg);

        } else if (loginUser.getUserType() == 2) {

            String alt_email, nameDept, nameAuthPer, designPerName, mob;

            alt_email = edAlterEmail.getText().toString().trim();
            designPerName = edDesg.getText().toString().trim();
            nameDept = edDept.getText().toString().trim();
            nameAuthPer = edAuthName.getText().toString().trim();
            mob = edMobile.getText().toString().trim();

            Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 2, RegModel.getEmails(), alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), RegModel.getCollegeName(), RegModel.getUnversityName(), designPerName, nameDept, mob, nameAuthPer, RegModel.getDob(), RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
            getRegistration(reg);


        } else if (loginUser.getUserType() == 3) {

            String alt_email, nameDept, nameAuthPer, designPerName, mob;

            alt_email = edAlterEmail.getText().toString().trim();
            designPerName = edDesg.getText().toString().trim();
            nameDept = edDept.getText().toString().trim();
            nameAuthPer = edAuthName.getText().toString().trim();
            mob = edMobile.getText().toString().trim();

            Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 3, RegModel.getEmails(), alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), RegModel.getCollegeName(), RegModel.getUnversityName(), designPerName, nameDept, mob, nameAuthPer, RegModel.getDob(), RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
            getRegistration(reg);

        }

    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private void getRegistration(Reg registration) {

        if (Constants.isOnline(getActivity())) {
            //Log.e("PARAMETER : ", "---------------- REGISTRATION : " + registration);

            if (registration.getDob() != null) {
                //if (registration.getDob().isEmpty()) {
                registration.setDob(null);
                //}
            }

            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;

            Call<Reg> listCall = Constants.myInterface.editProfile(registration, token,authHeader);
            listCall.enqueue(new Callback<Reg>() {
                @Override
                public void onResponse(Call<Reg> call, Response<Reg> response) {
                    try {
                        if (response.body() != null) {
                            // Reg model = response.body();

                            //Log.e("Save Registration", "-----------------------------" + response.body());
                            Toast.makeText(getActivity(), "Updated Successfully ", Toast.LENGTH_SHORT).show();
                            commonDialog.dismiss();

                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                            ft.commit();

                        } else {
                            commonDialog.dismiss();
                            //Log.e("Data Null : ", "-----------");
                            Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Reg> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure1 : ", "------REG-----" + t.getMessage());
                    Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                    //  t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }
}
