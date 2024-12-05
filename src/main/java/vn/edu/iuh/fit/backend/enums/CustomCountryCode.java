package vn.edu.iuh.fit.backend.enums;
import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;

@Getter
public enum CustomCountryCode {
    VN(CountryCode.VN, "+84");
    private final CountryCode countryCode;
    private final String callingCode;
    CustomCountryCode(CountryCode countryCode, String callingCode) {
        this.countryCode = countryCode;
        this.callingCode = callingCode;
    }
}
