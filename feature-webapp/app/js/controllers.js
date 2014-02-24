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
			var features = FeatureService.query({
				featureId : $routeParams.featureId,
			}, function(features) {
				var pathString = "#features/feature/" + features[0].id
						+ "/" + $routeParams.name;
				Breadcrumbs.add($routeParams.name, pathString);
				$scope.breadcrumbs = Breadcrumbs;
				$scope.feature = features[0];
			});

		} ]);
