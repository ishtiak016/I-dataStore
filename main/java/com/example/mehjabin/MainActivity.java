package com.example.mehjabin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    sqlDatabaseHealper sqlDatabasehealper;
    private EditText nameEditText,genderEditText,ageEditText,idEditText;
    private Button addButton,showAlldataButton,updateButton,deleteButton,signInButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlDatabasehealper=new  sqlDatabaseHealper(this);
        SQLiteDatabase sqLiteDatabase= sqlDatabasehealper.getWritableDatabase();
        nameEditText=(EditText)findViewById(R.id.nameTextId);
        genderEditText=(EditText)findViewById(R.id.genderTextId);
        ageEditText=(EditText)findViewById(R.id.ageTextId);
        idEditText=(EditText)findViewById(R.id.idTextId);
        addButton=(Button)findViewById(R.id.addButton);
        showAlldataButton=(Button)findViewById(R.id.shawAllButton);
        updateButton=(Button)findViewById(R.id.updateButton);
        deleteButton=(Button)findViewById(R.id.deleteButton);
        signInButton=(Button)findViewById(R.id.loginButton);


    }


    public void onClick(View v) {
        String name=nameEditText.getText().toString();
        String gender=genderEditText.getText().toString();
        String age=ageEditText.getText().toString();
        String id=idEditText.getText().toString();
        if(v.getId()==R.id.addButton){
            long rowId=  sqlDatabasehealper.insertData(name,age,gender);
            if(rowId>0){
                Toast.makeText(getApplicationContext(),"Row is Successfully inserted",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Row is USuccessfully inserted",Toast.LENGTH_LONG).show();

            }
        }
        if(v.getId()==R.id.shawAllButton){
          Cursor cursor= sqlDatabasehealper.DisplayAllData();
            if(cursor.getCount()==0){
                showData("ERROR","NO DATA FOUND");
                return;
            }
            StringBuffer stringBuffer=new StringBuffer();
            while (cursor.moveToNext()){
                stringBuffer.append("ID :"+cursor.getString(0)+"\n");
                stringBuffer.append("NAME :"+cursor.getString(1)+"\n");
                stringBuffer.append("AGE :"+cursor.getString(2)+"\n");
                stringBuffer.append("GENDER :"+cursor.getString(3)+"\n");
            }
            showData("RESULTSHEET",stringBuffer.toString());
        }
        else  if(v.getId()==R.id.updateButton){
              Boolean isUpdate= sqlDatabasehealper.UpdateData(id,name,age,gender);

              if(isUpdate==true)
              {
                  Toast.makeText(getApplicationContext(),"Row is Successfully updated",Toast.LENGTH_LONG).show();
              }else{
                  Toast.makeText(getApplicationContext(),"update Unsuccessful",Toast.LENGTH_LONG).show();
              }
        }

        if(v.getId()==R.id.deleteButton){
            int value=sqlDatabasehealper.DeleteData(id);
            if(value>0){

                Toast.makeText(getApplicationContext()," Successfully Deleted",Toast.LENGTH_LONG).show();
            }else{

                Toast.makeText(getApplicationContext()," unSuccessfully Deleted",Toast.LENGTH_LONG).show();
            }

        }
        if(v.getId()==R.id.loginButton){
            boolean result=sqlDatabasehealper.SignIn(id,gender);
            if(result=true){
                Toast.makeText(getApplicationContext()," Successfully LOgin",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext()," ID & GENDER do not match",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void showData(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }
}
