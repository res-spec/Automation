package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.*;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import Helper.*;
import javax.swing.JComboBox;

public class ManagerGUI extends JFrame {

	static Manager manager = new Manager();
	Clinic clinic = new Clinic();
	private JPanel wrappedPane;
	private JTextField txtnamesurname;
	private JTextField txtidno;
	private JTextField txtUserID;
	private JPasswordField passfield;
	private JTable doctable;
	private DefaultTableModel doctorModel = null;
	private Object[] doctorData = null;
	private JTable tableclinic;
	private JTextField fldclinicname;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JPopupMenu clinicMenu;
	private JTable tableworker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerGUI frame = new ManagerGUI(manager);
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
	public ManagerGUI(Manager manager) throws SQLException {
		// Doctor Model
		doctorModel = new DefaultTableModel();
		Object[] colDoctorName = new Object[4];
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Name Surname";
		colDoctorName[2] = "Password";
		colDoctorName[3] = "Id No";
		doctorModel.setColumnIdentifiers(colDoctorName);
		doctorData = new Object[4];
		for (int i = 0; i < manager.getDoctorlist().size(); i++) {
			doctorData[0] = manager.getDoctorlist().get(i).getId();
			doctorData[1] = manager.getDoctorlist().get(i).getUsername();
			doctorData[2] = manager.getDoctorlist().get(i).getPassword();
			doctorData[3] = manager.getDoctorlist().get(i).getIdno();
			doctorModel.addRow(doctorData);

		}
		// Clinic Model
		clinicModel = new DefaultTableModel();
		Object[] colClinic = new Object[2];
		colClinic[0] = "ID";
		colClinic[1] = "Polyclinic Name";
		clinicModel.setColumnIdentifiers(colClinic);
		clinicData = new Object[2];
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getid();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}

		// WorkerModel

		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2];
		colWorker[0] = "ID";
		colWorker[1] = "Name Surname";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData = new Object[2];

		setTitle("Hospital Management System");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 750);

		wrappedPane = new JPanel();
		wrappedPane.setBackground(new Color(255, 255, 255));
		wrappedPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(wrappedPane);
		wrappedPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome, Saint" + manager.getUsername());
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 286, 50);
		wrappedPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Close\r\n");
		btnNewButton.setBackground(UIManager.getColor("Button.darkShadow"));
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton.setBounds(637, 11, 89, 23);
		wrappedPane.add(btnNewButton);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 146, 716, 556);
		wrappedPane.add(tabbedPane);

		JPanel doctormanagements = new JPanel();
		tabbedPane.addTab("Doctor Management", null, doctormanagements, null);
		doctormanagements.setLayout(null);

		JLabel lblnamesurname = new JLabel("Name Surname\r\n");
		lblnamesurname.setBounds(478, 93, 175, 20);
		lblnamesurname.setFont(new Font("Times New Roman", Font.BOLD, 16));
		doctormanagements.add(lblnamesurname);

		JLabel lblIdNo = new JLabel("ID No");
		lblIdNo.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblIdNo.setBounds(478, 158, 175, 20);
		doctormanagements.add(lblIdNo);

		JLabel lblPassword = new JLabel("Password\r\n");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblPassword.setBounds(478, 223, 175, 20);
		doctormanagements.add(lblPassword);

		JButton btnNewButton_1 = new JButton("Register");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtnamesurname.getText().length() == 0 || passfield.getText().length() == 0
						|| txtidno.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = manager.addDoctor(txtidno.getText(), passfield.getText(),
								txtnamesurname.getText());
						if (control) {
							Helper.showMsg("success");
							txtnamesurname.setText(null);
							txtidno.setText(null);
							passfield.setText(null);
							updateDoctorModel();
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnNewButton_1.setBounds(584, 285, 112, 23);
		doctormanagements.add(btnNewButton_1);

		JLabel lblUserId = new JLabel("User ID");
		lblUserId.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblUserId.setBounds(478, 415, 175, 20);
		doctormanagements.add(lblUserId);

		JButton btnNewButton_1_1 = new JButton("Delete");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtUserID.getText().length() == 0) {
					Helper.showMsg("Please choose a valid doctor !");
				} else {
					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(txtUserID.getText());
						try {
							boolean control = manager.deleteDoctor(selectID);
							if (control) {
								Helper.showMsg("success");
								txtUserID.setText(null);
								updateDoctorModel();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

					}

				}
			}
		});
		btnNewButton_1_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnNewButton_1_1.setBounds(607, 480, 89, 23);
		doctormanagements.add(btnNewButton_1_1);

		txtnamesurname = new JTextField();
		txtnamesurname.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtnamesurname.setBounds(478, 124, 218, 23);
		txtnamesurname.setColumns(10);
		doctormanagements.add(txtnamesurname);

		txtidno = new JTextField();
		txtidno.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtidno.setColumns(10);
		txtidno.setBounds(478, 189, 218, 23);
		doctormanagements.add(txtidno);

		txtUserID = new JTextField();
		txtUserID.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtUserID.setColumns(10);
		txtUserID.setBounds(478, 446, 218, 23);
		doctormanagements.add(txtUserID);

		passfield = new JPasswordField();
		passfield.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		passfield.setBounds(478, 254, 218, 20);
		doctormanagements.add(passfield);

		JScrollPane wscrolldoc = new JScrollPane();
		wscrolldoc.setBounds(10, 93, 451, 424);
		doctormanagements.add(wscrolldoc);

		doctable = new JTable(doctorModel);
		wscrolldoc.setViewportView(doctable);
		doctable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					txtUserID.setText(doctable.getValueAt(doctable.getSelectedRow(), 0).toString());
				} catch (Exception ex) {
				}

			}

		});

		JPanel wrapclinic = new JPanel();
		wrapclinic.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("PolyClinics ", null, wrapclinic, null);
		wrapclinic.setLayout(null);

		JScrollPane scrollClinic = new JScrollPane();
		scrollClinic.setBounds(10, 11, 266, 364);
		wrapclinic.add(scrollClinic);

		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Update");
		JMenuItem deleteMenu = new JMenuItem("Clear");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);

		updateMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selID = Integer.parseInt(tableclinic.getValueAt(tableclinic.getSelectedRow(), 0).toString());
				Clinic selectClinic = clinic.getFetch(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});

		deleteMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selID = Integer.parseInt(tableclinic.getValueAt(tableclinic.getSelectedRow(), 0).toString());
					try {
						if (clinic.deleteClinic(selID)) {
							Helper.showMsg("success");
							updateClinicModel();
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		tableclinic = new JTable(clinicModel);
		tableclinic.setComponentPopupMenu(clinicMenu);
		tableclinic.addMouseListener((MouseListener) new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = tableclinic.rowAtPoint(point);
				tableclinic.setRowSelectionInterval(selectedRow, selectedRow);
			}
		});
		scrollClinic.setViewportView(tableclinic);

		JLabel clinicname = new JLabel("Clinic Name");
		clinicname.setFont(new Font("Times New Roman", Font.BOLD, 16));
		clinicname.setBounds(10, 386, 175, 20);
		wrapclinic.add(clinicname);

		fldclinicname = new JTextField();
		fldclinicname.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		fldclinicname.setColumns(10);
		fldclinicname.setBounds(10, 420, 218, 23);
		wrapclinic.add(fldclinicname);

		JButton addclinicbtn = new JButton("Add");
		addclinicbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fldclinicname.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						if (clinic.addClinic(fldclinicname.getText())) {
							Helper.showMsg("success");
							fldclinicname.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		addclinicbtn.setFont(new Font("Times New Roman", Font.BOLD, 16));
		addclinicbtn.setBounds(116, 450, 112, 23);
		wrapclinic.add(addclinicbtn);

		JScrollPane wscrollworker = new JScrollPane();
		wscrollworker.setBounds(435, 11, 266, 364);
		wrapclinic.add(wscrollworker);

		tableworker = new JTable();
		wscrollworker.setViewportView(tableworker);

		JComboBox selectdoctor = new JComboBox();
		selectdoctor.setBounds(435, 386, 266, 23);
		for (int i = 0; i < manager.getDoctorlist().size(); i++) {
			// manager.getDoctorlist().get(i).getUsername() -- only names
			selectdoctor.addItem(
					new Item(manager.getDoctorlist().get(i).getId(), manager.getDoctorlist().get(i).getUsername()));
		}
		selectdoctor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + " : " + item.getValue());
		});
		wrapclinic.add(selectdoctor);

		JButton addworkbtn = new JButton("Add");
		addworkbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = tableclinic.getSelectedRow();
				if (selRow >= 0) {
					String selClinic = tableclinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					Item doctorItem = (Item) selectdoctor.getSelectedItem();
					try {
						boolean control = manager.addWorker(doctorItem.getKey(), selClinicID);
						if (control) {
							Helper.showMsg("success");
							DefaultTableModel clearModel = (DefaultTableModel) tableworker.getModel();
							clearModel.setRowCount(0);
							for (int i = 0; i < manager.getClinicDoctorlist(selClinicID).size(); i++) {
								workerData[0] = manager.getClinicDoctorlist(selClinicID).get(i).getIdno();
								workerData[1] = manager.getClinicDoctorlist(selClinicID).get(i).getUsername();
								workerModel.addRow(workerData);
							}
							tableworker.setModel(workerModel);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				} else {
					Helper.showMsg("Please select a clinic!");
				}
			}
		});
		addworkbtn.setFont(new Font("Times New Roman", Font.BOLD, 16));
		addworkbtn.setBounds(589, 421, 112, 23);
		wrapclinic.add(addworkbtn);

		JButton btnworkerSelect = new JButton("Select");
		btnworkerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = tableclinic.getSelectedRow();
				if (selRow >= 0) {
					String selClinic = tableclinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					DefaultTableModel clearModel = (DefaultTableModel) tableworker.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < manager.getClinicDoctorlist(selClinicID).size(); i++) {
							workerData[0] = manager.getClinicDoctorlist(selClinicID).get(i).getIdno();
							workerData[1] = manager.getClinicDoctorlist(selClinicID).get(i).getUsername();
							workerModel.addRow(workerData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					tableworker.setModel(workerModel);
				} else {
					Helper.showMsg("Please select a clinic!");
				}
			}
		});
		btnworkerSelect.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnworkerSelect.setBounds(288, 80, 112, 23);
		wrapclinic.add(btnworkerSelect);

		JLabel clinicname_1 = new JLabel("Clinic Name");
		clinicname_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		clinicname_1.setBounds(300, 48, 85, 20);
		wrapclinic.add(clinicname_1);

		doctable.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectID = Integer.parseInt(doctable.getValueAt(doctable.getSelectedRow(), 0).toString());
					String selectUserName = doctable.getValueAt(doctable.getSelectedRow(), 1).toString();
					String selectIdno = doctable.getValueAt(doctable.getSelectedRow(), 2).toString();
					String selectPassword = doctable.getValueAt(doctable.getSelectedRow(), 3).toString();

					try {
						boolean control = manager.updateDoctor(selectID, selectIdno, selectPassword, selectUserName);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}

			}
		});
	}

	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) doctable.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < manager.getDoctorlist().size(); i++) {
			doctorData[0] = manager.getDoctorlist().get(i).getId();
			doctorData[1] = manager.getDoctorlist().get(i).getUsername();
			doctorData[2] = manager.getDoctorlist().get(i).getPassword();
			doctorData[3] = manager.getDoctorlist().get(i).getIdno();
			doctorModel.addRow(doctorData);

		}

	}

	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) tableclinic.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getid();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}

	}
}
