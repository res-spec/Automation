package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Clinic;
import Model.Patient;
import Model.Whour;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class PatientGUI extends JFrame {

	private JPanel wrappane;
	private static Patient patient = new Patient();
	private Clinic clinic = new Clinic();
	private JTable tabledoctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable tablewhour;
	private Whour whour = new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientGUI frame = new PatientGUI(patient);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public PatientGUI(Patient patient) throws SQLException {

		doctorModel = new DefaultTableModel();
		Object[] colDoctor = new Object[2];
		colDoctor[0] = "ID";
		colDoctor[1] = "Name Surname";
		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[2];

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Date";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];

		setResizable(false);
		setTitle("Hospital Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 750);
		wrappane = new JPanel();
		wrappane.setBackground(new Color(255, 255, 255));
		wrappane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(wrappane);
		wrappane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome, Saint " + patient.getUsername());
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 286, 50);
		wrappane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton.setBackground(UIManager.getColor("Button.darkShadow"));
		btnNewButton.setBounds(637, 11, 89, 23);
		wrappane.add(btnNewButton);

		JTabbedPane wtab = new JTabbedPane(JTabbedPane.TOP);
		wtab.setBounds(10, 146, 716, 556);
		wrappane.add(wtab);

		JPanel wappointment = new JPanel();
		wappointment.setBackground(new Color(255, 255, 255));
		wtab.addTab("Appointment System", null, wappointment, null);
		wappointment.setLayout(null);

		JScrollPane wscrollDoctor = new JScrollPane();
		wscrollDoctor.setBounds(10, 81, 247, 436);
		wappointment.add(wscrollDoctor);

		tabledoctor = new JTable(doctorModel);
		wscrollDoctor.setViewportView(tabledoctor);

		JLabel lblNewLabel_1 = new JLabel("Doctor List");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1.setBounds(10, 44, 96, 35);
		wappointment.add(lblNewLabel_1);

		JLabel clinicname_1 = new JLabel("Clinic Name");
		clinicname_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		clinicname_1.setBounds(279, 52, 152, 20);
		wappointment.add(clinicname_1);

		JComboBox selectclinic = new JComboBox();
		selectclinic.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		selectclinic.setBounds(269, 81, 173, 35);
		selectclinic.addItem("--Select a Clinic--");
		for (int i = 0; i < clinic.getList().size(); i++) {
			selectclinic.addItem(new Item(clinic.getList().get(i).getid(), clinic.getList().get(i).getName()));
		}
		selectclinic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectclinic.getSelectedIndex() != 0) {
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) tabledoctor.getModel();
					clearModel.setRowCount(0);
					try {
						for (int i = 0; i < clinic.getClinicDoctorlist(item.getKey()).size(); i++) {
							doctorData[0] = clinic.getClinicDoctorlist(item.getKey()).get(i).getId();
							doctorData[0] = clinic.getClinicDoctorlist(item.getKey()).get(i).getUsername();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					DefaultTableModel clearModel = (DefaultTableModel) tabledoctor.getModel();
					clearModel.setRowCount(0);
				}
			}
		});
		wappointment.add(selectclinic);

		JLabel clinicname_1_1 = new JLabel("Select Doctor");
		clinicname_1_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		clinicname_1_1.setBounds(275, 213, 159, 20);
		wappointment.add(clinicname_1_1);

		JButton btnSelectDoctor = new JButton("Select");
		btnSelectDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tabledoctor.getSelectedRow();
				if (row >= 0) {
					String value = tabledoctor.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) tablewhour.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);

						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("Please select a Doctor!");
				}
			}
		});
		btnSelectDoctor.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnSelectDoctor.setBounds(269, 245, 171, 23);
		wappointment.add(btnSelectDoctor);

		JScrollPane wscrollwhour = new JScrollPane();
		wscrollwhour.setBounds(452, 78, 247, 436);
		wappointment.add(wscrollwhour);

		tablewhour = new JTable(whourModel);
		wscrollwhour.setViewportView(tablewhour);
		tablewhour.getColumnModel().getColumn(0).setPreferredWidth(5);

		JLabel lblNewLabel_1_1 = new JLabel("Doctor List");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(452, 44, 96, 35);
		wappointment.add(lblNewLabel_1_1);
	}
}
