'use strict';

/* Services */

var featureServices = angular.module('featureServices', [ 'ngResource' ]);

featureServices.factory('GroupService', [ '$resource', function($resource) {
	return $resource('api/features/group/:groupId', {}, {
		query : {
			method : 'GET',
			params : {
				groupId : 'root'
			},
			isArray : false
		}
	});
} ]);

featureServices.factory('FeatureService', [ '$resource', function($resource) {
	return $resource('api/features/feature/:featureId', {}, {
		query : {
			method : 'GET',
			params : {
				featureId : '@id'
			},
			isArray : true
		}
	});
} ]);

featureServices.factory('FeatureSearchService', [ '$resource', function($resource) {
	return $resource('api/features/feature?text=:text', {}, {
		search : {
			method : 'GET',
			params : {
				text : '@text'
			},
			isArray : true
		}
	});
} ]);



/*
featureServices.factory('Feature', [ '$resource', function($resource) {
	return $resource('api/features', {}, {});
} ]);
*/