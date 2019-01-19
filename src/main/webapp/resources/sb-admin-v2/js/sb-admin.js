var init_ab_admin_theme = function () {
    $(function () {
        $('#side-menu').metisMenu();
    })

    var is_Size_Of_Mobile = function () {
        var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        var is_size_of_mobile = width < 768
        return is_size_of_mobile
    }

    var setupEventBar_OnMobile = function () {
        if (is_Size_Of_Mobile()) {
            showLiCurrentActive(true)
        }
    }

    var url = window.location.pathname;
    var current_a_tag = $('ul.nav li a').filter(function () {
        return $(this).attr('href') === url || url.indexOf(this.href) === 0;
    }).addClass('active'); // current_a_tag is tag a has href to enter current page

    var showLiCurrentActive = function (is_size_of_mobile) {
        $('.sidebar-collapse li').removeClass('active')
        $('.sidebar-collapse ul li ul').removeClass('in')
        var current_ul = current_a_tag.parent().parent();
        $(current_ul).parent().addClass('active')
        if (!is_size_of_mobile) {
            $(current_ul).addClass('in')
        } else {
            current_ul.removeClass('in')
        }
    }
    var is_Mobile_Device = function () {
        var check = false;
        (function (a) {
            if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(a) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0, 4))) check = true;
        })(navigator.userAgent || navigator.vendor || window.opera);
        return check;
    }();

    var setMobileUI = function () {
        if (is_Size_Of_Mobile()) {
            var setupTableEmpty = function () {
                $('.table_empty').parent().css('border', 'none')
                    .parent().css('border', 'none')
            }()

            var setupMessagePosition = function () {
                $('#errorMessageWrapper').prependTo('#page-wrapper');
                $('#successMessageWrapper').prependTo('page-wrapper');
                $('#warningMessageWrapper').prependTo('page-wrapper');
            }()

            var setupButtonOnMobileMode = function () {
                var btnAppend = '';
                $('.panel-heading a').each(function () {
                    var title = $(this).data('original-title') ? $(this).data('original-title') : $(this).attr('title')
                    btnAppend += '<a class="btn-function" href="' + $(this).attr('href') + '">' + title + '</a>'
                })
                if (btnAppend != '') {
                    $('#page-wrapper').prepend('<div class="btn-function-div">' + btnAppend + '</div>');
                    $('.panel-heading').children().each(function () {
                        if(!$(this).is('span')) {
                            $(this).remove()
                        }
                    })
                }
            }()

            var reLayoutAndSetEvent_on_mobile = function () {
                var trArray = $('.table-responsive  tr');
                $(trArray).each(function () {
                    var tdArray = $(this).find('td').filter(function () {
                        return $(this).find('a .btn').length != 0 || $(this).find('a.btn').length != 0|| $(this).find('button').length != 0;
                    });
                    var stringAppend = '';
                    $(tdArray).each(function () {
                        var a = $(this).find('a').length !=0 ? $(this).find('a') : $(this).find('button');
                        var setupUrl = $(a).attr('href') ? 'href="' + $(a).attr('href') + '"' : 'onclick="' + $(a).attr('onclick') + '"';
                        var title = $(a).data('original-title') ? $(a).data('original-title') : $(a).attr('title')
                        if(!$(a).hasClass('disabled')) {
                            stringAppend += '<div><a ' + setupUrl + '>' + title + '</a></a>'
                        }
                    });
                    var tdBefore = $(tdArray).first().prev('td');
                    $(tdArray).remove()
                    tdBefore.after('<td class="td-function">' + stringAppend + '</td>')
                });
            }()

            // set icon to top
            var setIconToTop = function () {
                var element = '<a id="to_top" style="' +
                    'position: fixed; bottom: 200px; right: 15px;' +
                    'z-index: 9; display: none">' +
                    '       <img alt="" src="/assets/img/icon_to_top.png">' +
                    '</a>'

                $('body').append(element)

                $('#to_top').on('click', function () {
                    $("html, body").animate({scrollTop: 0}, "medium");
                })

                var scrollFunction = function () {
                    if (document.body.scrollTop > 60 || document.documentElement.scrollTop > 60) {
                        document.getElementById("to_top").style.display = "inline";
                    } else {
                        document.getElementById("to_top").style.display = "none";
                    }
                }

                $(window).on('scroll', function () {
                    scrollFunction()
                })
            }()

            //set search div if have
            var setSearchDiv = function () {
                var search_div = $('#search_div')
                var btn_search = $(search_div).find('.btnSearchInDiv')
                if (search_div.length != 0) {
                    $(search_div).css('display', 'none')
                    $('#page-wrapper').css('padding-top', '10px')
                    var element = $(
                        '<div id="btn_search_mobile" style="display: block; position: absolute;' +
                        ' right: 10px; top:10px; padding: 5px">' +
                        '<i class="glyphicon glyphicon-search" style="font-size: 17px"></i>' +
                        '</div>')
                    $('.navbar-header').append(element)
                    $(search_div).attr('style', 'position:fixed; z-index:10; display:none;' +
                        'background:#CCCCCC; width:100%; padding:10px; min-height:100%;' +
                        'top:0; left:0; right:0; margin:0; opacity:0.97')

                    var btnCancelAppend = '<button id="btn_close_search" type="button" class="btn btn-danger"' +
                        'style="padding: 6px 15px">Hủy bỏ</button>'
                    $('.btnSearchInDiv').attr('style', 'padding-right:5px')
                    if (btn_search.length != 0) {
                        var elementParent = $(btn_search).parent().is('a') ?
                            $(btn_search).parent().parent() : $(btn_search).parent()
                        $(elementParent).attr('style', 'width:100%; text-align:center')
                        $(elementParent).parent('div').css('width', '100%')
                        $(btn_search).css('margin-right', '10px')
                        $(elementParent).append(btnCancelAppend);
                    } else {
                        $(search_div).append('<div style="text-align:center">' + btnCancelAppend + '</div>');
                    }
                    $('#btn_close_search').on('click', function (e) {
                        $('body').css('overflow', 'auto')
                        $(search_div).css('display', 'none')
                    })
                    $(search_div).appendTo('body')
                    $('.navbar-header #btn_search_mobile').on('click', function () {
                        $('body').css('overflow', 'hidden')
                        $(search_div).css('display', 'block')
                    })
                }
            }()
            var disableTooltip = function () {
                $('[data-toggle="tooltip"]').tooltip('disable');
            }()
        }
    }()

    var setupReSize = function () {
        $('#page-wrapper').css('min-height', $(window).innerHeight() - $('.navbar-header').outerHeight())
        if ($('#side-menu').innerHeight() > $('#page-wrapper').attr('min-height')) {
            $('#page-wrapper').css('height', $('#side-menu').innerHeight())
        }
        else {
            $('.sidebar-collapse').css('height', $('#page-wrapper').attr('min-height'))
        }
        $('.navbar-default.navbar-static-side').css('height', $('#page-wrapper').innerHeight())
        var a;
        if ($('.sidebar-collapse.collapse').hasClass('in')) {
            a = '100%'
        } else {
            a = $('body').width() - $('#side-menu li').outerWidth()
        }

        $('#page-wrapper').css('width', a );

        if (is_Size_Of_Mobile()) {
            $('#side-menu li ul li a').css('width', '100%')
            $('#side-menu > li > ul').css('width', $(window).outerWidth() - $('.navbar-default.navbar-static-side').outerWidth() - 1)
            $(window).on('mouseup', function (e) {
                if (!$(e.target).hasClass('menubar-element')) {
                    $('#side-menu > li').removeClass('active')
                    $('#side-menu li ul.in').removeClass('in')
                    showLiCurrentActive(true)
                }
            })
        } else {
            $('#side-menu li ul li a').css('width', $('#side-menu').outerWidth())
        }
        showLiCurrentActive(is_Size_Of_Mobile())
    }

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
    $(window).bind("load resize", function () {
        setupReSize()
    });
    return {}
}()