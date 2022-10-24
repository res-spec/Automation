package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Helper.Helper;
import Model.Doctor;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class DoctorGUI extends JFrame {

	private JPanel wrappedpanee;
	private static Doctor doctor = new Doctor();
	private JTable whourtable;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
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
	public DoctorGUI(Doctor doctor) throws SQLException {

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Date";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);

		}

		setResizable(false);
		setTitle("Hospital Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		wrappedpanee = new JPanel();
		wrappedpanee.setForeground(new Color(255, 255, 255));
		wrappedpanee.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(wrappedpanee);
		wrappedpanee.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome, Saint " + doctor.getUsername());
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 286, 50);
		wrappedpanee.add(lblNewLabel);

		JButton btnNewButton = new JButton("Close");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setBounds(637, 11, 89, 23);
		wrappedpanee.add(btnNewButton);

		JTabbedPane wrappedtabbed = new JTabbedPane(JTabbedPane.TOP);
		wrappedtabbed.setBounds(10, 104, 716, 348);
		wrappedpanee.add(wrappedtabbed);

		JPanel wrappedhour = new JPanel();
		wrappedtabbed.addTab("Work Hours", null, wrappedhour, null);
		wrappedhour.setLayout(null);

		JDateChooser selectdate = new JDateChooser();
		selectdate.setBounds(10, 23, 90, 25);
		wrappedhour.add(selectdate);

		JComboBox selecttime = new JComboBox();
		selecttime.setFont(new Font("Times New Roman", Font.BOLD, 16));
		selecttime.setModel(new DefaultComboBoxModel(new String[] { "8:30", "9:00", "9:30", "10:00", "10:30", "11:00",
				"11:30", "13:00", "13:30", "14:00", "14:30", "15:00" }));
		selecttime.setBounds(110, 23, 90, 25);
		wrappedhour.add(selecttime);

		JButton addwhourbtn = new JButton("Add");
		addwhourbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(selectdate.getDate());
				} catch (Exception e2) {
				}
				if (date.length() == 0) {
					Helper.showMsg("Please select a valid date !");
				} else {
					String time = " " + selecttime.getSelectedItem().toString() + ":00";
					String selectDate = date + time;
					try {
						boolean control = doctor.addwhour(doctor.getId(), doctor.getUsername(), selectDate);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		addwhourbtn.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		addwhourbtn.setBackground(new Color(255, 255, 255));
		addwhourbtn.setBounds(210, 23, 90, 25);
		wrappedhour.add(addwhourbtn);

		JScrollPane scrollwhour = new JScrollPane();
		scrollwhour.setBounds(10, 59, 691, 250);
		wrappedhour.add(scrollwhour);

		whourtable = new JTable(whourModel);
		scrollwhour.setViewportView(whourtable);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = whourtable.getSelectedRow();
				if (selRow >= 0) {
					String selectRow = whourtable.getModel().getValueAt(selRow, 0).toString();
					int selID = Integer.parseInt(selectRow);
					boolean control;
					try {
						control = doctor.deletewHour(selID);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} else {
					Helper.showMsg("Please select a valid date!");
				}
			}
		});
		btnClear.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnClear.setBackground(new Color(255, 255, 255));
		btnClear.setBounds(611, 23, 90, 25);
		wrappedhour.add(btnClear);
	}

	public void updateWhourModel(Doctor doctor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) whourtable.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);

		}

	}
}
