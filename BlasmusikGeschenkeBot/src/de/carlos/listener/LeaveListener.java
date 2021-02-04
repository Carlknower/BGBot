package de.carlos.listener;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LeaveListener extends ListenerAdapter {
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		Member p = event.getMember();

		String nachricht1 = "Hey, " + p.getAsMention() + "!\n" + "Schade, dass du uns verlässt!"
				+ "\nUm unseren Server zu verbessern, wäre es voll cool, wenn du uns kurz sagen könntest, warum du gegangen bist."
				+ "\nHat dir der Server nicht gefallen? Warst du mit einer unserer Entscheidungen unzufrieden?"
				+ "\nWir wollen dich hier jetzt nicht auf Biegen und Brechen überreden, noch zu bleiben; das ist ganz allein deine Entscheidung, aber "
				+ "wir möchten unseren Server natürlich so benutzerfreundlich wie möglich gestalten und sind daher interessiert daran, "
				+ "Kritik zu erhalten."
				+ "\n"
				+ "\nWir würden uns über eine kurze Rückmeldung freuen!"
				+ "\nAlles Gute und die besten Grüße"
				+ "\n"
				+ "\nDein BlasmusikGeschenke-Team";
		p.getUser().openPrivateChannel().queue((pc) -> {
			pc.sendMessage(nachricht1).queue();
		});

	}
}