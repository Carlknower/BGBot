package de.carlos.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import de.carlos.BlasmusikGeschenkeBot;
import de.carlos.commands.manager.ServerCommand;
import de.carlos.music.MusicController;
import de.carlos.music.MusicUtil;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class StopCommandAlt implements ServerCommand{

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		/*/
		GuildVoiceState state;
		if ((state = p.getVoiceState()) != null) {
			VoiceChannel vc;
			if((vc = state.getChannel()) != null) {
				MusicController controller = BlasmusikGeschenkeBot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
				AudioManager manager = vc.getGuild().getAudioManager();	
				AudioPlayer player = controller.getPlayer();
				MusicUtil.updateChannel(channel);
				player.stopTrack();
				manager.closeAudioConnection();
				message.addReaction("U+1f44c").queue();
			}
		}
		/*/
	}

	@Override
	public String getInformation() {
		return null;
	}
	
}
