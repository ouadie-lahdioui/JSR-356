package demo.webSocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@ServerEndpoint( value = "/ws/contact/{buyingFrame}/{subDepartement}")
public class Contact {
	
	// Guava MultiMap API
	private static final Multimap<String, Session> SESSIONS;
	private static final String BUYING_FRAME_PARAM = "buyingFrame";
	private static final String CLIENT_PARAM = "clients";
	
	static {
		SESSIONS = ArrayListMultimap.create();
	}
	
	@OnOpen
	public void OnOpen(Session session, EndpointConfig config) {
		
		String buyingFrame = session.getPathParameters().get(BUYING_FRAME_PARAM);
		
		Set<String> clients = new HashSet<String>();
		clients.add("SQLi");
		clients.add("Atos");
		
		session.getUserProperties().put(CLIENT_PARAM, clients);
		
		SESSIONS.put(buyingFrame, session);
		
		System.out.println("Server > Session opened : " + session.getId() + " / " + SESSIONS.size());
	}
	
	@OnClose
	public void onClose(Session session) {
		
		String buyingFrame = session.getPathParameters().get(BUYING_FRAME_PARAM);
		SESSIONS.get(buyingFrame).remove(session);
		
		System.out.println("Server > Session closed : " + session.getId() + " / " + SESSIONS.size());
	}

	public static void notifyAll(String buyingFrame, List<String> clients) {
		System.out.println("Server > Prodcast to all sessions");
		try {
			for (Entry<String, Session> sessionEntry : SESSIONS.entries()) {
				Session session = sessionEntry.getValue();
				
				System.out.println("Sending data to :");
				System.out.println("BuyingFrame = " + sessionEntry.getKey() + ", SessionId = " + session.getId());
				
				session.getBasicRemote().sendText(buyingFrame);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
