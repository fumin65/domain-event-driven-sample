package dev.fumin.sample.eventdriven.presentation.event;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.fumin.sample.eventdriven.application.persistence.Transactional;

public abstract class EventReceiver extends HttpServlet {

    private final JsonParser parser = new JsonParser();

    @Inject
    private ConsumedEventStore consumedEventStore;

    @Override
    @Transactional
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader().lines().collect(Collectors.joining());

        System.out.println(body);

        JsonObject json = parser.parse(body).getAsJsonObject();
        JsonObject messageObj = json.get("message").getAsJsonObject();
        JsonObject attributesObj = messageObj.get("attributes").getAsJsonObject();

        Event.Message message = new Event.Message(
                messageObj.get("messageId").getAsString(),
                new String(Base64.getDecoder().decode(messageObj.get("data").getAsString()))
        );
        attributesObj.entrySet()
                .forEach(entry -> message.getAttributes()
                        .put(entry.getKey(), entry.getValue().getAsString()));

        long eventId = Long.parseLong(message.getAttributes().get("eventId"));
        Event event = new Event(eventId, message, json.get("subscription").getAsString());

        // 消費済みのイベントは無視する
        if (!consumedEventStore.exists(eventId, event.getSubscription())) {
            // Pub/Subから同一のメッセージが重複して送信される可能性があるので
            // 冪等性をもたせるために消費したイベントを保存する
            ConsumedEvent consumedEvent =
                    new ConsumedEvent(eventId, event.getSubscription(), new Date());
            consumedEventStore.insert(consumedEvent);
            onReceive(event);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    // 未処理の場合、サブクラスに処理を委譲する
    protected abstract void onReceive(Event event);

}
