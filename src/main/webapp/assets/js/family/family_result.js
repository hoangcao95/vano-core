$("#successMessage").hide().fadeIn(0).delay(1000).fadeOut(500);

$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
});

function copyConfirm(id) {
    BootstrapDialog.show({
        title: 'Xác nhận sao chép dữ liệu',
        message: 'Bạn có muốn sao chép dữ liệu người dùng hiện tại? Bấm Accept để sao chép',
        buttons: [{
            label: 'Bỏ qua',
            action: function (dialogItself) {
                dialogItself.close();
            }
        }, {
            label: 'Accept',
            cssClass: 'btn-primary',
            action: function (dialogItself) {
                var copyUrl = $('#contextPath').val() + "family/family-copy.html/" + id;
                $.ajax({
                    url: copyUrl,
                    method: 'GET',
                    data: {
                        'id': id
                    },
                    success: function (response) {

                    }
                }).always(function (ketqua) {
                    window.location.href = 'family-copy.html/' + id;
                });
            }
        }]
    });
}