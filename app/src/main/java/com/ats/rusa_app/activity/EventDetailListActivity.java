package com.ats.rusa_app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.BuildConfig;
import com.ats.rusa_app.R;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.EventRegCheck;
import com.ats.rusa_app.model.EventRegistration;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.UpcomingEvent;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.ats.rusa_app.util.FilePath;
import com.ats.rusa_app.util.PermissionsUtil;
import com.ats.rusa_app.util.RealPathUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class EventDetailListActivity extends AppCompatActivity implements View.OnClickListener {
    UpcomingEvent upcomingEvent;
    ImageView imageView;
    TextView tv_eventName, tv_eventVenu, tv_eventDate, tv_uploadText, btn_upload;
    Button btn_apply;
    Login loginUser;
    private String selectedImagePath;
    private final int ACTIVITY_CAMERA = 0;
    private final int RESULT_OK = -1;
    private final int ACTIVITY_CHOOSE_FILE = 1;
    Uri URI = null;
    private static final int PICK_FROM_GALLERY = 101;
    private static final int PICK_FILE_REQUEST = 1;
    int columnIndex;
    String attachmentFile;
    private String selectedFilePath;
    private static int RESULT_LOAD_IMAGE = 1;

    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "rusa_folder");
    File f;

    public static String path, imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail_list);

        imageView = (ImageView) findViewById(R.id.iv_baner);
        tv_eventName = (TextView) findViewById(R.id.tvEventName);
        tv_eventVenu = (TextView) findViewById(R.id.tvEventVenu);
        tv_eventDate = (TextView) findViewById(R.id.tvEventDate);
        btn_apply = (Button) findViewById(R.id.btn_apply);
        btn_upload = findViewById(R.id.btn_upload);
        tv_uploadText = (TextView) findViewById(R.id.tv_uploadText);

        btn_apply.setOnClickListener(this);
        btn_upload.setOnClickListener(this);

        if (PermissionsUtil.checkAndRequestPermissions(EventDetailListActivity.this)) {
        }

        // createFolder();

        String upcomingStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        upcomingEvent = gson.fromJson(upcomingStr, UpcomingEvent.class);
        Log.e("responce", "-----------------------" + upcomingEvent);

        String userStr = CustomSharedPreference.getString(EventDetailListActivity.this, CustomSharedPreference.KEY_USER);
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("LOGIN_ACTIVITY : ", "--------USER-------" + loginUser);

        tv_eventName.setText(upcomingEvent.getHeading());
        tv_eventVenu.setText("" + upcomingEvent.getEventLocation());
        tv_eventDate.setText("" + upcomingEvent.getEventDateFrom());

        try {
            String imageUri = "" + upcomingEvent.getFeaturedImage();
            Log.e("URI", "-----------" + imageUri);
            Picasso.with(getApplicationContext()).load(imageUri).placeholder(getResources().getDrawable(R.drawable.img_placeholder)).into(imageView);
        } catch (Exception e) {
            Log.e("Exception User : ", "-----------" + e.getMessage());
        }

        try {
            // if (loginUser.getIsActive() == 1 && loginUser.getDelStatus() == 1 && loginUser.getEmailVerified() == 1) {
            if (loginUser.getExInt2() == 1) {
                btn_upload.setVisibility(View.VISIBLE);
                tv_uploadText.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            Log.e("Exception User : ", "-----------" + e.getMessage());
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_apply) {
            try {
                if (loginUser.getIsActive() == 1 && loginUser.getDelStatus() == 1 && loginUser.getEmailVerified() == 1) {
                    //getAppliedEvent(upcomingEvent.getNewsblogsId(), loginUser.getRegId());

                    File imgFile = new File(imagePath);
                    int pos = imgFile.getName().lastIndexOf(".");
                    String ext = imgFile.getName().substring(pos + 1);
                    String fileName = loginUser.getRegId() + "_" + System.currentTimeMillis() + "." + ext;

                    sendDocToServer(fileName, upcomingEvent.getNewsblogsId(), loginUser.getRegId());

                } else {
                    Toast.makeText(EventDetailListActivity.this, "Not Apply For This Event", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
            } catch (Exception e) {
                Log.e("Exception User : ", "-----------" + e.getMessage());
                Toast.makeText(EventDetailListActivity.this, "Not Apply For This Event", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }


        } else if (v.getId() == R.id.btn_upload) {

//            Intent intent = new Intent();
//            //sets the select file to all types of files
//            intent.setType("*/*");
//            //allows to select data and return it
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            //starts new activity to select file and return data
//            startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);

            showFileChooser();

            //  browseDocuments();

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

    private void getAppliedEvent(Integer newsblogsId, Integer regId, final String fileName) {

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(getApplicationContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<EventRegCheck>> listCall = Constants.myInterface.getAppliedEvents(newsblogsId, regId);
            listCall.enqueue(new Callback<ArrayList<EventRegCheck>>() {
                @Override
                public void onResponse(Call<ArrayList<EventRegCheck>> call, Response<ArrayList<EventRegCheck>> response) {
                    try {
                        if (response.body().isEmpty()) {

                            Log.e("APPLIED EVENT LIST : ", " - " + response.body());

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            EventRegistration eventRegistration = new EventRegistration(1, loginUser.getRegId(), sdf.format(System.currentTimeMillis()), upcomingEvent.getNewsblogsId(), 0, null, 0, fileName, 0, 1, 1, 0, 0, "", "");
                            getEventRegistration(eventRegistration);

                            commonDialog.dismiss();

                        } else if (response.body() != null) {
                            commonDialog.dismiss();
                            Log.e("APPLIED EVENT LIST1 : ", " - " + response.body());
                            Toast.makeText(EventDetailListActivity.this, "Already Applied For This Event", Toast.LENGTH_SHORT).show();
                            finish();
//                            Intent intent=new Intent(EventDetailListActivity.this, MainActivity.class);
//                            startActivity(intent);

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<EventRegCheck>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEventRegistration(EventRegistration eventRegistration) {
        if (Constants.isOnline(getApplicationContext())) {
            Log.e("PARAMETER : ", "---------------- EVENT REGISTRATION : " + eventRegistration);

            final CommonDialog commonDialog = new CommonDialog(EventDetailListActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<EventRegistration> listCall = Constants.myInterface.saveEventRegister(eventRegistration);
            listCall.enqueue(new Callback<EventRegistration>() {
                @Override
                public void onResponse(Call<EventRegistration> call, Response<EventRegistration> response) {
                    try {
                        if (response.body() != null) {

                            EventRegistration model = response.body();

                            Log.e("EVENT REGISTRATION", "-----------------------------" + response.body());
                            Log.e("EVENT REG MODEL", "-----------------------------" + model);

                            Toast.makeText(EventDetailListActivity.this, "Applied for this Event", Toast.LENGTH_SHORT).show();
                            commonDialog.dismiss();
                            finish();

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
                public void onFailure(Call<EventRegistration> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
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

                Log.e("FILE URI ", "********************* " + getContentResolver().openInputStream(selectedFileUri).toString());


                Log.e("DATA PATH", "---------------------- " + getPath(EventDetailListActivity.this, selectedFileUri));

                imagePath = getPath(EventDetailListActivity.this, selectedFileUri);
                if (getPath(EventDetailListActivity.this, selectedFileUri) == null) {
                    imagePath = "";
                    AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailListActivity.this, R.style.AlertDialogTheme);
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
                    tv_uploadText.setText("" + getPath(EventDetailListActivity.this, selectedFileUri));
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("EVENT DET LIST ACT : ", "-----Exception : ------" + e.getMessage());
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


    private void sendDocToServer(final String filename, final int eventId, final int regId) {
        final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
        commonDialog.show();

        File imgFile = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("file"), imgFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imgFile.getName(), requestFile);

        RequestBody imgName = RequestBody.create(MediaType.parse("text/plain"), filename);


        Call<JSONObject> call = Constants.myInterface.docUpload(body, imgName);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                commonDialog.dismiss();
                //  addNewNotification(bean);
                imagePath = "";
                Log.e("Response : ", "--" + response.body());

                getAppliedEvent(eventId, regId, filename);

            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("Error : ", "--" + t.getMessage());
                commonDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(EventDetailListActivity.this, "Unable To Process", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
