
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.net.Socket;

/*    */
/*    */ public class Clientrunnable implements Runnable
/*    */ {
	/*    */ private Socket _socket;
	/* 10 */ protected String CharacterSet = "ASCII";

	/*    */
	/*    */ public Clientrunnable(Socket soc) {
		/* 13 */ this._socket = soc;
		/* 14 */ System.out.print("Thread ID : ");
		/* 15 */ System.out.println(Thread.currentThread().getId());
		/*    */ try {
			/* 17 */ System.out.println("KEEPALIVE CHECK : " + soc.getKeepAlive());
			/* 18 */ System.out.println("Remote Client :" + soc.getRemoteSocketAddress().toString());
			/* 19 */ System.out.println("Timeout : " + soc.getSoTimeout());
			/* 20 */ this._socket.setKeepAlive(true);
			/* 21 */ this._socket.setSoTimeout(5000);
			/* 22 */ System.out.println("KEEPALIVE CHECK : " + this._socket.getKeepAlive());
			/* 23 */ System.out.println("Timeout : " + this._socket.getSoTimeout());
			/*    */ }
		/*    */ catch (java.net.SocketException e) {
			/* 26 */ e.printStackTrace();
			/*    */ }
		/*    */ }

	/*    */
	/*    */ public void run()
	/*    */ {
		/*    */ try
		/*    */ {
			/* 34 */ InputStream in = this._socket.getInputStream();
			/* 35 */ @SuppressWarnings("unused")
			int byteRead = 0;
			/*    */ do
			/*    */ {
				/* 38 */ if (in.available() > 0)
				/*    */ {
					/* 40 */ byte[] bArr = new byte[in.available()];
					/* 41 */ byteRead = in.read(bArr, 0, in.available());
					/* 42 */ String strCommand = new String(bArr, this.CharacterSet);
					/* 43 */ System.out.println(strCommand);
					/* 44 */ String[] strInput = strCommand.split(":");
					/* 45 */ if (strInput.length >= 2)
					/*    */ {
						/* 47 */ OutputStream out = this._socket.getOutputStream();
						/* 48 */ String strMessage = "command is success. \r\n";
						/* 49 */ out.write(strMessage.getBytes(this.CharacterSet), 0, strMessage.length());
						/* 50 */ out.flush();
						/*    */ }
					/*    */ else
					/*    */ {
						/* 54 */ OutputStream out = this._socket.getOutputStream();
						/* 55 */ String strMessage = "We Cannot support your command \r\n";
						/* 56 */ out.write(strMessage.getBytes(this.CharacterSet), 0, strMessage.length());
						/* 57 */ out.flush();
						/* 58 */ break;
						/*    */ }
					/*    */ }
				/* 61 */ Thread.currentThread();
				/* 62 */ } while (!Thread.interrupted());
			/* 63 */ this._socket.close();
			/*    */ }
		/*    */ catch (IOException e)
		/*    */ {
			/* 67 */ e.printStackTrace();
			/*    */ }
		/*    */ }
	/*    */ }

/*
 * Location: D:\Java Environment\Java Console
 * Application\OpenPort.jar!\Clientrunnable.class Java compiler version: 8
 * (52.0) JD-Core Version: 0.7.1
 */