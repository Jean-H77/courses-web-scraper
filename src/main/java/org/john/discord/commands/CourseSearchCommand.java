package org.john.discord.commands;

import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.john.course.Course;
import org.john.course.CourseRepository;

import java.util.List;

public class CourseSearchCommand extends ListenerAdapter {

    private final CourseRepository repository;

    @Inject
    public CourseSearchCommand(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if(event.getName().equalsIgnoreCase("course")) {

            OptionMapping option = event.getOption("course_title");

            if(option == null) {
                event.reply("Invalid request").setEphemeral(true).queue();
                return;
            }

            String optionString = option.getAsString().replaceAll("\\s", "").toUpperCase();
            List<Course> courses = repository.getByTitle(optionString);

            if(courses.isEmpty()) {
                event.reply("No courses exist for " + option).setEphemeral(true).queue();
                return;
            }

            event.reply(createTable(courses)).setEphemeral(true).queue();
        }
    }

    private String createTable(List<Course> courses) {
        StringBuilder table = new StringBuilder();
        table.append("```");

        table.append(String.format("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                "Title", "Section", "Class Number", "Available Seats", "Status", "Component", "Location", "Days", "Time", "Instructor"));

        table.append("|-----------------|-----------------|-----------------|-----------------|-----------------|-----------------|-----------------|-----------------|-----------------|-----------------|\n");

        for (Course course : courses) {
            table.append(String.format("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                    course.title(), course.section(), course.classNumber(), course.availableSeats(),
                    course.status(), course.component(), course.location(), course.days(), course.time(),
                    course.instructor()));
        }

        table.append("```");

        return table.toString();
    }
}
