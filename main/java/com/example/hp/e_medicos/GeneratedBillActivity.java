package com.example.hp.e_medicos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class GeneratedBillActivity extends ActionBarActivity {
    TextView tv;
    Button b;
    AlertDialog.Builder builder;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_bill);
        b=(Button)findViewById(R.id.button15);
        tv=(TextView)findViewById(R.id.textView13);

        bundle=getIntent().getExtras();
        final String productName=bundle.getString("PName");
        final int userQty=bundle.getInt("UQty");
        final int totalBill=bundle.getInt("Bill");
        final String name=bundle.getString("UserName");
        final String LoginID=bundle.getString("LoginID");
        final String fromLoc=bundle.getString("fromLoc");
        final String password=bundle.getString("Password");

        tv.setText("Buyer Name : "+name+"\nLoginID : "+LoginID+"\n\nProduct Name : "+productName+"\nQuantity : "+userQty+"\nProviding From :"+fromLoc+"\n\nTotal Bill is : "+totalBill);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(GeneratedBillActivity.this);
                builder.setMessage("Buyer Name : "+name+"\nLoginID : "+LoginID+"\n\nProduct Name : "+productName+"\nQuantity : "+userQty+"\nProviding From :"+fromLoc+"\n\nTotal Bill is : "+totalBill).setTitle("BILL");
                builder.setPositiveButton("SHARE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Total Bill");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Buyer Name : "+name+"\nLoginID : "+LoginID+"\n\nProduct Name : "+productName+"\nQuantity : "+userQty+"\nProviding From :"+fromLoc+"\n\nTotal Bill is : "+totalBill);

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Share....."));
                            finish();
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(GeneratedBillActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                bundle=getIntent().getExtras();
                final String productName=bundle.getString("PName");
                final int userQty=bundle.getInt("UQty");
                final int totalBill=bundle.getInt("Bill");
                final String name=bundle.getString("UserName");
                final String LoginID=bundle.getString("LoginID");
                final String fromLoc=bundle.getString("fromLoc");
                final String password=bundle.getString("Password");
                Intent i=new Intent(this,UserMenuActivity.class);
                i.putExtra("Name",name);
                i.putExtra("LoginID",LoginID);
                i.putExtra("Password",password);
                i.putExtra("LorP",fromLoc);
                startActivity(i);
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
