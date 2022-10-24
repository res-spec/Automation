package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Patient;
import Model.User;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel wrappane;
	private JTextField namefield;
	private JTextField fieldidno;
	private JPasswordField passfield;
	private Patient patient = new Patient();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setBackground(new Color(255, 255, 255));
		setResizable(false);
		setTitle("Hospital Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 330);
		wrappane = new JPanel();
		wrappane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(wrappane);
		wrappane.setLayout(null);

		JLabel lblnamesurname = new JLabel("Name Surname\r\n");
		lblnamesurname.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblnamesurname.setBounds(12, 12, 175, 20);
		wrappane.add(lblnamesurname);

		namefield = new JTextField();
		namefield.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		namefield.setColumns(10);
		namefield.setBounds(12, 43, 218, 23);
		wrappane.add(namefield);

		JLabel lblIdNo = new JLabel("ID No\r\n");
		lblIdNo.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblIdNo.setBounds(12, 89, 175, 20);
		wrappane.add(lblIdNo);

		fieldidno = new JTextField();
		fieldidno.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		fieldidno.setColumns(10);
		fieldidno.setBounds(12, 120, 218, 23);
		wrappane.add(fieldidno);

		JLabel lblPassword = new JLabel("Password\r\n");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblPassword.setBounds(12, 160, 175, 20);
		wrappane.add(lblPassword);

		passfield = new JPasswordField();
		passfield.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		passfield.setBounds(12, 192, 218, 23);
		wrappane.add(passfield);

		JButton cancelbtn = new JButton("Cancel\r\n");
		cancelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		cancelbtn.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cancelbtn.setBounds(132, 244, 108, 37);
		wrappane.add(cancelbtn);

		JButton registerbtn = new JButton("Register");
		registerbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fieldidno.getText().length() == 0 || passfield.getText().length() == 0
						|| namefield.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = patient.register(fieldidno.getText(), passfield.getText(),
								namefield.getText());
						if (control) {
							Helper.showMsg("success");
							LoginGUI login = new LoginGUI();
							login.setVisible(true);
							dispose();

						} else {
							Helper.showMsg("error");

						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		registerbtn.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		registerbtn.setBounds(12, 244, 108, 37);
		wrappane.add(registerbtn);
	}
}
