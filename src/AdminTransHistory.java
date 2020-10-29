import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
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

public class AdminTransHistory extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JLabel l_accno;
	JTextField tx_accno;
	JButton b_back, b_clear, b_proceed;
	JPanel p_accno, p_buttons;
	
	public AdminTransHistory() {
		/* Text Field Configuration */
		l_accno = new JLabel("Account Number:");
		l_accno.setFont(new Font("Serif", Font.BOLD, 20));
		tx_accno = new JTextField(14);
		tx_accno.setFont(new Font("Serif", Font.BOLD, 20));
		p_accno = new JPanel();
		p_accno.add(l_accno, BorderLayout.WEST);
		p_accno.add(tx_accno, BorderLayout.EAST);

		/* Back Button Configuration */
		this.b_back = new JButton("Back");
		this.b_back.addActionListener(this);
		this.b_back.setFont(new Font("Serif", Font.BOLD, 20));
		this.b_back.setBackground(Color.white);
		this.b_back.setForeground(Color.black);
		p_buttons = new JPanel();
		p_buttons.add(b_back);

		/* Clear Button Configuration */
		this.b_clear = new JButton("Clear");
		this.b_clear.addActionListener(this);
		this.b_clear.setFont(new Font("Serif", Font.BOLD, 20));
		this.b_clear.setBackground(Color.white);
		this.b_clear.setForeground(Color.black);
		p_buttons.add(b_clear);

		/* Proceed Button Configuration */
		this.b_proceed = new JButton("Proceed");
		this.b_proceed.addActionListener(this);
		this.b_proceed.setFont(new Font("Serif", Font.BOLD, 20));
		this.b_proceed.setBackground(Color.white);
		this.b_proceed.setForeground(Color.black);
		p_buttons.add(b_proceed);

		/* Window Layout Configuration */
		GridLayout global_layout = new GridLayout();
		global_layout.setVgap(25);
		JPanel global_panel = new JPanel(global_layout);
		global_panel.setBorder(new EmptyBorder(1, 1, 100, 1));
		global_panel.add(p_accno);
		global_panel.add(p_buttons);

		/* Window Properties */
		setTitle("Pay Me!");
		setSize(1400, 900);
		setLocationRelativeTo(null);
		setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p_accno, BorderLayout.CENTER);
		getContentPane().add(p_buttons, BorderLayout.SOUTH);
		// getContentPane().add(global_panel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
				new AdminTransaction();
				this.setVisible(false);
			}
		} else if(ae.getSource() == b_clear) {
			tx_accno.setText("");
		}
		else if(ae.getSource() == b_proceed) {
			MysqlConnection connection = new MysqlConnection();
			String account_number = tx_accno.getText();
			if(account_number.equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter" +
							      " an account number");
				return;
			}
			String q_user_acc = "SELECT account_number FROM accounts " +
					   "WHERE account_number = '" +
					   account_number + "'";
			ResultSet rs_acc = null;
			String t_user_acc = "";
			try {
				rs_acc = connection.statement.executeQuery(q_user_acc);
				while (rs_acc.next()) {
					t_user_acc = rs_acc.getString("account_number");
				}
				if (!t_user_acc.equals(account_number))
					JOptionPane.showMessageDialog(null, "No" +
								      " such account" +
								      " exists");
				else
					new ShowStatement(t_user_acc);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private class ShowStatement extends JFrame implements ActionListener {
		private static final long serialVersionUID = 1L;
		JLabel l_detailed_statement;
		JTable t_detailed_statement;
		JButton b_back;
		JPanel p_detailed_statement, p_buttons, p_table;

		private ShowStatement() {/* Default Constructor */}

		private ShowStatement(String account_number) throws SQLException {

			/* Banner Configuration */
			this.l_detailed_statement = new JLabel("Detailed Statement");
			this.l_detailed_statement.setFont(new Font("Arial",
								   Font.BOLD, 50));
			p_detailed_statement = new JPanel();
			p_detailed_statement.add(l_detailed_statement,
						 BorderLayout.CENTER);
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
			t_detailed_statement.setModel(fill_table(account_number));
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
			setTitle("Pay Me!");
			setSize(1400, 900);
			setLocationRelativeTo(null);
			setVisible(true);
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(p_detailed_statement,
					     BorderLayout.NORTH);
			getContentPane().add(p_buttons,
					     BorderLayout.SOUTH);
			getContentPane().add(global_panel,
					     BorderLayout.CENTER);

			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

		private DefaultTableModel fill_table(String acc_no) throws SQLException {
			int ms_r = 10;
			DefaultTableModel model = new DefaultTableModel(new String[]
									{"Transaction ID",
									 "Date",
									 "From Account",
									 "To Account",
									 "CR/DR",
									 "Amount (INR)"}, 0);
			MysqlConnection connection = new MysqlConnection();
			String q_detst = "SELECT * FROM transaction WHERE" +
					" from_account_number = '" + acc_no + "'" +
					" OR to_account_number = '" + acc_no + "'" +
					" ORDER BY timestamp DESC" +
					" LIMIT " + Integer.toString(ms_r);
			ResultSet rs_detst = connection.statement.executeQuery(q_detst);
			while (rs_detst.next()) {
				String t_id = rs_detst.getString("transactionID");
				String t_date = rs_detst.getString("timestamp");
				String t_from = rs_detst.getString("from_account_number");
				String t_to = rs_detst.getString("to_account_number");
				String t_cr_dr = (acc_no.equals(t_from)) ? "DR" : "CR";
				String t_am = rs_detst.getString("amount");
				model.addRow(new Object[]{t_id, t_date,
							  t_from, t_to,
							  t_cr_dr, t_am});
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
					new AdminTransHistory();
					this.setVisible(false);
				}
			}
		}
	}
}
