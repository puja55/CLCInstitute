package clc.datacubesoftech.clcinstitute.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {


    private String std_FatherName,std_image, std_mobile1,std_courseId,
            std_Gender,std_DateofBirth,std_City,std_FirstName,form_no
            ,std_village,std_post,std_Tehsil,std_Zip,std_State;

    public Profile(){
    }

    public Profile(Parcel in) {
        super();
        readFromParcel(in);
    }
    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    private void readFromParcel(Parcel in) {
        std_FatherName = in.readString();
        std_mobile1 = in.readString();
        std_courseId = in.readString();
        std_Gender = in.readString();
        std_DateofBirth = in.readString();
        std_City = in.readString();
        std_FirstName=in.readString();
        form_no=in.readString();
        std_village=in.readString();
        std_Tehsil=in.readString();
        std_post=in.readString();
        std_Zip=in.readString();
        std_State=in.readString();
        std_image=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(std_FatherName);
        parcel.writeString(std_mobile1);
        parcel.writeString(std_courseId);
        parcel.writeString(std_Gender);
        parcel.writeString(std_DateofBirth);
        parcel.writeString(std_City);
        parcel.writeString(std_FirstName);
        parcel.writeString(form_no);
        parcel.writeString(std_village);
        parcel.writeString(std_Tehsil);
        parcel.writeString(std_post);
        parcel.writeString(std_Zip);
        parcel.writeString(std_State);
        parcel.writeString(std_image);

    }

    public String getStd_FatherName() {
        return std_FatherName;
    }

    public void setStd_FatherName(String std_FatherName) {
        this.std_FatherName = std_FatherName;
    }

    public String getStd_mobile1() {
        return std_mobile1;
    }

    public void setStd_mobile1(String std_mobile1) {
        this.std_mobile1 = std_mobile1;
    }


    public String getStd_courseId() {
        return std_courseId;
    }

    public void setStd_courseId(String std_courseId) {
        this.std_courseId = std_courseId;
    }

    public String getStd_Gender() {
        return std_Gender;
    }

    public void setStd_Gender(String std_Gender) {
        this.std_Gender = std_Gender;
    }


    public String getStd_DateofBirth() {
        return std_DateofBirth;
    }

    public void setStd_DateofBirth(String std_DateofBirth) {
        this.std_DateofBirth = std_DateofBirth;
    }


    public String getStd_City() {
        return std_City;
    }

    public void setStd_City(String std_City) {
        this.std_City = std_City;
    }

    public String getStd_FirstName() {
        return std_FirstName;
    }

    public void setStd_FirstName(String std_FirstName) {
        this.std_FirstName = std_FirstName;
    }


    public String getForm_no() {
        return form_no;
    }

    public void setForm_no(String form_no) {
        this.form_no = form_no;
    }



    public String getStd_village() {
        return std_village;
    }

    public void setStd_village(String std_village) {
        this.std_village = std_village;
    }

    public String getStd_Tehsil() {
        return std_Tehsil;
    }

    public void setStd_Tehsil(String std_Tehsil) {
        this.std_Tehsil = std_Tehsil;
    }

    public String getStd_post() {
        return std_post;
    }

    public void setStd_post(String std_post) {
        this.std_post = std_post;
    }

    public String getStd_Zip() {
        return std_Zip;
    }

    public void setStd_Zip(String std_Zip) {
        this.std_Zip = std_Zip;
    }

    public String getStd_State() {
        return std_State;
    }

    public void setStd_State(String std_State) {
        this.std_State = std_State;
    }
    public String getStd_image() {
        return std_image;
    }

    public void setStd_image(String std_image) {
        this.std_image = std_image;
    }
    public static Creator<Profile> getCREATOR() {
        return CREATOR;
    }
}