package diretorio;

import java.io.*;
import java.net.Socket;


public class ClientHandler implements Runnable {

	private boolean stopped = false;
	private Diretorio diretorio;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private int p2pPort;
	private long lastActivityTime;

	public ClientHandler(Diretorio diretorio, Socket socket) {
		this.diretorio = diretorio;
		this.socket = socket;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.lastActivityTime = System.currentTimeMillis();
	}

	public long getLastActivityTime() {
		return lastActivityTime;
	}

	public void setLastActivityTime(long lastActivityTime) {
		this.lastActivityTime = lastActivityTime;
	}

	String getIP() {
		return socket.getInetAddress().getHostAddress();
	}

	int getPorto() {
		return p2pPort;
	}

	void stopThread(){
		this.stopped = true;
	}

	@Override
	public void run() {
		try {
		    String str = "     ";
		    diretorio.addClientHandler(this);
		    while (!str.substring(0, 4).equals("INSC")) {
		    	str = in.readLine();
		    }
		    this.p2pPort = Integer.parseInt(str.split("\\s+")[2].replace(" ", ""));
		    this.lastActivityTime = System.currentTimeMillis();

		    while (!stopped) {
				str = in.readLine();
				this.lastActivityTime = System.currentTimeMillis();
				if (str.equals("CLT")) {
					for (String resposta : diretorio.CLTResponse()) {
						sendMessage(resposta);
					}
					sendMessage("END");
				}
				else if(str.equals("BYE")) {
					diretorio.removeClientHandler(this, true);
				}
				else if(str.equals("HI")) {
					this.setLastActivityTime(System.currentTimeMillis());
				}
				else {
					throw new IllegalStateException("Client message " + str + " is not valid!!");
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	private void sendMessage(String mensagem){
		out.println(mensagem);
		out.flush();
	}

	@Override
	public String toString(){
		return socket.toString() + " | " + p2pPort;
	}

}
