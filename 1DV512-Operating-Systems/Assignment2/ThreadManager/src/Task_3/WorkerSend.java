package Task_3;

public class WorkerSend implements Runnable {
    private char msg;
    private MessageQueue messageQueue;

    public WorkerSend(char msg, MessageQueue messageQueue) {
        this.msg = msg;
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while (true) {
            messageQueue.Send(msg);

        }
    }
}
