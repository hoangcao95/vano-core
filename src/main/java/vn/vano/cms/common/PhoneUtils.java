package vn.vano.cms.common;

public class PhoneUtils {

    public static String normalizeMsIsdn(String phoneNumber) {
        String result = phoneNumber;

        try {
            String pattern = "^84";
            result = result.replaceAll(pattern, "");
            pattern = "^0";
            result = result.replaceAll(pattern, "");
        } catch (Exception ex) {
            result = phoneNumber;
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println("0".concat(PhoneUtils.normalizeMsIsdn("0912345678")));
    }
}
