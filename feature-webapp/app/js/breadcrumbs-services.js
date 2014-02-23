'use strict';

var breadcrumbServices = angular.module('breadcrumbServices',[]);

breadcrumbServices.factory('Breadcrumbs', [ '$rootScope', '$location',
		function($rootScope, $location) {

			var breadcrumbs = [];
			var breadcrumbsService = {};

			breadcrumbsService.add = function(name, path) {
				breadcrumbs.push({
					name : name,
					path : path
				});
			}
			breadcrumbsService.getAll = function() {
				return breadcrumbs;
			};

			breadcrumbsService.getFirst = function() {
				return breadcrumbs[0] || {};
			};

			return breadcrumbsService;
		} ]);
