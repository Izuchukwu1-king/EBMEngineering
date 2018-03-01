package com.example.android.ebmengineering;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ProgressBar progressBar;

    TextView toptitle;
    ImageView topimage;

    EditText first, last, edu, contact, email,confirm,phone,countrycode;



    private Spinner country, course;
    private Button submitButton;


    private Button onclicklistener;

    String firstnameinput, lastnameinput, levelofeducationinput, contactinformationinput,emailinput,confirmemailinput,phonenumberinput, contrycodeinput, selectcountryinput;

    // Declaring connection variables
    Connection con;
    String un, pass, db, ip;
    String mess;
    //End Declaring connection variables


    Button submitInfoButton;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Declaring Server ip, username, database name and password
        ip   = "nepamsonline.com";
        db   = "EBM";
        un   = "john";
        pass = "kingjohn";
        // Declaring Server ip, username, database name and password
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        toptitle = (TextView) findViewById(R.id.title);
        topimage = (ImageView) findViewById(R.id.image);
        course = (Spinner)findViewById(R.id.course);
        first = (EditText) findViewById(R.id.FirstName);
        last = (EditText) findViewById(R.id.LastName);
        edu = (EditText) findViewById(R.id.LevelofEducation);
        contact = (EditText) findViewById(R.id.ContactInformation);
        email = (EditText) findViewById(R.id.EnterYourEmail);
        confirm = (EditText) findViewById(R.id.ConfirmyourlEmail);
        phone = (EditText) findViewById(R.id.InputYourPhonenumber);
        countrycode = (EditText) findViewById(R.id.InputYourCountryCode);
        country= (Spinner) findViewById(R.id.country);
        submitButton=(Button)findViewById(R.id.btnSubmit);


        // Create an ArrayAdapter using the string_array and a default spinner layout
        ArrayAdapter<CharSequence> course_adapter = ArrayAdapter
                .createFromResource(this,
                        R.array.course_option,
                         android.R.layout.simple_spinner_item);


       //Specify the layout to use when the list of choices appears
        course_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

      //Apply the adapter to the spinner2
        course.setAdapter(course_adapter);


        course.setOnItemSelectedListener(this);







        //action listener for button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

    /*              CheckBox softwareCheckBox = (CheckBox) findViewById(R.id.Software_checkbox);
                  CheckBox hardwareCheckBox = (CheckBox) findViewById(R.id.Hardware_checkbox);
                  boolean hasprogramming = programmingCheckBox.isChecked();
 * /                boolean hassoftware = softwareCheckBox.isChecked();
                boolean hashardware = hardwareCheckBox.isChecked();

/*                //Indicates whether the view is currently in pressed state.
                hard.isPressed();//return True if this view is enabled, false otherwise.
                soft.isPressed();
                prog.isPressed();
                //set strings to String values of EditText objects
*/
                firstnameinput = first.getText().toString();
                lastnameinput = last.getText().toString();
                levelofeducationinput = edu.getText().toString();


                contactinformationinput = contact.getText().toString();
                emailinput = email.getText().toString();
                confirmemailinput = confirm.getText().toString();
                 phonenumberinput= phone.getText().toString();
                contrycodeinput = countrycode.getText().toString();



                CheckLogin checkLogin = new CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkLogin.execute("");


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class CheckLogin extends AsyncTask<String,String,String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(MainActivity.this, "Good", Toast.LENGTH_LONG).show();
                //finish();
            }
        }

        @Override
        protected String doInBackground(String... params) {


            //  String username = username.getText().toString();
            //  String password = password.getText().toString();
            if (firstnameinput.trim().equals("") && lastnameinput.trim().equals("") && levelofeducationinput.trim().equals("") && contactinformationinput.trim().equals("") && emailinput.trim().equals("") && confirmemailinput.trim().equals("") && phonenumberinput.trim().equals("") && selectcountryinput.trim().equals("") && contrycodeinput.trim().equals(""))


                z = "Please fill all values";

            else {
                try {
                    con = connectionclass(un, pass, db, ip);        // Connect to database
                    if (con == null) {
                       z = "Check Your Internet Access!";

                    } else {
                        // Change below query according to your own database.
                        String query = "insert into register (course, first_name,last_lame,education_level,contact_add,email_add,confirm_email,phone_no,conutry_input,conutry_code  )" +
                                 "values ('" + course + "','" + firstnameinput + "','" + lastnameinput + "','" + levelofeducationinput + "','" + contactinformationinput + "','" + emailinput + "','" + confirmemailinput+ "','" + phonenumberinput + "','" + selectcountryinput + "','" + contrycodeinput + "')";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            z = "New Customer Record Created";
                            isSuccess = true;
                            con.close();
                        } else {
                            z = "Incomplete information";
                            isSuccess = false;
                        }

                    }
                    return z;
                } catch (SQLException ex) {
                    isSuccess = false;
                    ex.printStackTrace();
                    z = ex.getMessage();
                }
              }
            return z;

        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server)
    {
        String z = "";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://nepamsonline.com/EBM;user=john;password=kingjohn;useNTLMv2=true;integratedSecurity=true";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {

            Toast.makeText(MainActivity.this, se.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;

    }


    // add items into spinner dynamically


    public void addListenerOnSpinnerItemSelection() {
        country = (Spinner) findViewById(R.id.course);
        course = (Spinner) findViewById(R.id.country);



    }

    public Button Onclicklistener() {
        return onclicklistener;
    }

    // get the selected dropdown list value
    public void addListenerOnButton () {
     submitButton.setText("onClickListener");
        country = (Spinner) findViewById(R.id.course);
        course = (Spinner) findViewById (R.id.country);








        submitButton = (Button) findViewById(R.id.btnSubmit);



                 addListenerOnButton(); findViewById(R.id.btnSubmit);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Code here executes on main thread after user presses button


                    }



});

    }

}

