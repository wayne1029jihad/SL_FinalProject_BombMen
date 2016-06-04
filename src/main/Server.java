package main;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class Server extends JFrame {
	private static final long serialVersionUID = 1L;
	private ServerSocket serverSocket;
	private List<ConnectionThread> connections = new ArrayList<ConnectionThread>();
	private JTextArea textArea;
	private String loginpasswd = "", loginaccount = "";
	BufferedReader br = null;
	BufferedWriter wr = null;
	
	//prop
	private randomGenerator RG = new randomGenerator(15,13);
	public Server(int portNum) {				
		setSize(300, 250);// set window size
		setResizable(false);// fixed the window size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Initialize textArea show information from client and method 
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		this.textArea.setPreferredSize(new Dimension(250, 2500));
		JScrollPane scrollPane = new JScrollPane(this.textArea);
		this.add(scrollPane);
		this.setVisible(true);
		try {
			this.serverSocket = new ServerSocket(portNum);
			addLine("Server starts listening on port "+portNum+".\n");//show on frame when server socket build successful			
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	private void begin(boolean start)//broadcast the start signal
	{
		if(start)
		{
			broadcast("BEGIN");
		}		
	}
	public void runForever() {
		int count = 1;
		//System.out.println("Server starts waiting for client.");
		addLine("Server starts waiting for client.");			
		
		while (count < 3) {//only make to two player
			try {
				Socket connectionToClient = this.serverSocket.accept();
				addLine("Get connection from client " + connectionToClient.getInetAddress() + ":"
						+ connectionToClient.getPort());
				System.out.println("Get connection from client " + connectionToClient.getInetAddress() + ":"
						+ connectionToClient.getPort());
				// new served client thread start
				ConnectionThread connThread = new ConnectionThread(connectionToClient);
				connThread.sendMessage(Integer.toString(count));
				addLine(Integer.toString(count));
				connThread.sendMessage(RG.getPropMap());
				connThread.start();				
				connections.add(connThread);
				//let connThread wait connections.sendMessage();
				count++;
			} catch (IOException e) {}
		}
		boolean test = true;
		while(true)
		{
			if(connections.get(0).getReady() && connections.get(1).getReady())
			{
				addLine("Game Start!");//show on frame			
				begin(true);
				break;
			}
			else
				System.out.print("");
				
			if(test)//some bug, if not add ,check never used   
			{
				test = false;				
			}	
		}
	}
	public void setchange(boolean change)
	{
		for (ConnectionThread connection : connections) {			
			connection.setchange(change);//reset all change signal (like broadcast)
		}
	}

	private void broadcast(String message) {//send same message to all client	
		for (ConnectionThread connection : connections) {
			connection.sendMessage(message);//call connection's method to send message
		}
	}

	class ConnectionThread extends Thread {
		private Socket socket;
		private BufferedReader reader;
		private PrintWriter writer;
		private String line;
		private boolean change = false;
		private boolean ready = false;
		public ConnectionThread(Socket socket) {
			this.socket = socket;
			try {
				this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		private String loginjudge(String line){
			int i;
			PApplet s = new PApplet();
			JSONArray data = s.loadJSONArray("account.json");
			JSONObject obj = null;
			String []arr = line.split("@",2);
			String message = "";
			for (i = 0;i< data.size();i++){
				obj = data.getJSONObject(i);
				if (obj.getString("account").equals(arr[0]))
					break;
			}
			if(data.size() == 0 ){
				addLine("account list is empty");
			} else if (data.size() == i){
				message = "noaccount";
			} else {
				if (obj.getString("password").equals(arr[1])) {
					message = "true";
					//String compasswd = "", comacct = "";
					if (loginaccount.equals("")) {
						loginaccount = arr[0];
						loginpasswd = arr[1];
					} else if (loginaccount.equals(arr[0])){
						message = "islogin";
					}
				} else {
					message = "wrongpwd";
				}
			}
			addLine(message);
			addLine(arr[0] + " and " + arr[1]);
			return message;
		}
		public void run() {				
			String msg = "";
			do {
				try {
					line = this.reader.readLine();
					addLine("receive: " + line);
					msg = loginjudge(line);
					sendMessage(msg);
				} catch (IOException e) {
					//e.printStackTrace();
				}
			} while(!msg.equals("true"));
			while (true) {				
				try {
					line = this.reader.readLine();
					if(line.equals("READY"))
						ready();
					else
						Server.this.broadcast(line);
					addLine("data:"+line); // line is message from server					
				} catch (IOException e) {
					//e.printStackTrace();
				}			
			}
		}
		public void ready()
		{
			ready = true;
		}
		public String getline()//accept read line from client
		{
			return line;
		}
		public boolean getchange()//signal of get new data from client
		{			
			return change;
		}
		public void setchange(boolean change)//set change signal
		{
			this.change = change;
		}
		public void sendMessage(String message) {//send message to client by socket
			this.writer.println(message);
			this.writer.flush();
		}
		public boolean getReady()
		{
			return ready;
		}
	}

	private void addLine(final String message) {//show information on frame
		this.textArea.append(message + "\n");
	}

	public static void main(String[] args) {//main of server
		Server server = new Server(8000);
		server.runForever();
	}
}
