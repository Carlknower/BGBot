package de.carlos.commands.manager;

import java.util.concurrent.ConcurrentHashMap;

import de.carlos.commands.ClearCommand;
import de.carlos.commands.EchoCommand;
import de.carlos.commands.HelpCommand;
import de.carlos.commands.InfoCommand;
import de.carlos.commands.PreviewCommand;
import de.carlos.commands.ReactCommand;
import de.carlos.commands.RoleCommand;
import de.carlos.commands.ShutDownCommand;
import de.carlos.music.commands.JoinCommand;
import de.carlos.music.commands.LeaveCommand;
import de.carlos.music.commands.NowPlayingCommand;
import de.carlos.music.commands.PauseCommand;
import de.carlos.music.commands.PlayCommand;
import de.carlos.music.commands.PlayCommand2;
import de.carlos.music.commands.PlayCommand3;
import de.carlos.music.commands.QueueCommand;
import de.carlos.music.commands.RepeatCommand;
import de.carlos.music.commands.ResumeCommand;
import de.carlos.music.commands.SearchCommand;
import de.carlos.music.commands.SkipCommand;
import de.carlos.music.commands.StopCommand;
import de.carlos.music.commands.StopCommandAlt;
import de.carlos.music.commands.TrackInfoCommand;
import de.carlos.music.commands.TuschCommand;
import de.carlos.music.commands.TuschCommand2;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandManager {
	
	private ConcurrentHashMap<String, ServerCommand> commands = new ConcurrentHashMap<>();
	
	public CommandManager() {
		this.commands.put("clear", new ClearCommand());
		this.commands.put("help", new HelpCommand());
		this.commands.put("echo", new EchoCommand());
		this.commands.put("info", new InfoCommand());
		this.commands.put("role", new RoleCommand());
		this.commands.put("preview", new PreviewCommand());
		this.commands.put("react", new ReactCommand());
		this.commands.put("shutdown", new ShutDownCommand());
		
		this.commands.put("play", new PlayCommand3());
		this.commands.put("stop", new StopCommand());
		this.commands.put("np", new TrackInfoCommand());
		this.commands.put("tusch1", new TuschCommand());
		this.commands.put("t1", new TuschCommand());
		this.commands.put("tusch2", new TuschCommand2());
		this.commands.put("t2", new TuschCommand2());
		this.commands.put("join", new JoinCommand());
		this.commands.put("queue", new QueueCommand());
		this.commands.put("q", new QueueCommand());
		this.commands.put("np", new NowPlayingCommand());
		this.commands.put("loop", new RepeatCommand());
		this.commands.put("repeat", new RepeatCommand());
		this.commands.put("skip", new SkipCommand());
		this.commands.put("leave", new LeaveCommand());
		this.commands.put("search", new SearchCommand());
		this.commands.put("pause", new PauseCommand());
		this.commands.put("resume", new ResumeCommand());
	}
	
	public boolean canPerform(String command, Member p, TextChannel channel, Message message) {
		
		ServerCommand cmd = this.commands.get(command.toLowerCase());
		if(cmd != null) {
			cmd.performCommand(p, channel, message);
			return true;
		}
		return false;
	}
	public String getHelpText(String command) {		
		ServerCommand cmd = this.commands.get(command.toLowerCase());
		return cmd.getInformation();
	}
	
	public ConcurrentHashMap<String, ServerCommand> getCommands(){
		return commands;
	}
}
