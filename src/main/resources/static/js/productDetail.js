$(document).ready(function() {
    // 상품 ID를 URL 매개변수에서 추출합니다.
    var productId = getProductIdFromURL();

    // 상품 상세 정보를 가져오는 AJAX 요청을 보냅니다.
    $.ajax({
        url: '/getProductDetail',
        type: 'GET',
        data: { id: productId },
        dataType: 'json',
        beforeSend: function (xhr){
            const token = localStorage.getItem('jwt');
            xhr.setRequestHeader("Authorization", token);
        },
        success: function(product) {
            // 상세 페이지를 상품 정보로 업데이트합니다.
            $('#product-name').text(product.name);
            $('#product-stock').text('남은 갯수: ' + product.stock);
            $('#product-price').text('$' + product.price);
            $('#quantity').attr('max', product.stock);

            $('#product-img').attr('src',product.imgPath);
            $('#quantity').on('change', function() {
                var selectedQuantity = $(this).val();

                if (selectedQuantity < 1 || selectedQuantity > product.stock) {
                    alert('갯수를 확인해주세요');
                    $('#quantity').val(product.stock);
                } else {
                    // 유효한 수량인 경우 메시지 초기화
                }
            });

        },
        error: function() {
            alert('상품 정보를 불러오는 데 실패했습니다.');
        }
    });

    // 구매 버튼 클릭 시 구매 처리를 수행합니다.
    $('#purchase-btn').click(function() {
        var quantity = $('#quantity').val();
        var productName = $('#product-name').text();
        var productPriceText = $('#product-price').text();
        var productPrice = parseInt(productPriceText.match(/\d+/)[0]);

        $.ajax({
            url: '/addCart',
            type: 'POST',
            data: { pnum: productId, stock: quantity, name: productName, price : productPrice, uno : productId },
            dataType:'json',
            beforeSend: function (xhr){
                const token = localStorage.getItem('jwt');
                xhr.setRequestHeader("Authorization", token);
            },
            success: function() {
                alert('상품을 성공적으로 구매했습니다.');
// 구매 완료 후 다른 동작을 수행하거나 페이지를 이동할 수 있습니다.
            },
            error: function() {
                alert('상품 구매에 실패했습니다.');
            }
        });
    });
    // URL 매개변수에서 상품 ID를 추출하는 도우미 함수입니다.
    function getProductIdFromURL() {
        var urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('id');
    }
});
