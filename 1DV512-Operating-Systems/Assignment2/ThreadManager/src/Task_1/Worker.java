package Task_1;

import java.util.concurrent.Semaphore;

public class Worker implements Runnable{
	private String message;
	private Semaphore semThis;
	private Semaphore semSignal;

	public Worker(String message, Semaphore semThis, Semaphore semSignal) {
		this.message = message;
		this.semThis = semThis;
		this.semSignal = semSignal;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10 ; i++) {
			try {
				semThis.acquire();
			} catch (InterruptedException e) {
			}
			System.out.print(message);
			semSignal.release();
		}
	}
}
