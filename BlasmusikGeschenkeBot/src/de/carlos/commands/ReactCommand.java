package de.carlos.commands;

import java.util.List;

import de.carlos.commands.manager.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class ReactCommand implements ServerCommand {

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		// TODO Auto-generated method stub

		// args[0]args[1] args[2] args[3]
		// /bg react #channel 21651352 :ok:

		if (hasRole(p, "Moderator")) {
			String[] args = message.getContentDisplay().split(" ");

			// List<TextChannel>
			// if(!channel.getJDA().getTextChannelsByName(args[2], false).isEmpty()) {

			List<TextChannel> channels = message.getMentionedChannels();

			if (!channels.isEmpty()) {
				TextChannel tc = message.getMentionedChannels().get(0);
				String messageIDString = args[3];

				try {
					Long messageID = Long.parseLong(messageIDString);

					for (int i = 4; i < args.length; i++) {
						tc.addReactionById(messageID, args[i]).queue();

					}
				} catch (NumberFormatException e) {
				}

			}
		}

	}

	public boolean hasRole(Member member, String name) {
		List<Role> roles = member.getRoles();
		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return " [Textkanal] [Nachrichten-ID] [Emojis]` lässt den Bot mit Emojis auf Nachrichten antworten.";
	}

}
