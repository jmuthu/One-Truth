'use strict';

/* Controllers */

var featureControllers = angular.module('featureControllers', []);

/*
 * featureControllers.controller('FeatureListCtrl', [ '$scope', function($scope) {
 * $scope.features = [ { 'name' : 'Product.feature', 'id' : '1234' } ]; } ]);
 * 
 */
featureControllers.controller('FeatureRootDetailCtrl', [ '$scope', 'Feature',
		function($scope, Feature) {
			$scope.group = Feature.query();
		} ]);

featureControllers.controller('FeatureGroupDetailCtrl', [ '$scope', '$routeParams',
		'Feature', function($scope, $routeParams, Feature) {
			$scope.group = Feature.get({
				groupId : $routeParams.groupId
			}, function(Feature) {
			});

		} ]);
