package de.carlos.commands;

import java.util.ArrayList;
import java.util.List;

import de.carlos.commands.manager.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClearCommand implements ServerCommand {

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		//if (!p.hasPermission(channel, Permission.MESSAGE_MANAGE)) {
		if(!hasRole(p, "Moderator")) {
			channel.sendMessage(
					"Fehler: Du verfügst nicht über die Berechtigung, Nachrichten in diesem Kanal zu löschen. Bitte wende dich an einen Moderator oder einen Admin.")
					.queue();
			return;
		}
//System.out.println(message.getContentDisplay().substring(9).trim());
		String[] args = (message.getContentDisplay().substring(8)).trim().split(" ");

		switch (args.length) {
		case 2:
			try {
				int amount = Integer.parseInt(args[1]);
				channel.purgeMessages(getMessages(channel, amount));
				// TODO send to log
				channel.sendMessage(p.getEffectiveName() + " hat " + amount + " Nachrichten gelöscht.").queue();
				return;
			} catch (NumberFormatException e) {
				channel.sendMessage("'" + args[1] + "' ist keine ganze Zahl").queue();
			}
			return;
		default:
			if (args.length < 2) {
				channel.sendMessage("Der Command muss die Anzahl an zu löschenden Nachrichten als Attribut haben.")
						.queue();
			} else {
				channel.sendMessage("Der Command darf nur ein Attribut haben.").queue();
			}
			return;
		}

		

	}

	private List<Message> getMessages(MessageChannel channel, int amount) {
		List<Message> messages = new ArrayList<>();
		int i = amount + 1;

		for (Message message : channel.getIterableHistory().cache(false)) {
			if (!message.isPinned()) {
				messages.add(message);
			}
			if (--i <= 0)
				break;
		}

		return messages;
	}

	public String getInformation() {
		return " [Anzahl der zu löschenden Nachrichten]` löscht die angegebene Anzahl an Nachrichten aus dem aktuellen Textkanal.";
	}

	public static boolean isInteger(String s) {
		System.out.println(s);
		if (s == null) {
			return false;
		}
		s = s.trim();
		if (s.length() == 0) {
			return false;
		}
		int start = 0;
		if (s.charAt(0) == '-') { // handle negative numbers
			if (s.length() == 1) {
				return false;
			} else {
				start = 1;
			}
		}
		for (int i = start; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean hasRole(Member member, String name) {
		List<Role> roles = member.getRoles();
		for(int i = 0; i < roles.size(); i++) {
			if(roles.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

}
