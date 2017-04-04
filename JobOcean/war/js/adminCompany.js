/*
* This file contains the backend process for the 'Admin' screen
* @author Jireh Bautista
 * @version 0.01
 * Version History
 * [09/07/2016] 0.01 - Jireh Bautista  - Initial codes.  
 * [09/21/2016] 0.02 - Jireh Bautista - Added highlight filter
 * [09/29/2016] 0.03 - Jireh Bautista - Added init function
 */

app.controller('adminCompanyController', function($http, $scope, $sce) {
	console.log("adminCompany.adminCompanyController " + "start");
	
	$scope.companies;
	
	$scope.init = function(){
		$http.get("/ListCompany")
		.then(function(response) {
			if (response.data.errorList.length == 0) {
				//There were no errors.
				// passing the json data from the response to the personList
				$scope.companies = response.data.companyList;
				console.log(response.data.companyList);
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
	
	$scope.highlight = function(text, search) {
	    if (!search) {
	        return $sce.trustAsHtml(text);
	    }
	    return $sce.trustAsHtml(text.replace(new RegExp(search, 'gi'), '<span class="highlight">$&</span>'));
	};
	
	console.log("adminCompany.adminCompanyController " + "end");
});


