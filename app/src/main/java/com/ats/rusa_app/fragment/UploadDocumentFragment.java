package com.ats.rusa_app.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.EventDetailListActivity;
import com.ats.rusa_app.activity.IndividualRegActivity;
import com.ats.rusa_app.adapter.DocListAdapter;
import com.ats.rusa_app.adapter.RvGalleryAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.DocTypeList;
import com.ats.rusa_app.model.DocUpload;
import com.ats.rusa_app.model.EventRegistration;
import com.ats.rusa_app.model.Info;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.University;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.ats.rusa_app.util.PermissionsUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.rusa_app.constants.Constants.authHeader;
import static com.ats.rusa_app.fragment.GalleryDisplayFragment.staticPhotosList;

public class UploadDocumentFragment extends Fragment implements View.OnClickListener {

    private EditText edName;
    private Spinner spType;
    private TextView tvBrowse, tvFileName;
    private Button btnUpload;
    private RecyclerView recyclerView;

    public ArrayList<String> docTypeNameList = new ArrayList<>();
    public ArrayList<Integer> docTypeIdList = new ArrayList<>();

    public ArrayList<DocUpload> docList = new ArrayList<>();

    Login loginUser;

    private final int RESULT_OK = -1;
    public static String path, imagePath;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_document, container, false);

        edName = view.findViewById(R.id.edName);
        spType = view.findViewById(R.id.spType);
        tvBrowse = view.findViewById(R.id.tvBrowse);
        tvFileName = view.findViewById(R.id.tvFileName);
        btnUpload = view.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(this);
        tvBrowse.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerView);


        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
        }

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        if (loginUser != null) {
            getUserDocList(loginUser.getRegId());
        }
        getDocTypeList();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvBrowse) {

            showFileChooser();

        } else if (v.getId() == R.id.btnUpload) {

            String name = edName.getText().toString().trim();
            String fileName = tvFileName.getText().toString();

            if (name.isEmpty()) {
                edName.setError("required");

            } else if (spType.getSelectedItemPosition() == 0) {
                edName.setError(null);

                TextView viewType = (TextView) spType.getSelectedView();
                viewType.setError("required");

            } else if (imagePath == null) {

                Toast.makeText(getContext(), "Please select document file to upload", Toast.LENGTH_SHORT).show();

                TextView viewType = (TextView) spType.getSelectedView();
                viewType.setError(null);
            } else {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");

                File imgFile = new File(imagePath);
                int pos = imgFile.getName().lastIndexOf(".");
                String ext = imgFile.getName().substring(pos + 1);
                String docFileName = sdf1.format(System.currentTimeMillis()) + "_" + loginUser.getRegId() + "." + ext;


                int typeId = docTypeIdList.get(spType.getSelectedItemPosition());
                long fileSize = imgFile.length();

                String currDate = sdf.format(Calendar.getInstance().getTimeInMillis());

                DocUpload docUpload = new DocUpload(0, loginUser.getRegId(), name, docFileName, typeId, 1, currDate, fileSize, "");

                sendDocToServer(docFileName, docUpload);

            }

        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        String[] mimetypes = {"application/msword", "application/pdf", "application/vnd.ms-excel", "application/excel", "application/x-excel", "application/x-msexcel"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);//allows to select data and return it
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), 1);
    }


    private void getDocTypeList() {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            docTypeNameList.clear();
            docTypeIdList.clear();

            docTypeIdList.add(0);
            docTypeNameList.add("Select Type");


            Call<ArrayList<DocTypeList>> listCall = Constants.myInterface.getDocTypeList(authHeader);
            listCall.enqueue(new Callback<ArrayList<DocTypeList>>() {
                @Override
                public void onResponse(Call<ArrayList<DocTypeList>> call, Response<ArrayList<DocTypeList>> response) {
                    try {


                        Log.e("RESPONSE ", "-----------------------------" + response.body());

                        if (response.body() != null) {

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    docTypeIdList.add(response.body().get(i).getTypeId());
                                    docTypeNameList.add(response.body().get(i).getTypeName());
                                }
                            }
                        }

                        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, docTypeNameList);
                        spType.setAdapter(typeAdapter);


                        commonDialog.dismiss();
                        // }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<DocTypeList>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        }

    }


    private void getUserDocList(int id) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();


            Call<ArrayList<DocUpload>> listCall = Constants.myInterface.getDocList(id,authHeader);
            listCall.enqueue(new Callback<ArrayList<DocUpload>>() {
                @Override
                public void onResponse(Call<ArrayList<DocUpload>> call, Response<ArrayList<DocUpload>> response) {
                    try {


                        Log.e("RESPONSE ", "-----------------------------" + response.body());

                        docList = response.body();

                        if (docList != null) {

                            DocListAdapter adapter = new DocListAdapter(docList, getContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

                        }
                        commonDialog.dismiss();
                        // }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<DocUpload>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        }

    }


    //--------------------------------------------------------------------

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String realPath;

        Log.e("EVENT DET LIST ACT", "---------------------- onActivityResult " + requestCode + " - " + resultCode);

        if (resultCode == RESULT_OK && requestCode == 1) {
            try {

                if (data == null) {
                    //no data present
                    return;
                }
                Uri selectedFileUri = data.getData();
                Log.e("UriPath", "----------" + selectedFileUri.getPath());

                Log.e("DATA PATH", "---------------------- " + getPath(getActivity(), selectedFileUri));

                imagePath = getPath(getActivity(), selectedFileUri);
                if (getPath(getActivity(), selectedFileUri) == null) {
                    imagePath = "";
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setMessage("Please select other file");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    tvFileName.setText("" + getPath(getActivity(), selectedFileUri));
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("UPLOAD DOC FRG : ", "-----Exception : ------" + e.getMessage());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            System.out.println("getPath() uri: " + uri.toString());
            System.out.println("getPath() uri authority: " + uri.getAuthority());
            System.out.println("getPath() uri path: " + uri.getPath());

            // ExternalStorageProvider
            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                System.out.println("getPath() docId: " + docId + ", split: " + split.length + ", type: " + type);

                // This is for checking Main Memory
                if ("primary".equalsIgnoreCase(type)) {
                    if (split.length > 1) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1] + "/";
                    } else {
                        return Environment.getExternalStorageDirectory() + "/";
                    }
                    // This is for checking SD Card
                } else {
                    return "storage" + "/" + docId.replace(":", "/");
                }

            } else {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                System.out.println("getPath() docId: " + docId + ", split: " + split.length + ", type: " + type);

                // This is for checking Main Memory
                if ("primary".equalsIgnoreCase(type)) {
                    if (split.length > 1) {
                        return Environment.getDataDirectory() + "/" + split[1] + "/";
                    } else {
                        return Environment.getDataDirectory() + "/";
                    }
                    // This is for checking SD Card
                } else {
                    return "storage" + "/" + docId.replace(":", "/");
                }

            }
        }
        return null;
    }


    private void sendDocToServer(final String filename, final DocUpload docUpload) {
        final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
        commonDialog.show();

        File imgFile = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("file"), imgFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imgFile.getName(), requestFile);

        RequestBody imgName = RequestBody.create(MediaType.parse("text/plain"), filename);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "2");

        Call<JSONObject> call = Constants.myInterface.docUpload(body, imgName, type,authHeader);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                commonDialog.dismiss();
                //  addNewNotification(bean);
                imagePath = "";
                Log.e("Response : ", "--" + response.body());

                saveDoc(docUpload);

            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("Error : ", "--" + t.getMessage());
                commonDialog.dismiss();
                t.printStackTrace();
                //Toast.makeText(EventDetailListActivity.this, "Unable To Process", Toast.LENGTH_SHORT).show();
                imagePath = "";
                // getAppliedEvent(eventId, regId, filename);

                Toast.makeText(getContext(), "Unable to upload document!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void saveDoc(DocUpload docUpload) {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<DocUpload> listCall = Constants.myInterface.saveDocument(docUpload,authHeader);
            listCall.enqueue(new Callback<DocUpload>() {
                @Override
                public void onResponse(Call<DocUpload> call, Response<DocUpload> response) {
                    try {
                        if (response.body() == null) {

                            Toast.makeText(getContext(), "Unable to upload file!", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                            Fragment adf = new UploadDocumentFragment();
                            Bundle args = new Bundle();
                            args.putString("slugName", "uploadDoc");
                            adf.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();


                        }
                        commonDialog.dismiss();

                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<DocUpload> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }


}
