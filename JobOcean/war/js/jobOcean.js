/* ------------------------------------------------------------------------------
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Copyright (C) Rococo Global Technologies, Inc - All Rights Reserved 2016
 * --------------------------------------------------------------------------- */
 
/*
* This file contains the backend process for the web page.
* The holds the values/properties that is common to the application.
* @author Lehmar Cabrillos
 * @version 0.01
 * Version History
 * [04/13/2016] 0.01 � Lehmar Cabrillos  � Initial codes.
 */

/**
 * The angular module object.
 * @param pizzaTimeApp - the application name (refer to the 'ng-app' directive)
 */
var app = angular.module('jobOceanApp', ['ngRoute']);

/**
* Sets the routing of the screen and controller depending on the url that
* is accessed.
**/
app.config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/CreateCompany', {
        templateUrl: '/html/register.html',
        controller: 'registerController'
      })
	  .when('/Company', {
        templateUrl: '/html/company.html',
        controller: 'companyController'
      })
	  .when('/Company/Profile', {
        templateUrl: '/html/profile.html',
        controller: 'companyProfileController'
      })
	  .when('/AdminLogin', {
        templateUrl: '/html/adminLogin.html',
        controller: 'adminLoginController'
      })
      .when('/Admin', {
        templateUrl: '/html/admin.html',
        controller: 'adminController'
      })
      .otherwise({
		  templateUrl: '../html/login.html',
	      controller: 'loginController'
	    });
  }]);

app.controller('jobOceanController', function($scope) {
	console.log("jobOcean.jobOceanController " + "start");
	
	$scope.jobRequestToEdit = null;
	$scope.companyJobRequest = null;
	$scope.companyContract = null;
	$scope.companyNameLogin = null;
	$scope.companyIdLogin = null;
	$scope.companyAddress = null;
	$scope.companyTelephone = null;
	$scope.companyUrl = null;
	
	$scope.type = {
			typeOfContracts: ["Billboard", "Commercial", "Radio Ad", "Poster"],
			typeOfContractSelected: "Billboard"
	}

	console.log("jobOcean.jobOceanController " + "end");
});


