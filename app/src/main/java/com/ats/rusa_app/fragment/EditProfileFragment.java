package com.ats.rusa_app.fragment;


import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.PreviousRecord;
import com.ats.rusa_app.model.Reg;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONObject;

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
    public EditText ed_Name, ed_email, ed_alterEmail, ed_clgName, ed_aisheCode, ed_designationPerson, ed_nameDept, ed_nameAuthePerson, ed_DOB, ed_univercityAff, ed_mobile, ed_type;
    public TextInputLayout tv_Name, tv_email, tv_alterEmail, tv_clgName, tv_aisheCode, tv_designationPerson, tv_nameDept, tv_nameAuthePerson, tv_DOB, tv_univercityAff, tv_mobile,tv_type;
    public Button btn_registration;
    public Spinner spType;
    String spinnerPosition;
    long dateMillis;
    Login loginUser;
    int yyyy, mm, dd;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private BroadcastReceiver receiver;
    ArrayList<String> typeNameArray = new ArrayList<>();
    Reg RegModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ed_Name = (EditText) view.findViewById(R.id.ed_name);
        ed_email = (EditText) view.findViewById(R.id.ed_email);
        ed_alterEmail = (EditText) view.findViewById(R.id.ed_alterEmail);
        ed_clgName = (EditText) view.findViewById(R.id.ed_clgName);
        ed_aisheCode = (EditText) view.findViewById(R.id.ed_AISHE_code);
        ed_designationPerson = (EditText) view.findViewById(R.id.ed_designationPerson);
        ed_nameDept = (EditText) view.findViewById(R.id.ed_nameDept);
        ed_nameAuthePerson = (EditText) view.findViewById(R.id.ed_nameAuthPerson);
        ed_DOB = (EditText) view.findViewById(R.id.ed_DOB);
        ed_mobile = (EditText) view.findViewById(R.id.ed_mobileNo);
        ed_univercityAff = (EditText) view.findViewById(R.id.ed_univercityAffil);
        ed_type = (EditText) view.findViewById(R.id.ed_type);
        btn_registration = (Button) view.findViewById(R.id.btn_registration);


        tv_Name = (TextInputLayout)view.findViewById(R.id.tv_name);
        tv_type = (TextInputLayout)view.findViewById(R.id.tv_type);
        tv_email = (TextInputLayout) view.findViewById(R.id.tv_email);
        tv_alterEmail = (TextInputLayout) view.findViewById(R.id.tv_alterEmail);
        tv_clgName = (TextInputLayout) view.findViewById(R.id.tv_clgName);
        tv_aisheCode = (TextInputLayout) view.findViewById(R.id.tv_AISHE_code);
        tv_designationPerson = (TextInputLayout) view.findViewById(R.id.tv_designationPerson);
        tv_nameDept = (TextInputLayout) view.findViewById(R.id.tv_nameDept);
        tv_nameAuthePerson = (TextInputLayout) view.findViewById(R.id.tv_nameAuthPerson);
        tv_DOB = (TextInputLayout) view.findViewById(R.id.tv_DOB);
        tv_mobile = (TextInputLayout) view.findViewById(R.id.tv_mobileNo);
        tv_univercityAff = (TextInputLayout) view.findViewById(R.id.tv_univercityAffil);

        btn_registration.setOnClickListener(this);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        getRegById(loginUser.getRegId());

        if (loginUser.getUserType() == 1) {
            ed_Name.setVisibility(View.VISIBLE);
            tv_Name.setVisibility(View.VISIBLE);
            ed_Name.setHint(getString(R.string.str_full_name));
            tv_Name.setHint(getString(R.string.str_full_name));
            ed_email.setVisibility(View.VISIBLE);
            tv_email.setVisibility(View.VISIBLE);
            ed_alterEmail.setVisibility(View.VISIBLE);
            tv_alterEmail.setVisibility(View.VISIBLE);
            ed_clgName.setVisibility(View.VISIBLE);
            tv_clgName.setVisibility(View.VISIBLE);
            ed_aisheCode.setVisibility(View.GONE);
            tv_aisheCode.setVisibility(View.GONE);
            ed_designationPerson.setVisibility(View.VISIBLE);
            tv_designationPerson.setVisibility(View.VISIBLE);
            ed_nameDept.setVisibility(View.VISIBLE);
            tv_nameDept.setVisibility(View.VISIBLE);
            ed_nameAuthePerson.setVisibility(View.GONE);
            tv_nameAuthePerson.setVisibility(View.GONE);
            ed_DOB.setVisibility(View.GONE);
            tv_DOB.setVisibility(View.GONE);
            ed_mobile.setVisibility(View.VISIBLE);
            tv_mobile.setVisibility(View.VISIBLE);
            ed_univercityAff.setVisibility(View.VISIBLE);
            tv_univercityAff.setVisibility(View.VISIBLE);

        } else if (loginUser.getUserType() == 2) {
            ed_Name.setVisibility(View.VISIBLE);
            tv_Name.setVisibility(View.VISIBLE);
            ed_Name.setHint(getString(R.string.str_institute_name));
            tv_Name.setHint(getString(R.string.str_institute_name));
            ed_email.setVisibility(View.VISIBLE);
            tv_email.setVisibility(View.VISIBLE);
            ed_alterEmail.setVisibility(View.VISIBLE);
            tv_alterEmail.setVisibility(View.VISIBLE);
            ed_clgName.setVisibility(View.GONE);
            tv_clgName.setVisibility(View.GONE);
            ed_aisheCode.setVisibility(View.VISIBLE);
            tv_aisheCode.setVisibility(View.VISIBLE);
            ed_designationPerson.setVisibility(View.VISIBLE);
            tv_designationPerson.setVisibility(View.VISIBLE);
            ed_nameDept.setVisibility(View.VISIBLE);
            tv_nameDept.setVisibility(View.VISIBLE);
            ed_nameAuthePerson.setVisibility(View.VISIBLE);
            tv_nameAuthePerson.setVisibility(View.VISIBLE);
            ed_DOB.setVisibility(View.GONE);
            tv_DOB.setVisibility(View.GONE);
            ed_mobile.setVisibility(View.VISIBLE);
            tv_mobile.setVisibility(View.VISIBLE);
            ed_univercityAff.setVisibility(View.VISIBLE);
            tv_univercityAff.setVisibility(View.VISIBLE);

        } else if (loginUser.getUserType() == 3) {
            ed_Name.setVisibility(View.VISIBLE);
            tv_Name.setVisibility(View.VISIBLE);
            ed_Name.setHint(getString(R.string.str_university_name));
            tv_Name.setHint(getString(R.string.str_university_name));
            ed_email.setVisibility(View.VISIBLE);
            tv_email.setVisibility(View.VISIBLE);
            ed_alterEmail.setVisibility(View.VISIBLE);
            tv_alterEmail.setVisibility(View.VISIBLE);
            ed_clgName.setVisibility(View.GONE);
            tv_clgName.setVisibility(View.GONE);
            ed_aisheCode.setVisibility(View.VISIBLE);
            tv_aisheCode.setVisibility(View.VISIBLE);
            ed_designationPerson.setVisibility(View.VISIBLE);
            tv_designationPerson.setVisibility(View.VISIBLE);
            ed_nameDept.setVisibility(View.VISIBLE);
            tv_nameDept.setVisibility(View.VISIBLE);
            ed_nameAuthePerson.setVisibility(View.VISIBLE);
            tv_nameAuthePerson.setVisibility(View.VISIBLE);
            ed_DOB.setVisibility(View.GONE);
            tv_DOB.setVisibility(View.GONE);
            ed_mobile.setVisibility(View.VISIBLE);
            tv_mobile.setVisibility(View.VISIBLE);
            ed_univercityAff.setVisibility(View.GONE);
            tv_univercityAff.setVisibility(View.GONE);
        }

        return view;
    }

    private void getRegById(Integer regId) {
        if (Constants.isOnline(getActivity())) {
            Log.e("PARAMETER : ", "---------------- regId : " + regId);

            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Reg> listCall = Constants.myInterface.getRegUserbyRegId(regId);
            listCall.enqueue(new Callback<Reg>() {
                @Override
                public void onResponse(Call<Reg> call, Response<Reg> response) {
                    try {
                        if (response.body() != null) {
                            RegModel = response.body();

                            if(RegModel.getUserType()==1)
                            {
                                ed_type.setText("Individual");
                            }else  if(RegModel.getUserType()==2)
                            {
                                ed_type.setText("Colleges");
                            }else  if(RegModel.getUserType()==3)
                            {
                                ed_type.setText("University");
                            }
                            ed_email.setText(RegModel.getEmails());
                            ed_alterEmail.setText(RegModel.getAlternateEmail());
                            ed_Name.setText(RegModel.getName());
                            ed_aisheCode.setText(RegModel.getAisheCode());
                            ed_clgName.setText(RegModel.getCollegeName());
                            ed_univercityAff.setText(RegModel.getUnversityName());
                            ed_designationPerson.setText(RegModel.getDesignationName());
                            ed_nameDept.setText(RegModel.getDepartmentName());
                            ed_mobile.setText(RegModel.getMobileNumber());
                            ed_nameAuthePerson.setText(RegModel.getAuthorizedPerson());

                            Log.e("Register By id", "-----------------------------" + RegModel);
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
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_registration) {

            //Individual
            if (loginUser.getUserType() == 1) {
                Log.e("SpinnerInd", "-----------------" + loginUser.getUserType());
                boolean isValidEmail = false, isValidClgName = false, isValidUniverAff = false, isValidName = false, isValidNameDept = false, isValidNameAuthPer = false, isDesignationPer = false, isValidDob = false, isValidMob = false;
                String email, alt_email, fullName, clgName, univercityAff, nameDept, nameAuthPer, designationPer, dob, mob;
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
                if (designationPer.isEmpty()) {
                    ed_designationPerson.setError("required");
                } else {
                    ed_designationPerson.setError(null);
                    isDesignationPer = true;
                }
                if (dob.isEmpty()) {
                    ed_DOB.setError("required");
                } else {
                    ed_DOB.setError(null);
                    isValidDob = true;
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

                if (isValidName && isValidEmail && isValidNameDept && isDesignationPer && isValidClgName && isValidUniverAff && isValidMob) {
                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    //Reg registration = new Reg(loginUser.getRegId(), loginUser.getUserUuid(), loginUser.getUserType(), loginUser.getEmails(), alt_email, loginUser.getUserPassword(), fullName, "", clgName, univercityAff, designationPer, nameDept, loginUser.getMobileNumber(), loginUser.getAuthorizedPerson(), null, "", "", loginUser.getRegisterVia(), loginUser.getIsActive(), loginUser.getDelStatus(), loginUser.getAddDate(), sdf.format(System.currentTimeMillis()), loginUser.getEditByUserId(), loginUser.getExInt1(), loginUser.getExInt2(), email, loginUser., "", loginUser.getEmailVerified(), "", loginUser.getSmsVerified(), loginUser.getEditByAdminuserId());
                    //Reg registration = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), RegModel.getUserType(), email, alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), clgName, univercityAff, designationPer, nameDept, mob, RegModel.getAuthorizedPerson(), null, RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), "", RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
                    //Log.e("Registration", "--------------" + registration);

                    getPreviousRecord(loginUser.getRegId(), RegModel);

                    // getRegistration(registration);

                }

            }
            if (loginUser.getUserType() == 2) {//College
                Log.e("Spinnerclg", "-----------------" + loginUser.getUserType());
                boolean isValidEmail = false, isValidUniverAff = false, isValidName = false, isValidDesigPerson = false, isValidNameDept = false, isValidNameAuthPer = false, isValidMob = false;
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

//                    if (!loginUser.getMobileNumber().equals(mob) && !loginUser.getEmails().equals(email)) {
//                        Reg registration = new Reg(loginUser.getRegId(), loginUser.getUserUuid(), loginUser.getUserType(), loginUser.getEmails(), alt_email, loginUser.getUserPassword(), institudeName, AISHECode, "", univercityAff, designPerName, nameDept, loginUser.getMobileNumber(), nameAuthPer, null, "", "", loginUser.getRegisterVia(), loginUser.getIsActive(), loginUser.getDelStatus(), loginUser.getAddDate(), sdf.format(System.currentTimeMillis()), loginUser.getEditByUserId(), loginUser.getExInt1(), loginUser.getExInt2(), email, mob, "", loginUser.getEmailVerified(), "", loginUser.getSmsVerified(), loginUser.getEditByAdminuserId());
//                        getRegistration(registration);
//                    }

                    getPreviousRecord(loginUser.getRegId(), RegModel);

                }
            }
            if (loginUser.getUserType() == 3) {//University
                Log.e("SpinnerUni", "-----------------" + loginUser.getUserType());
                boolean isValidEmail = false, isValidName = false, isValidDesigPerson = false, isValidNameDept = false, isValidNameAuthPer = false, isValidMob = false;
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
//                    Reg registration = new Reg(loginUser.getRegId(), loginUser.getUserUuid(), loginUser.getUserType(), loginUser.getEmails(), loginUser.getAlternateEmail(), loginUser.getUserPassword(), loginUser.getName(), loginUser.getAisheCode(), loginUser.getCollegeName(), loginUser.getUnversityName(), loginUser.getDesignationName(), loginUser.getDepartmentName(), loginUser.getMobileNumber(), loginUser.getAuthorizedPerson(), null, "", "", loginUser.getRegisterVia(), loginUser.getIsActive(), loginUser.getDelStatus(), loginUser.getAddDate(), loginUser.getEditDate(), loginUser.getEditByUserId(), loginUser.getExInt1(), loginUser.getExInt2(), "", "", "", loginUser.getEmailVerified(), "", loginUser.getSmsVerified(), loginUser.getEditByAdminuserId());
//                    getRegistration(registration);

                    getPreviousRecord(loginUser.getRegId(), RegModel);


                }


            }
        }


    }

    private void getPreviousRecord(Integer regId, final Reg registration) {

        Log.e("PARAMETERS : ", "        REG ID : " + regId + "      REGISTARION BIN : " + registration);

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<PreviousRecord> listCall = Constants.myInterface.getPrevRecordByRegId(regId);
            listCall.enqueue(new Callback<PreviousRecord>() {
                @Override
                public void onResponse(Call<PreviousRecord> call, Response<PreviousRecord> response) {
                    try {
                        Log.e("Record", "-----------------------------" + response.body());
                        PreviousRecord previousRecord = response.body();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (response.body() != null) {
                            if (response.body().getError()) {
//                                getRegistration(registration);

                                ObjectMapper objectMapper = new ObjectMapper();
                                String jsonStr = objectMapper.writeValueAsString(registration);
                                Log.e("JSON STRING", "-------------------" + jsonStr);
                                previousRecord.setRecord(jsonStr);

                                PreviousRecord previousRecord1 = new PreviousRecord(previousRecord.getPrevId(), RegModel.getRegId(), previousRecord.getRecord(), sdf.format(System.currentTimeMillis()), previousRecord.getExtraVar1());

                                Log.e("PREV REC : ","-************************************************************- TRUE");
                                getSavePreviousRecord(previousRecord1);

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
                                Log.e("JSON STRING", "-------------------" + regPrevious);

                                String email, alt_email, clgName, univercityAff, nameDept, nameAuthPer, designationPer, mob;
                                email = ed_email.getText().toString().trim();
                                alt_email = ed_alterEmail.getText().toString().trim();
                                clgName = ed_clgName.getText().toString().trim();
                                univercityAff = ed_univercityAff.getText().toString().trim();
                                nameDept = ed_nameDept.getText().toString().trim();
                                nameAuthPer = ed_nameAuthePerson.getText().toString().trim();
                                designationPer = ed_designationPerson.getText().toString().trim();
                                mob = ed_mobile.getText().toString().trim();

                                Log.e("email", "------------------" + email);
                                Log.e("clgName", "------------------" + clgName);
                                Log.e("univercityAff", "------------------" + univercityAff);
                                Log.e("nameDept", "------------------" + nameDept);
                                Log.e("nameAuthPer", "------------------" + nameAuthPer);
                                Log.e("designationPer", "------------------" + designationPer);

                                if (loginUser.getUserType() == 1) {

                                    boolean valChange = false;

                                    if (!RegModel.getCollegeName().equalsIgnoreCase(clgName)) {
                                        regPrevious.setCollegeName(RegModel.getCollegeName());
                                        valChange = true;
                                    } else if (!RegModel.getUnversityName().equalsIgnoreCase(univercityAff)) {
                                        regPrevious.setUnversityName(RegModel.getUnversityName());
                                        valChange = true;
                                    } else if (!RegModel.getDesignationName().equalsIgnoreCase(designationPer)) {
                                        regPrevious.setDesignationName(RegModel.getDesignationName());
                                        valChange = true;
                                    } else if (!RegModel.getDepartmentName().equalsIgnoreCase(nameDept)) {
                                        regPrevious.setDepartmentName(RegModel.getDepartmentName());
                                        valChange = true;
                                    } else if (!RegModel.getMobileNumber().equalsIgnoreCase(mob)) {
                                        regPrevious.setMobileNumber(RegModel.getMobileNumber());
                                        valChange = true;
                                    } else if (!RegModel.getEmails().equalsIgnoreCase(email)) {
                                        regPrevious.setEmails(RegModel.getEmails());
                                        valChange = true;
                                    } else if (!RegModel.getAlternateEmail().equalsIgnoreCase(alt_email)) {
                                        regPrevious.setAlternateEmail(RegModel.getAlternateEmail());
                                        valChange = true;
                                    }

                                    if (valChange) {

                                        ObjectMapper objectMapper = new ObjectMapper();
                                        String jsonStr = objectMapper.writeValueAsString(regPrevious);
                                        Log.e("JSON STRING", "-------regPrevious------------" + jsonStr);
                                        previousRecord.setRecord(jsonStr);

                                        PreviousRecord previousRecord1 = new PreviousRecord(previousRecord.getPrevId(), previousRecord.getRegId(), previousRecord.getRecord(), sdf.format(System.currentTimeMillis()), previousRecord.getExtraVar1());

                                        Log.e("PREV REC : ","-************************************************************- FALSE ----- 1");
                                        getSavePreviousRecord(previousRecord1);


                                    } else {
                                        saveRegData();
                                    }


                                } else if (loginUser.getUserType() == 2) {

                                    boolean valChange = false;

                                    if (!RegModel.getCollegeName().equalsIgnoreCase(clgName)) {
                                        regPrevious.setCollegeName(RegModel.getCollegeName());
                                        valChange = true;
                                    } else if (!RegModel.getUnversityName().equalsIgnoreCase(univercityAff)) {
                                        regPrevious.setUnversityName(RegModel.getUnversityName());
                                        valChange = true;
                                    } else if (!RegModel.getDesignationName().equalsIgnoreCase(designationPer)) {
                                        regPrevious.setDesignationName(RegModel.getDesignationName());
                                        valChange = true;
                                    } else if (!RegModel.getDepartmentName().equalsIgnoreCase(nameDept)) {
                                        regPrevious.setDepartmentName(RegModel.getDepartmentName());
                                        valChange = true;
                                    } else if (!RegModel.getMobileNumber().equalsIgnoreCase(mob)) {
                                        regPrevious.setMobileNumber(RegModel.getMobileNumber());
                                        valChange = true;
                                    } else if (!RegModel.getEmails().equalsIgnoreCase(email)) {
                                        regPrevious.setEmails(RegModel.getEmails());
                                        valChange = true;
                                    } else if (!RegModel.getAlternateEmail().equalsIgnoreCase(alt_email)) {
                                        regPrevious.setAlternateEmail(RegModel.getAlternateEmail());
                                        valChange = true;
                                    } else if (!RegModel.getAuthorizedPerson().equalsIgnoreCase(nameAuthPer)) {
                                        regPrevious.setAuthorizedPerson(RegModel.getAuthorizedPerson());
                                        valChange = true;
                                    }

                                    if (valChange) {

                                        ObjectMapper objectMapper = new ObjectMapper();
                                        String jsonStr = objectMapper.writeValueAsString(regPrevious);
                                        Log.e("JSON STRING", "-------regPrevious------------" + jsonStr);
                                        previousRecord.setRecord(jsonStr);

                                        PreviousRecord previousRecord1 = new PreviousRecord(previousRecord.getPrevId(), previousRecord.getRegId(), previousRecord.getRecord(), sdf.format(System.currentTimeMillis()), previousRecord.getExtraVar1());

                                        Log.e("PREV REC : ","-************************************************************- FALSE ----- 2");
                                        getSavePreviousRecord(previousRecord1);

                                    } else {
                                        saveRegData();
                                    }


                                } else if (loginUser.getUserType() == 3) {

                                    boolean valChange = false;

                                    if (!RegModel.getCollegeName().equalsIgnoreCase(clgName)) {
                                        regPrevious.setCollegeName(RegModel.getCollegeName());
                                        valChange = true;
                                    } else if (!RegModel.getDesignationName().equalsIgnoreCase(designationPer)) {
                                        regPrevious.setDesignationName(RegModel.getDesignationName());
                                        valChange = true;
                                    } else if (!RegModel.getDepartmentName().equalsIgnoreCase(nameDept)) {
                                        regPrevious.setDepartmentName(RegModel.getDepartmentName());
                                        valChange = true;
                                    } else if (!RegModel.getMobileNumber().equalsIgnoreCase(mob)) {
                                        regPrevious.setMobileNumber(RegModel.getMobileNumber());
                                        valChange = true;
                                    } else if (!RegModel.getEmails().equalsIgnoreCase(email)) {
                                        regPrevious.setEmails(RegModel.getEmails());
                                        valChange = true;
                                    } else if (!RegModel.getAlternateEmail().equalsIgnoreCase(alt_email)) {
                                        regPrevious.setAlternateEmail(RegModel.getAlternateEmail());
                                        valChange = true;
                                    } else if (!RegModel.getAuthorizedPerson().equalsIgnoreCase(nameAuthPer)) {
                                        regPrevious.setAuthorizedPerson(RegModel.getAuthorizedPerson());
                                        valChange = true;
                                    }

                                    if (valChange) {

                                        ObjectMapper objectMapper = new ObjectMapper();
                                        String jsonStr = objectMapper.writeValueAsString(regPrevious);
                                        Log.e("JSON STRING", "-------regPrevious------------" + jsonStr);
                                        previousRecord.setRecord(jsonStr);

                                        PreviousRecord previousRecord1 = new PreviousRecord(previousRecord.getPrevId(), previousRecord.getRegId(), previousRecord.getRecord(), sdf.format(System.currentTimeMillis()), previousRecord.getExtraVar1());

                                        Log.e("PREV REC : ","-************************************************************- FALSE ----- 3");
                                        getSavePreviousRecord(previousRecord1);

                                    } else {
                                        saveRegData();
                                    }


                                }

                            }

                            commonDialog.dismiss();

                        }else{
                            commonDialog.dismiss();
                            Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                        commonDialog.dismiss();

                        // }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----RECORD1------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<PreviousRecord> call, Throwable t) {
                    Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "------RECORD-----" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getSavePreviousRecord(PreviousRecord previousRecord) {
        if (Constants.isOnline(getActivity())) {
            Log.e("PARAMETER : ", "---------------- SAVE PREVIOUS : " + previousRecord);

            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<PreviousRecord> listCall = Constants.myInterface.savePreviousRecord(previousRecord);
            listCall.enqueue(new Callback<PreviousRecord>() {
                @Override
                public void onResponse(Call<PreviousRecord> call, Response<PreviousRecord> response) {
                    try {
                        if (response.body() != null) {
                            PreviousRecord model = response.body();
                            Log.e("Save prev Record", "-----------------------------" + model);


                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                            if (loginUser.getUserType() == 1) {
                                String email, alt_email, fullName, clgName, univercityAff, nameDept, nameAuthPer, designationPer, dob, mob;
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

                                Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 1, email, alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), clgName, univercityAff, designationPer, nameDept, mob, RegModel.getAuthorizedPerson(), dob, RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
                                getRegistration(reg);

                            } else if (loginUser.getUserType() == 2) {

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

                                Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 2, email, alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), RegModel.getCollegeName(), univercityAff, designPerName, nameDept, mob, nameAuthPer, RegModel.getDob(), RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
                                getRegistration(reg);


                            } else if (loginUser.getUserType() == 3) {

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

                                Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 3, email, alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), RegModel.getCollegeName(), RegModel.getUnversityName(), designPerName, nameDept, mob, nameAuthPer, RegModel.getDob(), RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
                                getRegistration(reg);

                            }


                            commonDialog.dismiss();

                        } else {
                            Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<PreviousRecord> call, Throwable t) {
                    Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveRegData(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (loginUser.getUserType() == 1) {
            String email, alt_email, fullName, clgName, univercityAff, nameDept, nameAuthPer, designationPer, dob, mob;
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

            Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 1, email, alt_email, RegModel.getUserPassword(), fullName, RegModel.getAisheCode(), clgName, univercityAff, designationPer, nameDept, mob, RegModel.getAuthorizedPerson(), dob, RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
            getRegistration(reg);

        } else if (loginUser.getUserType() == 2) {

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

            Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 2, email, alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), RegModel.getCollegeName(), univercityAff, designPerName, nameDept, mob, nameAuthPer, RegModel.getDob(), RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
            getRegistration(reg);


        } else if (loginUser.getUserType() == 3) {

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

            Reg reg = new Reg(RegModel.getRegId(), RegModel.getUserUuid(), 3, email, alt_email, RegModel.getUserPassword(), RegModel.getName(), RegModel.getAisheCode(), RegModel.getCollegeName(), RegModel.getUnversityName(), designPerName, nameDept, mob, nameAuthPer, RegModel.getDob(), RegModel.getImageName(), RegModel.getTokenId(), RegModel.getRegisterVia(), RegModel.getIsActive(), RegModel.getDelStatus(), RegModel.getAddDate(), sdf.format(System.currentTimeMillis()), RegModel.getEditByUserId(), RegModel.getExInt1(), RegModel.getExInt2(), RegModel.getExVar1(), RegModel.getExVar2(), RegModel.getEmailCode(), RegModel.getEmailVerified(), RegModel.getSmsCode(), RegModel.getSmsVerified(), RegModel.getEditByAdminuserId());
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
            Log.e("PARAMETER : ", "---------------- REGISTRATION : " + registration);

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");

            if (registration.getDob().isEmpty()){
                registration.setDob(null);
            }


            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Reg> listCall = Constants.myInterface.editProfile(registration);
            listCall.enqueue(new Callback<Reg>() {
                @Override
                public void onResponse(Call<Reg> call, Response<Reg> response) {
                    try {
                        if (response.body() != null) {
                           // Reg model = response.body();

                            Log.e("Save Registration", "-----------------------------" + response.body());
                            Toast.makeText(getActivity(), "Updated Successfully ", Toast.LENGTH_SHORT).show();
                            commonDialog.dismiss();

                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                            ft.commit();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Reg> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure1 : ", "------REG-----" + t.getMessage());
                    Toast.makeText(getActivity(), "Unable to process", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }
}
