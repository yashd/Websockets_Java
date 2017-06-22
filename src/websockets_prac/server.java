package websockets_prac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/server")
public class server {

	static HashMap<Integer, Session> sessions = new HashMap<Integer, Session>();
	static int count = 1;
	static Scanner input=new Scanner(System.in);

	@OnOpen
	public void handleOpen(Session session) {
		System.out.println("Cnnection established for server");
		sessions.put(count, session);
		count++;
		System.out.println(sessions.size());

		System.out.println("Showing all sessions");
		for (int k : sessions.keySet()) {

			System.out.println(k + "===>" + sessions.get(k));
		}

	}

	@OnMessage
	public void handleMessage(String message, Session s) {
		System.out.println("Received message from client :" + message);
		String new_msg = "hello " + message;
		System.out.println("Sending message  to client :" + new_msg);
		try {

			System.out.println("printing number of sessions" + sessions.size());
		// for (Session ss:sessions.values()){
			System.out.println(s.getBasicRemote());
			System.out.println("Enter the message to the cleint");
		   String send_msg=input.nextLine();
			s.getBasicRemote().sendText(send_msg);
			System.out.println("send message to peer ");
			// }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@OnClose
	public void handleClose(Session session) {
		System.out.println("Cnnection closed for server");
		for (int k : sessions.keySet()) {

			if (sessions.get(k) == session) {
				sessions.remove(k);

			}
		}
       System.out.println("Remaining Sessions:"+sessions.size());
	}

	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}

}
