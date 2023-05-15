$(document).ready(function() {
    // 쇼핑카트 목록을 가져와서 화면에 표시하는 함수
    function showCartList() {
        $.ajax({
            url: '/getCart',
            type: 'GET',
            success: function (data, status, xhr) {
                // 쇼핑카트 목록이 비어있을 경우
                if (data.length === 0) {
                    $('#cartList').html('<p>쇼핑카트가 비어있습니다.</p>');
                } else {
                    // 쇼핑카트 목록이 존재할 경우
                    let html = '';
                    for (let i = 0; i < data.length; i++) {
                        const item = data[i];
                        html += '<div class="cart-item">';
                        html += '<img src="' + item.image + '" alt="' + item.name + '">';
                        html += '<div class="cart-item-info">';
                        html += '<p class="cart-item-name">' + item.name + '</p>';
                        html += '<p class="cart-item-price">' + item.price + '원</p>';
                        html += '<button class="cart-item-remove" data-id="' + item.id + '">삭제</button>';
                        html += '</div>'; // .cart-item-info
                        html += '</div>'; // .cart-item
                    }
                    $('#cartList').html(html);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Error: ' + errorThrown);
            }
        });
    }

    // 페이지 로드 시 쇼핑카트 목록을 화면에 표시
    showCartList();

    // 쇼핑카트에서 상품 삭제 버튼 클릭 시
    $('#cartList').on('click', '.cart-item-remove', function() {
        const itemId = $(this).data('id');
        $.ajax({
            url: '/removeFromCart',
            type: 'POST',
            data: {itemId: itemId},
            success: function (data, status, xhr) {
                // 쇼핑카트 목록을 다시 가져와서 화면에 표시
                showCartList();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Error: ' + errorThrown);
            }
        });
    });
});
