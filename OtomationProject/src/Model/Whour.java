package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Whour {
	private int id,doctorid;
	private String doctorname, wdate, status;
	
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	
	
	public Whour(int id, int doctorid, String doctorname, String wdate, String status) {
		this.id = id;
		this.doctorid = doctorid;
		this.doctorname = doctorname;
		this.wdate = wdate;
		this.status = status;
	}
	
	public Whour () {}
	


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getDoctorid() {
		return doctorid;
	}



	public void setDoctorid(int doctorid) {
		this.doctorid = doctorid;
	}



	public String getDoctorname() {
		return doctorname;
	}



	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}



	public String getWdate() {
		return wdate;
	}



	public void setWdate(String wdate) {
		this.wdate = wdate;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}
	
	public ArrayList<Whour> getWhourList(int doctorid) throws SQLException {
		ArrayList<Whour> list = new ArrayList<>();

		Whour obj;
		try {
			Connection con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM whour WHERE status = 'a' AND doctorid = " + doctorid);
			while (rs.next()) {
				obj = new Whour();
				obj.setId(rs.getInt("id"));
				obj.setDoctorid(rs.getInt("doctorid"));
				obj.setDoctorname(rs.getString("doctorname"));
				obj.setStatus(rs.getString("status"));
				obj.setWdate(rs.getString("wdate"));
				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return list;
	}
	
	
	
	 
}
