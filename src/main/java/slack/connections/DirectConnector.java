package slack.connections;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.IOException;

public class DirectConnector {
    private static SlackSession session;

    public static SlackSession getSession(String token) throws IOException {
        if (session != null) {
            return session;
        }

        session = SlackSessionFactory.createWebSocketSlackSession(token);
        session.connect();

        return session;
    }
}
