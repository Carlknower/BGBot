package de.carlos.music.commands;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import de.carlos.BlasmusikGeschenkeBot;
import de.carlos.commands.manager.ServerCommand;
import de.carlos.music.AudioLoadResult;
import de.carlos.music.MusicController;
import de.carlos.music.MusicUtil;
import de.carlos.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand2 implements ServerCommand {

	// VERALTETE KLASSE, NICHT MEHR IN VERWENDUNG
	
	
	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		// TODO Auto-generated method stub
		if (message.getContentDisplay().toString().length() < 9) {
			channel.sendMessage(
					"Du hast zu wenig Argumente f¸r diesen Befehl angegeben. Nutze `/bg help play` f¸r Hilfe.").queue();
		} else {
			String[] args = message.getContentDisplay().substring(9).split(" ");

			if (args.length < 1) {
				channel.sendMessage("Du musst einen Link oder einen Suchbegriff angeben. `/bg help play` f¸r Hilfe.")
						.queue();

				return;
			}
			if (args.length > 0) {
				GuildVoiceState state;
				if ((state = p.getVoiceState()) != null) {
					VoiceChannel vc;
					if ((vc = state.getChannel()) != null) {

						String input = String.join(" ", args);

						if (!(isUrl(input)) && !input.startsWith("ytsearch:")) {
							channel.sendMessage("Bitte gib einen validen Youtube-, Soundcloud- oder Bandcamp-Link ein.")
									.queue();

							return;
						}

						PlayerManager manager = PlayerManager.getInstance();

						manager.loadAndPlay(channel, input);
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
				channel.sendMessage(
						"Der Fehler, von dem keiner weiﬂ, woher er kommt.")
						.queue();
			}
		}
	}

	private boolean isUrl(String input) {
		try {
			new URL(input);

			return true;
		} catch (MalformedURLException ignored) {
			return false;
		}
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return null;
	}

}
