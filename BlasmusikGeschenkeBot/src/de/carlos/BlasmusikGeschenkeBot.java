package de.carlos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Permission;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import de.carlos.commands.manager.CommandManager;
import de.carlos.listener.CommandListener;
import de.carlos.listener.JoinListener;
import de.carlos.listener.LeaveListener;
import de.carlos.listener.ResponseListener;
import de.carlos.manage.LiteSQL;
import de.carlos.manage.SQLManager;
import de.carlos.music.PlayerManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class BlasmusikGeschenkeBot {

	public static BlasmusikGeschenkeBot INSTANCE;
	
	public boolean searchComAct;
	public Member searchMember;
	public int eingabe;
	
	public ShardManager shardMan;
	private CommandManager commandMan;
	public AudioPlayerManager audioPlayerManager;
	public PlayerManager playerManager;
	//public static final Long userid = 782299938293022751;
	//public static final User carlos = User.fromId("782299938293022751");
	
	
	public static void main(String[] args) {
			try {
				new BlasmusikGeschenkeBot();
				
			} catch(LoginException|IllegalArgumentException e) {
				e.printStackTrace();
				System.out.println("Bot Test not initialated");
			}
	}
	
	public BlasmusikGeschenkeBot() throws LoginException, IllegalArgumentException {
		INSTANCE = this;
		
		LiteSQL.connect();
		SQLManager.onCreate();
		
		DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.create(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES);
		
		builder.setToken("HIERSTEHTEIGENTLICHDASTOKEN");
	//builder.setActivity(Activity.playing("Trompete"));
	builder.setActivity(Activity.listening("Blasmusik"));
	builder.setStatus(OnlineStatus.ONLINE);
	
	this.audioPlayerManager = new DefaultAudioPlayerManager();
	this.playerManager = new PlayerManager();
	
	this.commandMan = new CommandManager();
	builder.addEventListeners(new CommandListener());
	builder.addEventListeners(new JoinListener());
	builder.addEventListeners(new LeaveListener());
	builder.addEventListeners(new ResponseListener());
	
	
	this.shardMan = builder.build();
	System.out.println("Bot aktiv");
	
	AudioSourceManagers.registerRemoteSources(audioPlayerManager);
	audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);

	
	shutdown();
		
	}
	
	public void shutdown() {
		new Thread(() -> {
			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				while((line = reader.readLine()) !=null) {
					if(line.equalsIgnoreCase("shut down")) {
						if(shardMan !=null) {
							shardMan.setStatus(OnlineStatus.OFFLINE);
							LiteSQL.disconnect();
							System.out.println("Bot offline");
							shardMan.shutdown();
						}
						reader.close();
					}
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public CommandManager getCmdMan() {
		return commandMan;
	}
}
