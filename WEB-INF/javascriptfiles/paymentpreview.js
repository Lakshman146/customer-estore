    // Get the amount value from the input field
function openCheckout() {
    var amt = document.getElementById("tid").value;
    console.log("amount in payment options jsp " + amt);
    var orderId;

    // Send an AJAX request to get the order ID
    $.ajax({
        url: "getOrderId",
        method: 'GET',
        data: { amt: amt },
        success: function(response) {
            orderId = response;
            console.log("response == " + response);
            handleOrder(orderId, amt);
        },
        error: function(xhr, status, error) {
            console.log('AJAX Error: ' + error);
        }
    });
}

function handleOrder(orderId, amt) {
    var options = {
        key: "rzp_test_Eu94k5nuplVQzA",
        name: "E-Cart",
        // amount: 1000,
        description: "SLAM payments",
        image: "https://s29.postimg.org/r6dj1g85z/daft_punk.jpg",
        prefill: {
            name: "Adithya",
            email: "adithya.thandra@gmail.com",
            contact: "9290005690"
        },
        notes: {
            address: "India",
            merchant_order_id: orderId
        },
        theme: {
            color: "#F37254"
        },
        order_id: orderId,
        handler: function(response) {
        console.log("payment response:"+response);
                console.log( response.razorpay_payment_id);
        
            document.getElementById('paymentReference').value = response.razorpay_payment_id;
            document.getElementById('razorpay_order_id').value = orderId;
            // document.getElementById('razorpay_signature').value = response.razorpay_signature;
            document.getElementById('razorpay_amount').value = amt;

            // Submit the form
            document.razorpayForm.submit();
        },
        modal: {
            ondismiss: function() {
                console.log("This code runs when the popup is closed");
            },
            escape: true,
            backdropclose: false
        }
    };

    var rzpButton = document.getElementById("rzp-button1");
    rzpButton.addEventListener("click", function(e) {
        e.preventDefault();
        console.log("inside");

        // Create a new instance of Razorpay and open the checkout modal
        var rzp = new Razorpay(options);
        rzp.open();
    });
}

    // Get the total and wallet amount values
     //Function to call the controller method for checking the user entered wallet amount is Sufficient or not
    function checkWalletAmount() {
    var total = document.getElementById("tod").value;
        var wallet =document.getElementById("wallet").value;
        var useWallet = document.getElementById("walletAmount10").checked;
        console.log(total+""+wallet);
        if(useWallet)
        	{
        $.ajax({
            url: "wallet",
            method: 'GET',
            data: { wallet: wallet,orderamt:total},
            success: function(response, textStatus, xhr) {
                var contentType = xhr.getResponseHeader('Content-Type');
                
                if (contentType.includes('text/html')) {
                    $('#id1').html(response);

                  
                } else {
                  // The response is a normal value (not an HTML page)
                  console.log('Received a normal value response');
                   console.log(response);
                  if (response) {
                      console.log(response);
$('#afterwallet').text("After Wallet used Remaining amount to pay");
                    document.getElementById("tid").value = response;
                  }
                }
              },
            error: function(xhr, status, error) {
                alert("Insuffient wallet amount");
            }
        });
        	}
        else
        	{
            document.getElementById("tid").value=total;
            $('#afterwallet').text("");
            
        	}
        
    }
 

        $('#walletAmount10').click(function() {
        	checkWalletAmount();
          });
