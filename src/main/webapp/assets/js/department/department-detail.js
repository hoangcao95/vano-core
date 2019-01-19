var departmentDetail = (function () {

    var MESSAGE_ERROR_STYLE = 'messageError';
    var MESSAGE_SUCCESS_STYLE = 'messageSuccess';
    var VALIDATE_CODE_DEPARTMENT_EXIST = 'Mã đã tồn tại';
    var VALIDATE_CODE_DEPARTMENT_NOT_EXIST = 'Mã được dùng';
    var WARNING_FORMAT_ERROR_MESSAGE = 'Chưa nhập hoặc sai định dạng';
    var SUCCESS_FORMAT_MESSAGE = '';
    var WARNING_FORMAT_ERROR_MESSAGE_TELEPHONE = 'Sai định dạng';
    var DELAY_TIME_AJAX = 200;
    var WAIT_ME_MESSAGE = 'Đang xử lý, xin chờ ...';

    var CONTEXT_PATH = $('#contextPath').val();
    var codeDepartmentField = $('#codeDepartmentField');
    var nameDepartmentField = $('#nameDepartmentField');
    var telephoneNumberField = $('#telephonetField');
    var companyIdField = $('#companySelect');
    var CSRF_TOKEN_NAME = 'X-CSRF-TOKEN';
    var CSRF_TOKEN_VALUE = $("input[name='_csrf']").val();

    var CODE_PATTERN_STRING = '^[a-z A-Z0-9]{2,20}$';
    var TELEPHONE_PATTERN_STRING = '^[0-9]{10,15}$';
    var NAME_PATTERN_STRING = '^[a-z0-9A-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀẾỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳýỵỷỹ ]{2,30}$';
    var isChangedInfor = false;

    var init = function () {
        setupFocusFirstInput();
        setMessage();
        setupOnFocusOut();
        setupCheckChangeValue();
    };

    var setCsrfToken = function (request) {
        request.setRequestHeader(CSRF_TOKEN_NAME, CSRF_TOKEN_VALUE);
    };

    var saveDepartment = function () {
        init();
        setupCodeFocusOut();
        setupSaveSubmit();
    };

    var updateDepartment = function () {
        init();
        setupCodeFocusOutToUpdate();
        setupUpdateSubmit();
    };

    var viewMember = function (user_Id) {
        var contextPath = $('#contextPath').val();
          window.location.href = contextPath + 'employee/employee-profile.html/' + user_Id;
    };

    var setMessage = function () {
        $("#successMessage").hide().fadeIn(0).delay(1000).fadeOut(500);
        $("#errorMessage").hide().fadeIn(0).delay(1000).fadeOut(500);
    };

    var validateCode = function () {
        if (!validateCustom(CODE_PATTERN_STRING, codeDepartmentField, SUCCESS_FORMAT_MESSAGE, WARNING_FORMAT_ERROR_MESSAGE)) {
        } else {
            $.ajax({
                method: "POST",
                url: CONTEXT_PATH + 'department/checkExistCode',
                beforeSend: function (request) {
                    setCsrfToken(request);
                },
                data: {codeId: trimString(codeDepartmentField)},
                success: function (result) {
                    if (result === '1') {
                        $(codeDepartmentField).next('span').removeClass(MESSAGE_SUCCESS_STYLE).addClass(MESSAGE_ERROR_STYLE).text(VALIDATE_CODE_DEPARTMENT_EXIST);
                    } else {
                        $(codeDepartmentField).next('span').removeClass(MESSAGE_ERROR_STYLE).addClass(MESSAGE_SUCCESS_STYLE).text(VALIDATE_CODE_DEPARTMENT_NOT_EXIST);
                    }
                }
            });
        }
    };

    var validateCodeUpdate = function () {
        if (!validateCustom(CODE_PATTERN_STRING, codeDepartmentField, SUCCESS_FORMAT_MESSAGE, WARNING_FORMAT_ERROR_MESSAGE)) {
        } else {
            $.ajax({
                method: "POST",
                url: CONTEXT_PATH + 'department/checkExistCodeToUpdate',
                beforeSend: function (request) {
                    setCsrfToken(request);
                },
                data: {codeId: trimString(codeDepartmentField), departmentId: $('#departmentIdField').val()},
                success: function (result) {
                    if (result === '1') {
                        $(codeDepartmentField).next('span').removeClass(MESSAGE_SUCCESS_STYLE).addClass(MESSAGE_ERROR_STYLE).text(VALIDATE_CODE_DEPARTMENT_EXIST);
                    } else {
                        $(codeDepartmentField).next('span').removeClass(MESSAGE_ERROR_STYLE).addClass(MESSAGE_SUCCESS_STYLE).text(VALIDATE_CODE_DEPARTMENT_NOT_EXIST);
                    }
                }
            });
        }
    };

    var setupCodeFocusOut = function () {
        $(codeDepartmentField).on('focusout', function (e) {
            if ($(e.relatedTarget).hasClass('btn')) {
                return false;
            }
            validateCode();
        });
    };

    var setupCodeFocusOutToUpdate = function () {
        $(codeDepartmentField).on('focusout', function (e) {
            if ($(e.relatedTarget).hasClass('btn')) {
                return false;
            }
            validateCodeUpdate();
        });
    };

    var validateName = function () {
        return validateCustom(NAME_PATTERN_STRING, nameDepartmentField, SUCCESS_FORMAT_MESSAGE, WARNING_FORMAT_ERROR_MESSAGE);
    };

    var validateTelephone = function () {
        if (trimString(telephoneNumberField) === '') {
            $(telephoneNumberField).next('span').text(SUCCESS_FORMAT_MESSAGE);
            return true;
        }
        return validateCustom(TELEPHONE_PATTERN_STRING, telephoneNumberField, SUCCESS_FORMAT_MESSAGE, WARNING_FORMAT_ERROR_MESSAGE_TELEPHONE);
    };

    var setupOnFocusOut = function () {
        $(nameDepartmentField).on('focusout', function (e) {
            if ($(e.relatedTarget).hasClass('btn')) {
                return false;
            }
            validateName();
        });
        $(telephoneNumberField).on('focusout', function (e) {
            if ($(e.relatedTarget).hasClass('btn')) {
                return false;
            }
            validateTelephone();
        });
    };

    var setupCheckChangeValue = function () {
        $(codeDepartmentField).on('input', function () {
            isChangedInfor = true;
        });
        $('textarea').on('input', function () {
            isChangedInfor = true;
        });
        $(telephoneNumberField).on('input', function () {
            isChangedInfor = true;
        });
        $(nameDepartmentField).on('input', function () {
            isChangedInfor = true;
        });
    };

    var setupFocusFirstInput = function () {
      $('#panelInfo input[type="text"]:first').focus();
    };

    var setupSaveSubmit = function () {
        $('#btnSubmit').on('click', function (e) {
            e.preventDefault();
            var nameValidate = validateName();
            var telephoneValidate = validateTelephone();
            var codeValidate = false;

            if (validateCustom(CODE_PATTERN_STRING, codeDepartmentField, SUCCESS_FORMAT_MESSAGE, WARNING_FORMAT_ERROR_MESSAGE)) {
                codeValidate = true;
            }
            if (telephoneValidate && nameValidate && codeValidate) {
                $.ajax({
                    method: "POST",
                    url: CONTEXT_PATH + 'department/checkExistCode',
                    data: {codeId: trimString(codeDepartmentField)},
                    header: {'Csrf_Token': $('input[name = _csrf]').val()},
                    beforeSend: function (request) {
                        run_waitMe($('body'), 2);
                        trimValueElement();
                        codeToUpperCase();
                        setCsrfToken(request);
                    },
                    success: function (result) {
                        setTimeout(function () {
                            if (result === '1') {
                                $(codeDepartmentField).next('span').removeClass(MESSAGE_SUCCESS_STYLE).addClass(MESSAGE_ERROR_STYLE).text(VALIDATE_CODE_DEPARTMENT_EXIST);
                                codeValidate = false;
                            } else {
                                codeValidate = true;
                            }
                            if (!codeValidate) {
                                $('body').waitMe('hide');
                                setupValidateError();

                            } else {
                                $('#saveForm').submit();
                            }
                        }, DELAY_TIME_AJAX);
                    }
                });
            }
            else {
                setupValidateError();
            }
        });
    };

    var setupUpdateSubmit = function () {
        $('#btnSubmit').on('click', function (e) {
            e.preventDefault();
            // if (!isChangedInfor) {
            //     $("#warningMessage").hide().fadeIn(0).delay(1000).fadeOut(500);
            //     return;
            // }
            var nameValidate = validateName();
            var telephoneValidate = validateTelephone();
            var codeValidate = false;
            if (validateCustom(CODE_PATTERN_STRING, codeDepartmentField, SUCCESS_FORMAT_MESSAGE, WARNING_FORMAT_ERROR_MESSAGE)) {
                codeValidate = true;
            }
            if (telephoneValidate && nameValidate && codeValidate) {
                $.ajax({
                    method: "POST",
                    url: CONTEXT_PATH + 'department/checkExistCodeToUpdate',
                    data: {codeId: (codeDepartmentField).val(), departmentId: $('#departmentIdField').val()},
                    header: {'Csrf_Token': $('input[name = _csrf]').val()},
                    beforeSend: function (request) {
                        run_waitMe($('body'), 2);
                        trimValueElement();
                        codeToUpperCase();
                        setCsrfToken(request);
                    },
                    success: function (result) {
                        setTimeout(function () {
                            if (result === '1') {
                                $(codeDepartmentField).next('span').removeClass(MESSAGE_SUCCESS_STYLE).addClass(MESSAGE_ERROR_STYLE).text(VALIDATE_CODE_DEPARTMENT_EXIST);
                                codeValidate = false;
                            } else {
                                codeValidate = true;
                            }
                            if (!codeValidate) {
                                $('body').waitMe('hide');
                                setupValidateError();

                            } else {
                                $('#updateForm').submit();
                            }
                        }, DELAY_TIME_AJAX);
                    }
                });
            }
            else {
                setupValidateError();
            }
        });
    };

    var setupValidateError = function () {
        var messageError = $('.messageError');
        if (messageError.length !== 0) {
            $(messageError[0]).prev().focus();
        }
    };


    var setMessageEmptyTable = function () {
        if ($('#tableAdd tbody tr').length === 0) {
            $('#tableAdd').append(
                '<tr id="emptyMessage"> ' +
                '<td colspan="6">' +
                EMPTY_TABLE_MESSAGE +
                '</td> ' +
                '</tr>'
            );
        }
    };

    var trimString = function (element) {
        return $.trim($(element).val());
    };

    var trimValueElement = function () {
        var inputArray = $('input[type = "text"]');
        var textAreaArray = $('textarea');
        $(inputArray).each(function (index, item) {
            $(item).val(trimString(item));
        });
        $(textAreaArray).each(function (index, item) {
            $(item).val(trimString(item));
        });
    };

    var validateCustom = function (patternString, element, success, error) {
        var pattern = new RegExp(patternString);
        var spanMessage = $(element).next('span');
        $(spanMessage).removeClass(MESSAGE_ERROR_STYLE);
        $(spanMessage).removeClass(MESSAGE_SUCCESS_STYLE);
        if (pattern.test(trimString(element))) {
            $(spanMessage).text(success);
            return true;
        }
        $(spanMessage).addClass(MESSAGE_ERROR_STYLE).text(error);
        return false;
    };

    var run_waitMe = function (el, num) {
        switch (num) {
            case 1:
                fontSize = '24';
                text = WAIT_ME_MESSAGE;
                effect = 'bounce';
                maxSize = '30';
                textPos = 'vertical';
                color = '#000';
                break;
            case 2:
                fontSize = '24';
                text = WAIT_ME_MESSAGE;
                effect = 'rotateplane';
                maxSize = 30;
                textPos = 'vertical';
                color = '#000';
                break;
        }
        el.waitMe({
            effect: effect,
            text: text,
            bg: 'rgba(255,255,255,0.7)',
            color: color,
            maxSize: maxSize,
            waitTime: -1,
            source: 'img.svg',
            textPos: textPos,
            fontSize: fontSize,
            onClose: function (el) {
            }
        });
    };

    var codeToUpperCase = function () {
        $(codeDepartmentField).val($(codeDepartmentField).val().toUpperCase());
    };

    return {
        init: init,
        updateDepartment: updateDepartment,
        saveDepartment: saveDepartment,
        viewMember: viewMember
    }
})();
