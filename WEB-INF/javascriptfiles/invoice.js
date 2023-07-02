
  function printInvoice(){
console.log("printttttt");
var printButton = document.getElementById('printButton');
        printButton.style.display = 'none'; // Hide the button
        window.print();
        printButton.style.display = 'block'; // Restore the button after printing
        window.location.href="loggedIn";
    }

