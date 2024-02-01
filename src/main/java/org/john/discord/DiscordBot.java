package org.john.discord;

import jakarta.inject.Inject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.john.discord.commands.CourseSearchCommand;

public class DiscordBot {

    private JDA jda;

    private final CourseSearchCommand courseSearchCommand;

    @Inject
    public DiscordBot(CourseSearchCommand courseSearchCommand) {
        this.courseSearchCommand = courseSearchCommand;
    }

    public void setup(String token) throws InterruptedException {
        jda = JDABuilder.createDefault(token)
                .setActivity(Activity.watching("Ask me about a course!"))
                .addEventListeners(courseSearchCommand)
                .build()
                .awaitReady();

        jda.upsertCommand("course", "Search for courses")
                .addOptions(
                        new OptionData(OptionType.STRING, "course_title", "Search by course title (e.g., COMP 100, COMP 467)"))
                .queue();
    }

    public JDA getJda() {
        return jda;
    }
}
