import com.ullink.slack.simpleslackapi.SlackSession;
import libs.helpers.Config;
import mother.Connect;
import org.fusesource.jansi.AnsiConsole;
import slack.connections.DirectConnector;

import java.io.IOException;
import slack.events.MessageListener;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class App
{
    public static void main( String[] args ) throws IOException {
        AnsiConsole.systemInstall();

        Config config = new Config("./config.properties");

        String token = config.get("slack.token");

        Boolean isTokenSet = token != null && !token.equals("SetYourToken");

        if (!isTokenSet) {
            System.out.println("Token: " + ansi().bg(RED).fg(BLACK).a("NOT SET").reset());
            System.out.println("Please set your Slack token");
            return;
        }

        System.out.println("Token: " + ansi().fg(GREEN).bold().a(token).reset());

        SlackSession session = DirectConnector.getSession(token);


        Connect mother = new Connect(config.get("mother.host"),  Integer.parseInt(config.get("mother.port")));

        MessageListener listener = new MessageListener(mother);

        listener.slackMessagePostedEventContent(session);
    }
}
