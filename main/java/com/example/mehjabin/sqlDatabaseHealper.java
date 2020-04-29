package com.example.mehjabin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class sqlDatabaseHealper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Student.db";
    private static final String TABLE_NAME="student_details";
    private static final int VERSION_NUMBER=9;
    private static final String ID="_id";
    private static final String NAME="Name";
    private static final String AGE="Age";
    private static final String GENDER="GENDER";

    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR(255),"+AGE+" INTEGER,"+GENDER+" VARCHAR(255));";

    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String SELECT_TABLE="SELECT * FROM "+TABLE_NAME;
    private Context context;
    public sqlDatabaseHealper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
          Toast.makeText(context,"onCreate is create :",Toast.LENGTH_LONG).show();
            db.execSQL(CREATE_TABLE);
        }catch (Exception e){
            Toast.makeText(context,"Exception :"+e,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e){
            Toast.makeText(context,"Exception :"+e,Toast.LENGTH_LONG).show();
        }
    }
    public long insertData(String name,String age,String gender){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);
        long rowid= db.insert(TABLE_NAME,null,contentValues);
        return rowid;
    }
    public Cursor DisplayAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
       Cursor cursor= db.rawQuery(SELECT_TABLE,null);
       return cursor;

    }
    public boolean UpdateData(String id,String name,String age,String gender){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);

        db.update(TABLE_NAME,contentValues,ID+" =?",new String[]{id});
        return true;

    }

    public int DeleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
       return db.delete(TABLE_NAME,ID+"=?", new String[]{id});
    }

    public boolean SignIn(String id,String gender){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor= db.rawQuery(SELECT_TABLE,null);
        Boolean result=false;

        if(cursor.getCount()==0)
        {
            Toast.makeText(context,"No Data Found ",Toast.LENGTH_LONG).show();
        }else{
            while (cursor.moveToNext()){
                String UserId=cursor.getString(0);
                String UserGender=cursor.getString(3);

                if(UserId.equals(id) && UserGender.equals(gender)){
                    result=true;
                    break;
                }
            }
        }
        return result;
    }
}
