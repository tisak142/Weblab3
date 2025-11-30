package Beans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("doubleConverter")
public class YConverter implements Converter<Double> {

    @Override
    public Double getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new ConverterException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка",
                            "Введите корректное число (от -5 до 3)"));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Double value) {
        return value == null ? "" : value.toString();
    }
}