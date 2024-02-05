function myHeader() {
    $(document).ready(function () {
        const userToken = localStorage.getItem('jwt'); // localStorage에서 토큰을 가져옴
        $('#login-btn').click(function () {
            $.ajax({
                url: '/sign-api/login',
                type: 'GET',
                success: function (data, status, xhr) {
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });
        });

        $('#register-btn').click(function () {
            $.ajax({
                url: '/sign-api/register',
                type: 'GET',
                success: function (data, status, xhr) {
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });
        });

        $('#register-btn').click(function () {
            $.ajax({
                url: '/sign-api/register',
                type: 'GET',
                success: function (data, status, xhr) {
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });
        });

        $('#mypage-btn').click(function () {
            $.ajax({
                url: '/mypage',
                type: 'GET',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("jwt", userToken);

                },
                success: function (data, status, xhr) {
                    console.log(xhr);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });

            fetch('/mypage', {
                headers: {
                    'content-type': 'application/json',
                    'jwt': userToken // Authorization 헤더에 토큰 값을 추가
                }
            })
                .then(response => {
                    console.log(response);
                    // ...
                });

        });
        if (userToken) {
            $('#login-btn').hide();
            $('#register-btn').hide();
            $('#logout-btn').show();
            $('#mypage-btn').show();
        } else {
            $('#login-btn').show();
            $('#register-btn').show();
            $('#logout-btn').hide();
            $('#mypage-btn').hide();
        }

        // 로그아웃 버튼 클릭 시
        $('#logout-btn').click(function () {
            localStorage.removeItem('jwt');
            $('#login-btn').show();
            $('#register-btn').show();
            $('#logout-btn').hide();
            $('#mypage-btn').hide();
        });

        $('html').removeClass('no-js');

    });

}

function submitSearchForm() {
    var searchKeyword = document.getElementById("search-form").elements["q"].value;
    if (searchKeyword.trim() !== "") {
        var form = document.createElement("form");
        form.action = "/searchPage";
        form.method = "GET";

        var input = document.createElement("input");
        input.type = "hidden";
        input.name = "q";
        input.value = searchKeyword;
        form.appendChild(input);

        document.body.appendChild(form);
        form.submit();
    }
}