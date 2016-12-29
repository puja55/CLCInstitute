package clc.datacubesoftech.clcinstitute.model;



import java.util.ArrayList;
import java.util.List;

public class BaseEntity {
	String status,msg,login_by_username;
	public ArrayList<Session> session;
	public ArrayList<Calling> callingFeedback;
	public ArrayList<Profile> student;
	public ArrayList<Result> results;
	public ArrayList<Attendance> attendance;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

public ArrayList<Attendance> getAttendance() {
	return attendance;
}

	public void setAttendance(ArrayList<Attendance> attendance) {
		this.attendance = attendance;
	}

	public static class Attendance {
		private int monthid,year;;

		private ArrayList<Attendance1> attendance1;
		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}


		public int getMonthid() {
			return monthid;
		}

		public void setMonthid(int monthid) {
			this.monthid = monthid;
		}

		public List<Attendance1> getAttendance1() {
			return attendance1;
		}

		public void setAttendance1(ArrayList<Attendance1> attendance1) {
			this.attendance1 = attendance1;
		}

		public static class Attendance1 {

			private String c1;

			public String getC1() {
				return c1;
			}

			public void setC1(String c1) {
				this.c1 = c1;
			}

		}
	}
}