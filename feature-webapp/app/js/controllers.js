'use strict';

/* Controllers */

var oneTruthController = angular.module('oneTruthController', [
		'featureServices', 'breadcrumbServices' ]);

oneTruthController.controller('GroupDetailCtrl', [ '$scope',
		'$routeParams', 'GroupService', 'Breadcrumbs',
		function($scope, $routeParams, GroupService, Breadcrumbs) {
			$scope.group = GroupService.get({
				path : $routeParams.path,
			}, function(group) {
			});

		} ]);

oneTruthController.controller('FeatureDetailCtrl', [ '$scope', '$routeParams',
		'FeatureService', 'Breadcrumbs',
		function($scope, $routeParams, FeatureService, Breadcrumbs) {
			var features = FeatureService.query({
				path : $routeParams.path,
			}, function(features) {
				$scope.features = features;
			});

		} ]);

oneTruthController.controller('SearchResultsCtrl', [ '$scope',
		'$routeParams', 'SearchService',
		function($scope, $routeParams, SearchService) {
			var searchResults = SearchService.search({
				text : $routeParams.text,
			}, function(searchResults) {
				$scope.searchResults = searchResults;
			});

		} ]);

oneTruthController.controller('SearchClickCtrl', [ '$scope', '$location',
		function($scope, $location) {
			$scope.submit = function() {
				$location.path("/search/" + $scope.text);
			}

		} ]);