/** ------------------------------------------------------------------------------
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Copyright (C) Rococo Global Technologies, Inc - All Rights Reserved 2016
 * --------------------------------------------------------------------------- **/
 
/**
* This file contains the functionalities for the 'Company Profile' screen.
* @author Renuel Avila
* @version 0.01
* Version History
* [07/20/2016] 0.01 � Renuel Avila � initial codes
* [09/07/2016] 0.02 � Renuel Avila � Angular JS codes
**/

//****** ANGULAR JS  ******//
angular.module('filters.stringUtils', [])

app.filter('removeSpaces', function() {
    return function(string) {
        if (!angular.isString(string)) {
            return string;
        }
        return string.replace(/[\s]/g, '');
    };
})

app.controller('companyProfileController', function($scope, $filter, $http, $location) {
	console.log("CompanyProfile.ProfileController " + "start");
	
	$http.get("/SessionChecker")
	.then(function(response) {
		// if the response is success
		// Getting the response data.
		if(response.data.status){
			
			if(response.data.type == "admin")
				window.location.href = "#Admin";	
			
			$scope.$parent.companyNameLogin = response.data.companyName;
			$scope.$parent.companyIdLogin = response.data.companyId;
			$scope.$parent.companyAddress = response.data.companyAddress;
			$scope.$parent.companyTelephone = response.data.companyTelephone;
			$scope.$parent.companyUrl = response.data.companyUrl;
			
			$scope.companyName = response.data.companyName;
			$scope.companyAddress = response.data.companyAddress;
			$scope.companyTelephone = response.data.companyTelephone;
			$scope.companyUrl = response.data.companyUrl;
		} else if(!response.data.status){
			window.location.href = "#Login";
		}
	}, function() {
		alert("An error has occured");
	});
		
	$scope.logout = function(){
		$http.get("/SessionInvalidate")
		.then(function() {
			window.location.href="#Login"
		}, function(){
			
		});
	}
	
	
	
	/**
	*This function is used to checks if the input fields are empty.
	**/
	function validateInputFields(){
		console.log("validateInputFields" + "  start");
		//checks if the input fields are empty
		if($filter('removeSpaces')($scope.companyName) != ""){
			if($filter('removeSpaces')($scope.companyAddress) != ""){
				if($filter('removeSpaces')($scope.companyTelephone) != ""){
					var pattern1 = /^([0-9]{3}[\-]?[0-9]{4})$/;
					if(pattern1.test($scope.companyTelephone) == false ){
						alert("Invalid format for telephone number");
						return false;
					}
					else if($filter('removeSpaces')($scope.companyUrl) != ""){
						return true;
					} else {
						alert("Company URL field is empty");
						return false;
					}
				} else {
					alert("Telephone # field is empty");
					return false;
				}
			} else {
				alert("Company address field is empty");
				return false;
			}
		} else {
			alert("Company name field is empty");
			return false;
		}
	}
	
	$scope.clearAllFields = function() {
		$scope.companyName = "";
		$scope.companyAddress = "";
	    $scope.companyTelephone = "";
	    $scope.companyUrl = "";
	}
	

	$scope.UpdateProfile = function(){
		console.log("CompanyProfile.ProfileController.UpdateProfile " + "start");
		
		if(validateInputFields()){
			var confirmation = window.confirm("Are you sure you want to update?");
			if (true == confirmation) {		
				var companyProfile = {
						id: $scope.$parent.companyIdLogin,
						companyName: $scope.companyName,
						address: $scope.companyAddress,
						telNumber: $scope.companyTelephone,
						companyUrl: $scope.companyUrl
				};
	
				$http.post("/UpdateCompanyProfile", companyProfile)
				.then(function(response) {
					if (response.data.status == 0) {
						//There were no errors.
						$scope.$parent.companyNameLogin = $scope.companyName;
						$scope.$parent.companyAddress = $scope.companyAddress;
						$scope.$parent.companyTelephone = $scope.companyTelephone;
						$scope.$parent.companyUrl = $scope.companyUrl;
						$scope.clearAllFields();
						window.location.href="#Company";
						alert("Successfully Updated Company's profile");
						
						//window.location.href ="#Company";
					} else if(response.data.status == 1) {
						// display the error messages.
						alert("New company name already exists");
					}
				}, function() {
					alert("An error has occured");
				});
			}
		}
	
		console.log("CompanyProfile.ProfileController.UpdateProfile " + "end");
	}
	
});

