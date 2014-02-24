'use strict';

/* App Module */

var featureWebApp = angular.module('featureWebApp', [ 'ngRoute',
		'featureServices', 'breadcrumbServices', 'featureControllers', ]);

/*
 * featureWebApp.controller('FeatureCtrl',function($scope){ $scope.features = [
 * {'name':'Product.feature','id':'1234'} ]; });
 */

featureWebApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/features/group/:groupId/:name', {
		templateUrl : 'partials/feature-list.html',
		controller : 'FeatureGroupDetailCtrl'
	}).when('/features/feature/:featureId/:name', {
		templateUrl : 'partials/feature-detail.html',
		controller : 'FeatureDetailCtrl'
	}).when('/features/group', {
		templateUrl : 'partials/feature-list.html',
		controller : 'FeatureRootDetailCtrl'
	}).otherwise({
		redirectTo : '/features/group'
	});
} ]);

/*
 * 
 * when('/features/feature?searchText=:text', { templateUrl:
 * 'partials/feature-detail.html', controller: 'FeatureSearchResultsCtrl' }).
 */