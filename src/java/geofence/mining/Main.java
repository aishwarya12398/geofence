/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geofence.mining;

import java.io.File;
import java.io.FileNotFoundException;

public class Main
{

    static int threshold = 2;
    static String file = "dataset.csv";

    public static void main(String[] args) throws FileNotFoundException
    {
	long start = System.currentTimeMillis();
	//new FPGrowth(new File(file), threshold);
	System.out.println((System.currentTimeMillis() - start));
    }
}
