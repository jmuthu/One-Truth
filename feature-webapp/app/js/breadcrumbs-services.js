'use strict';

var breadcrumbServices = angular.module('breadcrumbServices', []);

breadcrumbServices.factory('Breadcrumbs', [ '$rootScope', '$location',
		function($rootScope, $location) {

			var breadcrumbs = [];
			var breadcrumbsService = {};

			breadcrumbsService.add = function(name, path) {
				var length = breadcrumbs.length;
				var found = 0;
				for (var i = 0;i < length; i++) {
					if (found == 1) {
						breadcrumbs.pop();
					} else if (breadcrumbs[i].path == path) {
						found = 1;
					}
				}
   
				if (found == 0) {
					breadcrumbs.push({
						name : name,
						path : path
					});
				} 
			}
			breadcrumbsService.getAll = function() {
				return breadcrumbs;
			};

			breadcrumbsService.getFirst = function() {
				return breadcrumbs[0] || {};
			};

			return breadcrumbsService;
		} ]);
