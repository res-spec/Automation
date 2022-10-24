package Model;

import Helper.DBConnection;

public class User {

	private int id;
	String idno, password, username, type;
	DBConnection conn = new DBConnection();

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User(int id, String idno, String password, String username, String type) {
		super();
		this.id = id;
		this.idno = idno;
		this.password = password;
		this.username = username;
		this.type = type;
	}

	;

}
