function mainPageJs() {
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
    });
}