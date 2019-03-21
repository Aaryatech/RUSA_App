package com.ats.rusa_app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ContactUsActivity extends AppCompatActivity  {
//    public TextView tv_showLocation;
//    public Button btn_send;
//    public final String SiteKey = "6LcUwZYUAAAAANdBvuJ3_sXF-fBRC9ttaW-06t3r";
//    public final String SiteSecretKey = "6LcUwZYUAAAAAFV1xI7tTRCrNnvfhlD7GXyuZl5z";
//    private GoogleApiClient mGoogleApiClient;
//    String TAG = MainActivity.class.getSimpleName();
//    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_contact_us);
//        tv_showLocation=(TextView)findViewById(R.id.tv_showLocation);
//        btn_send=(Button)findViewById(R.id.btn_send);
//
//       tv_showLocation.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               double latitude=20.007357;
//               double longitude=73.792992;
//               String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
//               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//               startActivity(intent);
//           }
//       });
//
//        queue = Volley.newRequestQueue(getApplicationContext());
//
//    }
//    public void onClick(View v){
//        SafetyNet.getClient(this).verifyWithRecaptcha(SiteKey)
//                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
//                    @Override
//                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
//                        if (!response.getTokenResult().isEmpty()) {
//                            handleSiteVerify(response.getTokenResult());
//                        }
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        if (e instanceof ApiException) {
//                            ApiException apiException = (ApiException) e;
//                            Log.d(TAG, "Error message: " +
//                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
//                        } else {
//                            Log.d(TAG, "Unknown type of error: " + e.getMessage());
//                        }
//                    }
//                });
//
//    };
//
//    protected  void handleSiteVerify(final String responseToken){
//        //it is google recaptcha siteverify server
//        //you can place your server url
//        String url = "https://www.google.com/recaptcha/api/siteverify";
//        StringRequest request = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if(jsonObject.getBoolean("success")){
//                                //code logic when captcha returns true Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getBoolean("success")),Toast.LENGTH_LONG).show();
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getString("error-codes")),Toast.LENGTH_LONG).show();
//                            }
//                        } catch (Exception ex) {
//                            Log.d(TAG, "JSON exception: " + ex.getMessage());
//
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG, "Error message: " + error.getMessage());
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("secret", SiteSecretKey);
//                params.put("response", responseToken);
//                return params;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                50000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        queue.add(request);
//   }
    }
}
