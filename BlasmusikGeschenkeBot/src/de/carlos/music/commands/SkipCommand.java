package de.carlos.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import de.carlos.commands.manager.ServerCommand;
import de.carlos.music.GuildMusicManager;
import de.carlos.music.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class SkipCommand implements ServerCommand {

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		// TODO Auto-generated method stub
		final GuildVoiceState memberVoiceState = p.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Du musst in einem Sprachkanal sein, um diesen Befehl nutzen zu können.").queue();
            return;
        }

       

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if (audioPlayer.getPlayingTrack() == null) {
            channel.sendMessage("There is no track playing currently").queue();
            return;
        }

        musicManager.scheduler.nextTrack();
        channel.sendMessage("Skipped the current track").queue();
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return "überspringt den aktuellen Titel.";
	}
}
