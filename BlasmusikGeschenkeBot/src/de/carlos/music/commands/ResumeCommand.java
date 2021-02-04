package de.carlos.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import de.carlos.BlasmusikGeschenkeBot;
import de.carlos.commands.manager.ServerCommand;
import de.carlos.music.GuildMusicManager;
import de.carlos.music.MusicController;
import de.carlos.music.TrackScheduler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class ResumeCommand implements ServerCommand {

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		GuildVoiceState state;

		if ((state = p.getVoiceState()) != null) {
			VoiceChannel vc;
			if ((vc = state.getChannel()) != null) {
				MusicController controller = BlasmusikGeschenkeBot.INSTANCE.playerManager
						.getController(vc.getGuild().getIdLong());
				// AudioManager manager = vc.getGuild().getAudioManager();
				AudioPlayer player = controller.getPlayer();
				try {

					if (player.isPaused()) {
						player.setPaused(false);
						channel.sendMessage("Jetzt geht es weiter!").queue();
					}

					// togglePause(p);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * public synchronized boolean togglePause(Member p) { VoiceChannel vc;
	 * GuildVoiceState state; state = p.getVoiceState(); vc = state.getChannel();
	 * MusicController controller = BlasmusikGeschenkeBot.INSTANCE.playerManager
	 * .getController(vc.getGuild().getIdLong()); AudioPlayer player =
	 * controller.getPlayer(); long pauseStart = 0; if (!player.isPaused()) {
	 * pauseStart = System.currentTimeMillis() / 1000L; player.setPaused(true); }
	 * else { long currentSongStartTimeInSeconds = (System.currentTimeMillis() /
	 * 1000L) - pauseStart; player.setPaused(false); } return player.isPaused();
	 * 
	 * }
	 */
	@Override
	public String getInformation() {
		return "` startet die Wiedergabe wieder. `/bg pause` hält die Wiedergabe an.";
	}
}