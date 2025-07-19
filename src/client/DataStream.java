
package client;


import java.io.DataInputStream;
import java.io.IOException;

public class DataStream extends Thread {

    private boolean run;
    private DataInputStream dis;
    private Client client;

    public DataStream(Client client, DataInputStream dis) {
        run = true;
        this.client = client;
        this.dis = dis;
        this.start();
    }


    public void run() {
        String msg1, msg2;
        while (run) {
            try {
                msg1 = dis.readUTF();
                msg2 = dis.readUTF();
                if(msg1.equals("1") || msg1.equals("2") || msg1.equals("3") || msg1.equals("4") || msg1.equals("5")) {
                    // gửi tin nhắn về client
                    client.getMSG(msg1, msg2);
                } else {
                    // gửi ảnh về client
                    client.getObject(msg1, msg2);
                }               
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// dừng thread    
    public void stopThread() {
        this.run = false;
    }
}
