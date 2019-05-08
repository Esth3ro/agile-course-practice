package ru.unn.agile.RB.Infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TxtLoggerTests {
    private TxtLogger logger;

    @Before
    public void setUp() {
        logger = new TxtLogger("./RBTree-TxtLoggerTest.log");
    }

    @After
    public void tearDown() {
        logger = null;
    }

    @Test
    public void loggerCreated() {
        assertNotNull(logger);
    }

    @Test
    public void writingALogMessage() {
        logger.log("ABRACADABRA");

        String message = logger.getLog().get(0);
        assertTrue(message.matches(".*ABRACADABRA$"));
    }


    @Test
    public void logIncludesDateAndTime() {
        logger.log("Message");

        String message = logger.getLog().get(0);
        final String dateFormat = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} --> .*";
        assertTrue(message.matches(dateFormat));
    }

    @Test
    public void writingAFewLogMessages() {
        String[] messages = {"asdad", "asdad", "12easdasda", "-;123sa"};

        for (String msg : messages) {
            logger.log(msg);
        }

        List<String> newMessages = logger.getLog();
        for (int i = 0; i < newMessages.size(); i++) {
            assertTrue(newMessages.get(i).matches(".*" + messages[i] + "$"));
        }
    }
}
