package com.damdamdeo.santa;

import com.damdamdamdeo.santa.Gift;
import com.damdamdamdeo.santa.SantaClausService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class SantaClausServiceTest {

    @Inject
    SantaClausService santaClausService;

    @Inject
    EntityManager em;

    @Test
    public void should_create_gift() {
        // Given
        // When
        santaClausService.createGift("Quarkus !");

        // Then
        final List<Gift> gifts = em.createQuery("SELECT g FROM Gift g").getResultList();
        assertThat(gifts.size()).isEqualTo(1);
        assertThat(gifts.get(0).getName()).isEqualTo("Quarkus !");

        // search
        final List<Gift> giftsSearched = santaClausService.searchGifts("Quarkus");
        assertThat(giftsSearched.size()).isEqualTo(1);
        assertThat(giftsSearched.get(0).getName()).isEqualTo("Quarkus !");
    }

}
