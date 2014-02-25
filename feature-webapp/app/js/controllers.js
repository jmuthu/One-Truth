'use strict';

/* Controllers */

var featureControllers = angular.module('featureControllers', [
		'featureServices', 'breadcrumbServices' ]);

featureControllers.controller('FeatureRootDetailCtrl', [ '$scope',
		'GroupService', 'Breadcrumbs',
		function($scope, GroupService, Breadcrumbs) {
			$scope.group = GroupService.query({}, function(group) {
				Breadcrumbs.add(group.name, "#features/group/root");
				$scope.breadcrumbs = Breadcrumbs;
			})

		} ]);

featureControllers.controller('FeatureGroupDetailCtrl', [
		'$scope',
		'$routeParams',
		'GroupService',
		'Breadcrumbs',
		function($scope, $routeParams, GroupService, Breadcrumbs) {
			$scope.group = GroupService.get({
				groupId : $routeParams.groupId,
			}, function(group) {
				var pathString = "#features/group/" + group.id + "/"
						+ $routeParams.name;
				Breadcrumbs.add($routeParams.name, pathString);
				$scope.breadcrumbs = Breadcrumbs;
			});

		} ]);

featureControllers.controller('FeatureDetailCtrl', [
		'$scope',
		'$routeParams',
		'FeatureService',
		'Breadcrumbs',
		function($scope, $routeParams, FeatureService, Breadcrumbs) {
			var feature = FeatureService.query({
				featureId : $routeParams.featureId,
			}, function(feature) {
				var pathString = "#features/feature/" + feature.id + "/"
						+ $routeParams.name;
				Breadcrumbs.add($routeParams.name, pathString);
				$scope.breadcrumbs = Breadcrumbs;
				$scope.feature = feature;
			});

		} ]);

featureControllers.controller('FeatureSearchResultsCtrl', [ '$scope',
		'$routeParams', 'FeatureSearchService',
		function($scope, $routeParams, FeatureSearchService) {
			var searchResults = FeatureSearchService.search({
				text : $routeParams.text,
			}, function(searchResults) {
				$scope.searchResults = searchResults;
			});

		} ]);

featureControllers.controller('searchCtrl', [ '$scope', '$location',
		function($scope, $location) {
			$scope.submit = function() {
				$location.path("/features/search/" + $scope.text);
			}

		} ]);