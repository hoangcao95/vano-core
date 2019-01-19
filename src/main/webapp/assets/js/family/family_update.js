$('#inputbirthDay').daterangepicker({
    "singleDatePicker": true,
    "linkedCalendars": false,
    "showDropdowns": true,
    "showCustomRangeLabel": false,
    format: 'DD/MM/YYYY',
    "opens": "center"
}, function (start, end, label) {
});

$("#errorMessage").hide().fadeIn(0).delay(1000).fadeOut(500);

$('.formUpdate').bootstrapValidator({
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        name: {
            validators: {
                notEmpty: {
                    message: 'Tên người thân bắt buộc phải nhập !!!'
                },
                regexp: {
                    regexp: /^[a-z A-ZÀÁÃẠẢĂẰẮẴẶẲÂẦẤẪẬẨÈÉẼẸẺÊỀẾỄỆỂÌÍĨỊỈÒÓÕỌỎÔỒỐỘỔỖỠỜƠỚỢỞÙÚŨỤỦÙÚỰỮỬƯĐYỴỶÝỲàáãạảăằắẵặẳâấầậẫèéẻẹẽêếềểệễìíỉịĩòóỏõọôồốổộỗơỡợờớởùúủụũưừứửựữđỷýỳỹỵ]+$/,
                    message: 'Tên người thân không chứa ký tự số '
                },
                stringLength: {
                    min: 7,
                    max: 30,
                    message: 'Tên người thân nằm trong khoảng 7-30 ký tự '
                }
            }
        },
        familyRelation: {
            validators:{
                notEmpty:{
                    message: 'Mối quan hệ với nhân viên bắt buộc phải nhập !!!'
                },
                regexp: {
                    regexp: /^[a-z A-ZÀÁÃẠẢĂẰẮẴẶẲÂẦẤẪẬẨÈÉẼẸẺÊỀẾỄỆỂÌÍĨỊỈÒÓÕỌỎÔỒỐỘỔỖỠỜƠỚỢỞÙÚŨỤỦÙÚỰỮỬƯĐYỴỶÝỲàáãạảăằắẵặẳâấầậẫèéẻẹẽêếềểệễìíỉịĩòóỏõọôồốổộỗơỡợờớởùúủụũưừứửựữđỷýỳỹỵ]+$/,
                    message: 'Mối quan hệ không chứa ký tự số'
                },
            }
        }
    }});