package com.example.hp.e_medicos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MenuScreenActivity extends ActionBarActivity {
    Button b[];
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        b=new Button[7];
        b[0]=(Button)findViewById(R.id.button9);
        b[1]=(Button)findViewById(R.id.button10);
        b[2]=(Button)findViewById(R.id.button4);
        b[3]=(Button)findViewById(R.id.button5);
        b[4]=(Button)findViewById(R.id.button6);
        b[5]=(Button)findViewById(R.id.button7);
        b[6]=(Button)findViewById(R.id.button8);
        b[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuScreenActivity.this,MedicineSearchActivity.class);
                i.putExtra("Login",false);
                i.putExtra("Name","");
                i.putExtra("LoginID","");
                startActivity(i);
            }
        });
        b[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuScreenActivity.this,EquipmentSearchActivity.class);
                i.putExtra("Login",false);
                i.putExtra("Name","");
                i.putExtra("LoginID","");
                startActivity(i);
            }
        });
        b[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuScreenActivity.this,LoginActivity.class));
            }
        });
        b[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuScreenActivity.this,RegisterActivity.class));
            }
        });
        b[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(MenuScreenActivity.this);
                builder.setMessage("Are you sure you want to exit?").setTitle("Confirm Exit");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent j=new Intent(Intent.ACTION_MAIN);
                        j.addCategory(Intent.CATEGORY_HOME);
                        j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(j);
                        finish();
                        System.exit(0);
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
        b[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(MenuScreenActivity.this);
                builder.setMessage("I am using an app named E-Medicos for online purchasing of Medical equipments and different Medicines easily........\nTry this app Now").setTitle("Share E-Medicos");
                builder.setPositiveButton("SHARE INFO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                emailIntent.setData(Uri.parse("mailto:"));
                                emailIntent.setType("text/plain");
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "E-Medicos");
                                emailIntent.putExtra(Intent.EXTRA_TEXT, "I am using an app named E-Medicos for online purchasing of Medical equipments and different Medicines easily........\nTry this app Now");

                                try {
                                    startActivity(Intent.createChooser(emailIntent, "Share Info....."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(MenuScreenActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("DON'T SHARE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        b[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuScreenActivity.this, HelpActivity.class));
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK :
                System.exit(0);
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
