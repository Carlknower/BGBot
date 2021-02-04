package de.carlos.commands;

import java.security.Permission;
import java.util.List;

import de.carlos.BlasmusikGeschenkeBot;
//import de.carlos.BlasmusikGeschenkeBot;
import de.carlos.commands.manager.ServerCommand;
import de.carlos.manage.LiteSQL;
//import de.carlos.manage.LiteSQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
//import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class ShutDownCommand implements ServerCommand {

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		
		if(!hasRole(p, "Admin")) {
			channel.sendMessage("Du hast keine Berechtigung, mich einfach so auszuschalten! Das darf nur mein Papa! :-)").queue();
		}else {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Bis bald!");
		builder.setDescription("Herunterfahren...");
		builder.setColor(0x26a65b);
		channel.sendMessage(builder.build()).queue();

		/*
		 * if(shardMan !=null) { shardMan.setStatus(OnlineStatus.OFFLINE);
		 * LiteSQL.disconnect(); System.out.println("Bot offline"); shardMan.shutdown();
		 * }
		 */
		if(BlasmusikGeschenkeBot.INSTANCE.shardMan !=null) {
			BlasmusikGeschenkeBot.INSTANCE.shardMan.setStatus(OnlineStatus.OFFLINE);
			LiteSQL.disconnect();
			System.out.println("Bot offline");
			BlasmusikGeschenkeBot.INSTANCE.shardMan.shutdown();
		}

		}
	}
	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean hasRole(Member member, String name) {
		List<Role> roles = member.getRoles();
		for(int i = 0; i < roles.size(); i++) {
			if(roles.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
       // return roles.stream().filter(role -> role.getName().equals(name)).findFirst().orElse(null);
	}

}
