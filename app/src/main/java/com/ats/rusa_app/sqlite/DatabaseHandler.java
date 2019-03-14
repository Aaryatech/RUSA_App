package com.ats.rusa_app.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        db.execSQL(CREATE_TABLE_NOTIFICATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        onCreate(db);
    }


    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTIFICATION, null, null);
        db.close();
    }


    //----------------------------------NOTIFICATION------------------------------------


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
