package forms_datamodel;

import android.content.Context;
 import android.annotation.SuppressLint;
 import android.database.Cursor;
 import Common.Connection;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import java.util.HashMap;
 import Utility.AuditTrial;
 import Common.Global;
 import android.content.ContentValues;

 public class Specialist_DataModel{

        private String _SpecialistID = "";
        public String getSpecialistID(){
              return _SpecialistID;
         }
        public void setSpecialistID(String newValue){
              _SpecialistID = newValue;
         }
        private String _FacilityID = "";
        public String getFacilityID(){
              return _FacilityID;
         }
        public void setFacilityID(String newValue){
              _FacilityID = newValue;
         }
        private String _reg_date = "";
        public String getreg_date(){
              return _reg_date;
         }
        public void setreg_date(String newValue){
              _reg_date = newValue;
         }
        private String _Name = "";
        public String getName(){
              return _Name;
         }
        public void setName(String newValue){
              _Name = newValue;
         }
        private String _Specialty = "";
        public String getSpecialty(){
              return _Specialty;
         }
        public void setSpecialty(String newValue){
              _Specialty = newValue;
         }
        private String _Qualification = "";
        public String getQualification(){
              return _Qualification;
         }
        public void setQualification(String newValue){
              _Qualification = newValue;
         }
        private String _ExpeYear = "";
        public String getExpeYear(){
              return _ExpeYear;
         }
        public void setExpeYear(String newValue){
              _ExpeYear = newValue;
         }
        private String _ContactNumber = "";
        public String getContactNumber(){
              return _ContactNumber;
         }
        public void setContactNumber(String newValue){
              _ContactNumber = newValue;
         }
        private String _StartTime = "";
        public void setStartTime(String newValue){
              _StartTime = newValue;
         }
        private String _EndTime = "";
        public void setEndTime(String newValue){
              _EndTime = newValue;
         }
        private String _DeviceID = "";
        public void setDeviceID(String newValue){
              _DeviceID = newValue;
         }
        private String _EntryUser = "";
        public void setEntryUser(String newValue){
              _EntryUser = newValue;
         }
        private String _Lat = "";
        public void setLat(String newValue){
              _Lat = newValue;
         }
        private String _Lon = "";
        public void setLon(String newValue){
              _Lon = newValue;
         }
        private String _EnDt = Global.DateTimeNowYMDHMS();
        private int _Upload = 2;
        private String _modifyDate = Global.DateTimeNowYMDHMS();

        String TableName = "Specialist";

        public String SaveUpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            String SQL = "";
            try
            {
                 if(C.Existence("Select * from "+ TableName +"  Where SpecialistID='"+ _SpecialistID +"' and FacilityID='"+ _FacilityID +"' "))
                    response = UpdateData(context);
                 else
                    response = SaveData(context);
            }
            catch(Exception  e)
            {
                 response = e.getMessage();
            }
           return response;
        }
        Connection C;

        private String SaveData(Context context)
        {
            String response = "";
            C = new Connection(context);
            try
              {
                 ContentValues contentValues = new ContentValues();
                 contentValues.put("SpecialistID", _SpecialistID);
                 contentValues.put("FacilityID", _FacilityID);
                 contentValues.put("reg_date", _reg_date);
                 contentValues.put("Name", _Name);
                 contentValues.put("Specialty", _Specialty);
                 contentValues.put("Qualification", _Qualification);
                 contentValues.put("ExpeYear", _ExpeYear);
                 contentValues.put("ContactNumber", _ContactNumber);
                 contentValues.put("isdelete", 2);
                 contentValues.put("StartTime", _StartTime);
                 contentValues.put("EndTime", _EndTime);
                 contentValues.put("DeviceID", _DeviceID);
                 contentValues.put("EntryUser", _EntryUser);
                 contentValues.put("Lat", _Lat);
                 contentValues.put("Lon", _Lon);
                 contentValues.put("EnDt", _EnDt);
                 contentValues.put("Upload", _Upload);
                 contentValues.put("modifyDate", _modifyDate);
                 C.InsertData(TableName, contentValues);
              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }

        private String UpdateData(Context context)
        {
            String response = "";
            C = new Connection(context);
            try
              {
                 ContentValues contentValues = new ContentValues();
                 contentValues.put("SpecialistID", _SpecialistID);
                 contentValues.put("FacilityID", _FacilityID);
                 contentValues.put("reg_date", _reg_date);
                 contentValues.put("Name", _Name);
                 contentValues.put("Specialty", _Specialty);
                 contentValues.put("Qualification", _Qualification);
                 contentValues.put("ExpeYear", _ExpeYear);
                 contentValues.put("ContactNumber", _ContactNumber);
                 contentValues.put("Upload", _Upload);
                 contentValues.put("modifyDate", _modifyDate);
                 C.UpdateData(TableName, "SpecialistID,FacilityID", (""+ _SpecialistID +", "+ _FacilityID +""), contentValues);


              }
              catch(Exception  e)
              {
                 response = e.getMessage();
              }
           return response;
        }


        int Count = 0;
        private int _Count = 0;
        public int getCount(){ return _Count; }

        @SuppressLint("Range")
        public List<Specialist_DataModel> SelectAll(Context context, String SQL)
        {
            Connection C = new Connection(context);
            List<Specialist_DataModel> data = new ArrayList<Specialist_DataModel>();
            Specialist_DataModel d = new Specialist_DataModel();
            Cursor cur = C.ReadData(SQL);

            cur.moveToFirst();
            while(!cur.isAfterLast())
            {
                Count += 1;
                d = new Specialist_DataModel();
                d._Count = Count;
                d._SpecialistID = cur.getString(cur.getColumnIndex("SpecialistID"));
                d._FacilityID = cur.getString(cur.getColumnIndex("FacilityID"));
                d._reg_date = cur.getString(cur.getColumnIndex("reg_date"));
                d._Name = cur.getString(cur.getColumnIndex("Name"));
                d._Specialty = cur.getString(cur.getColumnIndex("Specialty"));
                d._Qualification = cur.getString(cur.getColumnIndex("Qualification"));
                d._ExpeYear = cur.getString(cur.getColumnIndex("ExpeYear"));
                d._ContactNumber = cur.getString(cur.getColumnIndex("ContactNumber"));
                data.add(d);



                cur.moveToNext();
            }
            cur.close();
          return data;
        }

 }