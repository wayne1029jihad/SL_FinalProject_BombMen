package main;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ConnectException;


import processing.core.PApplet;

public class Client extends PApplet implements Closeable{
	private static final long serialVersionUID = 1L;
	private String destinationIPAddr;
	private int destinationPortNum;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private boolean change = false;
	private int number;
	Socket connectionToServer;
	ClientThread connection;
	String data;

	public Client(String IPAddress, int portNum) {
		this.destinationIPAddr = IPAddress;
		this.destinationPortNum = portNum;
	}

	public void sendMessage(String message) {//send message to server
		this.writer.println(message);
		this.writer.flush();
		System.out.println("Send");
	}

	public Client setIPAddress(String IPAddress) {//set IP address
		this.destinationIPAddr = IPAddress;
		return this;
	}

	public Client setPort(int portNum) {//set port number
		this.destinationPortNum = portNum;
		return this;
	}

	public void connect() {
		// Create socket & thread, remember to start your thread
		try {
			this.connectionToServer = new Socket(this.destinationIPAddr, this.destinationPortNum);
			this.writer = new PrintWriter(new OutputStreamWriter(this.connectionToServer.getOutputStream()));
			this.reader = new BufferedReader(new InputStreamReader(this.connectionToServer.getInputStream()));
			this.connection = new ClientThread(reader);
			this.connection.start();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Define an inner class for handling message reading
	public class ClientThread extends Thread {
		private BufferedReader reader ;
		
		public ClientThread(BufferedReader reader)
		{
			this.reader = reader;
		}
		public void run()
		{
			String line;
			try {
				line = reader.readLine();
				System.out.println("Initial line:"+line);
				number = Integer.valueOf(line);
				System.out.println("Initial number:"+number);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}	
			while (true) {								
				try {
					line = reader.readLine();
					Client.this.getLine(line);
					setchange(true);
					System.out.println("CLI:"+line);
					sleep(100);
					//delay(100);
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}				
			}
		}
	}
	private void getLine(String line)//get data form server
	{
		this.data = line;
	}
	public String getdata()//send massage to class create this client
	{		
		System.out.println("get:"+data);
		return data;
	}
	public boolean getchange()//signal of get new data from server 
	{
		return change;
	}
	public void setchange(boolean change)//set change signal
	{
		this.change = change;
	}
	public int getNumber()
	{
		return number;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		connectionToServer.close();
	}
}
