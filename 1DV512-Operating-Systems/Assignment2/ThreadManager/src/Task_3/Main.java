package Task_3;

public class Main {
    public static void main(String[] args) {
        byte maxBufferSize = 5;
        MessageQueue messageQueue = new MessageQueue(maxBufferSize);

        WorkerSend sa = new WorkerSend('A', messageQueue);
        Thread ta = new Thread(sa);
        ta.start();

        WorkerSend sb = new WorkerSend('B', messageQueue);
        Thread tb = new Thread(sb);
        tb.start();

        WorkerSend sc = new WorkerSend('C', messageQueue);
        Thread tc = new Thread(sc);
        tc.start();

        WorkerReceive rd = new WorkerReceive(messageQueue);
        Thread td = new Thread(rd);
        td.start();

        try {
            ta.join();
            tb.join();
            tc.join();
            td.join();
        } catch (Exception e) {
        }

    }
}
