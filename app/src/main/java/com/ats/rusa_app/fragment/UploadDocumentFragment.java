package com.ats.rusa_app.fragment;


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
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rusa_app.BuildConfig;
import com.ats.rusa_app.R;
import com.ats.rusa_app.activity.LoginActivity;
import com.ats.rusa_app.adapter.DocListAdapter;
import com.ats.rusa_app.constants.Constants;
import com.ats.rusa_app.model.DocTypeList;
import com.ats.rusa_app.model.DocUpload;
import com.ats.rusa_app.model.Info;
import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.TokenInfo;
import com.ats.rusa_app.sqlite.DatabaseHandler;
import com.ats.rusa_app.util.CommonDialog;
import com.ats.rusa_app.util.CustomSharedPreference;
import com.ats.rusa_app.util.PermissionsUtil;
import com.ats.rusa_app.util.RealPathUtil;

import java.io.File;
import java.io.FileOutputStream;
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
    DatabaseHandler dbHelper;

    //---------------IMAGE----------------
    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "RUSA_DOCS");
    File f;

    Bitmap myBitmap = null;

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

        dbHelper=new DatabaseHandler(getActivity());


        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
        }

        createFolder();

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
            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;
            getCheckToken(loginUser.getRegId(),token);
           // getUserDocList(loginUser.getRegId());
        }
        getDocTypeList();

        return view;
    }

    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
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

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle("Choose");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent pictureActionIntent = null;
                pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, 101);
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");

                        String authorities = BuildConfig.APPLICATION_ID + ".provider";
                        Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, f);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);

                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);

                    }
                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }
            }
        });
        builder.show();

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


                        //Log.e("RESPONSE ", "-----------------------------" + response.body());

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
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                       // e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<DocTypeList>> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                   // t.printStackTrace();

                }
            });
        }

    }

    private void getCheckToken(final Integer regId, String token) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<TokenInfo> listCall = Constants.myInterface.tokenConfirmation(regId,token,authHeader);
            listCall.enqueue(new Callback<TokenInfo>() {
                @Override
                public void onResponse(Call<TokenInfo> call, Response<TokenInfo> response) {
                    // Log.e("Responce","--------------------------------------------------"+response.body());
                    try {
                        //if (response.body() == null) {

                            if(!response.body().getError()) {

                                getUserDocList(regId);

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


                      //  }
                        commonDialog.dismiss();

                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                        // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<TokenInfo> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                    //  t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getUserDocList(int id) {
       // Log.e("PARAMETER","          ID       "+id);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;


            Call<ArrayList<DocUpload>> listCall = Constants.myInterface.getDocList(id,token,authHeader);
            listCall.enqueue(new Callback<ArrayList<DocUpload>>() {
                @Override
                public void onResponse(Call<ArrayList<DocUpload>> call, Response<ArrayList<DocUpload>> response) {
                    try {

                        //Log.e("RESPONSE ", "-----------------------------" + response.body());

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
                        ////Log.e("Exception : ", "-----------" + e.getMessage());
                       // e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<DocUpload>> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                    //t.printStackTrace();

                }
            });
        }

    }


    //--------------------------------------------------------------------


    //----NEW-----
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String realPath;
        Bitmap bitmap = null;

        if (resultCode == getActivity().RESULT_OK && requestCode == 102) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    //imageView.setImageBitmap(myBitmap);

                    myBitmap = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        //Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        //Log.e("Exception : ", "--------" + e.getMessage());
                      //  e.printStackTrace();
                    }
                }
                imagePath = f.getAbsolutePath();
                tvFileName.setText("" + f.getName());
            } catch (Exception e) {
               // e.printStackTrace();
            }

        } else if (resultCode == getActivity().RESULT_OK && requestCode == 101) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap = getBitmapFromCameraData(data, getContext());

                //imageView.setImageBitmap(myBitmap);
                imagePath = uriFromPath.getPath();
                tvFileName.setText("" + uriFromPath.getPath());

                try {

                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    ////Log.e("Image Saved  ", "---------------");

                } catch (Exception e) {
                    // Log.e("Exception : ", "--------" + e.getMessage());
                  //  e.printStackTrace();
                }

            } catch (Exception e) {
              //  e.printStackTrace();
                // Log.e("Image Compress : ", "-----Exception : ------" + e.getMessage());
            }
        }
    }

    public static Bitmap shrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }

    public static Bitmap getBitmapFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        String picturePath = cursor.getString(columnIndex);
        path = picturePath;
        cursor.close();

        Bitmap bitm = shrinkBitmap(picturePath, 720, 720);
        //Log.e("Image Size : ---- ", " " + bitm.getByteCount());

        return bitm;
        // return BitmapFactory.decodeFile(picturePath, options);
    }



    //---OLD------
    /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String realPath;

        //Log.e("EVENT DET LIST ACT", "---------------------- onActivityResult " + requestCode + " - " + resultCode);

        if (resultCode == RESULT_OK && requestCode == 1) {
            try {

                if (data == null) {
                    //no data present
                    return;
                }
                Uri selectedFileUri = data.getData();
                //Log.e("UriPath", "----------" + selectedFileUri.getPath());

                //Log.e("DATA PATH", "---------------------- " + getPath(getActivity(), selectedFileUri));

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
               // e.printStackTrace();
                //Log.e("UPLOAD DOC FRG : ", "-----Exception : ------" + e.getMessage());
            }
        }
    }*/

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

        String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;
        //JSONObject

        Call<Info> call = Constants.myInterface.docUpload(body, imgName, type,loginUser.getRegId(),token,authHeader);
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                commonDialog.dismiss();
                //  addNewNotification(bean);
                imagePath = "";
               // Log.e("Response : ", "--" + response.body());

                if(!response.body().getError()) {

                    saveDoc(docUpload);

                }else{
                    if(response.body().getMsg().equalsIgnoreCase("File upload failed"))
                    {
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

                    }else if(response.body().getRetmsg().equalsIgnoreCase("Unauthorized User"))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                        builder.setTitle("Alert");
                        builder.setMessage("" + response.body().getRetmsg());
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



            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                //Log.e("Error : ", "--" + t.getMessage());
                commonDialog.dismiss();
               // t.printStackTrace();
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

            String token = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_LOGIN_TOKEN) ;

            Call<Info> listCall = Constants.myInterface.saveDocument(docUpload,token,authHeader);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                   // Log.e("Responce","--------------------------------------------------"+response.body());
                    try {
                        if (response.body() == null) {

                            Toast.makeText(getContext(), "Unable to upload file!", Toast.LENGTH_SHORT).show();

                        } else {

                            if(!response.body().getError()) {

                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                                Fragment adf = new UploadDocumentFragment();
                                Bundle args = new Bundle();
                                args.putString("slugName", "uploadDoc");
                                adf.setArguments(args);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

                            }else{
                                if (response.body().getRetmsg().equalsIgnoreCase("Unauthorized User")) {

                                            dbHelper.deleteData("user_data");
                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            getActivity().finish();

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

                            }

                        }
                        commonDialog.dismiss();

                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Log.e("Exception : ", "-----------" + e.getMessage());
                       // e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    //Log.e("onFailure : ", "-----------" + t.getMessage());
                  //  t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }


}
