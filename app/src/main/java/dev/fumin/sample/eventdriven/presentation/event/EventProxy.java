package dev.fumin.sample.eventdriven.presentation.event;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.annotation.WebServlet;

import dev.fumin.sample.eventdriven.application.event.EventRelay;

@Singleton
@WebServlet(value = "/event/proxy")
public class EventProxy extends EventReceiver {

    @Inject
    private EventRelay eventRelay;

    @Override
    protected void onReceive(Event event) {
        // イベントリレーに受信したイベントのIDを渡し、本来のチャンネルに送信させる。
        eventRelay.relay(event.getEventId());
    }

}
