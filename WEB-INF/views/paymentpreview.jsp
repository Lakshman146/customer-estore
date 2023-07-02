<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="eStoreProduct.utility.ProductStockPrice" %>
<%@ page import="eStoreProduct.model.custCredModel" %>
<%@ page import="eStoreProduct.model.productqty" %>
<%@ page import="eStoreProduct.model.wallet" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Order Summary</title>
            <link rel="stylesheet" type="text/css" href="./css/paymentpreview.css">
                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            
                      <script src="./js/paymentpreview.js"></script>
               
    <script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
     <script src="https://checkout.razorpay.com/v1/checkout.js"></script>

</head>
<body>
<br/>
   <b><h3 align="center">Order Summary</h3></b>
   <div id="div1">
    <div id="id1" align="center">
        <div class="container mt-5">
            <div class="row mt-4" align="center">
                <% custCredModel cust1 = (custCredModel) session.getAttribute("customer");
                System.out.println("Customer spincode: "+cust1.getCustSpincode());
                List<ProductStockPrice> products = (List<ProductStockPrice>) request.getAttribute("products");
                 wallet Wallet=(wallet) request.getAttribute("Wallet");
                for (ProductStockPrice product : products) {
                %>
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100">
                        <img class="card-img-top" src="<%= product.getImage_url() %>" alt="<%= product.getProd_title() %>">
                        <div class="card-body">
                            <h5 class="card-title"><%= product.getProd_title() %></h5>
                            <p class="card-text">Quantity: <%= product.getQuantity() %></p>
                            <p class="card-text">Price: <%= product.getPrice() %></p>
                            <p class="card-text">Subtotal: <%= product.getQtyprice() %></p>
                             <p class="card-text">gst: <%= product.getGst() %></p>
                            
                            
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
        <div id="cont" class="text-center">
		    <form id="shipment-form">
		        <table align="center">
		            <tr>
		                <td class="table-col2"><b>Delivery Location:</b></td>
		                <td class="table-col2"></td>
		            </tr>
		            <tr>
		                <td class="table-col1"><b>Name:</b></td>
		                <td class="table-col2"><%=cust1.getCustName()%></td>
		            </tr>
		            <tr>
		                <td class="table-col1"><b>Address:</b></td>
		                <td class="table-col2"><%=cust1.getCustSAddress()%></td>
		            </tr>
		            <tr>
		                <td class="table-col1"><b>Pincode:</b></td>
		                <td class="table-col2"><%=cust1.getCustPincode()%></td>
		            </tr>
		        </table>
		    </form>
		</div>

		<div style="text-align: center;">
       <label>Total Order Cost:</label>
		        <input type="number" id="tod" value="<%=ProductStockPrice.getTotal()%>">
		        </div>
		               <div style="text-align: center;">
		    <label for="wallet">Use Wallet:</label>
		    <input type="number" id="wallet" name="wallet" value="<%=Wallet.getAmount()%>" readonly>
		    <input type="checkbox" id="walletAmount10" name="walletAmount" onclick="checkWalletAmount()">
		</div>
		</div>
		<div style="text-align: center;">
		    <div style="display: inline-block; text-align: center;">
		    <p id="afterwallet" style="color:MediumSeaGreen;"></p>
		        <label for="tid">Total amount to pay:</label>
		        <input type="number" id="tid" value="<%=ProductStockPrice.getTotal()%>">
		    </div>
		    <br>
<!-- 		    <button class="btn btn-primary back">Back</button> -->
		    <button class="btn btn-primary pay" id="rzp-button1" onclick="openCheckout('${orderId}')">Proceed to Checkout</button>
		</div>
		</div>


    <form action="invoice" method="POST" name="razorpayForm">
    
        <input id="paymentReference" type="hidden" name="paymentReference" />
        <input id="razorpay_order_id" type="hidden" name="billNumber" />
		<input id="razorpay_amount" type="hidden" name="total" />

		<input id="shippingAddress" type="hidden" name="shippingAddress" value="<%=cust1.getCustSAddress()%>" />
		<input id="customerId" type="hidden" name="ordr_cust_id" value="<%=cust1.getCustId()%>" />
		<input id="shippingPincode" type="hidden" name="shippingPincode" value="<%=cust1.getCustSpincode() %>">
    </form>
</body>
</html>
 