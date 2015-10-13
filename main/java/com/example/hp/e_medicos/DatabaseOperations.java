package com.example.hp.e_medicos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseOperations extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.example.hp.e_medicos/databases/";
    private static String DB_NAME;
    private SQLiteDatabase myDataBase;
    private final Context myContext;


    public DatabaseOperations (Context context,String dbName)
    {
        super(context,dbName, null, 2);
        DB_NAME=dbName;
        this.myContext=context;
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        if(!dbExist)
        {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getWritableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }catch(SQLiteException e){
            //database does't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onCreate(SQLiteDatabase sdb ) {
    }

    public long put(String tableName, String name,String price,String quantity,String fromLocation)
    {
        ContentValues cv= new ContentValues();
        cv.put("name", name);
        cv.put("price",price);
        cv.put("qty", quantity);
        cv.put("fromLoc",fromLocation);
        long k=myDataBase.insert(tableName, null, cv);
        return k;
    }
    public long putInformation(String tableName,String username,String id, String pass,String phone)
    {
        ContentValues cv= new ContentValues();
        cv.put("name", username);
        cv.put("loginId",id);
        cv.put("password",pass);
        cv.put("phoneNumber", phone);
        long k=myDataBase.insert(tableName, null, cv);
        return k;
    }
    public Cursor getInformation(String tableName,String name)
    {
        String[] strings={name};
        Cursor CR=myDataBase.rawQuery("select name,price,qty,fromLoc from " + tableName + " where name=?", strings);
        return CR;
    }
    public Cursor getInformationUser(String loginId,String password )
    {
        String [] s={loginId,password};
        Cursor CR=myDataBase.rawQuery("select loginId,password,name,phoneNumber from Users where loginId=? and password=?", s);
        return CR;
    }
    public long updateInformation( String tableName,String name,String newPrice,String newQuantity,String fromLocation)
    {
        ContentValues cv= new ContentValues();
        cv.put("name",name);
        cv.put("price",newPrice);
        cv.put("qty", newQuantity);
        cv.put("fromLoc",fromLocation);
        String[] s={name};
        long i=myDataBase.update(tableName, cv, "name=?", s);
        return i;
    }

    public long deleteUser(String loginId)
    {
        String[] strings={loginId};
        long ans=myDataBase.delete("USERS", "loginId=?", strings);
        return ans;
    }
    public Cursor getInformationAdmin(String loginId,String pass )
    {
        String [] s={loginId,pass};
        Cursor CR=myDataBase.rawQuery("select loginId,password,name,location from Admin where loginId=? and Password=?", s);
        return CR;
    }
    public long update( String tableName,String loginID,String pass,String name,String locOrPhone)
    {
        ContentValues cv= new ContentValues();
        cv.put("name",name);
        cv.put("LoginID",loginID);
        cv.put("password",pass);
        String[] s={loginID};
        if(tableName.equals("Users"))  cv.put("phoneNumber",locOrPhone);
        else cv.put("Location",locOrPhone);
        long i=myDataBase.update(tableName, cv, "loginId=?", s);
        return i;
    }
}