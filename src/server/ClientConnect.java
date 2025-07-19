package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnect extends Thread {

//    public dung chung
    public Socket client;
    public Server server;

    private String nickName;
    private DataOutputStream dos;
    private DataInputStream dis;
    private boolean run;

public ClientConnect(Server server, Socket client) {
        try {
            this.server = server;
            this.client = client;
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
            run = true;
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        // xữ lý đăng nhập
        String msg = null;
        while (run) {
            nickName = getMSG();
            if (checkNick(nickName)) {
                sendMSG("0");
            } else {
                server.user.append(nickName + " đã kết nối với room\n");
// tin nhan gui tu server 
                server.sendAll(nickName, nickName + " đã vào room với anh em");
                server.listUser.put(nickName, this);
                server.sendAllUpdate(nickName);
                sendMSG("1");
                displayAllUser();
                while (run) {
                    int stt = Integer.parseInt(getMSG());
                    switch (stt) {
                        case 0:
                            run = false;
                            server.listUser.remove(this.nickName);
                            exit();
                            break;
                        case 1:
                            msg = getMSG();
// tin nhan gui tu server QUAN TRONG: GUI TIN ----------------------------------------------------------------------------    
                            server.sendAll(nickName, nickName + " : " + msg);
                            break;
                        case 2:
                            msg = getMSG();
                            server.sendAllObject(nickName, msg);
//                                ---------------------------------------------- bat dau tu day
                            break;
                    }
                }
            }

        }
    }

// thoát    
    private void exit() {
        try {
            server.sendAllUpdate(nickName);
            dos.close();
            dis.close();
            client.close();
            server.user.append(nickName + " đã thoát\n");
// gửi tin thoát cho các client
            server.sendAll(nickName, nickName + " đã thoát");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// kiểm tra nick trả về true nếu tồn tại nick
    private boolean checkNick(String nick) {
        return server.listUser.containsKey(nick);
    }

// nhận tin xử lý từ client    
    private void sendMSG(String data) {
        try {
            dos.writeUTF(data);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

// ham duoc goi o server  
    public void sendMSG(String msg1, String msg2) {
        sendMSG(msg1);
        sendMSG(msg2);
    }

// hàm xử lý ảnh trung gian, nhận sự kiện từ server    
    public void sendObject(String msg1, String msg2) {
        sendMSG(msg1);
        sendMSG(msg2);
    }

// đọc văn bản từ client gửi lên  
    private String getMSG() {
        String data = null;
        try {
            data = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

// cập nhật danh sách onine    
    private void displayAllUser() {
        String name = server.getAllName();
        sendMSG("4");
        sendMSG(name);
    }
}
