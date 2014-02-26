'use strict';

var breadcrumbServices = angular.module('breadcrumbServices', []);

breadcrumbServices.factory('Breadcrumbs', [ '$rootScope', '$location',
		function($rootScope, $location) {
			var breadcrumbs = [];
			var breadcrumbsService = {};

			$rootScope.$on('$routeChangeSuccess', function(event, current) {

				var pathElements = $location.path().split('/'), result = [], i;
				var breadcrumbPath = function(index) {
					return '/' + (pathElements.slice(0, index + 1)).join('/');
				};

				pathElements.shift();
				for (i = 1; i < pathElements.length; i++) {
					result.push({
						name : pathElements[i],
						path : breadcrumbPath(i)
					});
				}

				breadcrumbs = result;
			});

			breadcrumbsService.getAll = function() {
				return breadcrumbs;
			};

			breadcrumbsService.getFirst = function() {
				return breadcrumbs[0] || {};
			};

			return breadcrumbsService;
		} ]);
