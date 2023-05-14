function myHeader() {
    $(document).ready(function () {
        // 페이지 로드 시 실행되는 코드
        var token = localStorage.getItem('X-AUTH-TOKEN'); // 로컬 스토리지에서 토큰 가져오기

        // AJAX 요청 설정
        $.ajax({
            url: '/main',
            type: 'GET',
            beforeSend: myFunction,
            success: function (data, status, xhr) {
                xhr.setRequestHeader('X-AUTH-TOKEN', token);
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        });

        $.ajax({
            url: '/getProductPage',
            type: 'GET',
            beforeSend: myFunction,
            success: function (data, status, xhr) {
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        });


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

        $('#mypage-btn').click(function () {
            $.ajax({
                url: '/mypage',
                type: 'GET',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-AUTH-TOKEN", token);

                },
                success: function (data, status, xhr) {
                    console.log(xhr);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });
            const userToken = localStorage.getItem('X-AUTH-TOKEN'); // localStorage에서 토큰을 가져옴
            fetch('/mypage', {
                headers: {
                    'content-type': 'application/json',
                    'X-AUTH-TOKEN': userToken // Authorization 헤더에 토큰 값을 추가
                }
            })
                .then(response => {
                    console.log(response);
                    // ...
                });

        });


        if (token) {
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
            localStorage.removeItem('X-AUTH-TOKEN');
            $('#login-btn').show();
            $('#register-btn').show();
            $('#logout-btn').hide();
            $('#mypage-btn').hide();
        });

        function myFunction(xhr) {
            xhr.setRequestHeader("X-AUTH-TOKEN", token);
        }
    });
}