package org.john.discord;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.john.discord.commands.CourseSearchCommand;

public class DiscordModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DiscordBot.class).asEagerSingleton();
        bind(CourseSearchCommand.class).in(Scopes.SINGLETON);
    }
}
