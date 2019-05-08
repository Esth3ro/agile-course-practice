package ru.unn.agile.RB.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {

    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
        viewModel.setLogger(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void isKeyEmptyByDefault() {
        assertEquals("", viewModel.keyProperty().get());
    }

    @Test
    public void isValueEmptyByDefault() {
        assertEquals("", viewModel.valueProperty().get());
    }

    @Test
    public void isStatusEmptyByDefault() {
        assertEquals(ViewModel.Status.WAITING_FOR_INPUT, viewModel.statusProperty().get());
    }

    @Test
    public void insertingANode() {
        viewModel.keyProperty().setValue("45678");
        viewModel.valueProperty().setValue("asdadsasd");
        viewModel.actionInsert();
        assertEquals(ViewModel.Status.SUCCESS, viewModel.statusProperty().get());
    }

    @Test
    public void insertingBadKeyFormat() {
        viewModel.keyProperty().setValue("      123");
        viewModel.valueProperty().setValue("");
        viewModel.actionInsert();
        assertEquals(ViewModel.Status.BAD_KEY_FORMAT, viewModel.statusProperty().get());
    }

    @Test
    public void findANode() {
        viewModel.keyProperty().setValue("123");
        viewModel.valueProperty().setValue("");
        viewModel.actionInsert();
        viewModel.actionFind();
        assertEquals(ViewModel.Status.SUCCESS, viewModel.statusProperty().get());
    }

    @Test
    public void nodeNotFound() {
        viewModel.keyProperty().setValue("123");
        viewModel.valueProperty().setValue("");
        viewModel.actionInsert();
        viewModel.keyProperty().setValue("1");
        viewModel.actionFind();
        assertEquals(ViewModel.Status.NOT_FOUND, viewModel.statusProperty().get());
    }

    @Test
    public void findingBadKeyFormat() {
        viewModel.keyProperty().setValue("123");
        viewModel.valueProperty().setValue("");
        viewModel.actionInsert();
        viewModel.keyProperty().setValue("    1");
        viewModel.actionFind();
        assertEquals(ViewModel.Status.BAD_KEY_FORMAT, viewModel.statusProperty().get());
    }

    @Test
    public void findAndCompareValue() {
        viewModel.keyProperty().setValue("456");
        viewModel.valueProperty().setValue("asdasd");
        viewModel.actionInsert();
        viewModel.valueProperty().setValue("");
        viewModel.actionFind();
        assertEquals("asdasd", viewModel.valueProperty().get());
    }

    private void assertLogLastMessage(final String msg) {
        List<String> logger = viewModel.getLogger().getLog();
        assertFalse(logger.isEmpty());
        assertEquals(msg, logger.get(logger.size() - 1));
    }

    @Test
    public void logIsInitialized() {
        assertNotNull(viewModel.getLogger());
    }

    @Test
    public void logIsEmptyAtTheStart() {
        assertTrue(viewModel.getLogger().getLog().isEmpty());
    }

    @Test
    public void logRecordsInsertionEqualFields() {
        viewModel.keyProperty().setValue("1");
        viewModel.valueProperty().setValue("1");
        viewModel.actionInsert();
        assertLogLastMessage(ViewModel.LoggerMessages.INSERTION_SUCCESSFUL.toString("1", "1"));
    }

    @Test
    public void logRecordsInsertionDifferentFields() {
        viewModel.keyProperty().setValue("1");
        viewModel.valueProperty().setValue("a");
        viewModel.actionInsert();
        assertLogLastMessage(ViewModel.LoggerMessages.INSERTION_SUCCESSFUL.toString("1", "a"));
    }

    @Test
    public void logRecordsInsertingBadKeyFormat() {
        viewModel.keyProperty().setValue("      789");
        viewModel.valueProperty().setValue("");
        viewModel.actionInsert();
        assertLogLastMessage(ViewModel.LoggerMessages.BAD_KEY_FORMAT.toString("      789"));
    }

    @Test
    public void logRecordsFindingBadKeyFormat() {
        viewModel.keyProperty().setValue("123");
        viewModel.valueProperty().setValue("");
        viewModel.actionInsert();
        viewModel.keyProperty().setValue("    1");
        viewModel.actionFind();
        assertLogLastMessage(ViewModel.LoggerMessages.BAD_KEY_FORMAT.toString("    1"));
    }

    @Test
    public void logRecordsNodeNotFound() {
        viewModel.keyProperty().setValue("123");
        viewModel.valueProperty().setValue("");
        viewModel.actionInsert();
        viewModel.keyProperty().setValue("1");
        viewModel.actionFind();
        assertLogLastMessage(ViewModel.LoggerMessages.KEY_NOT_FOUND.toString("1"));
    }

    @Test
    public void logRecordsFindANode() {
        viewModel.keyProperty().setValue("789");
        viewModel.valueProperty().setValue("xyz");
        viewModel.actionInsert();
        viewModel.actionFind();
        assertLogLastMessage(ViewModel.LoggerMessages.KEY_FOUND.toString("789", "xyz"));
    }
}
