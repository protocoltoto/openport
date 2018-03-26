import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Clientrunnable implements Runnable {
	private Socket _socket;
	protected String CharacterSet = "ASCII";

	public Clientrunnable(Socket soc) {
		this._socket = soc;
		System.out.print("Thread ID : ");
		System.out.println(Thread.currentThread().getId());
		try {
			System.out.println("KEEPALIVE CHECK : " + soc.getKeepAlive());
			System.out.println("Remote Client :" + soc.getRemoteSocketAddress().toString());
			System.out.println("Timeout : " + soc.getSoTimeout());
			this._socket.setKeepAlive(true);
			this._socket.setSoTimeout(5000);
			System.out.println("KEEPALIVE CHECK : " + this._socket.getKeepAlive());
			System.out.println("Timeout : " + this._socket.getSoTimeout());
		} catch (java.net.SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			InputStream in = this._socket.getInputStream();
			@SuppressWarnings("unused")
			int byteRead = 0;
			do {
				if (in.available() > 0) {
					byte[] bArr = new byte[in.available()];
					byteRead = in.read(bArr, 0, in.available());
					String strCommand = new String(bArr, this.CharacterSet);
					System.out.println(strCommand);
					String[] strInput = strCommand.split(":");
					if (strInput.length >= 2) {
						OutputStream out = this._socket.getOutputStream();
						String strMessage = "command is success. \r\n";
						out.write(strMessage.getBytes(this.CharacterSet), 0, strMessage.length());
						out.flush();
					} else {
						OutputStream out = this._socket.getOutputStream();
						String strMessage = "We Cannot support your command \r\n";
						out.write(strMessage.getBytes(this.CharacterSet), 0, strMessage.length());
						out.flush();
						break;
					}
				}
				Thread.currentThread();
			} while (!Thread.interrupted());
			this._socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
