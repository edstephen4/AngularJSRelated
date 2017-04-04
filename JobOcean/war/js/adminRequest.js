/*
* This file contains the backend process for the 'Admin' screen
* @author Jireh Bautista
 * @version 0.01
 * Version History
 * [09/07/2016] 0.01 - Jireh Bautista  - Initial codes.  
 * [09/21/2016] 0.02 - Jireh Bautista - Added Search
 * [09/29/2016] 0.03 - Jireh Bautista - Get list of requests from datastore
 * [10/03/2016] 0.04 - Jireh Bautista - Added custom filter
 */

app.controller('adminRequestController', function($http, $scope, $sce) {
	console.log("adminRequest.adminRequestController " + "start");
	
	$scope.jobRequests = null;
	$scope.companies = [];
	
	$scope.init = function(){
		$http.get("/ListJobRequest")
		.then(function(response) {
			if (response.data.errorList.length == 0) {
				//There were no errors.
				// passing the json data from the response to the personList
				$scope.jobRequests = response.data.jobRequestList;
				console.log(response.data.jobRequestList);
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
	
	$scope.deleteRequest = function(request){

		var confirmation = window.confirm("Are you sure you want to delete the Job Request?");
		if(confirmation){
			$http.post("/DeleteJobRequest", request)
			.then(function(response) {
				
				if (response.data.errorList.length == 0) {
					//There were no errors.
						$scope.jobRequests.splice($scope.jobRequests.indexOf(request),1);
					
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
	}
	
	$scope.findCompanyNameById = function(companyId){
		var name = " - ";
		for(var i = 0; i < $scope.companies.length; i++){
			if($scope.companies[i].id == companyId){
				name = $scope.companies[i].companyName;
				break;
			}
		}
		return name;
	};
	
	$scope.highlight = function(text, search) {
	    if (!search) {
	        return $sce.trustAsHtml(text);
	    }
	    return $sce.trustAsHtml(text.replace(new RegExp(search, 'gi'), '<span class="highlight">$&</span>'));
	};
	
	console.log("adminRequest.adminRequestController " + "end");
});

app.filter('customRequest', function () {
    return function (data, search) {
    	if(angular.isArray(data)){
    		var output = [];
        	for(var i = 0; i < data.length; i++){
        		if(data[i].jobRequestNumber.toLowerCase().indexOf(search.toLowerCase()) != -1 || data[i].status.toLowerCase().indexOf(search.toLowerCase()) != -1)
        			output.push(data[i]);
        	}
        	return output;
    	}else
    		return data;
    };
});