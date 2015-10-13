package com.example.hp.e_medicos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class BuyActivity extends ActionBarActivity {
    TextView tv,tv2;
    Button b,b2;
    EditText e0,e,e2;
    DatabaseOperations DOP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        tv=(TextView)findViewById(R.id.textView4);
        tv2=(TextView)findViewById(R.id.textView12);
        b=(Button)findViewById(R.id.button13);
        b2=(Button)findViewById(R.id.button14);
        e0=(EditText)findViewById(R.id.editText8);
        DOP=new DatabaseOperations(BuyActivity.this,"Users");

        Bundle bundle=getIntent().getExtras();
        final Boolean log=bundle.getBoolean("Login");
        final String name=bundle.getString("PName");
        final String price=bundle.getString("Price");
        final String quantity=bundle.getString("Qty");
        final String userName=bundle.getString("UserName");
        final String LoginID=bundle.getString("LoginID");
        final String fromLoc=bundle.getString("fromLoc");
        final String password=bundle.getString("Password");

        tv.setText(name+"");
        tv2.setText(price+"");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (log == false) {
                    final int userQty = Integer.parseInt(e0.getText().toString());
                    if (userQty > Integer.parseInt(quantity)) {
                        Toast.makeText(BuyActivity.this, userQty + " is not available in stock\nEnter Quantity within " + quantity, Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BuyActivity.this);
                        LayoutInflater inflater = BuyActivity.this.getLayoutInflater();
                        try {
                            DOP.createDataBase();
                        } catch (IOException ioe) {
                            throw new Error("Unable to create database");
                        }
                        try {
                            DOP.openDataBase();
                        } catch (SQLException sqle) {
                            throw sqle;
                        }
                        final View view = inflater.inflate(R.layout.nonlogin_buy, null);
                        builder.setView(view);
                        builder.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                e = (EditText) view.findViewById(R.id.editText17);
                                e2 = (EditText) view.findViewById(R.id.editText14);
                                String newName = e.getText().toString();
                                String pass = e2.getText().toString();
                                Cursor CR = DOP.getInformationUser(newName, pass);
                                if (CR.getCount() != 0) {
                                    CR.moveToFirst();
                                    do {
                                        if (newName.equals(CR.getString(0)) && pass.equals(CR.getString(1))) {
                                            Toast.makeText(BuyActivity.this,"Hello "+CR.getString(2)+"....\nThank you for purchasing  :)", Toast.LENGTH_SHORT).show();

                                            Intent i = new Intent(BuyActivity.this, GeneratedBillActivity.class);
                                            i.putExtra("PName", name);
                                            i.putExtra("UQty", userQty);
                                            i.putExtra("Bill", userQty * Integer.parseInt(price));
                                            i.putExtra("fromLoc",fromLoc);
                                            i.putExtra("UserName",CR.getString(2) );
                                            i.putExtra("LoginID", newName);
                                            i.putExtra("Password",password);
                                            startActivity(i);
                                            finish();
                                        }
                                } while (CR.moveToNext()) ;
                            }

                            else

                            {
                                Toast.makeText(BuyActivity.this, "LoginID or Password is Incorrect\n!!   Please Try Again  !!", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNeutralButton("REGISTER", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(BuyActivity.this, RegisterActivity.class));
                            finish();
                        }
                    })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } }
                else {
                    int userQty2 = Integer.parseInt(e0.getText().toString());

                    if (userQty2 > Integer.parseInt(quantity)) {
                        Toast.makeText(BuyActivity.this, userQty2 + " is not available in stock\nEnter Quantity within " + quantity, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BuyActivity.this,"Thank you for purchasing  :)", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(BuyActivity.this, GeneratedBillActivity.class);
                        i.putExtra("PName", name);
                        i.putExtra("UQty", userQty2);
                        i.putExtra("Bill", userQty2 * Integer.parseInt(price));
                        i.putExtra("UserName", userName);
                        i.putExtra("LoginID", LoginID);
                        i.putExtra("fromLoc",fromLoc);
                        startActivity(i);
                        finish();
                    }
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
                startActivity(new Intent(this, MenuScreenActivity.class));
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
