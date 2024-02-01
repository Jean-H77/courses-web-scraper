package org.john.discord;

import com.google.inject.AbstractModule;

public class DiscordModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DiscordBot.class).asEagerSingleton();
    }
}
