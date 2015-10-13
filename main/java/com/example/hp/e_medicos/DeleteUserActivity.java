package com.example.hp.e_medicos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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


public class DeleteUserActivity extends ActionBarActivity {
    EditText et;
    Button b,b2;
    DatabaseOperations udb;
    AlertDialog.Builder builder;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws Error{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        et=(EditText)findViewById(R.id.editText3);
        b=(Button)findViewById(R.id.button3);
        b2=(Button)findViewById(R.id.button11);
        bundle=getIntent().getExtras();
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
        builder = new AlertDialog.Builder(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Are you sure you want to Delete User Data?")
                        .setTitle("Delete User");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        long r = udb.deleteUser(et.getText().toString());
                        if (r > 0) {
                            Toast.makeText(DeleteUserActivity.this, "User Data Successfully Deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DeleteUserActivity.this, AdminMenuActivity.class).putExtras(bundle));
                            finish();
                        } else
                            Toast.makeText(DeleteUserActivity.this, "User Login ID not found\nPlease Try Again......", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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
