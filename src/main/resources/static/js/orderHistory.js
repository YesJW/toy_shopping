$(document).ready(function() {
    // 쇼핑카트 목록을 가져와서 화면에 표시하는 함수
    function showOrderList() {
        $('#cartList').empty();

        $.ajax({
            url: '/getAllOrderHisyory',
            type: 'GET',
            beforeSend: function (xhr) {
                const token = localStorage.getItem('jwt');
                xhr.setRequestHeader("Authorization", token);
            },
            success: function (data, status, xhr) {
                // 쇼핑카트 목록이 비어있을 경우
                if (data.length === 0) {
                    $('#orderList').html('<p>구매 목록이 없습니다..</p>');
                } else {
                    total = 0
                    // 쇼핑카트 목록이 존재할 경우
                    //let html = '';
                    const orderList = document.getElementById('orderList');
                    for (let i = 0; i < data.length; i++) {
                        total += 1
                        const item = data[i];
                        for (let j = 0; j < item.orderProducts.length; j++) {
                            const product = item.orderProducts[j]
                            console.log(product)
                            const row = document.createElement('tr');

                            const numberCell = document.createElement('td');
                            numberCell.textContent = product.orderId;
                            row.appendChild(numberCell);
                            // Product 열
                            const productCell = document.createElement('td');
                            productCell.textContent = product.productName;
                            row.appendChild(productCell);


                            // Quantity 열
                            const quantityCell = document.createElement('td');
                            quantityCell.textContent = product.count + '개';
                            row.appendChild(quantityCell);

                            // Price 열
                            const priceCell = document.createElement('td');
                            priceCell.textContent = product.total +'원';
                            row.appendChild(priceCell);

                            const dateCell = document.createElement('td');
                            dateCell.textContent = item.time.toString();
                            row.appendChild(dateCell);

                            orderList.appendChild(row);
                        }

                    }
                    const totalProductCount = document.getElementById('totalCount');
                    totalProductCount.textContent = total + '개의 주문 내역';

                    //$('#cartList').html(html);
                }

            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Error: ' + errorThrown);
            }
        });

    }

    // 페이지 로드 시 쇼핑카트 목록을 화면에 표시
    showOrderList();
});