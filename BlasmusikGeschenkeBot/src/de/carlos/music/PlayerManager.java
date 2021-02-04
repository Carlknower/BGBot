package de.carlos.music;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.carlos.BlasmusikGeschenkeBot;
import de.carlos.listener.LeaveListener;
import de.carlos.listener.ResponseListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayerManager {

	private static PlayerManager INSTANCE;
	private final Map<Long, GuildMusicManager> musicManagers;
	public ConcurrentHashMap<Long, MusicController> controller;

	private AudioPlayerManager audioPlayerManager;


	public PlayerManager() {
		this.musicManagers = new HashMap<>();
		this.controller = new ConcurrentHashMap<Long, MusicController>();
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		AudioSourceManagers.registerLocalSource(audioPlayerManager);
	}
	
	public MusicController getController(long guildid) {
		MusicController mc = null;
		
		if(this.controller.containsKey(guildid)) {
			mc = this.controller.get(guildid);
		}
		else {
			mc = new MusicController(BlasmusikGeschenkeBot.INSTANCE.shardMan.getGuildById(guildid));
			
			this.controller.put(guildid, mc);
		}
		
		return mc;
	}


	public GuildMusicManager getMusicManager(Guild guild) {

		return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
			final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

			guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

			return guildMusicManager;
		});
	}

	public void loadAndPlay(TextChannel channel, String trackUrl) {
		final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);

                channel.sendMessage("Zur Wiedergabeliste hinzufügen: `")
                        .append(track.getInfo().title)
                        .append("` von `")
                        .append(track.getInfo().author)
                        .append('`')
                        .queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                final List<AudioTrack> tracks = playlist.getTracks();

                channel.sendMessage("Zur Wiedergabeliste hinzufügen: `" + tracks.get(1).getInfo().title + "` von `" + tracks.get(1).getInfo().author + "`")
                        .queue();
                
                musicManager.scheduler.queue(tracks.get(1));
                
            }

            @Override
            public void noMatches() {
                //
            	channel.sendMessage("keine Treffer").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                //
            	channel.sendMessage("Fehler beim Laden, sorryyyy").queue();
            }
        });
    }
	

	public List<AudioTrack> searchResultTracks; // als public notwendig, damit von anderen Klassen darauf zugegriffen werden kann (SearchCommand und ResponseListener)
	
	/*public void pauseSong(TextChannel channel) {
		final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

		AudioPlayer audioPlayer = null;
		musicManager.scheduler.onPlayerPause(audioPlayer);
			
		
	
	}*/
	
	public void loadAndSearch(TextChannel channel, String trackUrl) {
		final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);

                channel.sendMessage("Zur Wiedergabeliste hinzufügen: `")
                        .append(track.getInfo().title)
                        .append("` von `")
                        .append(track.getInfo().author)
                        .append('`')
                        .queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                
                searchResultTracks = playlist.getTracks();
            	
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle( "Suchergebnisse für " + trackUrl);
                String ergebnis = "";
                int i = 1;
                for (final AudioTrack track : searchResultTracks) {
                	long maxSeconds = track.getDuration() / 1000;
            		long maxMinutes = maxSeconds / 60;
            		long maxStunden = maxMinutes / 60;
            		maxSeconds %= 60;
            		maxMinutes %= 60;
            		
            		String time = ((maxStunden > 0) ? maxStunden + "h " : "") + maxMinutes + "min " + maxSeconds + "s";
                	ergebnis = ergebnis + "\n`" + i + ".` " + track.getInfo().title + " [" + time + "]" ;  
                	i++;
                }
                builder.setDescription(ergebnis + "\n\n\nSchreibe eine Nummer, um einen Titel auszuwählen. Schreibe `cancel` zum Abbrechen.");
                channel.sendMessage(builder.build()).queue();
            }
            
            

            @Override
            public void noMatches() {
                // wenn keine Treffer gefunden werden für den Link/den Suchbegriff
            	channel.sendMessage("Es wurden keine Ergebnisse gefunden. Überprüfe deine Eingabe auf Fehler und probiere es erneut.").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                //Falls irgendwas beim Laden schief läuft
            	channel.sendMessage("Beim Laden ist ein Fehler aufgetreten. Sorryyyy!").queue();
            }
        });
    }
	
    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }
		
		

}
