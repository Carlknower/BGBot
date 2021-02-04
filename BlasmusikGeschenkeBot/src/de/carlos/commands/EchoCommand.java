package de.carlos.commands;

import de.carlos.commands.manager.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class EchoCommand implements ServerCommand {
	public String getInformation() {
		return " [Nachricht]` antwortet mit der gleichen Nachricht, die du ihm schreibst";
	}

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		// TODO Auto-generated method stub
		channel.sendMessage(message.getContentDisplay().substring(9)).queue();
		
	}
}
