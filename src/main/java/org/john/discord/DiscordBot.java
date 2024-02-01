package org.john.discord;

import jakarta.inject.Inject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.john.course.CourseRepository;
import org.john.discord.commands.CourseSearchCommand;

import java.util.ArrayList;
import java.util.List;

public class DiscordBot {

    private JDA jda;

    private final CourseRepository courseRepository;

    private final List<Command.Choice> choiceList = new ArrayList<>();

    @Inject
    public DiscordBot(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void setup(String token) throws InterruptedException {
        jda = JDABuilder.createDefault(token)
                .setActivity(Activity.watching("Ask me about a course!"))
                .addEventListeners(new CourseSearchCommand(courseRepository))
                .build()
                .awaitReady();

        createCommands();
    }

    public void createCommands() {
        Guild guild = jda.getGuildById("1202385451726540840");

        if(guild == null) {
            return;
        }

        guild.upsertCommand("course", "Search for courses")
                .addOptions(
                        new OptionData(OptionType.STRING, "course_title", "Search by course title (e.g., COMP 100, COMP 467)")
                                /*.addChoices(choiceList)
                                .setAutoComplete(true),
                        new OptionData(OptionType.STRING, "instructor_name", "Search by instructor name (e.g. Wiegley,Jeffrey"),
                        new OptionData(OptionType.INTEGER, "class_number", "Search by class number (e.g. 20415, 20416")*/
                )
                .queue();
    }

    public JDA getJda() {
        return jda;
    }

    public List<Command.Choice> getChoiceList() {
        return choiceList;
    }
}
