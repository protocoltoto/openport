import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	public static void main(String[] args) {
		int portNumber = 3116;
		if (args.length > 0) {
			portNumber = Integer.parseInt(args[0]);
		}
		ExecutorService es = Executors.newFixedThreadPool(10);
		Scanner sc = new Scanner(System.in);
		Clientrunnable client = null;
		String stroption = "";
		try {
			ServerSocket serverSocket = null;
			do {
				java.net.Socket clientSocket;
				printmenu();
				if (serverSocket != null) {
					clientSocket = serverSocket.accept();
					client = new Clientrunnable(clientSocket);
					es.execute(client);
				}

				stroption = sc.next();
				switch (stroption) {
				case "1":
					if (serverSocket == null) {
						serverSocket = new ServerSocket(portNumber);
					}
					System.out.print(
							String.format("Listener port %d is open\n", new Object[] { Integer.valueOf(portNumber) }));
					break;
				case "2": {
					if (serverSocket != null) {
						serverSocket.close();
						serverSocket = null;
					}
					System.out.print(String.format("Listener port %d is closed\n",
							new Object[] { Integer.valueOf(portNumber) }));
				}
					break;
				case "3":
					break;
				default:
					System.out.print("Invalid Options!! \n");
				}
			} while (!stroption.equals("3"));
			System.out.print("Bye Bye!! \n");
			System.exit(0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			sc.close();
		}
	}

	private static void printmenu() {
		System.out.println("1. Start Listener");
		System.out.println("2. Stop Listener");
		System.out.println("3. Exit");
	}
}
