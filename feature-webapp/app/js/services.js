'use strict';

/* Services */

var featureServices = angular.module('featureServices', [ 'ngResource' ]);
/*
featureServices.factory('Feature', [ '$resource', function($resource) {
	return $resource('api/features/feature/:featureId', {}, {
		query : {
			method : 'GET',
			params : {
				featureId : 'Feature'
			},
			isArray : true
		}
	});
} ]);
*/
featureServices.factory('Feature', [ '$resource', function($resource) {
	return $resource('api/features/group/:groupId', {}, {
		query : {
			method : 'GET',
			params : {
				groupId : 'root'
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