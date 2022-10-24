package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Helper.Helper;

public class Patient extends User {

	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Patient() {
		super();
	}

	public Patient(int id, String idno, String password, String username, String type) {
		super(id, idno, password, username, type);
	}

	public boolean register(String idno, String password, String username) throws SQLException {
		int key = 0;
		boolean duplicate = false;
		String query = "INSERT INTO user" + "(idno,password,username,type) VALUES" + "(?,?,?,?)";

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE idno = '" + idno + "'");

			while (rs.next()) {
				duplicate = true;
				Helper.showMsg("This ID is already used!");
				break;
			}
			if (!duplicate) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, idno);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, username);
				preparedStatement.setString(4, "Patient");
				preparedStatement.executeUpdate();
				key = 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (key == 1)
			return true;
		else
			return false;

	}
}
