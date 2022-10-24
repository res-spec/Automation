package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Clinic;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class UpdateClinicGUI extends JFrame {

	private JPanel contentPane;
	private JTextField fieldclinicname;
	private static Clinic clinic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateClinicGUI frame = new UpdateClinicGUI(clinic);
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
	public UpdateClinicGUI(Clinic clinic) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 225, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel clinicname = new JLabel("Clinic Name");
		clinicname.setBounds(63, 10, 85, 20);
		clinicname.setFont(new Font("Times New Roman", Font.BOLD, 16));
		contentPane.add(clinicname);
		
		fieldclinicname = new JTextField();
		fieldclinicname.setBounds(42, 35, 126, 24);
		fieldclinicname.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		fieldclinicname.setColumns(10);
		fieldclinicname.setText(clinic.getName());
		contentPane.add(fieldclinicname);
		
		JButton btnupdateclnic = new JButton("Edit");
		btnupdateclnic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.confirm("sure")) {
					try {
						clinic.updateClinic(clinic.getid(), fieldclinicname.getText());
						Helper.showMsg("success");
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnupdateclnic.setBounds(61, 64, 89, 29);
		btnupdateclnic.setFont(new Font("Times New Roman", Font.BOLD, 16));
		contentPane.add(btnupdateclnic);
	}

}
