/**
 * Created by Alexander on 01.04.2016.
 */

function total(linesSize) {
    // total lines and order summ counting:
    var orderSumm = 0;
    for (var i = 0; i < linesSize; i++) {
        var item = document.getElementById("total_summ_of_line" + i);
        var res = parseInt(document.getElementById("book_price" + i).innerHTML) *
            parseInt(document.getElementById("books_quantity" + i).value);
        item.innerHTML = res.toString();
        orderSumm += parseInt(item.innerHTML);
    }
    document.getElementById("total_summ_of_order").innerHTML = orderSumm.toString() + ' rub.';
}


function booksQuantityValidate() {
    var size = parseInt(document.getElementById("list_size").value);
    var isOk = true;

    for (var i = 0; i < size; i++) {
        var bookId = parseInt(document.getElementById("book_id" + i).innerHTML);
        var bookQuantity = parseInt(document.getElementById("books_quantity" + i).value);
        var jqXHR = $.ajax({
            url: 'order/ajaxBooksQuantityValidation',
            data: ({bookId: bookId, bookQuantity: bookQuantity}),
            async: false,
            success: function (data) {
                if (data.length > 1) {
                    isOk = false;
                    $('#books_quantity_error').html(data);
                }
            }
        });
    }
    return isOk;
}

$(document).ready(function () {
    $("#order_form").submit(function () {
        var isValidated = booksQuantityValidate();
        return isValidated;
    });
});
