app.controller('registerController', function($scope, $filter, $http) {
	console.log("CompanyRegister.RegisterController " + "start");
	 //AVILA//
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
	
	function removeSpaces(val) {
	   return val.split(' ').join('');   
	}
	
	
	$scope.initRegisterScreen = function(){
		$scope.companyName = null;
		$scope.companyAddress = null;
		$scope.companyTelephone = null;
		$scope.companyUrl = null;
		$scope.companyEmail = null;
		$scope.companyPassword = null;
	}
	
	$scope.submitCompany = function() {
		console.log("Company.submitCompany" + " start");
		// Getting the elements to be copied into
		var company = {
				companyName: $scope.companyName,
				address: $scope.companyAddress,
				telephoneNumber: $scope.companyTelephone,
				companyUrl: $scope.companyUrl,
				email: $scope.companyEmail,
				password: $scope.companyPassword
		}
		 //AVILA//
		if($scope.validateFields()){
			$http.post("/RegisterCompany", company)
			.then(function(response) {
				
				if (response.data.errorList.length == 0) {
					//There were no errors.
					if(response.data.flag == 0){
						alert("Registering company was successful.");
						$scope.$parent.companyNameLogin = $scope.companyName;
						$scope.$parent.companyIdLogin = response.data.companyId;
						$scope.$parent.companyAddress =  $scope.companyAddress;
						$scope.$parent.companyTelephone = $scope.companyTelephone;
						$scope.$parent.companyUrl =  $scope.companyUrl;
						
						window.location.href = "#Company";
					} else if(response.data.flag == 1) {
						alert("Company name already existed.");
					} else if(response.data.flag == 2) {
						alert("Email already existed.");
					}
				} else {
					// display the error messages.
					var errorMessage = "";
					for (var i = 0; i < response.data.errorList.length; i++) {
						errorMessage += response.data.errorList[i];
					}
					alert(errorMessage);
				}
			}, function() {
				alert("An error has occured");
			});
		}
		
		
		console.log("Company.submitCompany" + " end");
	}
	
	$scope.validateFields= function(){
		console.log("validateCompany" + "  start");
		var valid = true;
		var name = $scope.companyName;
		var address = $scope.companyAddress;
		var url = $scope.companyUrl;
		var email = $scope.companyEmail;
		var password = $scope.companyPassword;
		var tel = $scope.companyTelephone;
		
		//company name validation
		if (removeSpaces(name) != "") {
			if(removeSpaces(address) != ""){
				if(removeSpaces(tel) != ""){
					//telephone number format validation
					var pattern1 = /^([0-9]{3}[\-]?[0-9]{4})$/;
					if(pattern1.test(tel) == false ){
						alert("Invalid format for telephone number");
						valid = false;
					}
					else if(removeSpaces(url) != ""){
						if(removeSpaces(email) != ""){
							//email format validation
							var pattern = /^([A-Za-z0-9_\-\,]{1,})\@([A-Za-z0-9_\-\.]{1,})\.([A-Za-z]{2,4})$/;
							if(pattern.test(email) == false){
								alert("Invalid format for email address");
								valid = false;
							} 
							else if(removeSpaces(password) != ""){
								//password format validation
								var pattern2 = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{1,16}$/;
								if(pattern2.test(password) == false){
									alert("Invalid format for password");
									valid = false;
								}
							} else {
								alert("Password field is empty.")
								valid = false;
							}
						} else {
							alert("Email field is empty.")
							valid = false;
						}
					} else {
						alert("Company URL field is empty.")
						valid = false;
					}
				} else {
					alert("Telephone # field is empty.")
					valid = false;
				}
			} else {
				alert("Address field is empty.")
				valid = false;
			}
		} else {
			alert("Company name field is empty.");
			valid = false;
		}		
		
		console.log("validateCompany" + "  end");
		return valid;	
	}
});