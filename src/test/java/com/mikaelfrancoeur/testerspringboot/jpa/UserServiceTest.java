package com.mikaelfrancoeur.testerspringboot.jpa;

import java.util.List;

import org.assertj.core.api.WithAssertions;
import org.hibernate.engine.transaction.internal.TransactionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator;
import jakarta.transaction.Transactional;

@DataJpaTest
@ActiveProfiles("jpa")
@AutoConfigureDataSourceProxy
@Import(JpaConfiguration.class)
class UserServiceTest implements WithAssertions {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        SQLStatementCountValidator.reset();
    }

    @Test
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    void updateFirstName() {
        List<ILoggingEvent> loggingEvents = interceptLogs(TransactionImpl.class, () -> {
            userService.updateFirstName(1L, "John");

            String firstName = jdbcTemplate.queryForObject("SELECT first_name FROM users WHERE id = 1", String.class);

            assertThat(firstName).isEqualTo("John");
        });

        assertThat(loggingEvents)
                .extracting(ILoggingEvent::getFormattedMessage)
                .containsOnlyOnce("committing");
    }

    @Test
    void getPostTitlesForUserIdNPlus1() {
        List<String> titles = userService.getPostTitlesForUserIds(List.of(1L, 2L, 3L));

        assertThat(titles).hasSize(3);

        SQLStatementCountValidator.assertSelectCount(4);
    }

    @Test
    void getPostTitlesForUserIdBetterPerformance() {
        List<String> titles = userService.getPostTitlesForUserIdsWithJoinFetch(List.of(1L, 2L, 3L));

        assertThat(titles).hasSize(3);

        SQLStatementCountValidator.assertSelectCount(1);
    }

    private List<ILoggingEvent> interceptLogs(Class<?> clazz, Runnable runnable) {
        Logger logger = (Logger) LoggerFactory.getLogger(clazz);
        ListAppender<ILoggingEvent> appender = new ListAppender<>();
        logger.setLevel(Level.DEBUG);
        logger.setAdditive(false);

        try {
            logger.addAppender(appender);
            appender.start();

            runnable.run();
            return appender.list;
        } finally {
            appender.stop();
        }
    }

}
