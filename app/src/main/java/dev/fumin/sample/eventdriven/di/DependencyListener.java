package dev.fumin.sample.eventdriven.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.annotation.WebListener;

import dev.fumin.sample.eventdriven.application.di.ApplicationModule;
import dev.fumin.sample.eventdriven.infrastructure.di.InfrastructureModule;
import dev.fumin.sample.eventdriven.presentation.di.PresentationModule;

@WebListener
public class DependencyListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new InfrastructureModule(),
                new PresentationModule(),
                new ApplicationModule()
        );
    }

}
