function mainPageJs() {
    $(document).ready(function () {
        // 페이지 로드 시 실행되는 코드
        const token = localStorage.getItem('X-AUTH-TOKEN'); // 로컬 스토리지에서 토큰 가져오기

        // AJAX 요청 설정
        $.ajax({
            url: '/main',
            type: 'GET',
            beforeSend: myFunction,
            success: function (data, status, xhr) {
                xhr.setRequestHeader('X-AUTH-TOKEN', token);
                console.log("asdasdasdasdasdasd")
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        });
        function myFunction(xhr) {
            xhr.setRequestHeader("X-AUTH-TOKEN", token);
        }
        $('a').click(function(event) {
            // 이벤트 기본 동작(링크 이동)을 막음
            event.preventDefault();

            // 클릭한 링크의 href 속성 값 가져오기
            var linkUrl = $(this).attr('href');
            if (linkUrl === '/contactPage') {
                // Add token to the request header
                var token = localStorage.getItem('X-AUTH-TOKEN');
                $.ajaxPrefilter(function(options, originalOptions, xhr) {
                    xhr.setRequestHeader('X-AUTH-TOKEN', token);
                });
            }
            // Ajax 요청 전에 수행할 작업 설정
            $.ajax({
                url: linkUrl,
                type: 'GET',
                beforeSend: function(xhr) {
                    // beforeSend 이벤트 콜백 함수
                    // 이곳에서 추가적인 작업을 수행할 수 있음
                    console.log(xhr);
                    console.log("호출됨");
                    // 예시: 로딩 스피너 표시
                    showLoadingSpinner();
                    xhr.setRequestHeader('X-AUTH-TOKEN', token);
                },
                success: function(data) {
                    // Ajax 요청이 성공적으로 완료된 후의 동작
                    // 예시: 페이지 이동
                    window.location.href = linkUrl;
                },
                error: function(xhr, status, error) {
                    // Ajax 요청 중 에러 발생 시의 동작
                    // 예시: 에러 처리
                    handleError(xhr, status, error);
                },
                complete: function() {
                    // Ajax 요청 완료 후의 동작
                    // 예시: 로딩 스피너 숨김
                    hideLoadingSpinner();
                }
            });

        });
    });

    function showLoadingSpinner() {
        // 로딩 스피너 요소를 생성하여 화면에 추가
        var spinnerElement = '<div class="loading-spinner"></div>';
        $('body').append(spinnerElement);
    }

    function hideLoadingSpinner() {
        // 로딩 스피너 요소를 제거
        $('.loading-spinner').remove();
    }

    function handleError(xhr, status, error) {
        // 에러 메시지를 표시하거나 다른 동작 수행
        console.log('An error occurred during the request:', error);
        alert('An error occurred. Please try again later.');
    }


}