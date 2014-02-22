'use strict';

/* App Module */

var featureWebApp = angular.module('featureWebApp', [
  'ngRoute',
  'featureControllers',
 'featureServices'
]);

/*
 featureWebApp.controller('FeatureCtrl',function($scope){
	$scope.features = [
	                  {'name':'Product.feature','id':'1234'} ];
});
*/

featureWebApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
    when('/features/group', {
        templateUrl: 'partials/feature-list.html',
        controller: 'FeatureRootDetailCtrl'
      }).
      when('/features/group/:groupId', {
          templateUrl: 'partials/feature-list.html',
          controller: 'FeatureGroupDetailCtrl'
        }).
      otherwise({
        redirectTo: '/features/group'
      });
  }]);

 
  
/*     
    when('/features/feature/:featureId', {
templateUrl: 'partials/feature-detail.html',
controller: 'FeatureDetailCtrl'
}).
when('/features/feature?searchText=:text', {
  templateUrl: 'partials/feature-detail.html',
  controller: 'FeatureSearchResultsCtrl'
}).
*/