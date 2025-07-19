# study_project_chat_socket

A simple Java Swing-based chat application for internal network communication. This project was originally created as a school assignment and demonstrates basic client-server socket programming with a graphical user interface.

## Features
- Real-time chat between multiple clients on a local network
- Java Swing GUI for both server and client
- Online user list display
- Support for sending text messages and images (emoticons)
- Basic text formatting (bold, italic, font size, color)

## Project Structure
- `src/server/Server.java`: Main server application. Handles client connections and message broadcasting.
- `src/server/ClientConnect.java`: Handles individual client sessions on the server.
- `src/client/Client.java`: Main client application. Provides the chat UI and connects to the server.
- `src/client/DataStream.java`: Handles incoming data streams for the client.
- `file_icon/` and `src/image/`: Contains image files used as chat emoticons.

## How to Run

### Prerequisites
- Java JDK 8 or higher
- (Optional) NetBeans or any Java IDE for easier project management

### 1. Build the Project
You can use NetBeans to build the project, or compile manually:

```
javac -d build src/server/Server.java src/server/ClientConnect.java src/client/Client.java src/client/DataStream.java
```

### 2. Start the Server
Run the server application first:

```
java -cp build server.Server
```

A window titled "Chat Chit : Server" will open. The server listens on port 1997 for client connections.

### 3. Start the Client(s)
Run the client application (can be on the same or different machines on the same network):

```
java -cp build client.Client
```

A window titled "Chat room : Client" will open. Enter the server's IP address when prompted.

## Usage
- Enter a nickname to join the chat room.
- Type messages and send them to all connected users.
- Use the online user list to see who is connected.
- Click on emoticon images to send them in chat.
- Use formatting options (bold, italic, font size, color) for your messages.
- The server window displays connection logs and allows you to close the server.

## Notes
- All communication is local network only (no internet relay).
- The server must be running before any clients can connect.
- Images for emoticons must be present in the `file_icon/` directory.

## License
This project is for educational purposes only.
