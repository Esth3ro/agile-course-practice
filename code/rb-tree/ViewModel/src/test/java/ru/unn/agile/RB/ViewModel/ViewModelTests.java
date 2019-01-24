package ru.unn.agile.RB.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewModelTests {

    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
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
}
