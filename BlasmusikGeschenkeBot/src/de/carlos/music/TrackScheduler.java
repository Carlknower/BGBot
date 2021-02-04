package de.carlos.music;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import de.carlos.BlasmusikGeschenkeBot;
import de.carlos.manage.LiteSQL;
import de.carlos.music.commands.LeaveCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class TrackScheduler extends AudioEventAdapter {

	public final AudioPlayer player;
	public final BlockingQueue<AudioTrack> queue;
	public boolean repeating = false;
	

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    public void nextTrack() {
        this.player.startTrack(this.queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            if (this.repeating) {
                this.player.startTrack(track.makeClone(), false);
                return;
            }

            nextTrack();
        }
        LeaveCommand leaveCmd = new LeaveCommand();
    }
    
    /*
    @Override
    public void onPlayerPause(AudioPlayer player) {
    	this.player.setPaused(true);
    }
    
    @Override
    public void onPlayerResume(AudioPlayer player) {
    	this.player.setPaused(false);
    }
    */
}
	
	/*/
	public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }
	/**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     *
    public void queue(AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    public BlockingQueue<AudioTrack> getQueue() {
        return queue;
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     *
    public void nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        player.startTrack(queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }
	
	@Override
	public void onPlayerPause(AudioPlayer player) {

	}

	@Override
	public void onPlayerResume(AudioPlayer player) {

	}

	public void onTrackStart(AudioPlayer player, com.sedmelluq.discord.lavaplayer.track.AudioTrack track) {
		long guildid = BlasmusikGeschenkeBot.INSTANCE.playerManager.getGuildByPlayerHash(player.hashCode());
		Guild guild = BlasmusikGeschenkeBot.INSTANCE.shardMan.getGuildById(guildid);

		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(Color.decode("#00e640"));
		AudioTrackInfo info = track.getInfo();
		builder.setDescription("Jetzt läuft: " + info.title);

		long sekunden = info.length / 1000;
		long minuten = sekunden / 60;
		long stunden = minuten / 60;
		minuten %= 60;
		sekunden %= 60;

		String url = info.uri;
		builder.addField(info.author, "[" + info.title + "](" + url + ")", false);
		builder.addField("Länge", info.isStream ? ":red_circle: STREAM"
				: (stunden > 0 ? stunden + "h " : "") + minuten + "min " + sekunden + "s", true);

		if (url.startsWith("https://www.youtube.com/watch?v=")) {
			String videoID = url.replace("https://www.youtube.com/watch?v=", "");

			InputStream file;
			try {
				file = new URL("https://img.youtube.com/vi/" + videoID + "/hqdefault.jpg").openStream();
				builder.setImage("attachment://thumbnail.png");

				ResultSet set = LiteSQL.onQuery("SELECT * FROM musicchannel WHERE guildid = " + guildid);

				try {
					if (set.next()) {
						long channelid = set.getLong("channelid");

						TextChannel channel;
						if ((channel = guild.getTextChannelById(channelid)) != null) {
							channel.sendTyping().queue();
							channel.sendFile(file, "thumbnail.png").embed(builder.build()).queue();
						}
					}
				} catch (SQLException ex) {
				}
			} catch (IOException ex) {}
		}else {
			MusicUtil.sendEmbed(guildid, builder);
		}
	}
/*/

