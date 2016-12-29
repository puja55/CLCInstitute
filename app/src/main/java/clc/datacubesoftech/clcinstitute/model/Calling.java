package clc.datacubesoftech.clcinstitute.model;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.id;

public class Calling implements Parcelable {


    private String calling_date,purpose,feedback;
    public Calling(){
    }

    public Calling(Parcel in) {
        super();
        readFromParcel(in);
    }
    public static final Creator<Calling> CREATOR = new Creator<Calling>() {
        @Override
        public Calling createFromParcel(Parcel in) {
            return new Calling(in);
        }

        @Override
        public Calling[] newArray(int size) {
            return new Calling[size];
        }
    };

    private void readFromParcel(Parcel in) {

        calling_date = in.readString();

        purpose = in.readString();
        feedback = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(calling_date);

        parcel.writeString(purpose);
        parcel.writeString(feedback);
    }
    public String getCalling_date() {
        return calling_date;
    }

    public void setCalling_date(String calling_date) {
        this.calling_date = calling_date;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getFeedback(){return feedback;}

    public void setFeedback(String feedback){this.feedback=feedback;}

    public static Creator<Calling> getCREATOR() {
        return CREATOR;
    }
}