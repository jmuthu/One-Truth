'use strict';

/* Controllers */

var featureControllers = angular.module('featureControllers', []);

featureControllers.controller('FeatureListCtrl', ['$scope', 'Feature',
  function($scope, Feature) {
    $scope.features = Feature.query();
    }]);

featureSControllers.controller('FeatureDetailCtrl', ['$scope', '$routeParams', 'Feature',
  function($scope, $routeParams, Feature) {
    $scope.feature = Feature.get({featureId: $routeParams.featureId}, function(feature) {
    });
    
  }]);