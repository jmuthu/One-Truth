'use strict';

/* App Module */

var oneTruthWebApp = angular.module('oneTruthWebApp', [ 'ngRoute',
		'featureServices', 'breadcrumbServices', 'oneTruthController' ]);

oneTruthWebApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/group/:path*', {
		templateUrl : 'partials/feature-list.html',
		controller : 'GroupDetailCtrl'
	}).when('/feature/:path*', {
		templateUrl : 'partials/feature-detail.html',
		controller : 'FeatureDetailCtrl'
	}).when('/search/:text', {
		templateUrl : 'partials/feature-search-results.html',
		controller : 'SearchResultsCtrl'
	}).otherwise({
		redirectTo : '/group/OneBill'
	});
} ]);

