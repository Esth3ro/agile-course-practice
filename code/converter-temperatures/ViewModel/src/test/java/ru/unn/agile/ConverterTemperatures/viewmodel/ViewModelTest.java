package ru.unn.agile.ConverterTemperatures.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.ConverterTemperatures.model.TemperaturesUnit;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewModelTest {
    private ViewModel viewModel;

    @Before
    public void initViewModel() {
        viewModel = new ViewModel();
        viewModel = new ViewModel(new FakeLogger());
    }

    @After
    public void nullViewModel() {
        viewModel = null;
    }

    @Test
    public void logIsInit() {
        List<String> log = viewModel.getLogList();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logMessageCanConvertToFahrenheit() {
        viewModel.convertFromProperty().set("20.0");
        viewModel.scaleProperty().setValue(TemperaturesUnit.FAHRENHEIT);

        viewModel.convert();

        String message = viewModel.getLogList().get(0);
        String expectedMessage = String.format(LogMessage.CONVERT_WAS_PRESSED,
                viewModel.getConvertFrom(), "°C",
                viewModel.getConvertTo(), TemperaturesUnit.FAHRENHEIT);
        assertTrue(message.contains(expectedMessage));
    }

    @Test
    public void logMessageGetNotCorrectValue() {
        viewModel.convertFromProperty().set("-300.0");
        viewModel.scaleProperty().setValue(TemperaturesUnit.FAHRENHEIT);

        viewModel.convert();

        String message = viewModel.getLogList().get(0);
        String expectedMessage = String.format(LogMessage.VALUE_FROM_IS_NOT_CORRECT,
                viewModel.getConvertFrom());
        assertTrue(message.contains(expectedMessage));
    }

    @Test
    public void logMessageGetNotCorrectSymbolValue() {
        viewModel.convertFromProperty().set("sdf");
        viewModel.scaleProperty().setValue(TemperaturesUnit.FAHRENHEIT);

        viewModel.convert();

        String message = viewModel.getLogList().get(0);
        String expectedMessage = String.format(LogMessage.VALUE_FROM_IS_NOT_CORRECT,
                viewModel.getConvertFrom());
        assertTrue(message.contains(expectedMessage));
    }

    @Test
    public void createEmptyViewModel() {
        assertEquals("", viewModel.convertFromProperty().get());
        assertEquals(TemperaturesUnit.FAHRENHEIT, viewModel.getScale());
        assertEquals("", viewModel.getConvertTo());
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void canAddValueToConvert() {
        viewModel.convertFromProperty().set("10.0");

        viewModel.checkInputValues();

        assertEquals("10.0", viewModel.convertFromProperty().get());
    }

    @Test
    public void canGetConvertToValue() {
        viewModel.convertFromProperty().set("10.0");

        viewModel.convert();

        assertEquals("50.0", viewModel.convertToProperty().get());
    }

    @Test
    public void canProcessBadInputFormat() {
        viewModel.convertFromProperty().set("a");

        viewModel.checkInputValues();

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void canConvertToFahrenheit() {
        viewModel.convertFromProperty().set("20.0");
        viewModel.scaleProperty().setValue(TemperaturesUnit.FAHRENHEIT);

        viewModel.convert();

        assertEquals("68.0", viewModel.getConvertTo());
        assertEquals(Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void canConvertToKelvin() {
        viewModel.convertFromProperty().set("20.0");
        viewModel.scaleProperty().setValue(TemperaturesUnit.KELVIN);

        viewModel.convert();

        assertEquals("293.15", viewModel.getConvertTo());
        assertEquals(Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void canConvertToNewton() {
        viewModel.convertFromProperty().set("20.0");
        viewModel.scaleProperty().setValue(TemperaturesUnit.NEWTON);

        viewModel.convert();

        assertEquals("6.6000000000000005", viewModel.getConvertTo());
        assertEquals(Status.SUCCESS.toString(), viewModel.getStatus());
    }

    @Test
    public void getExceptionFromConvert() {
        viewModel.convertFromProperty().set("-300.0");
        viewModel.scaleProperty().setValue(TemperaturesUnit.FAHRENHEIT);

        viewModel.convert();

        assertEquals(Status.ERROR.toString(), viewModel.getStatus());
    }

    @Test
    public void getCorrectStatusWithNullInput() {
        viewModel.convertFromProperty().set("");
        viewModel.scaleProperty().setValue(TemperaturesUnit.KELVIN);

        viewModel.convert();

        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void fahrenheitIsSetByDefault() {
        assertEquals(TemperaturesUnit.FAHRENHEIT, viewModel.getScale());
    }

    @Test
    public void getCorrectStatusWithEmptyFields() {
        viewModel.convert();

        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void getCorrectStatusWhenFillFields() {
        viewModel.convertFromProperty().set("20.0");

        viewModel.checkInputValues();

        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }
}
