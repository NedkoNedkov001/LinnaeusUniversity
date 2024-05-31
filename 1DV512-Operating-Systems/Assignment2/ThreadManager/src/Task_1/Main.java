package Task_1;

import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		Semaphore semA = new Semaphore(1);
		Semaphore semB = new Semaphore(0);

		Worker wa = new Worker("A", semA, semB);
		Thread ta = new Thread(wa);
		ta.start();

		Worker wb = new Worker("B", semB, semA);
		Thread tb = new Thread(wb);
		tb.start();

		try {
			ta.join();
			tb.join();
		} catch (InterruptedException e) {
		}
	}
}