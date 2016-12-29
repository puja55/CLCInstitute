package clc.datacubesoftech.clcinstitute.model;

/**
 * Created by Pri on 12/14/2016.
 */
public class CalendarModl {
    private BaseEntity.Attendance attendance;
    private BaseEntity.Attendance.Attendance1 attendance1;
    private boolean isCategory;
    public boolean isCategory() {
        return isCategory;
    }

    public void setCategory(boolean category) {
        isCategory = category;
    }

    public BaseEntity.Attendance.Attendance1 getAttendance1() {
        return attendance1;
    }

    public void setAttendance1(BaseEntity.Attendance.Attendance1 attendance1) {
        this.attendance1 = attendance1;
    }

    public BaseEntity.Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(BaseEntity.Attendance attendance) {
        this.attendance = attendance;
    }

}
