
package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Client extends JFrame implements ActionListener, MouseListener {
    private Socket client;
    private DataStream dataStream;
    private DataOutputStream dos;
    private DataInputStream dis;
    private JButton send, clear, exit, login, logout;
    private JButton bt_b,  bt_i;
    private JPanel p_login, p_chat;
    private JTextField nick, nickIn, message;
    private JTextArea online;
    private JTextPane msg;
    private JPanel iconPane;
    private JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, lb9;
    private ImageIcon icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8, icon9;
    private JComboBox listSize, listColor;
    private SimpleAttributeSet keyWord;
    private boolean check_b = false, check_i = false;
    private StyledDocument doc;
    private String[] str1 =  {"10" , "12" , "14" , "16" , "18" , "20" , "22" , "24" , "26" , "28" , "30"};
    private String[] str2 =  {"WHITE" , "YELLOW" , "BLUE" , "MAGENTA" , "CYAN" , "BLACK" , "GRAY" , "GREEN" , "ORANGE" , "PINK" , "RED"};

    public Client() {
        super("Chat room : Client");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        setSize(900, 600);
        addItem();
        setVisible(true);
        
    }
    //-----[ Tạo giao diện ]--------//

    private void addItem() {
        setLayout(new BorderLayout());
        
        listSize =new JComboBox(str1);
        listSize.setSelectedIndex(5);
        listSize.addActionListener(this);
        
        listColor =new JComboBox(str2);
        listColor.setSelectedIndex(5);
        listColor.addActionListener(this);

        iconPane = new JPanel();
        iconPane.setLayout(new FlowLayout());
        iconPane.setBackground(Color.WHITE);
        iconPane.setVisible(false);
        
        bt_b = new JButton("đậm");
        bt_b.addActionListener(this);
        bt_i = new JButton("nghiêng");
        bt_i.addActionListener(this);
        
        lb1 = new JLabel("");
        lb2 = new JLabel("");
        lb3 = new JLabel("");
        lb4 = new JLabel("");
        lb5 = new JLabel("");
        lb6 = new JLabel("");
        lb7 = new JLabel("");
        lb8 = new JLabel("");
        lb9 = new JLabel("");

        lb1.setSize(50, 50);
        lb2.setSize(50, 50);
        lb3.setSize(50, 50);
        lb4.setSize(50, 50);
        lb5.setSize(50, 50);
        lb6.setSize(50, 50);
        lb7.setSize(50, 50);
        lb8.setSize(50, 50);
        lb9.setSize(50, 50);

        lb1.addMouseListener(this);
        lb2.addMouseListener(this);
        lb3.addMouseListener(this);
        lb4.addMouseListener(this);
        lb5.addMouseListener(this);
        lb6.addMouseListener(this);
        lb7.addMouseListener(this);
        lb8.addMouseListener(this);
        lb9.addMouseListener(this);

        icon1 = new ImageIcon();
        icon2 = new ImageIcon();
        icon3 = new ImageIcon();
        icon4 = new ImageIcon();
        icon5 = new ImageIcon();
        icon6 = new ImageIcon();
        icon7 = new ImageIcon();
        icon8 = new ImageIcon();
        icon9 = new ImageIcon();

        createIcon(icon1, "file_icon/mc1.jpg");
        createIcon(icon2, "file_icon/mc2.jpg");
        createIcon(icon3, "file_icon/mc3.jpg");
        createIcon(icon4, "file_icon/mc4.jpg");
        createIcon(icon5, "file_icon/mc5.jpg");
        createIcon(icon6, "file_icon/mc6.jpg");
        createIcon(icon7, "file_icon/mc7.jpg");
        createIcon(icon8, "file_icon/mc8.jpg");
        createIcon(icon9, "file_icon/mc9.jpg");

        lb1.setIcon(icon1);
        lb2.setIcon(icon2);
        lb3.setIcon(icon3);
        lb4.setIcon(icon4);
        lb5.setIcon(icon5);
        lb6.setIcon(icon6);
        lb7.setIcon(icon7);
        lb8.setIcon(icon8);
        lb9.setIcon(icon9);
        
        iconPane.add(lb1);
        iconPane.add(lb2);
        iconPane.add(lb3);
        iconPane.add(lb4);
        iconPane.add(lb5);
        iconPane.add(lb6);
        iconPane.add(lb7);
        iconPane.add(lb8);
        iconPane.add(lb9);

        keyWord = new SimpleAttributeSet();
        StyleConstants.setFontSize(keyWord, 20);
        StyleConstants.setForeground(keyWord, Color.BLACK);
        StyleConstants.setBackground(keyWord, Color.WHITE);
        StyleConstants.setBold(keyWord, check_b);
        StyleConstants.setItalic(keyWord, check_i);

        exit = new JButton("Thoát");
        exit.addActionListener(this);
        send = new JButton("Gửi");
        send.addActionListener(this);
        clear = new JButton("Xóa");
        clear.addActionListener(this);
        login = new JButton("Đăng nhập");
        login.addActionListener(this);
        logout = new JButton("Thoát");
        logout.addActionListener(this);

        p_chat = new JPanel();
        p_chat.setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        nick = new JTextField(20);
        p1.add(new JLabel("Níck chát : "));
        p1.add(nick);
        p1.add(exit);

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());

        JPanel p22 = new JPanel();
        p22.setLayout(new FlowLayout(FlowLayout.CENTER));
        p22.add(new JLabel("Danh sách online"));
        p2.add(p22, BorderLayout.NORTH);

        online = new JTextArea(10, 10);
        online.setEditable(false);
        p2.add(new JScrollPane(online), BorderLayout.CENTER);
        p2.add(new JLabel("     "), BorderLayout.SOUTH);
        p2.add(new JLabel("     "), BorderLayout.EAST);
        p2.add(new JLabel("     "), BorderLayout.WEST);

        msg = new JTextPane();
        msg.setEditable(false);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Tin nhắn"));
        message = new JTextField(30);
        message.addActionListener(this);
        p3.add(message);
        p3.add(send);
        p3.add(clear);
        p3.add(bt_b);
        p3.add(bt_i);
        p3.add(listSize);
        p3.add(listColor);
        add(iconPane, BorderLayout.SOUTH);

        p_chat.add(new JScrollPane(msg), BorderLayout.CENTER);
        p_chat.add(p1, BorderLayout.NORTH);
        p_chat.add(p2, BorderLayout.EAST);
        p_chat.add(p3, BorderLayout.SOUTH);
        p_chat.add(new JLabel("     "), BorderLayout.WEST);

        p_chat.setVisible(false);
        add(p_chat, BorderLayout.CENTER);
        
        p_login = new JPanel();
        p_login.setLayout(new FlowLayout(FlowLayout.CENTER));
        p_login.add(new JLabel("Nick chát : "));
        nickIn = new JTextField(20);
        nickIn.addActionListener(this);
        p_login.add(nickIn);
        p_login.add(login);
        p_login.add(logout);

        add(p_login, BorderLayout.NORTH);
    }
    //---------[ Socket ]-----------//	

    private void go() {
        try {
            String ip = "localhost";
            ip = JOptionPane.showInputDialog(null, "Nhập IP máy chủ"); // hộp thoại yêu cầu nhập ip
            client = new Socket(ip, 1997);
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
            
        // môi trường hiển thịs
            doc = msg.getStyledDocument();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối.", "Message Dialog", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Client().go();
    }

    private void createIcon(ImageIcon ii, String s) {
        try {
            BufferedImage image = ImageIO.read(new File(s));
            ii.setImage(image.getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// gửi luồng dữ liệu    
    private void sendMSG(String data) {
        try {
            dos.writeUTF(data);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// lấy luồng dữ liệu    
    private String getMSG() {
        String data = null;
        try {
            data = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

// nhận và xữ lý luồng dữ liệu 
    public void getMSG(String msg1, String msg2) {
        int stt = Integer.parseInt(msg1);
        switch (stt) {
            // tin nhắn của người khác
            case 3:
                try {
                    this.doc.insertString(doc.getLength(), msg2 + "\n", keyWord);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            // update danh sách online
            case 4:
                this.online.setText(msg2);
                break;
            // server đóng cửa
            case 5:
                dataStream.stopThread();
                exit();
                break;
            default:
                break;
        }
    }

// xữ lý nhận ảnh    
    public void getObject(String s1, String s2) {
        try {
            doc.insertString(doc.getLength(), s1 + ":   ", keyWord);
            msg.setSelectionStart(doc.getLength());
            msg.setSelectionEnd(doc.getLength() + 1);
            if (s2.equals("lb1")) {
               msg.insertIcon(icon1);
            } else if (s2.equals("lb2")) {
               msg.insertIcon(icon2);
            } else if (s2.equals("lb3")) {
               msg.insertIcon(icon3);
            } else if (s2.equals("lb4")) {
               msg.insertIcon(icon4);
            } else if (s2.equals("lb5")) {
               msg.insertIcon(icon5);
            } else if (s2.equals("lb6")) {
               msg.insertIcon(icon6);
            } else if (s2.equals("lb7")) {
               msg.insertIcon(icon7);
            } else if (s2.equals("lb8")) {
               msg.insertIcon(icon8);
            } else if (s2.equals("lb9")) {
               msg.insertIcon(icon9);
            }
            doc.insertString(doc.getLength(), "\n", keyWord);
        } catch (BadLocationException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// xữ lý print text ở nơi gửi và gọi bước tiếp theo 
    private void checkSend(String msg3) {
        if (msg3.compareTo("\n") != 0) {
            try {
                this.doc.insertString(doc.getLength(), "Tôi: " + msg3 + "\n", keyWord);
                sendMSG("1");
                sendMSG(msg3);
            } catch (BadLocationException ex) {
                Logger.getLogger(Client.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

// xữ lý print ảnh ở nơi gửi
    private void printObject(ImageIcon ic) {
        try {
            doc.insertString(doc.getLength(), "Tôi: ", keyWord);
            msg.setSelectionStart(doc.getLength());
            msg.setSelectionEnd(doc.getLength() + 1);
            msg.insertIcon(ic);
            doc.insertString(doc.getLength(), "\n", keyWord);

        } catch (BadLocationException ex) {
            Logger.getLogger(Client.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

// xữ lý đăng nhập    
    private boolean checkLogin(String nick) {
        if (nick.compareTo("") == 0) {
            return false;
        } else if (nick.compareTo("0") == 0) {
            return false;
        } else {
            sendMSG(nick);
            int sst = Integer.parseInt(getMSG());
            if (sst == 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    
// thoát    
    private void exit() {
        try {
            sendMSG("0");
            dos.close();
            dis.close();
            client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.exit(0);
    }

// nhận và xữ lý sự kiện    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            dataStream.stopThread();
            exit();
        } else if (e.getSource() == clear) {
            message.setText("");
        } else if (e.getSource() == send || e.getSource() == message) {
            checkSend(message.getText());
            message.setText("");
        } else if(e.getSource() == bt_b) {
            if(check_b == false) {
                bt_b.setText("Bình thường");
                check_b = true;
            } else {
                bt_b.setText("Đậm");
                check_b = false;
            }
            StyleConstants.setBold(keyWord, check_b);
        } else if(e.getSource() == bt_i)  {
            if(check_i == false) {
                bt_i.setText("Bình thường");
                check_i = true;
            } else {
                bt_i.setText("Nghiêng");
                check_i = false;
            }
            StyleConstants.setItalic(keyWord, check_i);
        } else if (e.getSource() == listSize) {
            JComboBox cb = (JComboBox)e.getSource();
            int size = Integer.parseInt((String) cb.getSelectedItem());
            StyleConstants.setFontSize(keyWord, size);
        } else if (e.getSource() == listColor) {
            try {
                JComboBox cb = (JComboBox)e.getSource();
                String color = (String) cb.getSelectedItem();
                Color aColor   = (Color) Color.class.getField(color).get(null);
                StyleConstants.setForeground(keyWord, aColor);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == login || e.getSource() == nickIn) {
            if (checkLogin(nickIn.getText())) {
                p_chat.setVisible(true);
                iconPane.setVisible(true);
                p_login.setVisible(false);
                nick.setText(nickIn.getText());
                nick.setEditable(false);
                this.setTitle(nickIn.getText());
                try {
                    doc.insertString(doc.getLength(), "Đã đăng nhập thành công.\n", keyWord);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                dataStream = new DataStream(this, this.dis);
            } else {
                JOptionPane.showMessageDialog(this, "Ðã tồn tại nick này, vui lòng nhập lại.", "Message Dialog", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == logout) {
            exit();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

// xữ lý sự kiện trên label    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == lb1) {
            printObject(icon1);
            sendMSG("2");
            sendMSG("lb1");
        } else if (e.getSource() == lb2) {
            printObject(icon2);
            sendMSG("2");
            sendMSG("lb2");
        } else if (e.getSource() == lb3) {
            printObject(icon3);
            sendMSG("2");
            sendMSG("lb3");
        } else if (e.getSource() == lb4) {
            printObject(icon4);
            sendMSG("2");
            sendMSG("lb4");
        } else if (e.getSource() == lb5) {
            printObject(icon5);
            sendMSG("2");
            sendMSG("lb5");
        } else if (e.getSource() == lb6) {
            printObject(icon6);
            sendMSG("2");
            sendMSG("lb6");
        } else if (e.getSource() == lb7) {
            printObject(icon7);
            sendMSG("2");
            sendMSG("lb7");
        } else if (e.getSource() == lb8) {
            printObject(icon8);
            sendMSG("2");
            sendMSG("lb8");
        } else if (e.getSource() == lb9) {
            printObject(icon9);
            sendMSG("2");
            sendMSG("lb9");
        }
    }
}