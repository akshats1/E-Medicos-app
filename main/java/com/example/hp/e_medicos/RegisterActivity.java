package com.example.hp.e_medicos;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;


public class RegisterActivity extends ActionBarActivity {
    EditText e[];
    Button b,b2;
    DatabaseOperations udb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e=new EditText[5];
        e[0]=(EditText)findViewById(R.id.editText4);
        e[1]=(EditText)findViewById(R.id.editText5);
        e[2]=(EditText)findViewById(R.id.editText6);
        e[3]=(EditText)findViewById(R.id.editText7);
        e[4]=(EditText)findViewById(R.id.editText9);
        b=(Button)findViewById(R.id.button12);
        b2=(Button)findViewById(R.id.button3);
        udb=new DatabaseOperations(this,"Users");
        try {
            udb.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            udb.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((e[2].getText().toString()).equals(e[3].getText().toString()))
                {   long a=udb.putInformation("USERS",e[0].getText().toString(),e[1].getText().toString(),e[2].getText().toString(),e[4].getText().toString());
                    if(a!=-1)
                    {
                        Toast.makeText(RegisterActivity.this,"Welcome "+e[0].getText().toString()+".....",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(RegisterActivity.this,UserMenuActivity.class);
                        i.putExtra("Name",e[0].getText().toString());
                        i.putExtra("LoginID",e[1].getText().toString());
                        i.putExtra("Password",e[2].getText().toString());
                        i.putExtra("LorP",e[3].getText().toString());
                        startActivity(i);
                        finish();
                    }
                    else  {Toast.makeText(RegisterActivity.this,"LoginId Already Exist\nPlease Try again.....",Toast.LENGTH_SHORT).show();
                    }
                }
                else {Toast.makeText(RegisterActivity.this,"Password does not match\nPlease Retype Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(this,MenuScreenActivity.class));
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
