/**
* This file contains the functionalities for the 'Register Dish' screen.
* @author Jireh Bautista
* @version 0.01
* Version History
* [07/20/2016] 0.01 – Jireh Bautista – initial codes
* [08/31/2016] 0.02 "Jessie May Cantil"
**/

app.controller('loginController', function($scope, $http, $httpParamSerializer) {
	console.log("jobOCean.loginController " + "start");
	
	// checking session in login
	$http.get("/SessionChecker")
	.then(function(response) {
		if(response.data.status){
			if(response.data.type == "user"){
				window.location.href = "#Company";	
				
			}		
			else if(response.data.type == "admin")
				window.location.href = "#Admin";		
			
		} else if(!response.data.status){
			//alert("no session");
		}
	}, function() {
		alert("An error has occured");
	});
	
	/*gets the data inputted in html file*/
	$scope.loginCompany = function() {
		console.log("login.loginCompany" + " start");
		// Getting the elements to be copied into
		if($scope.username == null && $scope.password == null){
			alert("Input username and password.");
		}
		else if($scope.username == null){
			alert("Input username.");
		}
		else if($scope.password == null){
			alert("Input password.");
		}
		else{
			var username = $scope.username;
			var password = $scope.password;
			
			var companyAccount = {
					username: $scope.username,
					password: $scope.password
			}
			
			$http.post("/Login", companyAccount)
			.then(function(response) {
				if (response.data.loginStatus === 0) {
					//There were no errors.
					$scope.companyName = response.data.companyName;
					$scope.companyId = response.data.companyId;
					console.log($scope.companyId);
					if($scope.companyName != null && $scope.companyId != null){
						$scope.$parent.companyNameLogin = $scope.companyName;
						$scope.$parent.companyIdLogin = $scope.companyId;
						$scope.$parent.companyAddress = response.data.companyAddress;
						$scope.$parent.companyTelephone = response.data.companyTelephone;
						$scope.$parent.companyUrl = response.data.companyUrl;
						window.location.href = "#Company";
					}
					else{
						alert("Username or Password is empty");
					}
				} else if(response.data.loginStatus === 1){
					// email does not exist
					alert("Email does not exist");
				} else if(response.data.loginStatus === 2){
					alert("Password is incorrect");
				}
			}, function() {
				alert("An error has occured");
			});
		}
	}

	console.log("login.loginCompany" + " end");
});