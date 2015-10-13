package com.example.hp.e_medicos;

import android.content.Context;
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


public class InsertEquipmentActivity extends ActionBarActivity {
    Button b,b2;
    EditText e,e2,e3;
    Context ctx=this;
    Bundle bundle;
    String loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_eqipment);
        b=(Button)findViewById(R.id.button12);
        b2=(Button)findViewById(R.id.button3);
        e=(EditText)findViewById(R.id.editText4);
        e2=(EditText)findViewById(R.id.editText5);
        e3=(EditText)findViewById(R.id.editText6);
        bundle=getIntent().getExtras();
        loc=bundle.getString("LorP");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws Error{
                DatabaseOperations edb=new DatabaseOperations(ctx,"Equipments");
                try {
                    edb.createDataBase();
                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }
                try {
                    edb.openDataBase();
                }catch(SQLException sqle){
                    throw sqle;
                }
                long p=edb.put("EQUIPMENT",e.getText().toString().toUpperCase(),e2.getText().toString(),e3.getText().toString(),loc);
                if(p!=-1) {
                    Toast.makeText(InsertEquipmentActivity.this, "Successfully Inserted....", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(InsertEquipmentActivity.this, AdminMenuActivity.class).putExtras(bundle));
                    finish();
                }
                else{ Toast.makeText(InsertEquipmentActivity.this,"Data Not Inserted !!!!",Toast.LENGTH_SHORT).show();
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
