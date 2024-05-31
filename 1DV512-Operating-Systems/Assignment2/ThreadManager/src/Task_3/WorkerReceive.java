package Task_3;

public class WorkerReceive implements Runnable {
    private MessageQueue messageQueue;

    public WorkerReceive(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(messageQueue.Recv());
        }
    }

}
