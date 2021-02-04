package de.carlos.commands;

import java.util.ArrayList;
import java.util.List;

import de.carlos.commands.manager.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class RoleCommand implements ServerCommand {
	public String getInformation() {
		return " [Instrument]` gibt dir die jeweilige Instrumenten-Rolle. Dadurch bekommst du Zugang zur spezifischen Instrumentenkategorie mit eigenem Text- und Sprachkanal. Falls du schon eine Instrumentenrolle hast, werden alle weiteren Rollen als Zweitinstrumente hinzugefügt. Zum Entfernen der Rolle gib einfach den gleichen Command nochmal ein.";
	}

	@Override
	public void performCommand(Member p, TextChannel channel, Message message) {
		// TODO Auto-generated method stub
		String[] args = (message.getContentDisplay().substring(8).toLowerCase()).trim().split(" ");
		if (args.length < 1) {
			channel.sendMessage(
					"Du musst eine Rolle eingeben, die dir hinzugefügt werden soll. Für Hilfe und Erklärung nutze `/bg help role`")
					.queue();
		}
		channel.sendMessage("Eingabe wird verarbeitet.").queue();
		List<String> allMainInstr = new ArrayList<String>(); // Liste mit allen Instrumentenrollen (nur erst-Instr.)
		allMainInstr.add("Dirigent/Stabführer");
		allMainInstr.add("Flöte");
		allMainInstr.add("Klarinette");
		allMainInstr.add("Oboe");
		allMainInstr.add("Saxophon");
		allMainInstr.add("Horn");
		allMainInstr.add("Trompete");
		allMainInstr.add("Flügelhorn");
		allMainInstr.add("Tenorhorn");
		allMainInstr.add("Posaune");
		allMainInstr.add("Tuba");
		allMainInstr.add("Schlagzeug");
		allMainInstr.add("Komponist/Arrangeur");

		List<Role> allSecondInstr = new ArrayList<Role>(); // Liste mit allen Zweitinstrumentrollen
		for (String instr : allMainInstr) {
			allSecondInstr.addAll(channel.getGuild().getRolesByName(instr + "2", true));
		}

		for (int i = 0; i < args.length; i++) { // Eingabe prüfen auf Rollennamen, Zuweisung gefundener Rollen an die
												// Variable group
			Role group = args[i]
					.matches("((dirigent)|(stabführer))")
							? channel.getGuild().getRolesByName("Dirigent/Stabführer", true).get(0)
							: args[i].matches("((flöte)|(querflöte)|(blockflöte))")
									? channel.getGuild().getRolesByName("Flöte", true).get(0)
									: args[i].matches(
											"klarinette") ? channel.getGuild().getRolesByName("Klarinette", true).get(0)
													: args[i].matches(
															"oboe") ? channel.getGuild().getRolesByName("Oboe", true).get(0) : args[i].matches("((saxophon)|(altsaxophon)|(tenorsaxophon)|(baritonsaxophon)|(barisax)|(sopransaxophon))") ? channel.getGuild().getRolesByName("Saxophon", true).get(0) : args[i].matches("((horn)|(f-horn)|(es-horn)|(althorn))") ? channel.getGuild().getRolesByName("Horn", true).get(0) : args[i].matches("((trompete)|(c-Trompete)|(b-Trompete)|(bestesinstrument))") ? channel.getGuild().getRolesByName("Trompete", true).get(0) : args[i].matches("flügelhorn") ? channel.getGuild().getRolesByName("Flügelhorn", true).get(0) : args[i].matches("((tenorhorn)|(bariton)|(euphonium))") ? channel.getGuild().getRolesByName("Tenorhorn", true).get(0) : args[i].matches("posaune") ? channel.getGuild().getRolesByName("Posaune", true).get(0) : args[i].matches("tuba") ? channel.getGuild().getRolesByName("Tuba", true).get(0) : args[i].matches("((schlagzeug)|(schlagwerk)|(percussion)|(pauken))") ? channel.getGuild().getRolesByName("Schlagzeug", true).get(0) : args[i].matches("((komponist)|(arrangeur))") ? channel.getGuild().getRolesByName("Komponist/Arrangeur", true).get(0) : null;
			if (group == null) {
				// Let the user know if they used an invalid pronoun.
				channel.sendMessage("Sorry, " + p.getAsMention() + ", die Rolle " + args[i] + " gibt es bei uns nicht!")
						.queue();
			} else {
				// Assign the role.
				if (hasRole(p, group.getName() + "2")) {
					channel.getGuild().removeRoleFromMember(p,
							channel.getGuild().getRolesByName(group.getName() + "2", true).get(0)).queue();
					channel.sendMessage(
							"Die Rolle " + group.getName() + "2 wurde von " + p.getAsMention() + " entfernt.").queue();
				} else if (hasRole(p, group.getName())) { // wenn die Rolle schon an den User vergeben wurde, entferne
															// sie
					// wieder.
					channel.getGuild().removeRoleFromMember(p, group).queue();
					channel.sendMessage(
							"Die Rolle " + group.getName() + " wurde von " + p.getAsMention() + " entfernt.").queue();

				} else { // wenn nicht, prüfe, ob der User schon eine Rolle (Erstinstrument) hat.
					String roleName;
					if (hasMainInstr(p, allMainInstr, allSecondInstr)) {
						roleName = group.getName() + "2";
					} else {
						roleName = group.getName();
					}
					// Wenn User noch kein Hauptinstrument angegeben hat:
					channel.getGuild().addRoleToMember(p, channel.getGuild().getRolesByName(roleName, true).get(0))
							.queue();
					channel.sendMessage(p.getAsMention() + " wurde die Rolle " + roleName + " hinzugefügt!").queue();

				}

			}

		}

	}

	private boolean hasMainInstr(Member p, List<String> allMainInstr, List<Role> allSecondInstr) {
		List<Role> nutzerRollen = p.getRoles(); // Alle Rollen, die der User hat
		for (Role uRole : nutzerRollen) { // Prüfe für jede Rolle des Nutzers,
			for (String mainI : allMainInstr) { // ob eine Rolle aus allInstr ist
				if (uRole.getName().equals(mainI)) { // Wenn eine Rolle aus allInstr schon an den Nutzer vergeben wurde,
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasRole(Member member, String name) {
		List<Role> roles = member.getRoles();
		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
