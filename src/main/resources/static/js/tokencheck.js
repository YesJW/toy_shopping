function checkToken(xhr){
    if (!localStorage.getItem("jwt")){
        alert("로그인이 필요한 페이지입니다.");
        location.href = "/sign-api/login";
    }
    else{
        var jsonResponse = JSON.parse(xhr.responseText);
        if (jsonResponse.status == "403" || jsonResponse.status == "500") { //로그인을 안했거나 토큰이 만료됐거나
            $.ajax({
                url: "/auth/reissue",
                type: "GET",
                dataType: "json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Content-type", "application/json");
                    xhr.setRequestHeader("Authorization", localStorage.getItem("jwt"));
                },
                success: function(data, xhr, status) {
                    localStorage.setItem("jwt", data.grantType+" "+data.accessToken)
                    location.reload();
                },
                error: function (xhr) {
                    var jsonResponse = JSON.parse(xhr.responseText);
                    if (jsonResponse.throwableMessage == "리프레시 토큰 만료") {
                        alert("로그인이 필요한 페이지입니다.");
                        localStorage.removeItem("jwt");
                        location.href = "/sign-api/login";
                    }
                },
            })

        }
    }
}