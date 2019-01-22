package vn.vano.cms.common;

public interface Constants {
    interface App {
        String SHORT_CODE = "8888";
        String SHORT_NAME = "IVR";
        String SHORT_NAME_CODE = "<DICH_VU>";
        String PACKAGE_CODE = "<GOI_CUOC>";
    }

    interface CoreMoSms {
        Integer PROCESS_STATUS_DONE = 2;
        Integer PROCESS_STATUS_ERROR = 3;
    }

    interface MORequest {
        String COMMAND_DEFAULT = "DEFAULT";
        String COMMAND_VINA_REG = "VINA_REG";
        String COMMAND_VINA_UNREG = "VINA_UNREG";
    }

    interface MTRequest {
        String CHANNEL_SMS = "SMS";
        String CHANNEL_SYS = "SYS";
    }

    interface Subscriber {
        Integer ACTIVE = 1;
    }

    public interface Paging {
        public int SIZE = 10;
    }

    public static final Integer STATUS_ACTIVITY = 1;
    public static final Integer STATUS_NON_ACTIVITY = 0;

    public String NOT_FOUND_MESSAGE = "Không tìm thấy dữ liệu";

    public interface CoreService {

        public static final String ADD_SERVICE_SUCCESS = "Thêm mới dịch vụ thành công";
        public static final String ADD_SERVICE_ERROR = "Thêm mới dịch vụ thất bại";

        public static final String UPDATE_SERVICE_SUCCESS = "Cập nhật dịch vụ thành công";
        public static final String UPDATE_SERVICE_ERROR = "Cập nhật dịch vụ thất bại";

        public static final String DELETE_SERVICE_SUCCESS = "Xoá dịch vụ thành công";
        public static final String DELETE_SERVICE_ERROR = "Xóa dịch vụ thất bại";
    }
}
