function mainPageJs() {
    $(document).ready(function () {
        // 페이지 로드 시 실행되는 코드
        const token = localStorage.getItem('jwt'); // 로컬 스토리지에서 토큰 가져오기

        // AJAX 요청 설정
        $.ajax({
            url: '/main',
            type: 'GET',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Content-type","application/json");
                xhr.setRequestHeader("Authorization", localStorage.getItem("jwt"));
            },
        }).done(function(data,status,xhr) {
            console.log(data)
            $("#profile").html("사용자의 ID는 " + data.user.username + "입니다.<br>10초뒤에 토큰이 만료됩니다.");
        }).fail(function(xhr, status, error) {
            alert("asdasdasdas")

            var jsonResponse = JSON.parse(xhr.responseText);
            if (jsonResponse.status == "403" || jsonResponse.status == "500") { //로그인을 안했거나 토큰이 만료됐거나
                alert("로그인이 필요한 페이지입니다.");
                location.href = "/login";
            }
        });


    });

}