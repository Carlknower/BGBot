package de.carlos.music.commands;

import de.carlos.commands.manager.ServerCommand;
import de.carlos.music.GuildMusicManager;
import de.carlos.music.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class StopCommand implements ServerCommand {

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		// TODO Auto-generated method stub
		final GuildVoiceState memberVoiceState = p.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in a voice channel for this command to work").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(channel.getGuild());

        musicManager.scheduler.player.stopTrack();
        musicManager.scheduler.queue.clear();

        channel.sendMessage("The player has been stopped and the queue has been cleared").queue();
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return "stoppt den aktuellen Titel und löscht die Wiedergabeliste.";
	}
}
