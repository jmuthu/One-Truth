'use strict';

/* Services */

var featureServices = angular.module('featureServices', [ 'ngResource' ]);

featureServices.factory('Feature', [ '$resource', function($resource) {
	return $resource('api/features/:featureId', {}, {
		query : {
			method : 'GET',
			params : {
				featureId : 'features'
			},
			isArray : true
		}
	});
} ]);

featureServices.factory('Feature', [ '$resource', function($resource) {
	return $resource('api/features', {}, {},
			isArray : true
		}
	});
} ]);