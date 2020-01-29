package com.ats.rusa_app.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ats.rusa_app.model.Login;
import com.ats.rusa_app.model.NotificationModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "CommunicationApp";

    private static final String TABLE_NOTIFICATION = "notification";


    private static final String N_ID = "nId";
    private static final String N_TITLE = "nTitle";
    private static final String N_DESC = "nDesc";
    private static final String N_DATE = "nDate";

    //------------------------------------------------UserData-----------------------------------

    public static final String USER_TABLE = "user_data";
    public static final String USER_ID = "id";
    public static final String USER_REGID = "regId";
    public static final String USER_UUID = "userUuid";
    public static final String USER_TYPE = "userType";
    public static final String USER_EMAILS = "emails";
    public static final String USER_ALTER_EMAIL = "alternateEmail";
    public static final String USER_PASS = "userPassword";
    public static final String USER_NAME = "name";
    public static final String USER_AISHE_CODE = "aisheCode";
    public static final String USER_CLG_NAME = "collegeName";
    public static final String USER_UNIVERSITY_NAME = "unversityName";
    public static final String USER_DESIG_NAME = "designationName";
    public static final String USER_DEPT_NAME = "departmentName";
    public static final String USER_MOB = "mobileNumber";
    public static final String USER_AUTH_PERSON = "authorizedPerson";
    public static final String USER_DOB = "dob";
    public static final String USER_IMAGE_NAME = "imageName";
    public static final String USER_TOKEN_ID = "tokenId";
    public static final String USER_REGISTERVIA = "registerVia";
    public static final String USER_ISACTIVE = "isActive";
    public static final String USER_DELSTATUS = "delStatus";
    public static final String USER_ADD_DATE = "addDate";
    public static final String USER_EDIT_DATE = "editDate";
    public static final String USER_EDIT_BY_USDER_ID = "editByUserId";
    public static final String USER_EXINT1 = "exInt1";
    public static final String USER_EXINT2 = "exInt2";
    public static final String USER_EXVAR1 = "exVar1";
    public static final String USER_EXVAR2 = "exVar2";
    public static final String USER_EMIAL_CODE = "emailCode";
    public static final String USER_EMAIL_VERIFIED = "emailVerified";
    public static final String USER_SMS_CODE = "smsCode";
    public static final String USER_SMS_VERIFICATION = "smsVerified";
    public static final String USER_EDIT_BY_ADMIN_ID = "editByAdminuserId";
    public static final String USER_MSG = "msg";
    public static final String USER_ERROR = "error";


    String CREATE_TABLE_USER_DATA = "CREATE TABLE "
            + USER_TABLE + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, "
            + USER_REGID + " INTEGER , "
            + USER_UUID + " TEXT , "
            + USER_TYPE + " INTEGER , "
            + USER_EMAILS + " TEXT , "
            + USER_ALTER_EMAIL + " TEXT , "
            + USER_PASS + " TEXT , "
            + USER_NAME + " TEXT , "
            + USER_AISHE_CODE + " TEXT , "
            + USER_CLG_NAME + " TEXT , "
            + USER_UNIVERSITY_NAME + " TEXT , "
            + USER_DESIG_NAME + " TEXT , "
            + USER_DEPT_NAME + " TEXT , "
            + USER_MOB + " TEXT , "
            + USER_AUTH_PERSON + " TEXT , "
            + USER_DOB + " TEXT , "
            + USER_IMAGE_NAME + " TEXT , "
            + USER_TOKEN_ID + " TEXT , "
            + USER_REGISTERVIA + " TEXT , "
            + USER_ISACTIVE + " INTEGER , "
            + USER_DELSTATUS + " INTEGER , "
            + USER_ADD_DATE + " TEXT , "
            + USER_EDIT_DATE + " TEXT , "
            + USER_EDIT_BY_USDER_ID + " INTEGER , "
            + USER_EXINT1 + " INTEGER , "
            + USER_EXINT2 + " INTEGER , "
            + USER_EXVAR1 + " TEXT , "
            + USER_EXVAR2 + " TEXT , "
            + USER_EMIAL_CODE + " TEXT , "
            + USER_EMAIL_VERIFIED + " INTEGER , "
            + USER_SMS_CODE + " TEXT , "
            + USER_SMS_VERIFICATION + " INTEGER , "
            + USER_EDIT_BY_ADMIN_ID + " TEXT , "
            + USER_MSG + " TEXT , "
            + USER_ERROR + " TEXT)";



    String CREATE_TABLE_NOTIFICATION = "CREATE TABLE "
            + TABLE_NOTIFICATION + "("
            + N_ID + " INTEGER PRIMARY KEY, "
            + N_TITLE + " TEXT, "
            + N_DESC + " TEXT, "
            + N_DATE + " TEXT) ";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_DATA);
        db.execSQL(CREATE_TABLE_NOTIFICATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public void removeUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE, null, null);

        db.close();
    }

    public void deleteData(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+table);
        db.close();
        //  return true;
    }

    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTIFICATION, null, null);
        db.close();
    }


    //----------------------------------NOTIFICATION------------------------------------


    public void insertUserData(Login login) {
        try {
            SQLiteDatabase dbnew = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_REGID, login.getRegId());
            contentValues.put(USER_UUID, login.getUserUuid());
            contentValues.put(USER_TYPE, login.getUserType());
            contentValues.put(USER_EMAILS, login.getEmails());
            contentValues.put(USER_ALTER_EMAIL, login.getAlternateEmail());
            contentValues.put(USER_PASS, login.getUserPassword());
            contentValues.put(USER_NAME, login.getName());
            contentValues.put(USER_AISHE_CODE, login.getAisheCode());
            contentValues.put(USER_CLG_NAME, login.getCollegeName());
            contentValues.put(USER_UNIVERSITY_NAME, login.getUnversityName());
            contentValues.put(USER_DESIG_NAME, login.getDesignationName());
            contentValues.put(USER_DEPT_NAME, login.getDepartmentName());
            contentValues.put(USER_MOB, login.getMobileNumber());
            contentValues.put(USER_AUTH_PERSON, login.getAuthorizedPerson());
            contentValues.put(USER_DOB, login.getDob());
            contentValues.put(USER_IMAGE_NAME, login.getImageName());
            contentValues.put(USER_TOKEN_ID, login.getTokenId());
            contentValues.put(USER_REGISTERVIA, login.getRegisterVia());
            contentValues.put(USER_ISACTIVE, login.getIsActive());
            contentValues.put(USER_DELSTATUS, login.getDelStatus());
            contentValues.put(USER_ADD_DATE, login.getAddDate());
            contentValues.put(USER_EDIT_DATE, login.getEditDate());
            contentValues.put(USER_EDIT_BY_USDER_ID, login.getEditByUserId());
            contentValues.put(USER_EXINT1, login.getExInt1());
            contentValues.put(USER_EXINT2, login.getExInt2());
            contentValues.put(USER_EXVAR1, login.getExVar1());
            contentValues.put(USER_EXVAR2, login.getExVar2());
            contentValues.put(USER_EMIAL_CODE, login.getEmailCode());
            contentValues.put(USER_EMAIL_VERIFIED, login.getEmailVerified());
            contentValues.put(USER_SMS_CODE, login.getSmsCode());
            contentValues.put(USER_SMS_VERIFICATION, login.getSmsVerified());
            contentValues.put(USER_EDIT_BY_ADMIN_ID, login.getEditByAdminuserId());
            contentValues.put(USER_MSG, login.getMsg());
            contentValues.put(USER_ERROR, login.getError());

            dbnew.insert(USER_TABLE, null, contentValues);

          //  Log.e("DBHelper", " --------------------------------------User Date------------------------------- " + contentValues);

            dbnew.close();

        } catch (Exception e) {
           // Log.e("DBHelper", " .. " + e.getMessage());
        }
        // return true;
    }


    public Login getLoginData() {

        Login login = new Login();
        try {

            String query = "SELECT * FROM "+ USER_TABLE ;

          //  Log.e("DB : ", "-------------------------------------- Query-------------------------- " + query);

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {

               // Log.e("DB : ", "------------------------------------Cursor---------------------------------" + cursor.getFloat(0));

                login  = new Login();

                login.setRegId(cursor.getInt(1));
                login.setUserUuid(cursor.getString(2));
                login.setUserType(cursor.getInt(3));
                login.setEmails(cursor.getString(4));
                login.setAlternateEmail(cursor.getString(5));
                //login.setUserPassword(cursor.getString(6));
                login.setName(cursor.getString(7));
                login.setAisheCode(cursor.getString(8));
                login.setCollegeName(cursor.getString(9));
                login.setUnversityName(cursor.getString(10));
                login.setDesignationName(cursor.getString(11));
                login.setDepartmentName(cursor.getString(12));
                login.setMobileNumber(cursor.getString(13));
                login.setAuthorizedPerson(cursor.getString(14));
                login.setDob(cursor.getString(15));
                login.setImageName(cursor.getString(16));
                login.setTokenId(cursor.getString(17));
                login.setRegisterVia(cursor.getString(18));
                login.setIsActive(cursor.getInt(19));
                login.setDelStatus(cursor.getInt(20));
                login.setAddDate(cursor.getString(21));
                login.setEditDate(cursor.getString(22));
                login.setEditByUserId(cursor.getInt(23));
                login.setExInt1(cursor.getInt(24));
                login.setExInt2(cursor.getInt(25));
                login.setExVar1(cursor.getString(26));
                login.setExVar2(cursor.getString(27));
                login.setEmailCode(cursor.getString(28));
                login.setEmailVerified(cursor.getInt(29));
              //  login.setSmsCode(cursor.getString(30));
                login.setSmsVerified(cursor.getInt(31));
                login.setEditByAdminuserId(cursor.getInt(32));
               // login.setMsg(cursor.getString(33));
               // login.setError(cursor.get(34));


                cursor.close();
            }

          //  Log.e("DB : ", "-----------get All Login Data-----------" + login);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(login.getRegId()==null || login.getRegId()==0)
        {
            login=null;
        }

        return login;
    }


    public void addNotification(String title, String desc, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(N_TITLE, title);
        values.put(N_DESC, desc);
        values.put(N_DATE, date);

        db.insert(TABLE_NOTIFICATION, null, values);
        db.close();
    }


    public ArrayList<NotificationModel> getAllNotification() {
        ArrayList<NotificationModel> messageList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todaysDate = sdf.format(date.getTime());
        //Log.e("TODAYS DATE : ", "-------------" + todaysDate);

        //String query = "SELECT * FROM " + TABLE_MESSAGE + " WHERE " + M_TO_DATE + "<'" + todaysDate + "'";
        String query = "SELECT * FROM " + TABLE_NOTIFICATION + " ORDER BY " + N_ID + " DESC";

        //Log.e("QUERY : ", "-------------" + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                NotificationModel message = new NotificationModel();
                message.setId(cursor.getInt(0));
                message.setTitle(cursor.getString(1));
                message.setDesc(cursor.getString(2));
                message.setDate(cursor.getString(3));
                messageList.add(message);
            } while (cursor.moveToNext());
        }
        return messageList;
    }

    /*
    public Message getMessage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MESSAGE, new String[]{M_ID,
                        M_FROM_DATE, M_TO_DATE, M_IMAGE, M_HEADER, M_DETAILS, M_IS_ACTIVE, M_DEL_STATUS, M_READ}, M_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Message msg = new Message();
        if (cursor != null && cursor.moveToFirst()) {
            msg = new Message(Integer.parseInt(cursor.getString(0)));
            cursor.close();
        }
        return msg;
    }

    public int updateMessageById(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(M_ID, message.getMsgId());
        values.put(M_FROM_DATE, message.getMsgFrdt());
        values.put(M_TO_DATE, message.getMsgTodt());
        values.put(M_IMAGE, message.getMsgImage());
        values.put(M_HEADER, message.getMsgHeader());
        values.put(M_DETAILS, message.getMsgDetails());
        values.put(M_IS_ACTIVE, message.getIsActive());
        values.put(M_DEL_STATUS, message.getDelStatus());
        values.put(M_READ, 0);

        // updating row
        return db.update(TABLE_MESSAGE, values, "mId=" + message.getMsgId(), null);

    }

    public int updateMessageRead() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(M_READ, 1);

        // updating row
        return db.update(TABLE_MESSAGE, values, "mRead=0", null);

    }

    public int getMessageUnreadCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MESSAGE + " WHERE " + M_READ + "=0", null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

*/


}
