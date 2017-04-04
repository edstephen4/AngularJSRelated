/*
* This file contains the backend process for the 'Admin' screen
* @author Jireh Bautista
 * @version 0.01
 * Version History
 * [09/07/2016] 0.01 - Jireh Bautista  - Initial codes.  
 * [09/29/2016] 0.02 - Jireh Bautista - Added init function
 * [10/03/2016] 0.03 - Jireh Bautista - Added custom filters
 */

app.controller('adminContractController', function($scope, $http, $sce) {
	console.log("adminContract.adminContractController " + "start");
	
	$scope.jobRequests;
	$scope.contracts;
	$scope.statuses = [ "New", "In-progress", "Closed"];
	
	$scope.underContracts = [];
	
	$scope.init = function(){
		$http.get("/ListContract")
		.then(function(response) {
			if (response.data.errorList.length == 0) {
				//There were no errors.
				// passing the json data from the response to the personList
				$scope.contracts = response.data.contractList;
				console.log(response.data.contractList);
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
		
		$scope.deleteContract = function (contract){
			console.log("ni sud dinhi.");
			var confirmation = window.confirm("Are you sure you want to delete the Contract?");
			if(confirmation){
				$http.post("/DeleteContract", contract)
				.then(function(response) {
					
					if (response.data.errorList.length == 0) {
						//There were no errors.
						$scope.contracts.splice($scope.contracts.indexOf(contract),1);
						$scope.init();
						$scope.checkJobRequestStatus();
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
	}
	
	$scope.checkJobRequestStatus = function(){
		console.log($scope.jobRequests.length);
		var today = new Date();
		$scope.contractOnly = [];
		$scope.closedStatus = [];
		$scope.inProgressStatus = [];
		$scope.newStatus = [];
		for(var y = 0; y < $scope.contracts.length; y++){
			$scope.contractOnly.push($scope.contracts[y].contractNumber);
		}
		console.log(today);
		for(var i = 0; i < $scope.jobRequests.length; i++){
			if($scope.jobRequests[i].status != "Closed"){
				var jobReqContract = $scope.jobRequests[i].contracts;
				console.log(jobReqContract);
				var z = 2;
				var count = 0;
				for(var x = 0; x < jobReqContract.length; x++){
					var index = $scope.contractOnly.indexOf(jobReqContract[x]);
					console.log(index);
					if(index != -1){
						if($scope.contracts[index].status == "In Progress" && z != 0){
							z = 0;
							break;
						}
						else if($scope.contracts[index].status == "Closed"){
							count++;
						}
					}
				}
				if(count == jobReqContract.length && count != 0){
					$scope.closedStatus.push({jobRequestNumber: $scope.jobRequests[i].jobRequestNumber});
				}
				else if(z == 0){
					$scope.inProgressStatus.push({jobRequestNumber: $scope.jobRequests[i].jobRequestNumber});
				}
				else {
					$scope.newStatus.push({jobRequestNumber: $scope.jobRequests[i].jobRequestNumber});
				}
			}
		}
		console.log($scope.closedStatus);
		console.log($scope.inProgressStatus);
		console.log($scope.newStatus);
		var updateCompanyJobRequest = {
				updateClosedJobRequestStatus: $scope.closedStatus,
				updateInProgressJobRequestStatus: $scope.inProgressStatus,
				updateNewJobRequestStatus: $scope.newStatus,
				closedDate: convert(today+"")
		}
		
		var res = $http.post('/UpdateJobRequestStatus', updateCompanyJobRequest);
		res.success(function(data, status, headers, config) {
			
		});
		res.error(function(data, status, headers, config) {
			
		});	
		
	}
	
	function convert(str) {
	    var date = new Date(str),
	        mnth = ("0" + (date.getMonth()+1)).slice(-2),
	        day  = ("0" + date.getDate()).slice(-2);
	    return [ date.getFullYear(), mnth, day ].join("-");
	}
	
	
	$scope.updateContractStatus = function(contract, contractStatus){
		contract.status = contractStatus;
		var date = new Date();
		contract.closedDate = convert(date+"");
		var confirmation = window.confirm("Are you sure you want to update the Contract status?");
		if(confirmation){
			$http.post("/UpdateCompanyContractStatus", contract)
			.then(function(response) {	
				
				if (response.data.errorList.length == 0) {
					//There were no errors.
					$scope.init();
					$scope.checkJobRequestStatus();
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
	
	$scope.highlight = function(text, search) {
	    if (!search) {
	        return $sce.trustAsHtml(text);
	    }
	    return $sce.trustAsHtml(text.replace(new RegExp(search, 'gi'), '<span class="highlight">$&</span>'));
	};
	
	var flag = 0;
	
	
	
	$scope.listContractsUnderRequest = function(request){
		$scope.underContracts = [];
		if(request != null){
			for(var i = 0; i < $scope.contracts.length; i++){
				if(request.contracts.indexOf($scope.contracts[i].contractNumber) != -1)
					$scope.underContracts.push($scope.contracts[i]);
			}
		}
	}
	
	console.log("adminContract.adminContractController " + "end");
});

app.filter('custom', function () {
    return function (data, search) {
    	if(angular.isArray(data)){
    		var output = [];
        	for(var i = 0; i < data.length; i++){
        		if(data[i].contractNumber != undefined){
        			if(data[i].contractNumber.toLowerCase().indexOf(search.toLowerCase()) != -1 || data[i].status.toLowerCase().indexOf(search.toLowerCase()) != -1)
        				output.push(data[i]);
        		}
        	}
        	return output;
    	}else
    		return data;
    };
});
