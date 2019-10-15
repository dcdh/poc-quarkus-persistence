package com.damdamdamdeo.santa;

import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

@ApplicationScoped
public class GiftConsumer {

    private final static Logger LOGGER = Logger.getLogger(GiftConsumer.class.getName());

    private final EntityManager em;
    private final UserTransaction userTransaction;

    public GiftConsumer(final EntityManager em, final UserTransaction userTransaction) {
        this.em = em;
        this.userTransaction = userTransaction;
    }

    @Incoming("gift")
    public CompletionStage<Void> onMessage(final KafkaMessage<JsonObject, JsonObject> message) throws Exception {
        // TODO create event consumed.
        LOGGER.info("Consumed gift !");
        userTransaction.begin();
        final Gift gift = new Gift();
        gift.setName("Consumed");
        em.persist(gift);
        userTransaction.commit();
        return message.ack();
    }

}
