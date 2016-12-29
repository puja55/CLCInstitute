package clc.datacubesoftech.clcinstitute.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.attr.type;

public class Session implements Parcelable {

    private String session_id,session_name;

    public Session(){
    }

    public Session(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Creator<Session> CREATOR = new Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };

    private void readFromParcel(Parcel in) {
        session_id = in.readString();
        session_name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(session_id);
        parcel.writeString(session_name);

    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public static Creator<Session> getCREATOR() {
        return CREATOR;
    }
}