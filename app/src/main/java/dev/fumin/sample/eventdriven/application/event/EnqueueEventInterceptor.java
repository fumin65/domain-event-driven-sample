package dev.fumin.sample.eventdriven.application.event;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dev.fumin.sample.eventdriven.domain.event.DomainEvent;
import dev.fumin.sample.eventdriven.domain.event.DomainEventPublisher;
import dev.fumin.sample.eventdriven.domain.event.DomainEventSubscriber;

public class EnqueueEventInterceptor implements MethodInterceptor {

    @Inject
    private EventQueue eventQueue;

    @Inject
    private DomainEventPublisher publisher;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        EnqueueEvent annotation = invocation.getMethod().getAnnotation(EnqueueEvent.class);
        EventCapture captor = new EventCapture();
        Arrays.stream(annotation.value())
                .forEach(eventClass -> publisher.subscribe(eventClass, captor::handle));
        Object o = invocation.proceed();
        captor.pushAll(eventQueue);
        return o;
    }

    private static class EventCapture implements DomainEventSubscriber<DomainEvent> {

        private List<DomainEvent> events = new ArrayList<>();

        @Override
        public void handle(DomainEvent event) {
            events.add(event);
        }

        public void pushAll(EventQueue queue) {
            events.forEach(queue::push);
        }

    }

}
