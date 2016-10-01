
var fomoApp = angular
    .module('FomoApllication', ['ngRoute', 'FomoSecurity','FomoControllers']);
fomoApp
		.config(
				function($routeProvider, $httpProvider,USER_ROLES) {

					$routeProvider.when('/home', {
						templateUrl : 'views/home.html',
						controller : 'home',
						controllerAs : 'controller',
						access:{
							loginRequired: true,
				            authorizedRoles: [USER_ROLES.all]
						}
					}).when('/login', {
						templateUrl : 'views/login.html',
						controller : 'LoginController',
						controllerAs : 'controller'
					}).when('/error/404',{
						templateUrl : 'error/404.html'
					})
					.otherwise({
						redirectTo : '/error/404'
					});
					$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
				}).run(function($rootScope, AuthSharedService, USER_ROLES,$location,Session,$timeout,$location){
					$rootScope.$on('$routeChangeStart',function(event,next){
						if(next.originalPath=="/login" && $rootScope.authenticated){
							event.preventDefault();
						}else if(next.access && next.access.loginRequired && !$rootScope.authenticated){
							event.preventDefault();
							$rootScope.$broadcast("event:auth-loginRequired", {});
						}else if(next.access && !AuthSharedService.isAuthorized(next.access.authorizedRoles)){
							event.preventDefault();
							$rootScope.$broadcast("event:auth-forbidden", {});
						}
					});
					
					$rootScope.$on('event:auth-loginConfirmed',function(event,data){
						$rootScope.loadingAccount=false;
						var nextLocation = ($rootScope.requestedUrl? $rootScope.requestedUrl:'/home');
						var delay = ($location.path() === "/loading"? 1500:0);
						
						$timeout(function(){
							Session.loginUser(data);
							$rootScope.account= Session;
							$rootScope.authenticated = true;
							$location.path(nextLocation).replace();
						},delay);
					});
					$rootScope.$on('event:auth-loginRequired',function(event, data){
						if($rootScope.loadingAccount && data.status !== 401){
							$rootScope.requestedUrl = $location.path();
							$location.path('/loading');
						}else{
							Session.invalidate();
							$rootScope.authenticated = false;
							$rootScope.loadingAccount = false;
							$location.path('/login');
						}
					});
					
					AuthSharedService.getAccount();
				})
		