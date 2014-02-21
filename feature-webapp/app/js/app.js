'use strict';

/* App Module */

var featureWebApp = angular.module('featureWebApp', [
  'ngRoute',
  'featureControllers',
  'featureServices'
]);

featureWebApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
    when('/features', {
        templateUrl: 'partials/feature-list.html',
        controller: 'FeatureListCtrl'
      }).
      when('/features/directory/:directoryId', {
        templateUrl: 'partials/feature-list.html',
        controller: 'FeatureListCtrl'
      }).
 /*     when('/features/feature/:featureId', {
        templateUrl: 'partials/feature-detail.html',
        controller: 'FeatureDetailCtrl'
      }).
      when('/features/feature?searchText=:text', {
          templateUrl: 'partials/feature-detail.html',
          controller: 'FeatureSearchResultsCtrl'
        }).
 */     otherwise({
        redirectTo: 'partials/feature-list.html'
      });
  }]);