package diretorio;


import estruturasDeCoordenacao.Semaphore;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class Diretorio {

	private static Diretorio instance;

	private final static long CLIENT_HANDLER_TIMEOUT = 60000 * 3 /*(minutes)*/;
	private final static int CLIENT_HANDLER_TIMEOUT_EVALUATION_PERIOD = 60000 * 4 /*(minutes)*/;

	private int port;
	private ServerSocket serverSocket;
	private Socket socket;
	private List<ClientHandler> listClientHandler;
	private Semaphore listClientHandlerSemaphoreAcessManager;

	protected Diretorio(int port) {
		instance = this;
		this.port = port;
		this.listClientHandler = new ArrayList<>();
		this.listClientHandlerSemaphoreAcessManager = new Semaphore(1);
		this.implementClientHandlerTimeoutEvaluation();
	}

	public static Diretorio getInstance(){
		return instance;
	}

	void startDiretorio() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Diretorio iniciado e a aceitar pedidos em " + serverSocket.toString());
			while (!Thread.currentThread().isInterrupted()) {
				waitForConnection();
				buildClientHandler();
			}
		} catch (IOException e) {
			System.out.println("Tentativa de ligação de " + socket.toString() + " falhou!");
			e.printStackTrace();
		}
	}

	private List<ClientHandler> getListClientHandler() {
		return listClientHandler;
	}

	void addClientHandler(ClientHandler ch) {
		listClientHandlerSemaphoreAcessManager.acquire();
		listClientHandler.add(ch);
		listClientHandlerSemaphoreAcessManager.release();
		System.out.println("New connection: Client Handler: " + socket.getRemoteSocketAddress().toString());
	}

	void removeClientHandler(ClientHandler clientHandler, boolean useLocalSemaphore) {
		if(useLocalSemaphore) {
			listClientHandlerSemaphoreAcessManager.acquire();
		}
		this.listClientHandler.remove(clientHandler);
		clientHandler.stopThread();
		if(useLocalSemaphore) {
			listClientHandlerSemaphoreAcessManager.release();
		}
		System.out.println(Thread.currentThread().getName() + " connection closed");
	}

	ArrayList<String> CLTResponse() {
		ArrayList<String> listaDeClientesAux = new ArrayList<>();
		listClientHandlerSemaphoreAcessManager.acquire();
		for (ClientHandler cH : listClientHandler)
			listaDeClientesAux.add("CLT " + cH.getIP() + " " + cH.getPorto());
		listClientHandlerSemaphoreAcessManager.release();
		return listaDeClientesAux;
	}

	private void waitForConnection() throws IOException {
		socket = serverSocket.accept();
	}

	private void buildClientHandler() {
		ClientHandler clientHandler = new ClientHandler(this, socket);
		new Thread(clientHandler, "Client Handler: " + socket.getRemoteSocketAddress().toString()).start();
	}

	private void implementClientHandlerTimeoutEvaluation(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						sleep(CLIENT_HANDLER_TIMEOUT_EVALUATION_PERIOD);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					listClientHandlerSemaphoreAcessManager.acquire();
					long time = System.currentTimeMillis();
					for(ClientHandler clientHandler: Diretorio.getInstance().getListClientHandler()){
						if(time - clientHandler.getLastActivityTime() > CLIENT_HANDLER_TIMEOUT){
							Diretorio.getInstance().removeClientHandler(clientHandler, false);
						}
						else {
							System.out.println("CLient " + clientHandler.toString() + " timein");
						}
					}
					listClientHandlerSemaphoreAcessManager.release();
				}
			}
		});
	}

	// TODO apagar no final
	String printClientHandlers(){
		StringBuilder stringBuilder = new StringBuilder();
		for(ClientHandler clientHandler:this.listClientHandler){
			stringBuilder.append(clientHandler.toString()).append('\n');
		}
		return stringBuilder.toString();
	}
}
