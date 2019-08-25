package com.damdamdamdeo.santa;

import org.hibernate.search.mapper.orm.Search;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SantaClausService {

    @Inject
    EntityManager em;

    @Transactional
    public void createGift(final String giftDescription) {
        final Gift gift = new Gift();
        gift.setName(giftDescription);
        em.persist(gift);
    }

    public List<Gift> searchGifts(final String giftDescription) {
        return Search.session(em)
                .search(Gift.class)
                .predicate(f ->
                        giftDescription == null || giftDescription.trim().isEmpty() ?
                                f.matchAll() :
                                f.simpleQueryString()
                                        .onFields("name").matching(giftDescription)
                )
                .fetchHits();
    }

}
