/*
* This file contains the backend process for the web page.
* The holds the values/properties that is common to the application.
* @author Jireh Bautista
 * @version 0.01
 * Version History
 * [09/07/2016] 0.01 � Jireh Bautista � Initial codes.
 * [10/03/2016] 0.02 - Jireh Bautista - removed routing; created tabs
 */

/**
 * The angular module object.
 * @param adminApp - the application name (refer to the 'ng-app' directive)
 */

/**
 * This controller serves as the holder for the all the other controllers
 * defined in the application.
 * Setting the properties, and methods of the angular controller.
 * The properties and methods inside this controller can be accessed using the '$parent'.
 * @param adminController - the controller name 
 * 			(refer to the 'ng-controller' directive)
 * @param function() - the services that will be used in this controller.
 */	

app.controller('adminController', function($scope, $http) {
	console.log("admin.adminController" + "start");
	
	$scope.tabs = [{
        title: 'Companies',
        url: '/html/adminCompany.html',
        controller: 'adminCompanyController'
    }, {
        title: 'Requests',
        url: '/html/adminRequest.html'
    }, {
        title: 'Contracts',
        url: '/html/adminContract.html'
	}];
	
	//avila
	$http.get("/SessionChecker")
	.then(function(response) {
		if(response.data.status){
			if(response.data.type == "user")
				window.location.href = "#Company";
			
		} else if(!response.data.status){
			window.location.href = "#AdminLogin";
		}
	}, function() {
		//alert("no session");
	});
	
	$scope.logout = function(){
		$http.get("/SessionInvalidate")
		.then(function() {
			window.location.href="#AdminLogin"
		}, function(){
			
		});
	}
	//avila
	
	$scope.currentTabHtml = '/html/adminCompany.html';
	$scope.currentTab = "Companies";
	
	$scope.onClickTab = function (tab) {
	    $scope.currentTabHtml = tab.url;
	    $scope.currentTab = tab.title;
	}
	
	$scope.isActiveTab = function(tabUrl) {
	    return tabUrl == $scope.currentTabHtml;
	}
	
	console.log("admin.adminController" + "end");
});
