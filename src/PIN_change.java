import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.plaf.synth.SynthStyle;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.sql.*;

public class PIN_change extends JFrame implements ActionListener{
    JPasswordField current_pin,new_pin,confirm_pin;
    JButton cancel,save;
    JLabel l_change_pin,l_current_pin,l_new_pin,l_confirm_pin;
    JPanel p_change_pin,p_current_pin,p_new_pin,p_confirm_pin,p_buttons,p_show_password;
    JCheckBox show_password;
    String u_ID = "";

    Border blackline = BorderFactory.createLineBorder(Color.black);
    public PIN_change(){

    }

    public PIN_change(String User_ID){

        u_ID=User_ID;

        setTitle("PIN Change");
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setVisible(true);

        l_change_pin=new JLabel("CHANGE YOUR PIN");
        l_change_pin.setFont(new Font("System",Font.BOLD,48));

        l_current_pin= new JLabel("CURRENT PIN");
        l_current_pin.setFont(new Font("System",Font.BOLD,25));

        l_new_pin = new JLabel("NEW PIN");
        l_new_pin.setFont(new Font("System",Font.BOLD,25));

        l_confirm_pin = new JLabel("CONFIRM NEW PIN");
        l_confirm_pin.setFont( new Font("System",Font.BOLD,25));

        current_pin = new JPasswordField(15);
        
        current_pin.setFont(new Font("Raleway", Font.BOLD, 28));
        
        new_pin = new JPasswordField(15);
        new_pin.setFont(new Font("Raleway", Font.BOLD, 28));
        
        confirm_pin = new JPasswordField(15);
        confirm_pin.setFont(new Font("Raleway", Font.BOLD, 28));
        
        save = new JButton("SAVE");
        save.setFont(new Font("System", Font.BOLD, 25));
        save.setBackground(Color.white);
        save.setForeground(Color.black);
    
        cancel = new JButton("CANCEL");
        cancel.setFont(new Font("System", Font.BOLD, 25));
        cancel.setBackground(Color.white);
        cancel.setForeground(Color.black);

        show_password = new JCheckBox("Show Password");
		show_password.setFont(new Font("Serif", Font.BOLD, 20));
		current_pin.setEchoChar('*');
        new_pin.setEchoChar('*');
        confirm_pin.setEchoChar('*');

        show_password.addActionListener((ae) -> {
			JCheckBox value = (JCheckBox) ae.getSource();
		    current_pin.setEchoChar(value.isSelected() ? (char) 0 : '*');
            new_pin.setEchoChar(value.isSelected() ? (char) 0 : '*');
            confirm_pin.setEchoChar(value.isSelected() ? (char) 0 : '*');
		});

        p_change_pin = new JPanel();
        p_current_pin = new JPanel();
        p_new_pin = new JPanel();
        p_confirm_pin = new JPanel();
        p_show_password = new JPanel();
        p_buttons = new JPanel();

        p_change_pin.setLayout(new BorderLayout());
        p_current_pin.setLayout(new BorderLayout());
        p_new_pin.setLayout(new BorderLayout());
        p_confirm_pin.setLayout(new BorderLayout());
        p_show_password.setLayout(new BorderLayout());
        p_buttons.setLayout(new BorderLayout());
       
        GridLayout button_layout = new GridLayout(1, 2);
		button_layout.setHgap(200);
        p_buttons.setLayout(button_layout);
        
        p_current_pin.add(l_current_pin, BorderLayout.WEST);
        p_current_pin.add(current_pin, BorderLayout.EAST);

        p_new_pin.add(l_new_pin, BorderLayout.WEST);
        p_new_pin.add(new_pin, BorderLayout.EAST);

        p_confirm_pin.add(l_confirm_pin, BorderLayout.WEST);
        p_confirm_pin.add(confirm_pin, BorderLayout.EAST);

        
        p_show_password.add(show_password, BorderLayout.EAST);

        p_change_pin.add(l_change_pin, BorderLayout.CENTER);
        p_change_pin.setBorder(new EmptyBorder(100, 360, 0, 260));
        
        p_buttons.add(save);
        p_buttons.add(cancel);

        GridLayout global_layout = new GridLayout(6, 1);
		global_layout.setVgap(50);
		JPanel global_panel = new JPanel(global_layout);
        global_panel.setBorder(new EmptyBorder(150, 260, 100, 260));
        

        global_panel.add(p_change_pin);
        global_panel.add(p_current_pin);
        global_panel.add(p_new_pin);
        global_panel.add(p_confirm_pin);
        global_panel.add(p_show_password);
        global_panel.add(p_buttons);


        getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p_change_pin, BorderLayout.NORTH);
		getContentPane().add(global_panel, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
       
        save.addActionListener(this);
        cancel.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent ae){
    
        try{        
            
            String a = new String(current_pin.getPassword());
            String b = new String(new_pin.getPassword());
            String c = new String(confirm_pin.getPassword());
            
            System.out.println(a + " "+b + " "+ c);
            
            if(ae.getSource()==save){
                if(a.equals("")){                    
                    JOptionPane.showMessageDialog(null, "Please Enter Current PIN");
                }

                else if(b.equals("")){
                    JOptionPane.showMessageDialog(null, "Enter New PIN");
                }

                else if (c.equals("")){                    
                    JOptionPane.showMessageDialog(null, "Re-Enter new PIN");
                }
                
                else if(b.length() < 4 || b.length() > 4){
                    JOptionPane.showMessageDialog(null,"New PIN must be of four digits");
                }

                MysqlConnection c1 = new MysqlConnection();
                ResultSet rs =null;
                String getData = "select pin from accounts where userID = '"+u_ID+"' ";
                rs = c1.statement.executeQuery(getData);
                String output = "";
                while (rs.next()) {
                    output =  (String) rs.getObject(1);
                }
                System.out.println(output);

                if(a.equals(output)){

                    if(b.equals(c)){
                        if(isNumber(b) == false){
                            System.out.println(isNumber(b));
                            JOptionPane.showMessageDialog(null, "PIN must be a four digit number only");
                        }
                        // MysqlConnection c1 = new MysqlConnection();
                        else{
                            String q1 = "update accounts set pin = '"+b+"' where userID = '"+u_ID+"' ";
                         
                            c1.statement.executeUpdate(q1);
                        
                            JOptionPane.showMessageDialog(null, "PIN changed successfully");
                         
                            //new Transcations().setVisible(true);
                            setVisible(false);
                        }
                         
                     }else{
                         JOptionPane.showMessageDialog(null, "PIN entered doesn't match");
                     }

                }else{
                    JOptionPane.showMessageDialog(null, "Please enter the correct current PIN");
                }

                    

                
            }else if(ae.getSource()==cancel){
                
               // new Transcations().setVisible(true);
                setVisible(false);
                
            }
            
        }catch(Exception e){
                    //e.printStackTrace();
                    System.out.println("error in pin change");
                    System.out.println("error: "+e);
        }
            
    }

    public static void main(String[] args){
        new PIN_change("90001").setVisible(true);
    }


    public static boolean isNumber(String s) {
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(((c - '0') < 0) || ((c - '0') > 9 )){
                return false;
            }
        }
        return true;
    }
}
