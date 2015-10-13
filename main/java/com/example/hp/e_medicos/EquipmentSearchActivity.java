package com.example.hp.e_medicos;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class EquipmentSearchActivity extends ActionBarActivity {
    private Button b,b2;
    private EditText e;
    String eqpName;
    DatabaseOperations mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws Error{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_search);
        b=(Button)findViewById(R.id.button3);
        b2=(Button)findViewById(R.id.button11);
        e=(EditText)findViewById(R.id.editText3);

        Bundle bundle=getIntent().getExtras();
        final Boolean bl=bundle.getBoolean("Login");
        final String name=bundle.getString("Name");
        final String LoginID=bundle.getString("LoginID");
        final String password=bundle.getString("Password");

        mdb=new DatabaseOperations(this,"Equipments");
        try {
            mdb.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            mdb.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                eqpName=e.getText().toString();
                Cursor cursor=mdb.getInformation("EQUIPMENT",eqpName.toUpperCase());
                if(cursor.getCount()==0)
                {
                    Toast.makeText(EquipmentSearchActivity.this,"No Equipment With This Name Exist\nPlease Try Again.....",Toast.LENGTH_SHORT).show();
                }
                else {
                    cursor.moveToFirst();
                    Intent i=new Intent(EquipmentSearchActivity.this,BuyActivity.class);
                    i.putExtra("Login",bl);
                    i.putExtra("PName",cursor.getString(0));
                    i.putExtra("Price",cursor.getString(1));
                    i.putExtra("Qty",cursor.getString(2));
                    i.putExtra("fromLoc",cursor.getString(3));
                    i.putExtra("UserName",name);
                    i.putExtra("LoginID",LoginID);
                    i.putExtra("Password",password);
                    startActivity(i);
                    finish();
                }
            }
        });
        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                startActivity(new Intent(EquipmentSearchActivity.this,MenuScreenActivity.class));
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
