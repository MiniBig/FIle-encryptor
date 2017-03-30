package Threads;

import Wind.MainForm;

public class DeCrypto extends Thread{

	public void run(){
		MainForm.DecryptFile();
	}
	
	
	
}