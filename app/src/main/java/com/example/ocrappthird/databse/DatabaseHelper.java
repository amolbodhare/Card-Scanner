package com.example.ocrappthird.databse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adoisstudio.helper.Session;
import com.example.ocrappthird.commen.P;
import com.example.ocrappthird.entities.Visitor;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    Context context;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "ocr_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Visitor.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Visitor.TABLE_NAME);
    }

    public long insertVisitor(Visitor visitor) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        try {
            Session session=new Session(context);
            values.put(Visitor.COLUMN_USERNAME, session.getString(P.full_name));
            values.put(Visitor.COLUMN_NAME, visitor.getName());
            values.put(Visitor.COLUMN_MOB_ONE, visitor.getMobone());
            values.put(Visitor.COLUMN_MOB_TWO, visitor.getMobtwo());
            values.put(Visitor.COLUMN_EMAIL_ONE, visitor.getEmailone());
            values.put(Visitor.COLUMN_EMAIL_TWO, visitor.getEmailtwo());
            values.put(Visitor.COLUMN_WEB_ONE, visitor.getWebone());
            values.put(Visitor.COLUMN_WEB_TWO, visitor.getWebtwo());
            values.put(Visitor.COLUMN_ADDRESS, visitor.getAddress());
            values.put(Visitor.COLUMN_COMPANY, visitor.getCompany());
            values.put(Visitor.COLUMN_CARD_NOTE, visitor.getCardnote());
            values.put(Visitor.COLUMN_IMAGE, visitor.getImageByteArray());
            //values.put(Visitor.COLUMN_TIMESTAMP, visitor.getTimestamp());

            // insert row
            long id = db.insert(Visitor.TABLE_NAME, null, values);

            // close db connecti
            //
            //
            //
            // on
            db.close();

            // return newly inserted row id
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public long updateVisitor(Visitor visitor,int cardid) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        try {
            //values.put(Visitor.COLUMN_ID, visitor.getId());
            values.put(Visitor.COLUMN_NAME, visitor.getName());
            values.put(Visitor.COLUMN_MOB_ONE, visitor.getMobone());
            values.put(Visitor.COLUMN_MOB_TWO, visitor.getMobtwo());
            values.put(Visitor.COLUMN_EMAIL_ONE, visitor.getEmailone());
            values.put(Visitor.COLUMN_EMAIL_TWO, visitor.getEmailtwo());
            values.put(Visitor.COLUMN_WEB_ONE, visitor.getWebone());
            values.put(Visitor.COLUMN_WEB_TWO, visitor.getWebtwo());
            values.put(Visitor.COLUMN_ADDRESS, visitor.getAddress());
            values.put(Visitor.COLUMN_COMPANY, visitor.getCompany());
            values.put(Visitor.COLUMN_CARD_NOTE, visitor.getCardnote());
            values.put(Visitor.COLUMN_IMAGE, visitor.getImageByteArray());
            //values.put(Visitor.COLUMN_TIMESTAMP, visitor.getTimestamp());

            // insert row
            //long id = db.insert(Visitor.TABLE_NAME, null, values);
            int id=db.update(Visitor.TABLE_NAME, values, "id="+cardid,null);

            // close db connecti
            //
            //
            //
            // on
            db.close();

            // return newly inserted row id
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }}

    public ArrayList<Visitor> getAllVisitors() {
        ArrayList<Visitor> visitors = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Visitor.TABLE_NAME + " ORDER BY " +
                Visitor.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Visitor visitor = new Visitor();
                visitor.setId(cursor.getInt(cursor.getColumnIndex(Visitor.COLUMN_ID)));
                visitor.setUsername(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_USERNAME)));
                visitor.setName(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_NAME)));
                visitor.setMobone(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_MOB_ONE)));
                visitor.setMobtwo(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_MOB_TWO)));
                visitor.setEmailone(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_EMAIL_ONE)));
                visitor.setEmailtwo(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_EMAIL_TWO)));
                visitor.setWebone(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_WEB_ONE)));
                visitor.setWebtwo(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_WEB_TWO)));
                visitor.setAddress(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_ADDRESS)));
                visitor.setCompany(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_COMPANY)));
                visitor.setCardnote(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_CARD_NOTE)));
                visitor.setImageByteArray(cursor.getBlob(cursor.getColumnIndex(Visitor.COLUMN_IMAGE)));
                visitor.setTimestamp(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_TIMESTAMP)));

                visitors.add(visitor);
            }
            while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return visitors list
        return visitors;
    }
    public ArrayList<Visitor> checkAllVisitors(String name,String email) {
        ArrayList<Visitor> visitors = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Visitor.TABLE_NAME + " ORDER BY " +
                Visitor.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Visitor visitor = new Visitor();
                visitor.setId(cursor.getInt(cursor.getColumnIndex(Visitor.COLUMN_ID)));
                visitor.setName(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_NAME)));
                visitor.setMobone(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_MOB_ONE)));
                visitor.setMobtwo(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_MOB_TWO)));
                visitor.setEmailone(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_EMAIL_ONE)));
                visitor.setEmailtwo(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_EMAIL_TWO)));
                visitor.setWebone(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_WEB_ONE)));
                visitor.setWebtwo(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_WEB_TWO)));
                visitor.setAddress(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_ADDRESS)));
                visitor.setCompany(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_COMPANY)));
                visitor.setImageByteArray(cursor.getBlob(cursor.getColumnIndex(Visitor.COLUMN_IMAGE)));
                visitor.setTimestamp(cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_TIMESTAMP)));
                visitors.add(visitor);
            }
            while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return visitors list
        return visitors;
    }
    public Visitor getVisitor(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Visitor.TABLE_NAME,
                new String[]{Visitor.COLUMN_ID, Visitor.COLUMN_USERNAME,Visitor.COLUMN_NAME, Visitor.COLUMN_MOB_ONE,Visitor.COLUMN_MOB_TWO,
                        Visitor.COLUMN_EMAIL_ONE,Visitor.COLUMN_EMAIL_TWO,Visitor.COLUMN_WEB_ONE,Visitor.COLUMN_WEB_TWO,
                        Visitor.COLUMN_ADDRESS,Visitor.COLUMN_COMPANY,Visitor.COLUMN_CARD_NOTE,Visitor.COLUMN_IMAGE,Visitor.COLUMN_TIMESTAMP},
                Visitor.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare visitor object
        Visitor visitor = new Visitor(
                cursor.getInt(cursor.getColumnIndex(Visitor.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_MOB_ONE)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_MOB_TWO)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_EMAIL_ONE)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_EMAIL_TWO)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_WEB_ONE)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_WEB_TWO)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_COMPANY)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_CARD_NOTE)),
                cursor.getBlob(cursor.getColumnIndex(Visitor.COLUMN_IMAGE)),
                cursor.getString(cursor.getColumnIndex(Visitor.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return visitor;
    }
    public int deleteCard(int  id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            int returnValue=db.delete(Visitor.TABLE_NAME, Visitor.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)});
            db.close();
            return returnValue;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }


    }
    public long deleteAllCards() {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(Visitor.TABLE_NAME, null, null);

        //db.execSQL("delete from "+ TABLE_NAME);
        db.close();
        return id;
    }
}
