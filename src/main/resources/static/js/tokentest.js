function sendRequest(url) {
    console.log("호출됨.")
    var token = localStorage.getItem('X-AUTH-TOKEN'); // 로컬 스토리지에서 토큰 가져오기
    $.ajax({
        url: url,
        type: "GET",
        xhrFields:{
            withCredentials: true
        },
        beforeSend: function(xhr) {
            console.log("ㅁㄴㅇㅁㅁㄴㅇ");
            xhr.setRequestHeader("X-AUTH-TOKEN", token);
        },
        success: function(data) {
            console.log("성공함.")
            // Ajax 요청 성공 시 실행되는 코드
        },
        error: function(jqXHR, textStatus, errorThrown) {
            // Ajax 요청 실패 시 실행되는 코드
            console.log("실패함.")
        }
    });
}