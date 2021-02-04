package de.carlos.commands;

import java.util.List;

import de.carlos.commands.manager.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class PreviewCommand implements ServerCommand {

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		
		if(hasRole(p, "Moderator")) {

		List<TextChannel> channels = message.getMentionedChannels();

		if (!channels.isEmpty()) {
			TextChannel tc = message.getMentionedChannels().get(0);

			try {
				String nachricht = message.getContentDisplay().substring(message.getContentDisplay().indexOf(" ", 13));
				EmbedBuilder builder = new EmbedBuilder();
				builder.setDescription(nachricht);
				builder.setColor(0x26a65b);
				builder.setAuthor(p.getEffectiveName());
				tc.sendMessage(builder.build()).queue();
			} catch (NumberFormatException e) {
				channel.sendMessage("Fehler. Irgendwas ist schiefgelaufen.");
			}

	}else

	{
		channel.sendMessage(
				"Du musst einen Channel angeben, in dem du die Nachricht senden willst. Tippe dazu # ein und wähle aus der Liste den entsprechenden Kanal aus. Für Hilfe tippe `/bg help preview` ein.").queue();
	}message.delete().queue();
		}else {
			channel.sendMessage("Du kannst diesen Befehl nicht benutzen, weil du kein Administrator bist.");
		}
	}

	public String getInformation() {
		// TODO Auto-generated method stub

		return " [Textkanal] [Nachricht]` lässt dich eine eingebettete Nachricht erstellen. Nur für Admins und Teammitglieder.";

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
