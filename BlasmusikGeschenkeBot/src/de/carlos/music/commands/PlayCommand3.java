package de.carlos.music.commands;

import java.net.URI;
import java.net.URISyntaxException;

import de.carlos.commands.manager.ServerCommand;
import de.carlos.music.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayCommand3 implements ServerCommand {
	// @SuppressWarnings("ConstantConditions")
	@Override
		public void performCommand(Member p, TextChannel channel, Message message) {
	    	if (message.getContentDisplay().toString().length() < 9) {
				channel.sendMessage(
						"Du hast zu wenig Argumente für diesen Befehl angegeben. Nutze `/bg help play` für Hilfe.").queue();
			} else {
				String[] args = message.getContentDisplay().substring(9).split(" ");
				
	        if (args.length < 1) {
	            channel.sendMessage("Correct usage is `/bg play <youtube link>`").queue();
	            return;
	        }

	        final GuildVoiceState memberVoiceState = p.getVoiceState();

	        if (!memberVoiceState.inVoiceChannel()) {
	            channel.sendMessage("Du musst in einem Sprachkanal sein, um diesen Befehl nutzen zu können.").queue();
	            return;
	        }

	        String link = String.join(" ", args);

	        /*/if (!isUrl(link)) {
	            link = "ytsearch:" + link;
	        }/*/
	        if(!link.startsWith("http")) {
	        	link = "ytsearch:" + link;
	        }

	        PlayerManager.getInstance().loadAndPlay(channel, link);}
	    	JoinCommand join = new JoinCommand();
	    	join.performCommand(p, channel, message);
	    }

	private boolean isUrl(String url) {
		try {
			new URI(url);
			return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return "` Plays a song\n" + "Usage: `/bg play <youtube link>`";
	}

}
