package de.carlos.commands;

//import me.duncte123.botcommons.commands.ICommandContext;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

import de.carlos.commands.manager.ServerCommand;

public class CommandContext implements ICommand {
    private final GuildMessageReceivedEvent event;
    private final List<String> args;

    public CommandContext(GuildMessageReceivedEvent event, List<String> args) {
        this.event = event;
        this.args = args;
    }

    
    public Guild getGuild() {
        return this.getEvent().getGuild();
    }

    
    public GuildMessageReceivedEvent getEvent() {
        return this.event;
    }

    public List<String> getArgs() {
        return this.args;
    }

	


	@Override
	public void handle(CommandContext ctx) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}
}
