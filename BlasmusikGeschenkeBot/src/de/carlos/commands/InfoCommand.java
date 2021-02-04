package de.carlos.commands;

import de.carlos.commands.manager.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class InfoCommand implements ServerCommand {
	public String getInformation() {
		return "` gibt dir im Privatchat Informationen über die Seite und ihre Betreiber.";
	}

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		// TODO Auto-generated method stub
		message.delete().queue();
		String nachricht = "Hey, " + p.getAsMention() + "!\n"
				+ "Schön, dass du dich für unser Team interessierst. \n" + 
				"Um die anderen nicht unnötig vollzuspammen, bekommst du diese Nachricht im Privatchat.\n\n" + 
				"Die Seite wurde Anfang 2018 von Philipp erstellt. Im April 2020 hat er sich aus Zeigründen dazu entschlossen, sich Verstärkung zu holen. " +
				"So kamen Susann, Carlos und Jan ins Team. Philipp bleibt uns als Leiter der Gruppe jedoch immer noch erhalten. "
				+ "Im Januar 2021 sind Daniel und Caroline zum Team dazugestoßen, sie befinden sich aktuell noch in der Einarbeitungs- und Probephase. "
				+ "\nDie Arbeit im Team läuft meist so ab: Susanne sucht Ideen und Memes für Posts. Sie oder Jan machen daraus dann Beiträge. Jan postet die Beiträge auf Instagram und erstellt zusammen mit Carlos Storys. Die beiden kümmern sich auch um Kommentare und DMs. "
				+ "\n\nFalls du eine gute Idee für einen Post hast, darfst du sie gerne auf dem Server unter #post-vorschläge einreichen. Vielleicht wird sie ja schon bald veröffentlicht... :D. \n"
				+ "Wenn du noch mehr Infos möchtest, kannst du uns über #support kontaktieren oder uns eine DM (Auf Discord oder Instagram) schreiben. Wir freuen uns, von dir zu hören!\n\n"
				+ "Dein BlasmusikGeschenke-Team";
		p.getUser().openPrivateChannel().queue((pc) -> {
			pc.sendMessage(nachricht).queue();
		});
	}

}
