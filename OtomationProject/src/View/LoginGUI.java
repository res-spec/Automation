package View;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import Helper.*;
import Model.Doctor;
import Model.Manager;

public class LoginGUI extends JFrame {

	private JPanel wrapp_pane;
	private JTextField field_patiemtid;
	private JPasswordField pass_patient;
	private JTextField field_docid;
	private JPasswordField pass_doc;
	private DBConnection conn = new DBConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hospital Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		wrapp_pane = new JPanel();
		wrapp_pane.setBackground(new Color(255, 255, 255));
		wrapp_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(wrapp_pane);
		wrapp_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome to the Hospital Management System");
		lblNewLabel.setForeground(new Color(255, 128, 64));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblNewLabel.setBounds(69, 87, 354, 59);
		wrapp_pane.add(lblNewLabel);

		JTabbedPane wrapp_tabpane = new JTabbedPane(JTabbedPane.TOP);
		wrapp_tabpane.setBounds(10, 157, 466, 192);
		wrapp_pane.add(wrapp_tabpane);

		JPanel wrapp_PatientLogin = new JPanel();
		wrapp_PatientLogin.setBackground(new Color(255, 255, 255));
		wrapp_tabpane.addTab("Patient Entrance", null, wrapp_PatientLogin, null);
		wrapp_PatientLogin.setLayout(null);

		JLabel lblIdNumber = new JLabel("ID Number:");
		lblIdNumber.setForeground(new Color(255, 128, 64));
		lblIdNumber.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblIdNumber.setBounds(26, 11, 117, 37);
		wrapp_PatientLogin.add(lblIdNumber);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(255, 128, 64));
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblPassword.setBounds(26, 59, 117, 37);
		wrapp_PatientLogin.add(lblPassword);

		field_patiemtid = new JTextField();
		field_patiemtid.setBounds(118, 21, 304, 20);
		wrapp_PatientLogin.add(field_patiemtid);
		field_patiemtid.setColumns(10);

		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(66, 107, 108, 37);
		wrapp_PatientLogin.add(btnNewButton);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(271, 107, 108, 37);
		wrapp_PatientLogin.add(btnLogin);

		pass_patient = new JPasswordField();
		pass_patient.setBounds(118, 69, 304, 20);
		wrapp_PatientLogin.add(pass_patient);

		JPanel wrapp_DocLogin = new JPanel();
		wrapp_tabpane.addTab("Doctor Login", null, wrapp_DocLogin, null);
		wrapp_DocLogin.setLayout(null);

		JLabel lblIdNumber_1 = new JLabel("ID Number:");
		lblIdNumber_1.setForeground(new Color(255, 128, 64));
		lblIdNumber_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblIdNumber_1.setBounds(32, 11, 117, 37);
		wrapp_DocLogin.add(lblIdNumber_1);

		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setForeground(new Color(255, 128, 64));
		lblPassword_1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblPassword_1.setBounds(32, 59, 117, 37);
		wrapp_DocLogin.add(lblPassword_1);

		field_docid = new JTextField();
		field_docid.setColumns(10);
		field_docid.setBounds(124, 21, 304, 20);
		wrapp_DocLogin.add(field_docid);

		JButton btn_DocLogin = new JButton("Login");
		btn_DocLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (field_docid.getText().length() == 0 || pass_doc.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while (rs.next()) {
							if (field_docid.getText().equals(rs.getString("idno"))
									&& pass_doc.getText().equals(rs.getString("password"))) {
								if (rs.getString("type").equals("manager")) {
									Manager mngr = new Manager();
									mngr.setId(rs.getInt("id"));
									mngr.setIdno(rs.getString("idno"));
									mngr.setPassword("password");
									mngr.setUsername(rs.getString("Username"));
									mngr.setType(rs.getString("type"));
									ManagerGUI mGUI = new ManagerGUI(mngr);
									mGUI.setVisible(true);
									dispose();
								}
								if (rs.getString("type").equals("doctor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setIdno(rs.getString("idno"));
									doctor.setPassword("password");
									doctor.setUsername(rs.getString("Username"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
							}

						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_DocLogin.setBounds(256, 105, 154, 48);
		wrapp_DocLogin.add(btn_DocLogin);

		pass_doc = new JPasswordField();
		pass_doc.setBounds(124, 69, 304, 20);
		wrapp_DocLogin.add(pass_doc);
	}
}
