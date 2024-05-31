package Task_3;

import java.util.concurrent.Semaphore;

public class MessageQueue implements IMessageQueue {
    private char[] buffer;
    private byte maxBufferSize;
    private Semaphore receivedMessages;
    private Semaphore sentMessages;
    private Semaphore available;
    private byte head;
    private byte tail;

    public MessageQueue(byte maxBufferSize) {
        this.buffer = new char[maxBufferSize];
        this.maxBufferSize = maxBufferSize;
        this.receivedMessages = new Semaphore(maxBufferSize);
        this.sentMessages = new Semaphore(0);
        this.available = new Semaphore(1);
        this.head = 0;
        this.tail = 0;
    }

    @Override
    public boolean Send(char msg) {
        try {
            receivedMessages.acquire();
            buffer[head] = msg;
            head = (byte) ((head + 1) % maxBufferSize);
            sentMessages.release();
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public char Recv() {
        Character msg = null;
        try {
            available.acquire();
            sentMessages.acquire();
            msg = buffer[tail];
            tail = (byte) ((tail + 1) % maxBufferSize);
            receivedMessages.release();
            available.release();
        } catch (InterruptedException e) {
        }
        return msg;
    }
}
