package com.example.hp.e_medicos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
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


public class LoginActivity extends ActionBarActivity {
    EditText et1,et2;
    Button b1,b2;
    String name,pass;
    DatabaseOperations DOP,DOP2;
    int f=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws  Error{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et1=(EditText)findViewById(R.id.editText2);
        et2=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button2);
        b2=(Button)findViewById(R.id.button);
        DOP = new DatabaseOperations(this,"Users");
        try {
            DOP.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            DOP.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }
        DOP2 = new DatabaseOperations(this,"Admins");
        try {
            DOP2.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            DOP2.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }
        b1.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      name = et1.getText().toString();
                                      pass = et2.getText().toString();

                                      Cursor CR = DOP.getInformationUser(name,pass);
                                      if(CR.getCount()!=0)
                                      {
                                          CR.moveToFirst();
                                          do {
                                              if (name.equals(CR.getString(0)) && pass.equals(CR.getString(1))) {
                                                  Toast.makeText(LoginActivity.this, "Hello "+CR.getString(2)+"....", Toast.LENGTH_SHORT).show();
                                                  Intent i = new Intent(LoginActivity.this, UserMenuActivity.class);
                                                  i.putExtra("Name",CR.getString(2));
                                                  i.putExtra("LoginID",name);
                                                  i.putExtra("Password",pass);
                                                  i.putExtra("LorP",CR.getString(3));
                                                  f=1;
                                                  startActivity(i);
                                                  finish();
                                              }
                                          } while (CR.moveToNext());
                                      }

                                      Cursor CR2 = DOP2.getInformationAdmin(name,pass);
                                      if(CR2.getCount()!=0)
                                      {
                                          CR2.moveToFirst();
                                          do {
                                              if (name.equals(CR2.getString(0)) && pass.equals(CR2.getString(1))) {
                                                  Toast.makeText(LoginActivity.this, "Hello "+CR2.getString(2)+"....", Toast.LENGTH_SHORT).show();
                                                  Intent i = new Intent(LoginActivity.this, AdminMenuActivity.class);
                                                  i.putExtra("Name",CR2.getString(2));
                                                  i.putExtra("LoginID",name);
                                                  i.putExtra("Password",pass);
                                                  i.putExtra("LorP",CR2.getString(3));
                                                  startActivity(i);
                                                  finish();
                                              }
                                          } while (CR.moveToNext());
                                      }
                                      else
                                      if (f==0)
                                      {
                                          Toast.makeText(LoginActivity.this, "LoginID or Password is Incorrect\n!!   Please Try Again  !!", Toast.LENGTH_LONG).show();
                                      }
                                  }

                              }
        );
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
