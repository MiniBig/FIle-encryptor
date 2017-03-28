package source;

import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipNativeInitializationException;

public class test {
    public static void main(String[] args) {
        try {
        	// http://sevenzipjbind.sourceforge.net/first_steps.html
            SevenZip.initSevenZipFromPlatformJAR();
            System.out.println("7-Zip-JBinding library was initialized");
        } catch (SevenZipNativeInitializationException e) {
            e.printStackTrace();
        }
    }
}