$(document).ready(function() {
    // 쇼핑카트 목록을 가져와서 화면에 표시하는 함수
    function showCartList() {
        $.ajax({
            url: '/getCart',
            type: 'GET',
            beforeSend: function (xhr){
                const token = localStorage.getItem('X-AUTH-TOKEN');
                xhr.setRequestHeader("X-AUTH-TOKEN", token);
            },
            success: function (data, status, xhr) {
                // 쇼핑카트 목록이 비어있을 경우
                if (data.length === 0) {
                    $('#cartList').html('<p>쇼핑카트가 비어있습니다.</p>');
                } else {
                    // 쇼핑카트 목록이 존재할 경우
                    //let html = '';
                    const cartList = document.getElementById('cartList');
                    let totalPrice = 0;
                    for (let i = 0; i < data.length; i++) {
                        const item = data[i];

                        // 쇼핑카트 목록에 새로운 행 추가
                        const row = document.createElement('tr');

                        const checkboxCell = document.createElement('td');
                        const checkbox = document.createElement('input');
                        checkbox.type = 'checkbox';
                        checkbox.checked = true; // 기본으로 체크 상태로 설정
                        checkboxCell.appendChild(checkbox);
                        row.appendChild(checkboxCell);


                        // Product 열
                        const productCell = document.createElement('td');
                        productCell.textContent = item.pname;
                        row.appendChild(productCell);


                        // Quantity 열
                        const quantityCell = document.createElement('td');
                        quantityCell.textContent = item.stock + '개';
                        row.appendChild(quantityCell);

                        // Price 열
                        const priceCell = document.createElement('td');
                        priceCell.textContent = item.price + '원';
                        row.appendChild(priceCell);


                        // 삭제 버튼 열
                        const deleteCell = document.createElement('td');
                        const deleteButton = document.createElement('button');
                        deleteButton.textContent = '삭제';
                        deleteButton.className = 'delete-button';
                        deleteCell.appendChild(deleteButton);
                        row.appendChild(deleteCell);

                        // // Total 열
                        // const totalCell = document.createElement('td');
                        // const total = item.price * item.stock; // Total 계산
                        // totalCell.textContent = total + '원';
                        // row.appendChild(totalCell);

                        cartList.appendChild(row);

                        // totalPrice에 가격 추가
                        totalPrice += item.price;
                        const totalPriceElement = document.getElementById('totalPrice');
                        totalPriceElement.textContent = totalPrice + '원';

                        checkbox.addEventListener('change', function() {
                            // 체크 상태가 변경되었을 때 처리할 로직
                            if (checkbox.checked) {
                                totalPrice += item.price;
                                totalPriceElement.textContent = totalPrice + '원';

                            } else {
                                totalPrice -= item.price;
                                totalPriceElement.textContent = totalPrice + '원';

                            }
                        });

                    }

                    //$('#cartList').html(html);
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
