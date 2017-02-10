package slack.events;

import com.ullink.slack.simpleslackapi.SlackSession;
import mother.Connect;
import dto.socket.ClientMessage;
import dto.socket.MotherMessage;
import slack.Formatter;

public class MessageListener
{
    private Connect mother;

    public MessageListener(Connect mother) {
        this.mother = mother;
    }

    public void slackMessagePostedEventContent(SlackSession session) {
        session.addMessagePostedListener((message, listenerSession) -> {
            System.out.println(message.getMessageContent());

            String messageContent = message.getMessageContent();

            // Not for me. Ignore.
            if (!messageContent.contains("@" + listenerSession.sessionPersona().getId())) {
                return;
            }

            // Sent by me. Ignore.
            if (listenerSession.sessionPersona().getId().equals(message.getSender().getId())) {
                return;
            }

            ClientMessage clientMessage = new ClientMessage();

            clientMessage
                .setText(messageContent)
                .setChatId(message.getChannel().getId())
                .setSenderId(listenerSession.sessionPersona().getId())
            ;

            MotherMessage motherMessage = this.mother.send(clientMessage);

            listenerSession.sendMessage(message.getChannel(), Formatter.format(motherMessage.getText()));
        });
    }
}
