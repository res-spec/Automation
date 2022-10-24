package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Manager extends User {
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Manager(int id, String idno, String password, String username, String type) {
		super(id, idno, password, username, type);

	}

	public Manager() {
	}

	public ArrayList<User> getDoctorlist() throws SQLException {
		ArrayList<User> list = new ArrayList<>();

		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE type = 'doctor'");
			while (rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("idno"), rs.getString("password"),
						rs.getString("username"), rs.getString("type"));
				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return list;
	}
	
	
	public ArrayList<User> getClinicDoctorlist(int clinicid) throws SQLException {
		ArrayList<User> list = new ArrayList<>();

		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT u.id,u.idno,u.type,u.username,u.password FROM worker w LEFT JOIN user u ON w.userid = u.id WHERE clinicid = " + clinicid);
			while (rs.next()) {
				obj = new User(rs.getInt("u.id"), rs.getString("u.idno"), rs.getString("u.password"),
						rs.getString("u.username"), rs.getString("u.type"));
				list.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return list;
	}

	public boolean addDoctor(String idno, String password, String username) throws SQLException {
		String query = "INSERT INTO user" + "(idno,password,username,type) VALUES" + "(?,?,?,?)";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, idno);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, username);
			preparedStatement.setString(4, "doctor");
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

	public boolean deleteDoctor(int id) throws SQLException {

		String query = "DELETE FROM user WHERE id = ?";
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

	public boolean updateDoctor(int id, String idno, String password, String username) throws SQLException {

		String query = "UPDATE user SET username = ?,idno=?,password=? WHERE id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, idno);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, id);
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

	public boolean addWorker(int userid, int clinicid) throws SQLException {
		String query = "INSERT INTO worker" + "(userid, clinicid) VALUES" + "(?,?)";
		boolean key = false;
		int count = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM worker WHERE clinicid = " + clinicid + " AND userid = " + userid);
			while (rs.next()) {
				count++;

			}
			if (count == 0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, userid);
				preparedStatement.setInt(2, clinicid);
				preparedStatement.executeUpdate();
			}

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
