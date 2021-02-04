package de.carlos.listener;

import de.carlos.BlasmusikGeschenkeBot;
import de.carlos.music.PlayerManager;
import de.carlos.music.commands.SearchCommand;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ResponseListener extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		// Antworten auf search-Befehl auslesen
		if (!event.getAuthor().getName().equalsIgnoreCase("BlasmusikGeschenke")) { //Antworten von mir selbst sollen nicht ausgelesen werden (sonst Endlosschleife)
			Member p = event.getMember();
			String message = event.getMessage().getContentDisplay();
			if(message.equals("cancel")) { // Wenn die Eingabe cancel ist, dann brich die Suche ab + Bestätigung
				BlasmusikGeschenkeBot.INSTANCE.searchComAct = false;
				BlasmusikGeschenkeBot.INSTANCE.searchMember = null;
				event.getChannel().sendMessage("Die Suche wurde erfolgreich abgebrochen. Jetzt kann der nächste Begriff gesucht werden!").queue();
				return;
			}
			if (!isInt(message)) { // ignoriere alle nachrichten, die nicht eine Zahl sind
				return;
			}
			// Wenn Nachricht ist Zahl: Prüfe, ob search läuft. Wenn ja, gib eingegebene Zahl als Antwort,
			if (BlasmusikGeschenkeBot.INSTANCE.searchComAct) {
				// gibs weiter wenn user der ist, der search gemacht hat
				if (BlasmusikGeschenkeBot.INSTANCE.searchMember.equals(event.getMember())) {
					// ausgewählten Titel zur Wiedergabeliste hinzufügen
					BlasmusikGeschenkeBot.INSTANCE.eingabe = Integer.parseInt(message);
					SearchCommand.getInstance().playResult(p, event.getChannel(), event.getMessage());
				}
			}
		}
	}

	private boolean isInt(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
