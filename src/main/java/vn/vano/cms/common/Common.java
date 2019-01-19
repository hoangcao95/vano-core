package vn.vano.cms.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String EMAIL_PATTERN = "(^$)|(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)";
    public static final String IDENTIFY_PATTERN = "^[0-9]{7,12}$";
    public static final String FULL_NAME_PATTERN = "/^[a-z A-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$/";
    public static SimpleDateFormat sdf = new SimpleDateFormat();
    public static final String DATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_YYYYMMDD = "yyyyMMdd";

    public static boolean checkEmail(String email) { // check định dạng email
        if (checkLength(email)) {
            return false;
        } else {
            return email.matches(EMAIL_PATTERN);
        }
    }

    public static boolean checkIdentifyCode(String identify) { // check chứng minh thư
        if (identify.length() > 13 || identify.length() < 9) {
            return false;
        } else {
            return identify.matches(IDENTIFY_PATTERN);
        }
    }

    public static boolean checkFullName(String fullName) { // check họ tên đầy đủ
        return fullName.matches(FULL_NAME_PATTERN); // nếu chuỗi đúng trả về true
    }


    public static boolean checkLength(String str) { // check độ dài
        if (str.length() > 50) {
            return true;
        }
        return false;
    }

    public static Date strToDate(String value, String format) throws Exception {
        sdf.applyPattern(format);
        return sdf.parse(value);
    }

    public static String dateToString(Date value, String format) throws Exception {
        sdf.applyPattern(format);
        return sdf.format(value);
    }

}
