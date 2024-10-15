package chatapplication;

//used to import Jframe
import javax.swing.*;
//used to give the color,image
import java.awt.*;
//used to import action
import java.awt.event.*;
//To use empty border
import javax.swing.border.*;
//To use calendar
import java.util.Calendar;
import java.text.*;
//To use socket
import java.net.*;
//To use data input stream
import java.io.*;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Client implements ActionListener{
	
	JTextField text;//It is declared Globally, bcz JTextField is inside the constructor
	static JPanel a1;
	static Box vertical = Box.createVerticalBox();//To display the msg one below the other
	static JFrame f=new JFrame();
	static DataOutputStream dout;
	static DataInputStream din;
	static Socket s;
	
	
	Client(){
		
		f.setLayout(null);
		
		//To give the header section like name of the server or a chatting person
		JPanel p1=new JPanel();
		p1.setBackground(new Color(7,94,84));//color for the header
		p1.setBounds(0,0,450,70);//to pass the coordinates
		p1.setLayout(null);
		f.add(p1);//Header panel is added
		
		//To give the image and icon for the header
		ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));//Here image is blank with particular width and height
		Image i2=i1.getImage().getScaledInstance(25, 25,Image.SCALE_DEFAULT);// So here we are add the icon inside the blank space
		ImageIcon i3=new ImageIcon(i2);
		JLabel back=new JLabel(i3);
		back.setBounds(5,20,25,25);
		p1.add(back);//we need to add Jlabel inside the Jpanel
		
		//It is used bcz to work the back button on the o/p screen
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);//when we click back button, it should exit
			}
		});
		
		ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));//Here image is blank with particular width and height
		Image i5=i4.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT);// So here we are add the icon inside the blank space
		ImageIcon i6=new ImageIcon(i5);
		JLabel profile=new JLabel(i6);
		profile.setBounds(40,10,50,50);
		p1.add(profile);
		
		
		// Add this to your Client constructor after adding the profile JLabel

		profile.addMouseListener(new MouseAdapter() {
		
		    public void mouseClicked(MouseEvent e) {
		        // Create a dialog to show the enlarged image
		        JDialog dialog = new JDialog(f, "Profile Picture", true);
		        dialog.setLayout(new BorderLayout());
		        dialog.setSize(200, 200); // Set the size of the dialog
		        
		        // Add the enlarged profile picture
		        ImageIcon enlargedIcon = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
		        Image enlargedImage = enlargedIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
		        JLabel enlargedLabel = new JLabel(new ImageIcon(enlargedImage));
		        dialog.add(enlargedLabel, BorderLayout.CENTER);
		        
		        dialog.setLocationRelativeTo(f); // Center the dialog
		        dialog.setVisible(true); // Show the dialog
		    }
		});
		
		
		
		ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));//Here image is blank with particular width and height
		Image i8=i7.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT);// So here we are add the icon inside the blank space
		ImageIcon i9=new ImageIcon(i8);
		JLabel video=new JLabel(i9);
		video.setBounds(300,20,30,30);
		p1.add(video);
		
		
		ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));//Here image is blank with particular width and height
		Image i11=i10.getImage().getScaledInstance(35, 30,Image.SCALE_DEFAULT);// So here we are add the icon inside the blank space
		ImageIcon i12=new ImageIcon(i11);
		JLabel phone=new JLabel(i12);
		phone.setBounds(360,20,35,30);
		p1.add(phone);
		
		
		// Inside the Client constructor, after adding the phone JLabel
		phone.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        try {
		            dout.writeUTF("Calling...");
		        } catch (IOException ioException) {
		            ioException.printStackTrace();
		        }
		    }
		});
	
		ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));//Here image is blank with particular width and height
		Image i14=i13.getImage().getScaledInstance(10, 25,Image.SCALE_DEFAULT);// So here we are add the icon inside the blank space
		ImageIcon i15=new ImageIcon(i14);
		JLabel morevert=new JLabel(i15);
		morevert.setBounds(420,20,10,25);
		p1.add(morevert);
		
		//To give name of the person chatting
		JLabel name=new JLabel("Madhura");
		name.setBounds(110,15,100,18);
		name.setForeground(Color.WHITE);//To change the name text color
		name.setFont(new Font("SAN_SERIF",Font.BOLD,18));//To get the proper front size
		p1.add(name);
		
		//To give the current status of the person
		JLabel status=new JLabel("Active Now");
		status.setBounds(110,35,100,18);
		status.setForeground(Color.WHITE);//To change the name text color
		status.setFont(new Font("SAN_SERIF",Font.BOLD,11));//To get the proper front size
		p1.add(status);
		
		//To give the panel space area
		a1=new JPanel();
		a1.setBounds(5, 75, 440, 570);
		f.add(a1);
		
		//To give a particular space type the msg
		text=new JTextField();
		text.setBounds(5, 655, 310, 40);
		text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		f.add(text);
		
		//To using the button to send the msg 
		JButton send=new JButton("send");
		send.setBounds(320, 655,123, 40);
		send.setBackground(new Color(7, 94,84));
		send.setForeground(Color.WHITE);
		send.addActionListener(this);//To work the send btn
		send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		f.add(send);
		
	
		//size of the frame
		f.setSize(450, 700);
		//To give the default location when we run the code
		f.setLocation(800,50);
		//To remove the main o/p header where it contains minimize btn,close btn...
		f.setUndecorated(true);
		//To give the Background color
		f.getContentPane().setBackground(Color.WHITE);
		
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
	    try {
	        String out = text.getText();
	        JPanel p2 = formatLabel(out);

	        a1.setLayout(new BorderLayout());
	        JPanel right = new JPanel(new BorderLayout());
	        right.add(p2, BorderLayout.LINE_END);

	        vertical.add(right);
	        vertical.add(Box.createVerticalStrut(15));
	        a1.add(vertical, BorderLayout.PAGE_START);

	        dout.writeUTF(out);

	        // Check for reminder command
	        checkForReminder(out);

	        text.setText(" ");
	        f.repaint();
	        f.invalidate();
	        f.validate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private static void checkForReminder(String msg) {
	    // Pattern to match "remind me at HH:mm"
	    Pattern pattern = Pattern.compile("remind me at (\\d{1,2}):(\\d{2})");
	    Matcher matcher = pattern.matcher(msg);

	    if (matcher.find()) {
	        String hour = matcher.group(1);
	        String minute = matcher.group(2);
	        scheduleReminder(hour, minute, "Reminder: Time to check!");
	    }
	}

	private static void scheduleReminder(String hour, String minute, String reminderMessage) {
	    // Get the current time
	    Calendar now = Calendar.getInstance();
	    Calendar reminderTime = (Calendar) now.clone();

	    // Set the reminder time
	    reminderTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
	    reminderTime.set(Calendar.MINUTE, Integer.parseInt(minute));
	    reminderTime.set(Calendar.SECOND, 0);

	    // If the time has already passed, schedule it for the next day
	    if (reminderTime.before(now)) {
	        reminderTime.add(Calendar.DAY_OF_MONTH, 1);
	    }

	    long delay = reminderTime.getTimeInMillis() - now.getTimeInMillis();

	    // Schedule the reminder
	    Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	            try {
	                dout.writeUTF(reminderMessage);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }, delay);
	}
		
	
	public static JPanel formatLabel(String out) {
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output=new JLabel("<html><p style=\"width: 150px\">"+ out + "</html>");//To get more space we use html tags
		output.setFont(new Font("Tahoma", Font.PLAIN,16));
		output.setBackground(new Color(37,211,102));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15,15,15,50));
		
		panel.add(output);
		
		//To display time
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
		
		JLabel time=new JLabel();
		time.setText(sdf.format(cal.getTime()));
		panel.add(time);
		
		
		return panel;
	}
	
   public static void main(String[] args) throws IOException {
	   new Client();
	   
	   try {
		   s = new Socket("127.0.0.1",61075);
		   //To receive the msg
		   din=new DataInputStream(s.getInputStream());
		   //To send the msg
		   dout=new DataOutputStream(s.getOutputStream());
		   //To read the msg
		   
		   while(true) {
			   
			    a1.setLayout(new BorderLayout());
				String msg=din.readUTF();
			    JPanel panel=formatLabel(msg);
				   
				//To display the receive msg in the left side
				JPanel left=new JPanel(new BorderLayout());
				left.add(panel,BorderLayout.LINE_START);
				vertical.add(left);
				
				vertical.add(Box.createVerticalStrut(15));
				a1.add(vertical,BorderLayout.PAGE_START);
				
				f.validate();

				// Added this line to check for reminder commands in received messages 
				checkForReminder(msg);
				 
			   }
		  
	     }catch(Exception e) {
		   e.printStackTrace();
	   }
	   finally {
		   if(s!=null)
			   s.close();
	   }
   }
}