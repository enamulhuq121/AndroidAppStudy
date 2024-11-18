
 package forms_activity;


 import android.annotation.SuppressLint;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.HashMap;
 import java.util.List;
 import android.app.*;
 import android.app.AlertDialog;
 import android.app.DatePickerDialog;
 import android.app.Dialog;
 import android.app.TimePickerDialog;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.location.Location;
 import android.view.KeyEvent;
 import android.os.Bundle;
 import android.view.View;
 import android.view.MotionEvent;
 import android.widget.AdapterView;
 import android.widget.Button;
 import android.widget.CheckBox;
 import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.LinearLayout;
 import android.widget.RadioButton;
 import android.widget.RadioGroup;
 import android.widget.SimpleAdapter;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.TimePicker;
 import android.widget.ArrayAdapter;
 import android.graphics.Color;
 import android.view.WindowManager;
 import androidx.constraintlayout.widget.ConstraintLayout;
 import android.widget.AutoCompleteTextView;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.content.ContextCompat;
 import forms_datamodel.Specialist_DataModel;
 import Utility.*;
 import Common.*;
 import android.widget.Toast;
 import android.text.TextWatcher;
 import android.widget.CompoundButton;
 import android.text.Editable;

 import org.icddrb.mental_health_survey.R;

 public class Specialist extends AppCompatActivity {
    //Disabled Back/Home key
    //--------------------------------------------------------------------------------------------------
    @Override 
    public boolean onKeyDown(int iKeyCode, KeyEvent event)
    {
        if(iKeyCode == KeyEvent.KEYCODE_BACK || iKeyCode == KeyEvent.KEYCODE_HOME) 
             { return false; }
        else { return true;  }
    }

    boolean networkAvailable=false;
    Location currentLocation; 
    double currentLatitude,currentLongitude; 
    String VariableID;
    private int hour;
    private int minute;
    private int mDay;
    private int mMonth;
    private int mYear;
    static final int DATE_DIALOG = 1;
    static final int TIME_DIALOG = 2;

    Connection C;
    Global g;
    SimpleAdapter dataAdapter;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    TextView lblHeading;
    LinearLayout seclbl1;
    View linelbl1;
    LinearLayout secSpecialistID;
    View lineSpecialistID;
    TextView lblSpecialistID;
    TextView VlblSpecialistID;
    EditText txtSpecialistID;
    LinearLayout secFacilityID;
    View lineFacilityID;
    TextView lblFacilityID;
    TextView VlblFacilityID;
    EditText txtFacilityID;
    LinearLayout secreg_date;
    View linereg_date;
    TextView lblreg_date;
    TextView Vlblreg_date;
    EditText txtreg_date;
    LinearLayout secName;
    View lineName;
    TextView lblName;
    TextView VlblName;
    EditText txtName;
    LinearLayout secSpecialty;
    View lineSpecialty;
    TextView lblSpecialty;
    TextView VlblSpecialty;
    EditText txtSpecialty;
    LinearLayout secQualification;
    View lineQualification;
    TextView lblQualification;
    TextView VlblQualification;
    EditText txtQualification;
    LinearLayout secExpeYear;
    View lineExpeYear;
    TextView lblExpeYear;
    TextView VlblExpeYear;
    EditText txtExpeYear;
    LinearLayout secContactNumber;
    View lineContactNumber;
    TextView lblContactNumber;
    TextView VlblContactNumber;
    EditText txtContactNumber;

    static int MODULEID   = 0;
    static int LANGUAGEID = 0;
    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID  = "";
    static String ENTRYUSER = "";
    MySharedPreferences sp;

    Bundle IDbundle;
    static String SPECIALISTID = "";
    static String FACILITYID = "";

 @SuppressLint("ClickableViewAccessibility")
 public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
   try
     {
         setContentView(R.layout.specialist);
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         C = new Connection(this);
         g = Global.getInstance();

         STARTTIME = g.CurrentTime24();
         DEVICEID  = MySharedPreferences.getValue(this, "deviceid");
         ENTRYUSER = MySharedPreferences.getValue(this, "userid");

         IDbundle = getIntent().getExtras();
         SPECIALISTID = IDbundle.getString("SpecialistID");
         FACILITYID = IDbundle.getString("FacilityID");

         TableName = "Specialist";
         MODULEID = 8;
         LANGUAGEID = Integer.parseInt(MySharedPreferences.getValue(this, "languageid"));
         lblHeading = (TextView)findViewById(R.id.lblHeading);

         ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
         cmdBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 AlertDialog.Builder adb = new AlertDialog.Builder(Specialist.this);
                 adb.setTitle("Close");

                 adb.setMessage("Do you want to close this form[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }});
                 adb.show();
             }});

        Initialization();
        Connection.LocalizeLanguage(Specialist.this, MODULEID, LANGUAGEID);


         txtreg_date.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 if(event.getAction() == MotionEvent.ACTION_UP) {
                      VariableID = "btnreg_date"; showDialog(DATE_DIALOG);
                      return true;
                 }
                 return false;
             }
         });



         //Hide all skip variables


        DataSearch(SPECIALISTID,FACILITYID);


        Button cmdSave = (Button) findViewById(R.id.cmdSave);
        cmdSave.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) { 
            DataSave();
        }});


     }
     catch(Exception  e)
     {
         Connection.MessageBox(Specialist.this, e.getMessage());
         return;
     }
 }

 @SuppressLint("ClickableViewAccessibility")
 private void Initialization()
 {
   try
     {
          seclbl1 = (LinearLayout) findViewById(R.id.seclbl1);
          linelbl1 = (View) findViewById(R.id.linelbl1);
          secSpecialistID = (LinearLayout) findViewById(R.id.secSpecialistID);
          lineSpecialistID = (View) findViewById(R.id.lineSpecialistID);
          lblSpecialistID = (TextView) findViewById(R.id.lblSpecialistID);
          VlblSpecialistID = (TextView) findViewById(R.id.VlblSpecialistID);
          txtSpecialistID = (EditText) findViewById(R.id.txtSpecialistID);
         if (SPECIALISTID.length() == 0) txtSpecialistID.setText(C.NewSpecialistID(DEVICEID));
         else txtSpecialistID.setText(SPECIALISTID); 
         txtSpecialistID.setEnabled(false);

         secFacilityID = (LinearLayout) findViewById(R.id.secFacilityID);
         lineFacilityID = (View) findViewById(R.id.lineFacilityID);
         lblFacilityID = (TextView) findViewById(R.id.lblFacilityID);
         VlblFacilityID = (TextView) findViewById(R.id.VlblFacilityID);
         txtFacilityID = (EditText) findViewById(R.id.txtFacilityID);
         txtFacilityID.setText(FACILITYID);
         txtFacilityID.setEnabled(false);

         secreg_date = (LinearLayout) findViewById(R.id.secreg_date);
         linereg_date = (View) findViewById(R.id.linereg_date);
         lblreg_date = (TextView) findViewById(R.id.lblreg_date);
         Vlblreg_date = (TextView) findViewById(R.id.Vlblreg_date);
         txtreg_date = (EditText) findViewById(R.id.txtreg_date);

         secName = (LinearLayout) findViewById(R.id.secName);
         lineName = (View) findViewById(R.id.lineName);
         lblName = (TextView) findViewById(R.id.lblName);
         VlblName = (TextView) findViewById(R.id.VlblName);
         txtName = (EditText) findViewById(R.id.txtName);

         secSpecialty = (LinearLayout) findViewById(R.id.secSpecialty);
         lineSpecialty = (View) findViewById(R.id.lineSpecialty);
         lblSpecialty = (TextView) findViewById(R.id.lblSpecialty);
         VlblSpecialty = (TextView) findViewById(R.id.VlblSpecialty);
         txtSpecialty = (EditText) findViewById(R.id.txtSpecialty);

         secQualification = (LinearLayout) findViewById(R.id.secQualification);
         lineQualification = (View) findViewById(R.id.lineQualification);
         lblQualification = (TextView) findViewById(R.id.lblQualification);
         VlblQualification = (TextView) findViewById(R.id.VlblQualification);
         txtQualification = (EditText) findViewById(R.id.txtQualification);

         secExpeYear = (LinearLayout) findViewById(R.id.secExpeYear);
         lineExpeYear = (View) findViewById(R.id.lineExpeYear);
         lblExpeYear = (TextView) findViewById(R.id.lblExpeYear);
         VlblExpeYear = (TextView) findViewById(R.id.VlblExpeYear);
         txtExpeYear = (EditText) findViewById(R.id.txtExpeYear);

         secContactNumber = (LinearLayout) findViewById(R.id.secContactNumber);
         lineContactNumber = (View) findViewById(R.id.lineContactNumber);
         lblContactNumber = (TextView) findViewById(R.id.lblContactNumber);
         VlblContactNumber = (TextView) findViewById(R.id.VlblContactNumber);
         txtContactNumber = (EditText) findViewById(R.id.txtContactNumber);
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Specialist.this, e.getMessage());
         return;
     }
 }

 private void DataSave()
 {
   try
     {
         String ValidationMSG = ValidationCheck();
         if(ValidationMSG.length()>0)
         {
         	Connection.MessageBox(Specialist.this, ValidationMSG);
         	return;
         }
 
         String SQL = "";
         RadioButton rb;

         Specialist_DataModel objSave = new Specialist_DataModel();
         objSave.setSpecialistID(txtSpecialistID.getText().toString());
         objSave.setFacilityID(txtFacilityID.getText().toString());
         objSave.setreg_date(txtreg_date.getText().toString().length() > 0 ? Global.DateConvertYMD(txtreg_date.getText().toString()) : txtreg_date.getText().toString());
         objSave.setName(txtName.getText().toString());
         objSave.setSpecialty(txtSpecialty.getText().toString());
         objSave.setQualification(txtQualification.getText().toString());
         objSave.setExpeYear(txtExpeYear.getText().toString());
         objSave.setContactNumber(txtContactNumber.getText().toString());
         objSave.setStartTime(STARTTIME);
         objSave.setEndTime(g.CurrentTime24());
         objSave.setDeviceID(DEVICEID);
         objSave.setEntryUser(ENTRYUSER); //from data entry user list
         objSave.setLat(MySharedPreferences.getValue(this, "lat"));
         objSave.setLon(MySharedPreferences.getValue(this, "lon"));

         String status = objSave.SaveUpdateData(this);
         if(status.length()==0) {
             Intent returnIntent = new Intent();
             returnIntent.putExtra("res", "");
             setResult(Activity.RESULT_OK, returnIntent);

             Connection.MessageBox(Specialist.this,"Save Successfully...");
             finish();
         }
         else{
             Connection.MessageBox(Specialist.this, status);
             return;
         }
     }
     catch(Exception  e)
     {
         Connection.MessageBox(Specialist.this, e.getMessage());
         return;
     }
 }

 private String ValidationCheck()
 {
   String ValidationMsg = "";
   String DV = "";
   try
     {
         ResetSectionColor();
         if(txtSpecialistID.getText().toString().length()==0 & secSpecialistID.isShown())
           {
             ValidationMsg +=  "\nRequired field: SpecialistID .";
             secSpecialistID.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtFacilityID.getText().toString().length()==0 & secFacilityID.isShown())
           {
             ValidationMsg += "\nRequired field: highlighted";
             secFacilityID.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         DV = Global.DateValidate(txtreg_date.getText().toString());
         if(DV.length()!=0 & secreg_date.isShown())
           {
               ValidationMsg += "\nRequired field: highlighted";
             secreg_date.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtName.getText().toString().length()==0 & secName.isShown())
           {
               ValidationMsg += "\nRequired field: highlighted";
             secName.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtSpecialty.getText().toString().length()==0 & secSpecialty.isShown())
           {
               ValidationMsg += "\nRequired field: highlighted";
             secSpecialty.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtQualification.getText().toString().length()==0 & secQualification.isShown())
           {
               ValidationMsg += "\nRequired field: highlighted";
             secQualification.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtExpeYear.getText().toString().length()==0 & secExpeYear.isShown())
           {
               ValidationMsg += "\nRequired field: highlighted";
             secExpeYear.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
         if(txtContactNumber.getText().toString().length()==0 & secContactNumber.isShown())
           {
               ValidationMsg += "\nRequired field: highlighted";
             secContactNumber.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.color_Section_Highlight));
           }
     }
     catch(Exception  e)
     {
         ValidationMsg += "\n"+ e.getMessage();
     }

     return ValidationMsg;
 }

 private void ResetSectionColor()
 {
   try
     {
             secSpecialistID.setBackgroundColor(Color.WHITE);
             secFacilityID.setBackgroundColor(Color.WHITE);
             secreg_date.setBackgroundColor(Color.WHITE);
             secName.setBackgroundColor(Color.WHITE);
             secSpecialty.setBackgroundColor(Color.WHITE);
             secQualification.setBackgroundColor(Color.WHITE);
             secExpeYear.setBackgroundColor(Color.WHITE);
             secContactNumber.setBackgroundColor(Color.WHITE);
     }
     catch(Exception  e)
     {
     }
 }

 private void DataSearch(String SpecialistID, String FacilityID)
     {
       try
        {     
           RadioButton rb;
           Specialist_DataModel d = new Specialist_DataModel();
           String SQL = "Select * from "+ TableName +"  Where SpecialistID='"+ SpecialistID +"' and FacilityID='"+ FacilityID +"'";
           List<Specialist_DataModel> data = d.SelectAll(this, SQL);
           for(Specialist_DataModel item : data){
             txtSpecialistID.setText(item.getSpecialistID());
             txtFacilityID.setText(item.getFacilityID());
             txtreg_date.setText(item.getreg_date().toString().length()==0 ? "" : Global.DateConvertDMY(item.getreg_date()));
             txtName.setText(item.getName());
             txtSpecialty.setText(item.getSpecialty());
             txtQualification.setText(item.getQualification());
             txtExpeYear.setText(item.getExpeYear());
             txtContactNumber.setText(item.getContactNumber());
           }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(Specialist.this, e.getMessage());
            return;
        }
     }



 protected Dialog onCreateDialog(int id) {
   final Calendar c = Calendar.getInstance();
   hour = c.get(Calendar.HOUR_OF_DAY);
   minute = c.get(Calendar.MINUTE);
   switch (id) {
       case DATE_DIALOG:
           return new DatePickerDialog(this, mDateSetListener,g.mYear,g.mMonth-1,g.mDay);
       case TIME_DIALOG:
           return new TimePickerDialog(this, timePickerListener, hour, minute,false);
       }
     return null;
 }

 private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
      mYear = year; mMonth = monthOfYear+1; mDay = dayOfMonth;
      EditText txtDate;


      txtDate = findViewById(R.id.txtreg_date);
      if (VariableID.equals("btnreg_date"))
      {
          txtDate = findViewById(R.id.txtreg_date);
      }
      txtDate.setText(new StringBuilder()
      .append(Global.Right("00"+mDay,2)).append("/")
      .append(Global.Right("00"+mMonth,2)).append("/")
      .append(mYear));
      }
  };

 private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
       hour = selectedHour; minute = selectedMinute;
       EditText tpTime;

    }
  };


 
 // turning off the GPS if its in on state. to avoid the battery drain.
 @Override
 protected void onDestroy() {
     // TODO Auto-generated method stub
     super.onDestroy();
 }
}