'use strict';

/* Controllers */

var oneTruthController = angular.module('oneTruthController', [
		'featureServices', 'breadcrumbServices' ]);

oneTruthController.controller('GroupDetailCtrl', [ '$scope', '$routeParams',
		'GroupService', 'Breadcrumbs',
		function($scope, $routeParams, GroupService, Breadcrumbs) {
			$scope.breadcrumbs = Breadcrumbs;
			$scope.group = GroupService.get({
				path : $routeParams.path
			}, {});

		} ]);

oneTruthController.controller('FeatureDetailCtrl', [ '$scope', '$routeParams',
		'FeatureService', 'Breadcrumbs',
		function($scope, $routeParams, FeatureService, Breadcrumbs) {
			$scope.breadcrumbs = Breadcrumbs;
			$scope.features = FeatureService.query({
				path : $routeParams.path
			}, {});

		} ]);

oneTruthController.controller('SearchResultsCtrl', [ '$scope', '$routeParams',
		'SearchService', function($scope, $routeParams, SearchService) {
			$scope.searchResults = SearchService.search({
				text : $routeParams.text
			}, {});

		} ]);

oneTruthController.controller('SearchClickCtrl', [ '$scope', '$location',
		function($scope, $location) {
			$scope.submit = function() {
				$location.path("/search/" + $scope.text);
			}

		} ]);