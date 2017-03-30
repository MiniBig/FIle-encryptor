package Wind;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Threads.Crypto;
import Threads.DeCrypto;
import Threads.ProgressThread;
import Threads.tt;
import source.Crypt;
import source.test;

public class MainForm extends javax.swing.JFrame  {

	public static String file;
	public static String dir;
	public static String name;
	public static String folname;
	public static String path;
	
	private JPanel contentPane;
	private boolean folderch = false;
	public Thread st;
	public Thread std;
	public static String na ;
	private static  Crypt crt = new Crypt();
	
	public boolean needToPause = false;

	public Runnable r;
	
	public static JProgressBar progressBar = new JProgressBar();
	
	
	//threads
	public tt thread = new tt();
	public Crypto cr = new Crypto();
	public DeCrypto dec = new DeCrypto();
	
	public boolean returnVal = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainForm frame = new MainForm();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 */
	public MainForm() {
		initComponents();
	}
	
	private void initComponents(){
setTitle("JAVA File Ecryptor V1.0");
		
		JLabel label = new JLabel("*");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 222);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		// Pasirenkam faila is menu bar File choose
		JMenuItem mntmChooseFile = new JMenuItem("Choose file");
		mntmChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
		        chooser.setFileFilter(filter);
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		            System.out.println("You chose to open this file: " +
		                    chooser.getSelectedFile().getAbsolutePath());
		            		file = chooser.getSelectedFile().getAbsolutePath();
		            		label.setText(file);
		            		//System.out.println("dir: "+chooser.getSelectedFile().getParent());
		            		dir = chooser.getSelectedFile().getParent();
		            		name = chooser.getSelectedFile().getName();
		            		System.out.println("name: "+name);
		        }
				
			}
		});
		mnFile.add(mntmChooseFile);
		
		JMenuItem mntmChooseFolder = new JMenuItem("Choose folder");
		mntmChooseFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Crypt c = new Crypt();
				
				test t = new test();
				path = c.chooseFolder();
				//path = path.replace("\\", "\\\\");
				System.out.println(path);
				label.setText(path);
				folderch = true;
				t.SOURCE_FOLDER = path;
				t.OUTPUT_ZIP_FILE = path+"_Encrypted.zip";
				folname = path+"_Encrypted.zip";
				na = folname.substring(folname.lastIndexOf("\\")+1,folname.length());
				System.out.println("na: "+na);
			}
			
		});
		mnFile.add(mntmChooseFolder);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblFile = new JLabel("File: ");
		
		
		
		
		JButton btnStart = new JButton("Encrypt");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test t = new test();
				if(folderch)
				{
					ProgressThread pt = new ProgressThread();
					thread.start();
					//pt.start();
					//EncryptZip();
					
				}
				else{
					System.out.println(file);
				
					cr.start();
				}
				

				
			}
		});
		
		// http://www.java67.com/2015/07/how-to-stop-thread-in-java-example.html
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("pause");
				
				if(!needToPause)
				{
					needToPause = true;
				}
				else
					needToPause = false;
				
				
			}
		});
		
		
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thread.stop();
				progressBar.setMaximum(0);
				progressBar.setValue(0);
				progressBar.repaint();
			}
		});
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dec.start();
				
			}
		});
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblFile)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(97)
							.addComponent(btnPause, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(btnDecrypt, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnStart, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFile)
						.addComponent(label))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnStart)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDecrypt)
					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPause)
						.addComponent(btnStop))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel);
		  pack();
	}
	
	public static void EncryptZip()
	{
		test.main();
		crt.Encrypt(na,folname,path);
	}
	
	public static void EncryptFile()
	{
		crt.Encrypt(name,file,dir);
	}
	
	public static void DecryptFile()
	{
		crt.Decrypt(name,file,dir);
	}

	

    public synchronized void pausePoint() {
        while (needToPause) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public synchronized void pause() {
        needToPause = true;
    }

    public synchronized void unpause() {
        needToPause = false;
        this.notifyAll();
    }
    public boolean getPausecon()
    {
    	return needToPause;
    }
}
