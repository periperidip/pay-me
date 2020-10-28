import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class DetailedStatement extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JLabel l_detailed_statement;
	JTable t_detailed_statement;
	JButton b_back;
	JPanel p_detailed_statement, p_buttons, p_table;
	String u_ID;

	public DetailedStatement() {/* Default Constructor */}

	public DetailedStatement(String u_ID) throws SQLException {
		this.u_ID = u_ID;

		/* Banner Configuration */
		this.l_detailed_statement = new JLabel("Detailed Statement");
		this.l_detailed_statement.setFont(new Font("Arial", Font.BOLD, 50));
		p_detailed_statement = new JPanel();
		p_detailed_statement.add(l_detailed_statement, BorderLayout.CENTER);
		p_detailed_statement.setBorder(new EmptyBorder(40, 0, 0, 0));

		/* Back Button Configuration */
		this.b_back = new JButton("Back");
		this.b_back.addActionListener(this);
		this.b_back.setFont(new Font("Serif", Font.BOLD, 20));
		this.b_back.setBackground(Color.white);
		this.b_back.setForeground(Color.black);
		p_buttons = new JPanel();
		p_buttons.add(b_back);

		/* Table Configuration */
		this.t_detailed_statement = new JTable();
		JScrollPane tableSP = new JScrollPane(this.t_detailed_statement);
		t_detailed_statement.setModel(fill_table());
		t_detailed_statement.setSize(400, 600);
		this.p_table = new JPanel(new GridLayout());
		(this.p_table).add(tableSP);

		/* Window Layout Configuration */
		GridLayout global_layout = new GridLayout();
		global_layout.setVgap(25);
		JPanel global_panel = new JPanel(global_layout);
		global_panel.setBorder(new EmptyBorder(1, 1, 100, 1));
		global_panel.add(p_detailed_statement);
		global_panel.add(p_table);
		global_panel.add(p_buttons);

		/* Window Properties */
		setTitle("Detailed Statement");
		setSize(1400, 900);
		setLocationRelativeTo(null);
		setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p_detailed_statement, BorderLayout.NORTH);
		getContentPane().add(p_buttons, BorderLayout.SOUTH);
		getContentPane().add(global_panel, BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public DefaultTableModel fill_table() throws SQLException {
		int ms_r = 10;
		DefaultTableModel model = new DefaultTableModel(new String[]
								{"Transaction ID", "Date",
								 "From Account", "To Account", "CR/DR",
								 "Amount (INR)"}, 0);
		MysqlConnection connection = new MysqlConnection();
		String q_user_accno = "SELECT account_number FROM accounts " +
				      "WHERE userID = '" + u_ID + "'";
		ResultSet rs_acc = connection.statement.executeQuery(q_user_accno);
		String t_acc = "";
		while (rs_acc.next()) {t_acc = rs_acc.getString("account_number");}
		String q_detst = "SELECT * FROM transaction WHERE" +
				 " from_account_number = '" + t_acc + "'" +
				 " OR to_account_number = '" + t_acc + "'" +
				 " LIMIT " + Integer.toString(ms_r) +
				 " ORDER BY timestamp DESC";
		ResultSet rs_detst = connection.statement.executeQuery(q_detst);
		while (rs_detst.next()) {
			String t_id = rs_detst.getString("transactionID");
                        String t_date = rs_detst.getString("timestamp");
			String t_from = rs_detst.getString("from_account_number");
			String t_to = rs_detst.getString("to_account_number");
			String t_cr_dr = (t_acc.equals(t_from)) ? "DR" : "CR";
			String t_am = rs_detst.getString("amount");
			model.addRow(new Object[]{t_id, t_date, t_from, t_to, t_cr_dr, t_am});
		}
		connection.connection.close();
		return model;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b_back) {
			int result;
			UIManager.put("OptionPane.messageFont",
				      new Font("Serif", Font.PLAIN, 20));
			UIManager.put("OptionPane.buttonFont",
				      new Font("Serif", Font.BOLD, 20));
			UIManager.put("Button.background", Color.white);
			UIManager.put("Button.foreground", Color.black);

			result = JOptionPane.showOptionDialog(new JFrame(),
				 "Are you sure want to quit?", "QUIT WINDOW",
				 JOptionPane.YES_NO_OPTION,
				 JOptionPane.QUESTION_MESSAGE,
				 null, new Object[] {"Yes", "No"},
				 JOptionPane.YES_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				new Transaction(u_ID);
				this.setVisible(false);
			}
		}
	}
}
