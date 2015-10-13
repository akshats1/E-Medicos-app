package com.example.hp.e_medicos;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class UserMenuActivity extends ListActivity {
    String [] Menu={"Search or Buy Medicine","Search or Buy Equipment","Change Password","Update Phone Number","Exit"};
    Bundle bundle;
    String userName,loginID,password,phoneOrLoc;
    DatabaseOperations db,db2;
    EditText e,e2,e3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ArrayAdapter adapt=new ArrayAdapter(this,android.R.layout.simple_list_item_1,Menu);

        bundle=getIntent().getExtras();
        userName=bundle.getString("Name");
        loginID=bundle.getString("LoginID");
        password=bundle.getString("Password");
        phoneOrLoc=bundle.getString("LorP");

        setListAdapter(adapt);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        switch (position)
        {
            case 0:Intent i=new Intent(this,MedicineSearchActivity.class);
                    i.putExtra("Login",true);
                    i.putExtra("Name",userName);
                    i.putExtra("LoginID",loginID);
                    i.putExtra("Password",password);
                  startActivity(i);
                break;
            case 1: Intent i2=new Intent(this,EquipmentSearchActivity.class);
                i2.putExtra("Login",true);
                i2.putExtra("Name",userName);
                i2.putExtra("LoginID",loginID);
                i2.putExtra("Password",password);
                  startActivity(i2);
                break;
            case 2:AlertDialog.Builder builder = new AlertDialog.Builder(UserMenuActivity.this);
                LayoutInflater inflater = UserMenuActivity.this.getLayoutInflater();
                db=new DatabaseOperations(this,"Users");
                try {
                    db.createDataBase();
                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }
                try {
                    db.openDataBase();
                }catch(SQLException sqle){
                    throw sqle;
                }
                final View view=inflater.inflate(R.layout.change_password, null);
                builder.setView(view)
                        .setPositiveButton("CHANGE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                e = (EditText)view.findViewById(R.id.editText10);
                                e2 = (EditText)view.findViewById(R.id.editText11);
                                if(e.getText().toString().equals(e2.getText().toString()))
                                {
                                    long r=db.update("Users",loginID,e.getText().toString(),userName,phoneOrLoc);
                                    if(r>0) Toast.makeText(UserMenuActivity.this,"Password Successfully Changed..",Toast.LENGTH_SHORT).show();
                                    else Toast.makeText(UserMenuActivity.this,"Password Does not Changed..",Toast.LENGTH_SHORT).show();
                                }
                                else Toast.makeText(UserMenuActivity.this,"Password Does not match\nPlease Try Again....",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case 3:AlertDialog.Builder builder2 = new AlertDialog.Builder(UserMenuActivity.this);
                LayoutInflater inflater2 = UserMenuActivity.this.getLayoutInflater();
                db2=new DatabaseOperations(this,"Users");
                try {
                    db2.createDataBase();
                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }
                try {
                    db2.openDataBase();
                }catch(SQLException sqle){
                    throw sqle;
                }
                final View view1=inflater2.inflate(R.layout.update_phone, null);
                builder2.setView(view1)
                        .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                e3 = (EditText)view1.findViewById(R.id.editText12);
                                long r = db2.update("Users",loginID,password,userName,e3.getText().toString() );
                                if (r > 0)
                                    Toast.makeText(UserMenuActivity.this, "Phone Number Successfully Updated..", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(UserMenuActivity.this, "Problem in Updating....", Toast.LENGTH_SHORT).show();
                            }
                            })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                break;
            case 4:
                finish();
                break;
        }
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
