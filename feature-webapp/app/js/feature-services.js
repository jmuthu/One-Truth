'use strict';

/* Services */

var featureServices = angular.module('featureServices', [ 'ngResource' ]);

featureServices.factory('GroupService', [ '$resource', function($resource) {
	return $resource('api/group?path=:path', {}, {
		query : {
			method : 'GET',
			params : {
				path : '@path'
			},
			isArray : true
		}
	});
} ]);

featureServices.factory('FeatureService', [ '$resource', function($resource) {
	return $resource('api/feature/:featureId', {}, {
		query : {
			method : 'GET',
			params : {
				featureId : '@id'
			},
			isArray : true
		}
	});
} ]);

featureServices.factory('SearchService', [ '$resource',
		function($resource) {
			return $resource('api/search?text=:text', {}, {
				search : {
					method : 'GET',
					params : {
						text : '@text'
					},
					isArray : true
				}
			});
		} ]);