package vn.edu.iuh.fit.backend.converters;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CountryCodeConverter implements AttributeConverter<CountryCode, Short> {

    @Override
    public Short convertToDatabaseColumn(CountryCode countryCode) {
        if (countryCode == null) {
            return null;
        }
        return (short) countryCode.getNumeric();
    }

    @Override
    public CountryCode convertToEntityAttribute(Short dbData) {
        if (dbData == null) {
            return null;
        }
        for (CountryCode countryCode : CountryCode.values()) {
            if (countryCode.getNumeric() == dbData) {
                return countryCode;
            }
        }
        return null;
    }
}
