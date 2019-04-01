package com.ats.rusa_app.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailListActivity extends AppCompatActivity implements View.OnClickListener {
    UpcomingEvent upcomingEvent;
    ImageView imageView;
    TextView tv_eventName,tv_eventVenu,tv_eventDate,tv_uploadText;
    Button btn_apply,btn_upload;
    Login loginUser;
    private String selectedImagePath;
    private final int ACTIVITY_CAMERA = 0;
    private final int RESULT_OK = 100;
    private final int ACTIVITY_CHOOSE_FILE = 1;
    Uri URI = null;
    private static final int PICK_FROM_GALLERY = 101;
    private static final int PICK_FILE_REQUEST = 1;
    int columnIndex;
    String attachmentFile;
    private String selectedFilePath;
    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail_list);

        imageView=(ImageView)findViewById(R.id.iv_baner);
        tv_eventName=(TextView)findViewById(R.id.tvEventName);
        tv_eventVenu=(TextView)findViewById(R.id.tvEventVenu);
        tv_eventDate=(TextView)findViewById(R.id.tvEventDate);
        btn_apply=(Button)findViewById(R.id.btn_apply);
        btn_upload=(Button)findViewById(R.id.btn_upload);
        tv_uploadText=(TextView)findViewById(R.id.tv_uploadText);

        btn_apply.setOnClickListener(this);
        btn_upload.setOnClickListener(this);

        if (PermissionsUtil.checkAndRequestPermissions(EventDetailListActivity.this)) {
        }
        String upcomingStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        upcomingEvent = gson.fromJson(upcomingStr, UpcomingEvent.class);
        Log.e("responce","-----------------------"+upcomingEvent);

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
        }catch(Exception e)
        {
            Log.e("Exception User : ", "-----------" + e.getMessage());
        }
        try {
           // if (loginUser.getIsActive() == 1 && loginUser.getDelStatus() == 1 && loginUser.getEmailVerified() == 1) {
            if(loginUser.getExInt2()==1)
            {
                btn_upload.setVisibility(View.VISIBLE);
                tv_uploadText.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            Log.e("Exception User : ", "-----------" + e.getMessage());
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_apply)
        {
            try {
                if (loginUser.getIsActive() == 1 && loginUser.getDelStatus() == 1 && loginUser.getEmailVerified() == 1) {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    EventRegistration eventRegistration=new EventRegistration(1,loginUser.getRegId(),sdf.format(System.currentTimeMillis()),upcomingEvent.getNewsblogsId(),0,null,0,"",0,1,1,0,0,"","");
//                    getEventRegistration(eventRegistration);
                    getAppliedEvent(upcomingEvent.getNewsblogsId(),loginUser.getRegId());

                } else {
                    Toast.makeText(EventDetailListActivity.this, "Not Apply For This Event", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                }
            }catch (Exception e)
            {
                Log.e("Exception User : ", "-----------" + e.getMessage());
                Toast.makeText(EventDetailListActivity.this, "Not Apply For This Event", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
            }
        }else if(v.getId()==R.id.btn_upload)
        {
//                Intent i = new Intent(
//                        Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//                startActivityForResult(i, RESULT_OK);

//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent,
//                    "Select Picture"), RESULT_LOAD_IMAGE);

            Intent intent = new Intent();
            //sets the select file to all types of files
            intent.setType("*/*");
            //allows to select data and return it
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //starts new activity to select file and return data
            startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);


        }
    }

    private void getAppliedEvent(Integer newsblogsId, Integer regId) {

        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(getApplicationContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<EventRegCheck>> listCall = Constants.myInterface.getAppliedEvents(newsblogsId,regId);
            listCall.enqueue(new Callback<ArrayList<EventRegCheck>>() {
                @Override
                public void onResponse(Call<ArrayList<EventRegCheck>> call, Response<ArrayList<EventRegCheck>> response) {
                    try {
                        if (response.body().isEmpty()) {

                            Log.e("APPLIED EVENT LIST : ", " - " + response.body());

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            EventRegistration eventRegistration=new EventRegistration(1,loginUser.getRegId(),sdf.format(System.currentTimeMillis()),upcomingEvent.getNewsblogsId(),0,null,0,"",0,1,1,0,0,"","");
                            getEventRegistration(eventRegistration);

                            commonDialog.dismiss();

                        } else if(response.body()!=null){
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

                            EventRegistration model=response.body();

                            Log.e("EVENT REGISTRATION","-----------------------------"+response.body());
                            Log.e("EVENT REG MODEL","-----------------------------"+model);

                            Toast.makeText(EventDetailListActivity.this, "Applied for this Event", Toast.LENGTH_SHORT).show();
                            commonDialog.dismiss();
                            finish();
//                            FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
//                            ft.replace(R.id.content_frame, new UpcomingEventFragment(), "HomeFragment");
//                            ft.commit();
//                            commonDialog.dismiss();

//                            Intent intent=new Intent(EventDetailListActivity.this, MainActivity.class);
//                            startActivity(intent);
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if(resCode == Activity.RESULT_OK){
            if(reqCode == PICK_FILE_REQUEST){
                if(data == null){
                    //no data present
                    return;
                }
                Uri selectedFileUri = data.getData();
                Log.e("UriPath","----------"+selectedFileUri.getPath());
                selectedFilePath = FilePath.getPath(this,selectedFileUri);
                Log.i("TAG","Selected File Path:" + selectedFilePath);

                if(selectedFilePath != null && !selectedFilePath.equals("")){
                    savePath(selectedFilePath);
                    tv_uploadText.setText(selectedFilePath);
                }else{
                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void savePath(String selectedFilePath) {

        if (selectedFilePath != null) {
            // pathArray.add(imagePath1);
            File imgFile1 = new File(selectedFilePath);
            int pos = imgFile1.getName().lastIndexOf(".");
            String ext = imgFile1.getName().substring(pos + 1);
           String photo1 = System.currentTimeMillis() + "_p1." + ext;
           // fileNameArray.add(photo1);
            Log.e("SelectFilePath","-----------"+selectedFilePath);
            Log.e("photo1","-----------"+photo1);
        }
    }


//
//    public static String getPath(Context context, Uri uri ) {
//        String result = null;
//        String[] proj = { MediaStore.Images.Media.DATA };
//        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
//        if(cursor != null){
//            if ( cursor.moveToFirst( ) ) {
//                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
//                result = cursor.getString( column_index );
//            }
//            cursor.close( );
//        }
//        if(result == null) {
//            result = "Not found";
//        }
//        return result;
//    }



//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String s = getRealPathFromURI(selectedImage);
//            Log.d("Picture Path", "------------------"+s);
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//           // ImageView imageView = (ImageView) findViewById(R.id.imgView);
//
//            Bitmap bmp = null;
//            try {
//                bmp = getBitmapFromUri(selectedImage);
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            Log.e("ImageName","-----------------"+s);
//           // imageView.setImageBitmap(bmp);
//            tv_uploadText.setText(s);
//        }
//
//        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            attachmentFile = cursor.getString(columnIndex);
//            Log.e("Attachment Path:", attachmentFile);
//            URI = Uri.parse("file://" + attachmentFile);
//            tv_uploadText.setText(URI);
//            cursor.close();
//        }
 //  }
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }
    private String getRealPathFromURI(Uri selectedImage) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(selectedImage, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
               getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
