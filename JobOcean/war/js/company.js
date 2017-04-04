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
* [07/20/2016] 0.01 – Renuel Avila – initial codes
* [09/07/2016] 0.02 – Renuel Avila – Angular JS codes
**/

app.controller('companyController', function($scope, $http, $sce) {
	console.log("jobOcean.companyController " + "start");
	
	$scope.loginCompanyId = $scope.$parent.companyIdLogin;
	$scope.loginCompanyName = $scope.$parent.companyNameLogin;
	
	// checking session in company
	$http.get("/SessionChecker")
	.then(function(response) {

		if(response.data.status){
			if(response.data.type == "admin")
				window.location.href = "#Admin";
			
			$scope.$parent.companyNameLogin = response.data.companyName;
			$scope.$parent.companyIdLogin = response.data.companyId;
			$scope.$parent.companyAddress = response.data.companyAddress;
			$scope.$parent.companyTelephone = response.data.companyTelephone;
			$scope.$parent.companyUrl = response.data.companyUrl;
			
			$scope.loginCompanyName = response.data.companyName;
			$scope.loginCompanyId = response.data.companyId;
			$scope.tabClicked = 'jobRequestTab';
			$scope.listItems();
		} else if(!response.data.status){
			window.location.href = "#Login";
		}
	}, function() {
		alert("An error has occured");
	});
	
	
	// getting all Job Requests and Contracts of the Company
	$scope.listItems = function() {
		
		var company = {
				companyId: $scope.loginCompanyId
		}
		
		$http.post("/ListJobRequestAndContract", company)
		.then(function(response) {
			
			if (response.data.errorList.length == 0) {
				//There were no errors.
				$scope.jobRequests = response.data.jobRequestList;
				$scope.contracts = response.data.contractList;
				console.log($scope.contracts);
				$scope.checkAllContractStatus();
				$scope.checkJobRequestStatus();
			} else {
				// display the error messages.
				var errorMessage = "";
				//window.location.href="#";
				for (var i = 0; i < response.data.errorList.length; i++) {
					errorMessage += response.data.errorList[i];
				}
				//alert(errorMessage);
			}
		}, function() {
			alert("An error has occured");
		});
	}	

	// logout function
	$scope.logout = function(){
		$http.get("/SessionInvalidate")
		.then(function() {
			window.location.href="#Login"
		}, function(){
			
		});
	}
	
	// initializing job request tab view
	$scope.addJobReq = "clicked";
	
	// initializing contracts to null
	$scope.contracts = null;

	// initializing job requests to null
	$scope.jobRequests = null;
	
	// convert date to "YYYY-MM-DD"
	function convert(str) {
	    var date = new Date(str),
	        mnth = ("0" + (date.getMonth()+1)).slice(-2),
	        day  = ("0" + date.getDate()).slice(-2);
	    return [ date.getFullYear(), mnth, day ].join("-");
	}
	
	// checking all status of contracts and job requests
	$scope.checkAllContractStatus = function(){
		console.log($scope.contracts.length);
			if($scope.contracts.length > 0){
			var today = new Date();
	
			$scope.closedStatus = [];
			$scope.inProgressStatus = [];
	
			console.log(today);
			for(var i = 0; i < $scope.contracts.length; i++){
				console.log($scope.contracts);
				if($scope.contracts[i].status != "Closed"){
				var start = new Date($scope.contracts[i].startDate);
				console.log(start);
				var end = new Date($scope.contracts[i].endDate);
				console.log(end);
					if(Number(end.getFullYear()) < Number(today.getFullYear())){
						$scope.closedStatus.push({contractNumber: $scope.contracts[i].contractNumber});
					}
					else if(Number(start.getFullYear()) > Number(today.getFullYear())){
						console.log("New");
					}
					else{
						if(Number(start.getMonth()+1) > Number(today.getMonth()+1)){
							console.log("New");
						}
						else if((Number(end.getMonth()+1) < Number(today.getMonth()+1)) && (Number(end.getFullYear()) <= Number(today.getFullYear()))){
							$scope.closedStatus.push({contractNumber: $scope.contracts[i].contractNumber});
						}
						else{
							if(Number(start.getDate()) < Number(today.getDate())){
								console.log("New");
							}
							else if((Number(end.getDate()) < Number(today.getDate())) && (Number(end.getMonth()+1) <= Number(today.getMonth()+1))){
								$scope.closedStatus.push({contractNumber: $scope.contracts[i].contractNumber});
							}
							else if(Number(start.getDate()) <= Number(today.getDate()) && (Number(end.getDate()) >= Number(today.getDate()))){
								$scope.inProgressStatus.push({contractNumber: $scope.contracts[i].contractNumber});
							}
						}
					}
				}
			}
			
			var updateContract = {
					updateClosedContractStatus: $scope.closedStatus,
					updateInProgressContractStatus: $scope.inProgressStatus
			}
			
			var res = $http.post('/UpdateContractStatus', updateContract);
			res.success(function(data, status, headers, config) {
				
			});
			res.error(function(data, status, headers, config) {
				
			});	
		}
		
	}
	
	// checking all the job requests' status
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
	
	// initializing the Job Request Tab View
	$scope.clickJobRequestTab = function(){
		$scope.listItems();
		$scope.tabClicked = "jobRequestTab";
	}
	
	// initializing the Contract Tab View
	$scope.clickContractTab = function(){
		$scope.listItems();
		$scope.tabClicked = "contractTab";
		// getting all not closed job requests
		$scope.jobRequestsAvailable = function(){
			var jobrequests = []
			for(var i = 0; i < $scope.jobRequests.length; i++){
				if($scope.jobRequests[i].status != "Closed"){
					jobrequests.push($scope.jobRequests[i]);
				}
			}
			
			return jobrequests;
		}
	}
	
	// creating random string for Job Request Number and Contract Number
	$scope.randomString = function(length, chars){
	    var mask = '';
	    if (chars.indexOf('a') > -1) mask += 'abcdefghijklmnopqrstuvwxyz';
	    if (chars.indexOf('A') > -1) mask += 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
	    if (chars.indexOf('#') > -1) mask += '0123456789';
	    var result = '';
	    for (var i = length; i > 0; --i) result += mask[Math.floor(Math.random() * mask.length)];
	    return result;
	}
	
	// initializing the create new contract modal fields
	$scope.initCreateNewContract = function(){
		console.log("createNewJobRequest.createNewJobRequestController.initCustomizeScreen " + "start");
		$scope.contractNumber =  $scope.randomString(11, '#aA');
		$scope.type.typeOfContractSelected = "Billboard";
		$scope.contractorName =  null;
		$scope.startDateContracts =  null;
		$scope.endDateContracts = null;
		$scope.status =  "New";
		console.log("createNewJobRequest.createNewJobRequestController.initCustomizeScreen " + "end");
	}	
	
	$scope.initCreateNewContracts = function(){
		console.log("createNewJobRequest.createNewJobRequestController.initCustomizeScreen " + "start");
		$scope.contractNumber =  $scope.randomString(11, '#aA');
		$scope.type.typeOfContractSelected = "Billboard";
		$scope.contractorName =  null;
		$scope.contractStartDate =  null;
		$scope.contractEndDate = null;
		$scope.status =  "New";
		console.log("createNewJobRequest.createNewJobRequestController.initCustomizeScreen " + "end");
	}
	
	// function that retrieves the information given the jobRequestNumber for editing
	$scope.updateJobRequest = function(jobRequest){
		$scope.listItems();
		$scope.tabClicked = "updateJobRequestTab";
		$scope.jobReqToEdit = jobRequest;
		
		$http.post("/JobRequestToEdit", jobRequest)
		.then(function(response) {
			
			if (response.data.errorList.length == 0) {
				//There were no errors.
				$scope.updateJobRequestNumber = response.data.jobRequestNumber;
				$scope.updateDescription = response.data.description;
				var start = new Date(response.data.startDate);
				var end = new Date(response.data.endDate);
				$scope.updateStartDate = start;
				$scope.updateEndDate = end;
				$scope.contractsOfJobRequest = response.data.contractsOfJobRequest;
				console.log($scope.contractsOfJobRequest);
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

	// initializing the create new jobrequest modal fields
	$scope.initCreateNewJobRequest = function(){
		console.log("createNewJobRequest.createNewJobRequestController.initCustomizeScreen " + "start");
		$scope.jobRequestNumber = $scope.randomString(11, '#aA');
		$scope.description =  "";
		$scope.startDate =  null;
		$scope.endDate = null;
		$scope.status =  "New";
		console.log("createNewJobRequest.createNewJobRequestController.initCustomizeScreen " + "end");
	}
	
	// submitting all the fields of the updated job request
	$scope.submitUpdatedJobRequest = function(){
		console.log("jobRequest.submitJobRequest" + " start");
		
		// Getting the elements to be copied into
		var jobRequest = {
				id: $scope.loginCompanyId,
				jobRequestNumber: $scope.updateJobRequestNumber,
				description: $scope.updateDescription,
				startDate: $scope.updateStartDate,
				endDate: $scope.updateEndDate,
				status: "New"
		}
		
		var res = $http.post('/UpdateJobRequest', jobRequest);
		res.success(function(data, status, headers, config) {
			$scope.listItems();
			$scope.tabClicked = "jobRequestTab";
		});
		res.error(function(data, status, headers, config) {
			alert("Failed!");
		});	
		
		
		console.log("jobRequest.submitJobRequest" + " end");
	}
	
	// function that displays selected job request start date and end date
	$scope.addNewContractFromSelectedJobReq = function(selectedJobRequest){
		if($scope.selectedJobRequest != null){
			$scope.startDateOfContract = selectedJobRequest.startDate;
			$scope.endDateOfContract = selectedJobRequest.endDate;
		}
	}
	
	// function that displays the job request's start date and end date to be edited
	$scope.addContractinUpdateView = function(){
		$scope.startDateOfContract = $scope.jobReqToEdit.startDate;
		$scope.endDateOfContract = $scope.jobReqToEdit.endDate;
	}
	
	// verifying the start date in updating the job request
	$scope.verifyStartDateUpdating = function(){
		if($scope.updateStartDate != null){
			var today = new Date();
			var x = new Date($scope.updateStartDate);
			if(Number(today.getFullYear()) > Number(x.getFullYear())){
				alert("Start Date must not before or on the current date.");
				$scope.updateStartDate = null;
			}
			else if(Number(today.getFullYear()) == Number(x.getFullYear())){
				if(Number(x.getMonth()+1) < Number(today.getMonth()+1)){
					alert("Start Date must not before or on the current date.");
					$scope.updateStartDate = null;
				}
				else{
					if(Number(x.getDate()) < Number(today.getDate())){
						alert("Start Date must not before or on the current date.");
						$scope.updateStartDate = null;
					}
				}
			}
			$scope.updateEndDate = null;
		}
	}

	// verifying the end date in updating the job request
	$scope.verifyEndDateUpdating = function(){
		if($scope.updateEndDate != null){
			var startDate = new Date($scope.updateStartDate);
			var endDate = new Date($scope.updateEndDate);
			if(Number(startDate.getFullYear()) > Number(endDate.getFullYear())){
				alert("End Date must not before the start date.");
				$scope.endDate = null;
			}
			else if(Number(startDate.getFullYear()) == Number(endDate.getFullYear())){
				if(Number(endDate.getMonth()+1) < Number(startDate.getMonth()+1)){
					alert("End Date must not before the start date.");
					$scope.endDate = null;
				}
				else{
					if(Number(endDate.getDate()) < Number(startDate.getDate())){
						alert("End Date must not before the start date.");
						$scope.endDate = null;
					}
				}
			}
		}
	}
	
	
	// verifying the start date in creating a job request
	$scope.verifyStartDate = function(){
		if($scope.startDate != null){
			var today = new Date();
			var x = new Date($scope.startDate);
			if(Number(today.getFullYear()) > Number(x.getFullYear())){
				alert("Start Date must not before or on the current date.");
				$scope.startDate = null;
				$scope.endDate = null;
			}
			else if(Number(today.getFullYear()) == Number(x.getFullYear())){
				if(Number(x.getMonth()+1) < Number(today.getMonth()+1)){
					alert("Start Date must not before or on the current date.");
					$scope.startDate = null;
					$scope.endDate = null;
				}
				else{
					if(Number(x.getDate()) < Number(today.getDate())){
						alert("Start Date must not before or on the current date.");
						$scope.startDate = null;
						$scope.endDate = null;
					}
				}
			}
		}
	}

	// verifying the end date in creating a job request
	$scope.verifyEndDate = function(){
		if($scope.endDate != null){
			var startDate = new Date($scope.startDate);
			var endDate = new Date($scope.endDate);
			if(Number(startDate.getFullYear()) > Number(endDate.getFullYear())){
				alert("End Date must not before the start date.");
				$scope.endDate = null;
			}
			else if(Number(startDate.getFullYear()) == Number(endDate.getFullYear())){
				if(Number(endDate.getMonth()+1) < Number(startDate.getMonth()+1)){
					alert("End Date must not before the start date.");
					$scope.endDate = null;
				}
				else{
					if(Number(endDate.getDate()) < Number(startDate.getDate())){
						alert("End Date must not before the start date.");
						$scope.endDate = null;
					}
				}
			}
		}
	}

	//verifying the contract's start date during creating a contract
	$scope.verifyStartDateOfContract = function(){

		if($scope.startDateContracts != null){
			var start = new Date($scope.startDateOfContract);
			var end = new Date($scope.endDateOfContract);
			var startUpdated = new Date($scope.startDateContracts);
			
			if(Number(startUpdated.getFullYear()) < Number(start.getFullYear())){
				alert("Start date must not before the job request start date.");
				$scope.startDateContracts = null;
			}
			else if(Number(startUpdated.getFullYear()) == Number(start.getFullYear())){
				if(Number(startUpdated.getMonth()+1) < Number(start.getMonth()+1)){
					alert("Start date must not before the job request start date.");
					$scope.startDateContracts = null;
				}
				else if(Number(startUpdated.getMonth()+1) == Number(start.getMonth()+1)){
					if(Number(startUpdated.getDate()) < Number(start.getDate())){
						alert("Start date must not before the job request start date.");
						$scope.startDateContracts = null;
					}
					else if(Number(startUpdated.getDate()) > Number(end.getDate())){
						alert("Start date must not after the job request end date.");
						$scope.startDateContracts = null;
					} 
				}
				else{
					if(Number(startUpdated.getMonth()+1) > Number(end.getMonth()+1)){
						alert("Start date must not after the job request end date.");
						$scope.startDateContracts = null;
					}
				}
			}
			else if(Number(startUpdated.getFullYear()) > Number(end.getFullYear())){
				alert("Start date must not after the job request end date.");
				$scope.startDateContracts = null;
			}
			$scope.endDateContracts = null;
		}
	}

	//verifying the contract's end date during creating a contract
	$scope.verifyEndDateOfContract = function(){
		if($scope.endDateContracts != null){
			var startUpdated = new Date($scope.startDateContracts);
			var end = new Date($scope.endDateOfContract);
			var endUpdated = new Date($scope.endDateContracts);
			
			if(Number(endUpdated.getFullYear()) > Number(end.getFullYear())){
				alert("End date must not after the job request end date.");
				$scope.endDateContracts = null;
			}
			else if(Number(endUpdated.getFullYear()) == Number(end.getFullYear())){
				if(Number(endUpdated.getMonth()+1) > Number(end.getMonth()+1)){
					alert("End date must not after the job request end date.");
					$scope.endDateContracts = null;
				}
				else if(Number(endUpdated.getMonth()+1) < Number(startUpdated.getMonth()+1)){
					alert("End date must before the start date.");
					$scope.endDateContracts = null;
				}
				else if(Number(endUpdated.getMonth()+1) == Number(end.getMonth()+1)){
					if(Number(endUpdated.getDate()) > Number(end.getDate())){
						alert("End date must not after the job request end date.");
						$scope.endDateContracts = null;
					}
					else if(Number(endUpdated.getDate()) < Number(startUpdated.getDate())){
						alert("End date must before the start date.");
						$scope.endDateContracts = null;
					}
				}
			}
			else if(Number(endUpdated.getFullYear()) < Number(startUpdated.getFullYear())){
				alert("End date must before the start date.");
				$scope.endDateContract = null;
			}
		}
	}
	
	//verifying the contract's start date in during editing the contract
	$scope.verifyStartDateContractUpdateView = function(){
		console.log($scope.jobReqToEdit);
		if($scope.contractStartDate != null){
			var start = new Date($scope.updateStartDate);
			var end = new Date($scope.updateEndDate);
			var startUpdated = new Date($scope.contractStartDate);
			
			if(Number(startUpdated.getFullYear()) < Number(start.getFullYear())){
				alert("Start date must not before the job request start date.");
				$scope.contractStartDate = null;
			}
			else if(Number(startUpdated.getFullYear()) == Number(start.getFullYear())){
				if(Number(startUpdated.getMonth()+1) < Number(start.getMonth()+1)){
					alert("Start date must not before the job request start date.");
					$scope.contractStartDate = null;
				}
				else if(Number(startUpdated.getMonth()+1) == Number(start.getMonth()+1)){
					if(Number(startUpdated.getDate()) < Number(start.getDate())){
						alert("Start date must not before the job request start date.");
						$scope.contractStartDate = null;
					}
					else if(Number(startUpdated.getDate()) > Number(end.getDate())){
						alert("Start date must not after the job request end date.");
						$scope.contractStartDate = null;
					} 
				}
				else{
					if(Number(startUpdated.getMonth()+1) > Number(end.getMonth()+1)){
						alert("Start date must not after the job request end date.");
						$scope.contractStartDate = null;
					}
				}
			}
			else if(Number(startUpdated.getFullYear()) > Number(end.getFullYear())){
				alert("Start date must not after the job request end date.");
				$scope.contractStartDate = null;
			}
			$scope.contractEndDate = null;
		}
	}

	//verifying the contract's end date in during editing the contract
	$scope.verifyEndDateContractUpdateView = function(){
		if($scope.contractEndDate != null){
			var startUpdated = new Date($scope.contractStartDate);
			var end = new Date($scope.updateEndDate);
			var endUpdated = new Date($scope.contractEndDate);
			
			if(Number(endUpdated.getFullYear()) > Number(end.getFullYear())){
				alert("End date must not after the job request end date.");
				$scope.contractEndDate = null;
			}
			else if(Number(endUpdated.getFullYear()) == Number(end.getFullYear())){
				if(Number(endUpdated.getMonth()+1) > Number(end.getMonth()+1)){
					alert("End date must not after the job request end date.");
					$scope.contractEndDate = null;
				}
				else if(Number(endUpdated.getMonth()+1) < Number(startUpdated.getMonth()+1)){
					alert("End date must before the start date.");
					$scope.contractEndDate = null;
				}
				else if(Number(endUpdated.getMonth()+1) == Number(end.getMonth()+1)){
					if(Number(endUpdated.getDate()) > Number(end.getDate())){
						alert("End date must not after the job request end date.");
						$scope.contractEndDate = null;
					}
					else if(Number(endUpdated.getDate()) < Number(startUpdated.getDate())){
						alert("End date must before the start date.");
						$scope.contractEndDate = null;
					}
				}
			}
			else if(Number(endUpdated.getFullYear()) < Number(startUpdated.getFullYear())){
				alert("End date must before the start date.");
				$scope.contractEndDate = null;
			}
		}
	}
	
	// submitting  all the fields of contract in contract tab view
	$scope.submitContract = function(){
		console.log("contract.submitContract" + " start");
		
		// getting the elements to be copied into
		var confirmation = window.confirm("Are you sure you want to add contract?");	
		console.log($scope.selectedJobRequest.jobRequestNumber);
		if(confirmation){
			var contract = {
					jobRequestNumber: $scope.selectedJobRequest.jobRequestNumber,
					contractNumber: $scope.contractNumber,
					type: $scope.type.typeOfContractSelected,
					contractorName: $scope.contractorName,
					startDate: convert($scope.startDateContracts+""),
					endDate: convert($scope.endDateContracts+""),
					status: "New"
			}
			
			var res = $http.post('/RegisterContract', contract);
			res.success(function(data, status, headers, config) {
				alert("Successful");
				$scope.listItems();
				$scope.initCreateNewContract();
				modalContract.style.display = "none";
			});
			res.error(function(data, status, headers, config) {
				alert("Failed!");
			});	
		}
		console.log("contract.submitContract" + " end");
	}
	
	// submitting all the fields for updating a contract
	$scope.submitUpdateContract = function(){
		console.log("contract.submitContract" + " start");
		
		// getting the elements to be copied into
		var confirmation = window.confirm("Are you sure you want to update contract?");	
		if(confirmation){
			var contract = {
					contractNumber: $scope.contractNumber,
					type: $scope.type.typeOfContractSelected,
					contractorName: $scope.contractorName,
					startDate: convert($scope.startDateContract+""),
					endDate: convert($scope.endDateContract+"")
			}
			
			var res = $http.post('/UpdateContract', contract);
			res.success(function(data, status, headers, config) {
				$scope.listItems();
				$scope.initCreateNewContract();
				$scope.tabClicked = "contractTab";
			});
			res.error(function(data, status, headers, config) {
				alert("Failed!");
			});	
		}
		console.log("contract.submitContract" + " end");
	}

	// submit all the fields of newly created contract
	$scope.submitCreatedContract = function(){
		console.log("jobRequest.submitJobRequest" + " start");
		
		// getting the elements to be copied into
		var confirmation = window.confirm("Are you sure you want to add contract?");	
		if(confirmation){
			var contract = {
					jobRequestNumber: $scope.jobReqToEdit.jobRequestNumber,
					contractNumber: $scope.contractNumber,
					type: $scope.type.typeOfContractSelected,
					contractorName: $scope.contractorName,
					startDate: convert($scope.contractStartDate+""),
					endDate: convert($scope.contractEndDate+""),
					status: "New"
			}
			
			var res = $http.post('/RegisterContract', contract);
			res.success(function(data, status, headers, config) {
				alert("Successful!")
				$scope.listItems();
				$scope.initCreateNewContractInUpdateView();
				$scope.updateJobRequest($scope.jobReqToEdit);
				modalAddContract.style.display = "none";
			});
			res.error(function(data, status, headers, config) {
				alert("Failed!");
			});	
		}
		console.log("jobRequest.submitJobRequest" + " end");
	}
	
	// displays the data of the selected contract to be edited
	$scope.updateContract = function(contracts){
		$scope.tabClicked = "updateContract";
		for(var i = 0; i < $scope.jobRequests.length; i++){
			var contractsOfJobRequests = $scope.jobRequests[i].contracts;
			var index = contractsOfJobRequests.indexOf(contracts.contractNumber);
			console.log(index);
			if(index != -1){
				$scope.startDateOfContract = $scope.jobRequests[i].startDate;
				$scope.endDateOfContract = $scope.jobRequests[i].endDate;
			}
		}
		
		$scope.contractNumber = contracts.contractNumber;
		$scope.typeOfContract = contracts.type;
		$scope.contractorName = contracts.contractorName;
		$scope.startDateContract = new Date(contracts.startDate);
		$scope.endDateContract = new Date(contracts.endDate);
	}
	
	// initializing the contract modal fields in update view
	$scope.initCreateNewContractInUpdateView = function(){
		console.log("createNewJobRequest.createNewJobRequestController.initCustomizeScreen " + "start");
		$scope.contractNumber =  $scope.randomString(11, '#aA');
		$scope.type.typeOfContractSelected = "Billboard";
		$scope.contractorName =  null;
		$scope.startDateContract =  null;
		$scope.endDateContract = null;
		$scope.closedDateContract =  null;
		$scope.status =  "New";
		console.log("createNewJobRequest.createNewJobRequestController.initCustomizeScreen " + "end");
	}	
	
	// submitting the job request created to the /RegisterJobRequestController
	$scope.submitJobRequest = function(){
		console.log("jobRequest.submitJobRequest" + " start");
		// Getting the elements to be copied into
		var confirmation = window.confirm("Are you sure you want to add job request?");
		if(confirmation){
			var jobRequest = {
					id: $scope.loginCompanyId,
					jobRequestNumber: $scope.jobRequestNumber,
					description: $scope.description,
					startDate: convert($scope.startDate+""),
					endDate: convert($scope.endDate+""),
					status: "New"
			}
			
			var res = $http.post('/RegisterJobRequest', jobRequest);
			res.success(function(data, status, headers, config) {
				alert("Successful!");
				$scope.listItems();
				$scope.initCreateNewJobRequest();
			    modal.style.display = "none";
			});
			res.error(function(data, status, headers, config) {
				alert("Failed!");
			});	
		}
		console.log($scope.startDate);
		console.log("jobRequest.submitJobRequest" + " end");
	}

	
	// Get the modal to Create New Contract
	var modalContract = document.getElementById('myModalContract');

	// Get the button that opens the modal to Create New Contract
	var btnContract = document.getElementById("addNewContract");

	
	// When the user clicks the button, open the modal that Creates New Contract
	btnContract.onclick = function() {
		modalContract.style.display = "block";
	}
	
	// Get the modal to Create New Contract In Update Job Request View
	var modalAddContract = document.getElementById('myModalAddContract');

	// Get the button that opens the modal to Create New Contract In Update Job Request View
	var btnAddContract = document.getElementById("btnAddContract");

	// When the user clicks the button, open the modal that Creates New Contract
	btnAddContract.onclick = function() {
		modalAddContract.style.display = "block";
	}
	
	// Get the modal to Create New Job Request
	var modal = document.getElementById('myModal');

	// Get the button that opens the modal to Create New Job Request
	var btn = document.getElementById("addNewJobRequest");

	// Get the <span> element that closes the modal after Creating New Job Request
	var span = document.getElementById("close");
	
	var spanmodalContract = document.getElementById("closeContract");
	
	var spanmodalAddContract = document.getElementById("closeAddContract");

	// When the user clicks the button, open the modal that Creates New Job Request
	btn.onclick = function() {
	    modal.style.display = "block";
	}

	// When the user clicks on <span> (x), close the modal after Creating New Job Request
	spanmodalAddContract.onclick = function() {
		modalAddContract.style.display = "none";
	}
	
	span.onclick = function() {
	    modal.style.display = "none";
	}
	
	spanmodalContract.onclick = function() {
		modalContract.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it after Creating New Job Request
	window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	    else if (event.target == modalContract) {
	    	modalContract.style.display = "none";
	    }
	    else if (event.target == modalAddContract) {
	    	modalAddContract.style.display = "none";
	    }
	}
	
	$scope.highlight = function(text, search) {
	    if (!search) {
	        return $sce.trustAsHtml(text);
	    }
	    return $sce.trustAsHtml(text.replace(new RegExp(search, 'gi'), '<span class="highlight">$&</span>'));
	};
	
	console.log("jobOcean.companyController " + "start");
});

app.filter('custom1', function () {
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

app.filter('custom3', function () {
    return function (data, search) {
    	if(angular.isArray(data)){
    		var output = [];
        	for(var i = 0; i < data.length; i++){
        		if(data[i].contractNumber.toLowerCase().indexOf(search.toLowerCase()) != -1 || data[i].status.toLowerCase().indexOf(search.toLowerCase()) != -1)
        			output.push(data[i]);
        	}
        	return output;
    	}else
    		return data;
    };
});

