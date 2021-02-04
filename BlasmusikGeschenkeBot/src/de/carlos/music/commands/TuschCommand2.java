package de.carlos.music.commands;

import java.awt.Color;

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

public class TuschCommand2 implements ServerCommand {

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		// TODO Auto-generated method stub
		/*
		 * / String[] args = message.getContentDisplay().split(" ");
		 * 
		 * 
		 * GuildVoiceState state; if ((state = p.getVoiceState()) != null) {
		 * VoiceChannel vc; if((vc = state.getChannel()) != null) { MusicController
		 * controller =
		 * BlasmusikGeschenkeBot.INSTANCE.playerManager.getController(vc.getGuild().
		 * getIdLong()); AudioPlayerManager apm =
		 * BlasmusikGeschenkeBot.INSTANCE.audioPlayerManager;
		 * 
		 * AudioManager manager = vc.getGuild().getAudioManager();
		 * manager.openAudioConnection(vc);
		 * 
		 * MusicUtil.updateChannel(channel);
		 * 
		 * StringBuilder strBuilder = new StringBuilder(); String url =
		 * "https://www.youtube.com/watch?v=Vv-wq-prqNk";
		 * 
		 * apm.loadItem(url, new AudioLoadResult(controller, url));
		 * 
		 * }else { EmbedBuilder builder = new EmbedBuilder();
		 * builder.setDescription("Huch, du bist wohl in keinem VoiceChannel.");
		 * builder.setColor(Color.decode("#f22613"));
		 * channel.sendMessage(builder.build()).queue(); } } else { EmbedBuilder builder
		 * = new EmbedBuilder();
		 * builder.setDescription("Huch, du bist wohl in keinem VoiceChannel.");
		 * builder.setColor(Color.decode("#f22613"));
		 * channel.sendMessage(builder.build()).queue(); } /
		 */

		final GuildVoiceState memberVoiceState = p.getVoiceState();

		if (!memberVoiceState.inVoiceChannel()) {
			channel.sendMessage("Du musst in einem Sprachkanal sein, um diesen Befehl nutzen zu können.").queue();
			return;
		}

		String link = "https://www.youtube.com/watch?v=Vv-wq-prqNk";

		PlayerManager.getInstance().loadAndPlay(channel, link);

		JoinCommand join = new JoinCommand();
		join.performCommand(p, channel, message);
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return null;
	}

}
