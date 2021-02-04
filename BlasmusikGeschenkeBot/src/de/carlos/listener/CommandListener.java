package de.carlos.listener;

import de.carlos.BlasmusikGeschenkeBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.UserById;

public class CommandListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		// Privatnachrichten an Carlos weiterleiten
		if(event.isFromType(ChannelType.PRIVATE) && !event.getAuthor().getName().equalsIgnoreCase("BlasmusikGeschenke")) {
			Message nachricht = event.getMessage();
			User p = event.getAuthor();
			//User carlos = User.fromId(782299938293022751l);
			String carlosIdStr = "782299938293022751";
			long carlosIdLong = Long.parseLong(carlosIdStr);
			User carlos = event.getJDA().getUserById(carlosIdLong);
			System.out.println(carlos);
			
			carlos.openPrivateChannel().queue((pc) -> {
				pc.sendMessage(p.getName() + "#" + p.getDiscriminator() + " hat folgende Nachricht an mich gesendet:").queue();
				pc.sendMessage(nachricht).queue();
			});
			
		}
		
		
		
		if (event.isFromType(ChannelType.TEXT) && !event.getAuthor().getName().equalsIgnoreCase("BlasmusikGeschenke")) {
			String message = event.getMessage().getContentDisplay();
			if (!message.startsWith("/bg ")) {
				return;
			}
			String[] args = message.substring(4).split(" ");

			if (args.length > 0) {
				Boolean success = BlasmusikGeschenkeBot.INSTANCE.getCmdMan().canPerform(args[0], event.getMember(),
						event.getTextChannel(), event.getMessage());
				if (!success) {
					event.getChannel()
							.sendMessage("Ich kenne diesen Command nicht. '/bg help' zeigt dir alle meine Commands an.")
							.queue();
				}
			}
			// event.getChannel().sendMessage("Hallo Welt").queue();
		}

	}
	
	private boolean isBot(Member player) {
		if (player.getEffectiveName().equalsIgnoreCase("BlasmusikGeschenke")) {
			return true;
		}
		return false;
	}
}
