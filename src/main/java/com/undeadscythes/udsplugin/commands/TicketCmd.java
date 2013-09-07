package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.*;
import java.util.*;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.*;

/**
 * Submit a ticket to the email address supplied in the config.
 *
 * @author UndeadScythes
 */
public class TicketCmd extends CommandHandler {
    @Override
    public void playerExecute() {
        if(args.length == 0) {
            player.sendError("You cannot send a blank ticket.");
        } else {
            final String username = Config.GMAIL_ADDRESS;
            final String password = Config.GMAIL_PASSWORD;
            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.user", username);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, null);
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(username));
                message.setSubject("New UDS Minecraft Ticket - " + player.getName());
                message.setText(argsToMessage());
                Transport transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", username, password);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                player.sendNormal("Ticket sent.");
            } catch(MessagingException e) {
                player.sendError("There was an error sending your ticket.");
            }
        }
    }
}
