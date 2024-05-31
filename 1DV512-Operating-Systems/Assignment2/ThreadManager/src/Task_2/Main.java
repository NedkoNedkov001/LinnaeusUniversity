package Task_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		Semaphore semA = new Semaphore(1);
		Semaphore semB = new Semaphore(0);
		Semaphore semC = new Semaphore(0);
		Semaphore semD = new Semaphore(0);

		Worker wa = new Worker("A", new ArrayList<Semaphore>(Arrays.asList(semA, semB, semC)));
		Thread ta = new Thread(wa);

		Worker wb = new Worker("B", new ArrayList<Semaphore>(Arrays.asList(semB, semA)));
		Thread tb = new Thread(wb);

		Worker wc = new Worker("C", new ArrayList<Semaphore>(Arrays.asList(semC, semD, semA)));
		Thread tc = new Thread(wc);

		Worker wd = new Worker("D", new ArrayList<Semaphore>(Arrays.asList(semD, semC)));
		Thread td = new Thread(wd);

		tb.start();
		ta.start();
		tc.start();
		td.start();

		try {
			ta.join();
			tb.join();
			tc.join();
			td.join();
		} catch (InterruptedException e) {
		}
	}
}