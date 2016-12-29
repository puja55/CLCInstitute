package clc.datacubesoftech.clcinstitute.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {

    private String Name,Date,Exam,Neg_Mark,phy_MM,phy_OM,che_mm,
            che_om,zoo_mm,zoo_om,bot_mm,bot_om,totalMarks,totalOM,per,rank;
    public Result(){
    }

    public Result(Parcel in) {
        super();
        readFromParcel(in);
    }
    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    private void readFromParcel(Parcel in) {
        Name = in.readString();
        Date = in.readString();
        Exam = in.readString();
        phy_MM = in.readString();
        phy_OM = in.readString();
        che_mm = in.readString();
        che_om = in.readString();
        zoo_mm = in.readString();
        zoo_om = in.readString();
        bot_mm = in.readString();
        bot_om = in.readString();
        totalMarks = in.readString();
        totalOM = in.readString();
        per = in.readString();
        rank = in.readString();
        Neg_Mark=in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Date);
        parcel.writeString(Exam);
        parcel.writeString(phy_MM);
        parcel.writeString(phy_OM);
        parcel.writeString(che_mm);
        parcel.writeString(che_om);
        parcel.writeString(zoo_mm);
        parcel.writeString(zoo_om);
        parcel.writeString(bot_mm);
        parcel.writeString(bot_om);
        parcel.writeString(totalMarks);
        parcel.writeString(totalOM);
        parcel.writeString(per);
        parcel.writeString(rank);
        parcel.writeString(Neg_Mark);
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }


    public String getExam() {
        return Exam;
    }

    public void setExam(String Exam) {
        this.Exam = Exam;
    }

    public static Creator<Result> getCREATOR() {
        return CREATOR;
    }
}