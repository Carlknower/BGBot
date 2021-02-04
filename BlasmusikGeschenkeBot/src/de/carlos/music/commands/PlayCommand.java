package de.carlos.music.commands;

import java.awt.Color;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import de.carlos.BlasmusikGeschenkeBot;
import de.carlos.commands.manager.ServerCommand;
import de.carlos.music.AudioLoadResult;
import de.carlos.music.MusicController;
import de.carlos.music.MusicUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ServerCommand {

	// VERALTETE KLASSE, NICHT MEHR IN VERWENDUNG
	
	
	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
/*/
		String[] args = message.getContentDisplay().split(" ");

		if (args.length > 2) {
			GuildVoiceState state;
			if ((state = p.getVoiceState()) != null) {
				VoiceChannel vc;
				if ((vc = state.getChannel()) != null) {
					MusicController controller = BlasmusikGeschenkeBot.INSTANCE.playerManager
							.(vc.getGuild().getIdLong());
					AudioPlayerManager apm = BlasmusikGeschenkeBot.INSTANCE.audioPlayerManager;

					AudioManager manager = vc.getGuild().getAudioManager();
					manager.openAudioConnection(vc);

					MusicUtil.updateChannel(channel);

					StringBuilder strBuilder = new StringBuilder();
					if (args.length > 3) {
						for (int i = 2; i < args.length - 1; i++)
							strBuilder.append(args[i] + "+");
						// System.out.println(strBuilder.toString());
						
						String url = strBuilder.toString().trim();
						if (!url.startsWith("http")) {
							// url = "ytsearch: " + url;
							url = "https://www.youtube.com/results?search_query=" + url;

							System.out.println(url);
						}
						apm.loadItem(url, new AudioLoadResult(controller, url)); // controller, url));
					} else {
						for (int i = 2; i < args.length; i++)
							strBuilder.append(args[i] + " ");
						// System.out.println(strBuilder.toString());

						String url = strBuilder.toString().trim();
						if (!url.startsWith("http")) {
							// url = "ytsearch: " + url;
							url = "https://www.youtube.com/results?search_query=" + url;
							
							System.out.println(url);
						}
						
						apm.loadItem(url, new AudioLoadResult(controller, url)); // controller, url));
					}
					//apm.loadItem(url, new AudioLoadResult(controller, url)); // controller, url));

				} else {
					EmbedBuilder builder = new EmbedBuilder();
					builder.setDescription("Huch, du bist wohl in keinem VoiceChannel.");
					builder.setColor(Color.decode("#f22613"));
					channel.sendMessage(builder.build()).queue();
				}
			} else {
				EmbedBuilder builder = new EmbedBuilder();
				builder.setDescription("Huch, du bist wohl in keinem VoiceChannel.");
				builder.setColor(Color.decode("#f22613"));
				channel.sendMessage(builder.build()).queue();
			}
		} else {
			EmbedBuilder builder = new EmbedBuilder();
			builder.setDescription("Bitte benutze `/bg play <url/search query>`");
			builder.setColor(Color.decode("#f22613"));
			channel.sendMessage(builder.build()).queue();
		}
/*/
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return null;
	}
}
