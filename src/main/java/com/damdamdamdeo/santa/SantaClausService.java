package com.damdamdamdeo.santa;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Dependent
public class SantaClausService {

    @Inject
    EntityManager em;

    @Transactional
    public void createGift(final String giftDescription) {
        final Gift gift = new Gift();
        gift.setName(giftDescription);
        em.persist(gift);
    }

}
