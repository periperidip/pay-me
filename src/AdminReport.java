import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AdminReport extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JLabel l_detailed_statement;
	JTable t_detailed_statement;
	JButton b_back;
	JPanel p_detailed_statement, p_buttons, p_table;
	JButton b_solve, b_body;

	public AdminReport() throws SQLException {

		/* Banner Configuration */
		this.l_detailed_statement = new JLabel("Reports");
		this.l_detailed_statement.setFont(new Font("Serif", Font.BOLD, 50));
		p_detailed_statement = new JPanel();
		p_detailed_statement.add(l_detailed_statement, BorderLayout.CENTER);
		p_detailed_statement.setBorder(new EmptyBorder(40, 0, 0, 0));
		p_buttons = new JPanel();
		GridLayout button_layout = new GridLayout(1, 3);
		button_layout.setHgap(90);
		p_buttons.setLayout(button_layout);
		p_buttons.setBorder(new EmptyBorder(0, 300, 40, 300));

		/* Back Button Configuration */
		this.b_back = new JButton("Back");
		this.b_back.addActionListener(this);
		this.b_back.setFont(new Font("Serif", Font.BOLD, 20));
		this.b_back.setBackground(Color.white);
		this.b_back.setForeground(Color.black);
		p_buttons.add(b_back);

		this.b_solve = new JButton("Solve");
		this.b_solve.addActionListener(this);
		this.b_solve.setFont(new Font("Serif", Font.BOLD, 20));
		this.b_solve.setBackground(Color.white);
		this.b_solve.setForeground(Color.black);
		p_buttons.add(b_solve);

		this.b_body = new JButton("Body");
		this.b_body.addActionListener(this);
		this.b_body.setFont(new Font("Serif", Font.BOLD, 20));
		this.b_body.setBackground(Color.white);
		this.b_body.setForeground(Color.black);
		p_buttons.add(b_body);

		/* Table Configuration */
		this.t_detailed_statement = new JTable();
		JScrollPane tableSP = new JScrollPane(this.t_detailed_statement);
		t_detailed_statement.setModel(fill_table());
		t_detailed_statement.setRowHeight(35);
		t_detailed_statement.setFont(new Font("Verdana", Font.PLAIN, 18));
		t_detailed_statement.getTableHeader().setPreferredSize(new Dimension(0, 50));
		t_detailed_statement.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 25));
		t_detailed_statement.getColumnModel().getColumn(0).setPreferredWidth(70);
		t_detailed_statement.getColumnModel().getColumn(1).setPreferredWidth(70);
		t_detailed_statement.getColumnModel().getColumn(2).setPreferredWidth(700);
		t_detailed_statement.getColumnModel().getColumn(3).setPreferredWidth(0);
		t_detailed_statement.setSize(600, 600);
		this.p_table = new JPanel(new GridLayout());
		(this.p_table).add(tableSP);

		/* Window Layout Configuration */
		GridLayout global_layout = new GridLayout();
		global_layout.setVgap(25);
		JPanel global_panel = new JPanel(global_layout);
		global_panel.setBorder(new EmptyBorder(20, 80, 60, 80));
		global_panel.add(p_detailed_statement);
		global_panel.add(p_table);
		global_panel.add(p_buttons);

		/* Window Properties */
		setTitle("Pay-Me");
		setSize(1400, 900);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p_detailed_statement, BorderLayout.NORTH);
		getContentPane().add(p_buttons, BorderLayout.SOUTH);
		getContentPane().add(global_panel, BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public DefaultTableModel fill_table() throws SQLException {
		DefaultTableModel model = new DefaultTableModel(
				new String[] { "Report #", "User ID", "Report Title", "Solved" }, 0);

		MysqlConnection connection = new MysqlConnection();
		String q_detst = "SELECT * FROM report ORDER BY COALESCE(solved, FALSE), report_number";
		ResultSet rs_detst = connection.statement.executeQuery(q_detst);
		while (rs_detst.next()) {
			String t_id = "         " + rs_detst.getString("report_number");
			String t_date = "         " + rs_detst.getString("userID");
			String t_from = "    " + rs_detst.getString("title");
			String t_to = rs_detst.getBoolean("solved") ? "    Solved" : " NotSolved";
			model.addRow(new Object[] { t_id, t_date, t_from, t_to });
		}
		connection.connection.close();
		return model;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b_back) {
			int result;
			UIManager.put("OptionPane.messageFont", new Font("Serif", Font.PLAIN, 20));
			UIManager.put("OptionPane.buttonFont", new Font("Serif", Font.BOLD, 20));
			UIManager.put("Button.background", Color.white);
			UIManager.put("Button.foreground", Color.black);

			result = JOptionPane.showOptionDialog(new JFrame(), "Are you sure want to quit?", "QUIT WINDOW",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Yes", "No" },
					JOptionPane.YES_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				new AdminTransaction().setVisible(true);
				this.setVisible(false);
			}
		}
		if (ae.getSource() == b_solve) {
			MysqlConnection mysqlConnection = new MysqlConnection();

			UIManager.put("OptionPane.messageFont", new Font("Serif", Font.PLAIN, 20));
			UIManager.put("OptionPane.buttonFont", new Font("Serif", Font.BOLD, 20));
			UIManager.put("Button.background", Color.white);
			UIManager.put("Button.foreground", Color.black);

			String j_report_number = JOptionPane.showInputDialog(null, "Enter the solved Report number");
			int report_number = Integer.parseInt(j_report_number);

			String check_exits = "SELECT report_number from report where report_number = '" + report_number + "' ";
			ResultSet rs;
			try {
				rs = (ResultSet) mysqlConnection.statement.executeQuery(check_exits);
				if (rs.next()) {
					String u_q = "UPDATE report SET solved = True WHERE report_number ='" + report_number + "' ";
					mysqlConnection.statement.executeUpdate(u_q);
					JOptionPane.showMessageDialog(null, "Database has been updated", "Success!",
							JOptionPane.INFORMATION_MESSAGE);
							new AdminReport().setVisible(true);;
							mysqlConnection.connection.close();
							setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Report Number does not exists", "Error!",JOptionPane.ERROR_MESSAGE);
					mysqlConnection.connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ae.getSource() == b_body) {
			MysqlConnection mysqlConnection = new MysqlConnection();

			UIManager.put("OptionPane.messageFont", new Font("Serif", Font.PLAIN, 20));
			UIManager.put("OptionPane.buttonFont", new Font("Serif", Font.BOLD, 20));
			UIManager.put("Button.background", Color.white);
			UIManager.put("Button.foreground", Color.black);

			String j_report_number = JOptionPane.showInputDialog(null, "Enter the solved Report number");
			int report_number = Integer.parseInt(j_report_number);

			String check_exits = "SELECT report_number FROM report WHERE report_number = '" + report_number + "' ";
			ResultSet rs;
			try {
				rs = (ResultSet) mysqlConnection.statement.executeQuery(check_exits);
				if (rs.next()) {
					String g_q = "SELECT body FROM report WHERE report_number ='" + report_number + "' ";
					ResultSet rs_body = (ResultSet) mysqlConnection.statement.executeQuery(g_q);
					String body_string = "";
					if (rs_body.next()) {
						body_string = rs_body.getString("body");
					}
					JOptionPane.showMessageDialog(null, body_string, "BODY",
							JOptionPane.INFORMATION_MESSAGE);
							mysqlConnection.connection.close();
				} else {
					JOptionPane.showMessageDialog(null, "Report Number does not exists", "Error!",JOptionPane.ERROR_MESSAGE);
					mysqlConnection.connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
