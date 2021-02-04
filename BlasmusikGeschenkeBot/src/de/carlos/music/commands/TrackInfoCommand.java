package de.carlos.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import de.carlos.BlasmusikGeschenkeBot;
import de.carlos.commands.manager.ServerCommand;
import de.carlos.music.MusicController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class TrackInfoCommand implements ServerCommand{

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		/*/
		MusicController controller = BlasmusikGeschenkeBot.INSTANCE.playerManager.getController(channel.getGuild().getIdLong());
		AudioPlayer player = controller.getPlayer();
		AudioTrack track;
		if((track = player.getPlayingTrack()) != null) {
			AudioTrackInfo info = track.getInfo();
						
			String author = info.author;
			String title = info.title;
			String url = info.uri;
			long length = track.getDuration();
			boolean isStream = info.isStream;
			long position = track.getPosition();
			
			EmbedBuilder builder = new EmbedBuilder();
			builder.setAuthor(author);
			builder.setTitle(title, url);
			
			long curSeconds = position / 1000;
			long curMinutes = curSeconds / 60;
			long curStunden = curMinutes / 60;
			curSeconds %= 60;
			curMinutes %= 60;
			
			long maxSeconds = length / 1000;
			long maxMinutes = maxSeconds / 60;
			long maxStunden = maxMinutes / 60;
			maxSeconds %= 60;
			maxMinutes %= 60;
			
			String time = ((curStunden > 0) ? curStunden + "h " : "") + curMinutes + "min " + curSeconds + "s / " + ((maxStunden > 0) ? maxStunden + "h " : "") + maxMinutes + "min " + maxSeconds + "s";
			
			builder.setDescription(isStream ? "-> STREAM" : "-> " + time);

			channel.sendMessage(builder.build()).queue();
			
		}else {
			channel.sendMessage(new EmbedBuilder().setDescription("Ich spiel nix").build()).queue();
			
		}
		/*/
	}

	@Override
	public String getInformation() {
		return null;
	}
	
	
	
}
