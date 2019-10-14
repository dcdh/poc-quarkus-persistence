package com.damdamdamdeo.santa;

import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

@ApplicationScoped
public class GiftConsumer {

    private final static Logger LOGGER = Logger.getLogger(GiftConsumer.class.getName());

    @Inject
    EntityManager em;

    @Incoming("gift")
    public CompletionStage<Void> onMessage(final KafkaMessage<JsonObject, JsonObject> message) throws Exception {
        // TODO create event consumed.
        LOGGER.info("Consumed gift !");
        em.createQuery("SELECT g FROM Gift g").getResultList();
        return message.ack();
    }

}
