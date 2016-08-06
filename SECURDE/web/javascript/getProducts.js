/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getProducts() {
    var xhttp;
    
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(xhttp.readystate === 4 && xhttp.status === 200) {
            document.getElementById("product-list-container").innerHtml = xhttp.responseText();
        }
    };
    
    xhttp.open("POST", "getProducts.asp");
    xhttp.send();
}

