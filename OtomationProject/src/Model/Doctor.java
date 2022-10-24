package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doctor extends User {
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Doctor() {
		super();
	}

	public Doctor(int id, String idno, String password, String username, String type) {
		super(id, idno, password, username, type);
	}

	public boolean addwhour(int doctorid, String doctorname, String wdate) throws SQLException {
		int key = 0;
		int count = 0;
		String query = "INSERT INTO whour" + "(doctorid,doctorname,wdate) VALUES" + "(?,?,?)";

		try {
			st = con.createStatement();
			//rs = st.executeQuery("SELECT * FROM whour WHERE status='a' AND doctorid = " + doctorid + "AND wdate = '" + wdate + "'");
				// Check here tomorrow!
			
			
			while (rs.next()) {
				count++;
				break;
			}
			if (count == 0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, doctorid);
				preparedStatement.setString(2, doctorname);
				preparedStatement.setString(3, wdate);
				preparedStatement.executeUpdate();
			}

			key = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (key == 1)
			return true;
		else
			return false;
	}

	public ArrayList<Whour> getWhourList(int doctorid) throws SQLException {
		ArrayList<Whour> list = new ArrayList<>();

		Whour obj;
		try {
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
	public boolean deletewHour(int id) throws SQLException {

		String query = "DELETE FROM whour WHERE id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (key)
			return true;
		else
			return false;
	}
}
