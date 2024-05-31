package Task_2;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Worker implements Runnable {
	private String message;
	private ArrayList<Semaphore> semaphores;

	public Worker(String message, ArrayList<Semaphore> semaphores) {
		this.message = message;
		this.semaphores = semaphores;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < semaphores.size() - 1; j++) {
				try {
					semaphores.get(0).acquire();
				} catch (InterruptedException e) {
				}
				System.out.print(message);
				semaphores.get(j + 1).release();
			}
		}
	}
}
