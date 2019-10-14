package com.damdamdamdeo.santa;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ConsumerProducers {

    @Inject
    EntityManagerFactory emf;

    @Produces
    @Consumer
    public EntityManager produceEntityManager() {
        return emf.createEntityManager();
    }

}
