package Threads;

import Wind.MainForm;
import source.test;

public class ProgressThread extends Thread {
	
	public void run(){
		 MainForm.progressBar.setValue(test.n);
		//MainForm.progressBar.update();
	}
	
	
}
