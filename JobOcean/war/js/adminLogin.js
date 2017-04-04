/**
* This file contains the functionalities for the 'Register Dish' screen.
* @author Jireh Bautista
* @version 0.01
* Version History
* [07/20/2016] 0.01 – Jireh Bautista – initial codes
* [08/31/2016] 0.02 "Jessie May Cantil"
**/

app.controller('adminLoginController', function($scope, $http) {
	console.log("AdminLogin.adminLogin" + " start");
	
	$http.get("/SessionChecker")
	.then(function(response) {
		if(response.data.status){
			if(response.data.type == "user"){
				window.location.href = "#Company";	
				
			}		
			else if(response.data.type == "admin")
				window.location.href = "#Admin";		
			
		} else if(!response.data.status){
		}
	}, function() {
		alert("An error has occured");
	});
	
	/*gets the data inputted in html file*/
	$scope.login = function(username, password) {
		console.log("login.adminLogin" + " start");
		// Getting the elements to be copied into
		var jsonObj = {
				username:username,
				password:password,
		}
		if(username.trim() != "" && password.trim()  != ""){
			$http.post("/AdminLogin", jsonObj)
			.then(function(response) {
				if (response.data.status === 0) {
					window.location.href = "#Admin";
				} else if(response.data.status === 1){
					alert("Email does not exist");
				} else if(response.data.status === 2){
					alert("Password is incorrect");
				}
			}, function() {
				alert("Server error");
			});
		} else {
			alert("Input fields");
		}
	};

	console.log("AdminLogin.adminLogin" + " end");
});