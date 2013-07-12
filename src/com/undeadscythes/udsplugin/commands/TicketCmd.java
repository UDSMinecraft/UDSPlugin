package com.undeadscythes.udsplugin.commands;

import com.undeadscythes.udsplugin.Color;
import com.undeadscythes.udsplugin.CommandWrapper;
import com.undeadscythes.udsplugin.ConfigRef;
import com.undeadscythes.udsplugin.UDSPlugin;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.StringUtils;

/**
 * Toggles the admin chat channel.
 * @author UndeadScythes
 */
public class TicketCmd extends CommandWrapper {
    @Override
    public void playerExecute() {
        if(args.length == 0) {
            player.sendMessage(Color.ERROR + "You cannot send a blank ticket.");
        } else {
            final String username = UDSPlugin.getConfigString(ConfigRef.GMAIL_ADDRESS);
            final String password = UDSPlugin.getConfigString(ConfigRef.GMAIL_PASSWORD);
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
                message.setSubject("UDS Test");
                message.setText(StringUtils.join(args, " "));
                Transport transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", username, password);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                player.sendMessage(Color.MESSAGE + "Ticket sent.");
            } catch (MessagingException e) {
                player.sendMessage(Color.ERROR + "There was an error sending your ticket.");
            }
        }
    }
}
