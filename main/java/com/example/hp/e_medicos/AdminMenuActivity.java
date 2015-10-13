package com.example.hp.e_medicos;

import android.app.Activity;
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


public class AdminMenuActivity extends ListActivity {
    String [] Menu={"Insert New Medicine","Insert New Equipment","Update Medicine","Update Equipment","Delete User","Change Password","Exit"};
    //String [] infoMenu={"Increase Database","Increase Database","Modify Database","Modify Database","Delete Unused Accounts","Back to Main Menu"};
    DatabaseOperations db;
    String loginID,password,phoneOrLoc,adminName;
    EditText e,e2;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws Error
    {
        super.onCreate(savedInstanceState);
        ArrayAdapter adapt=new ArrayAdapter(this,android.R.layout.simple_list_item_1,Menu);
        bundle=getIntent().getExtras();
        adminName=bundle.getString("Name");
        loginID=bundle.getString("LoginID");
        password=bundle.getString("Password");
        phoneOrLoc=bundle.getString("LorP");
        setListAdapter(adapt);
    }
/*
    class MyAdapter extends ArrayAdapter
    {
        public MyAdapter(Context context,int resource,String[] objects)
        {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater li=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=li.inflate(R.layout.activity_admin_menu,parent,false);

            ImageView iv=(ImageView)row.findViewById(R.id.imageView);
            TextView tv=(TextView)row.findViewById(R.id.textView3);
            TextView tv2=(TextView)row.findViewById(R.id.textView2);

            tv.setText(Menu[position]);
            tv2.setText(infoMenu[position]);

            switch (position)
            {
                case 0: iv.setImageResource(R.drawable.pills);
                        break;
                case 1: iv.setImageResource(R.drawable.stethoscope);
                        break;
                case 2: iv.setImageResource(R.drawable.pills);
                    break;
                case 3: iv.setImageResource(R.drawable.stethoscope);
                    break;
                case 4: iv.setImageResource(R.drawable.images);
                    break;
                case 5: iv.setImageResource(R.drawable.exit);
                    break;
            }
            return row;
        }
    }
*/
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) throws Error
    {
        super.onListItemClick(l, v, position, id);
        switch (position)
        {
            case 0:Intent i=new Intent(this,InsertMedicineActivity.class);
                i.putExtras(bundle);
                startActivity(i);
                finish();
                break;
            case 1: startActivity(new Intent(this,InsertEquipmentActivity.class).putExtras(bundle));
                finish();
                break;
            case 2: startActivity(new Intent(this,UpdateMedicineActivity.class).putExtras(bundle));
                finish();
                break;
            case 3: startActivity(new Intent(this,UpdateEquipmentActivity.class).putExtras(bundle));
                finish();
                break;
            case 4: startActivity(new Intent(this,DeleteUserActivity.class).putExtras(bundle));
                finish();
                break;
            case 5:AlertDialog.Builder builder = new AlertDialog.Builder(AdminMenuActivity.this);
                LayoutInflater inflater = AdminMenuActivity.this.getLayoutInflater();
                db=new DatabaseOperations(this,"Admins");
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
                                if((e.getText().toString()).equals(e2.getText().toString()))
                                {
                                    long r=db.update("Admin",loginID,e.getText().toString(),adminName,phoneOrLoc);
                                    if(r>0) Toast.makeText(AdminMenuActivity.this,"Password Successfully Changed..",Toast.LENGTH_SHORT).show();
                                    else Toast.makeText(AdminMenuActivity.this,"Password Does not Changed..",Toast.LENGTH_SHORT).show();
                                }
                                else Toast.makeText(AdminMenuActivity.this,"Password Does not match\nPlease Try Again....",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case 6: startActivity(new Intent(this,MenuScreenActivity.class));
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
