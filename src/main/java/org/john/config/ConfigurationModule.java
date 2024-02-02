package org.john.config;

import com.google.inject.AbstractModule;

public class ConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ConfigurationModule.class).asEagerSingleton();
    }
}
