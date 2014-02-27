'use strict';

/* Services */

var featureServices = angular.module('featureServices', [ 'ngResource' ]);

featureServices.factory('GroupService', [ '$resource', function($resource) {
	return $resource('api/group', {}, {	});
} ]);

featureServices.factory('FeatureService', [ '$resource', function($resource) {
	return $resource('api/feature', {}, {});
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