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

public class PauseCommand implements ServerCommand{

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        GuildVoiceState state;
        
        
        if((state = m.getVoiceState()) != null) {
            VoiceChannel vc;
            if((vc = state.getChannel()) != null) {
                MusicController controller = BlasmusikGeschenkeBot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
                AudioManager manager = vc.getGuild().getAudioManager();
                AudioPlayer player = controller.getPlayer();
                
                if(!player.isPaused()) {
                    player.setPaused(true);
                    channel.sendMessage("Wiedergabe angehalten!").queue();
                }
            }
        }
    }

	@Override
	public String getInformation() {
		return "` hält die Wiedergabe an. `/bg resume` beendet die Pause.";
	}
}