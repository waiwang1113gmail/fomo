var fomoApp = angular
    .module('FomoApllication', ['ngResource', 'ngRoute', 'http-auth-interceptor']);

fomoApp.service('Session',function(){
	this.loginUser = function(userData){
		this.id=data.id;
		this.login=data.email;
		this.firstName=data.firstName;
		this.lastName=data.lastName;
		this.userRoles = [];
		angular.forEach(data.authorities,function(value, key){
			this.psuh(value.name);
		},this.userRoles);
	}
	this.invalidate = function(){
		this.id=null;
		this.email=null;
		this.firstName=null;
		this.lastName=null;
		this.userRoles =null;
	}
	return this;
}).controller('LoginController',function($rootScope,$scope,AuthSharedService){
	$scope.rememberMe = true;
	$scope.login = function() {
		$rootScope.athenticationError = false;
		AuthSharedService.login($scope.username, $scope.password,$scope.rememberMe);
	}
}).constant('USER_ROLES',{
	all:'*',
	admin: 'ADMIN',
	user: 'USER' 
}).service('AuthSharedService',function($rootScope, $http, $resource, 
	    authService, Session){
	return {
		login:function(username, password, rememberMe){
			var config = {
				params:{
					username:username,
					password:password,
					rememberme: rememberMe
				},
				ignoreAuthModule:'ignoreAuthModule'
			};
			$http.post('authenticate','',config)
				.success(function(data,status,headeers,config){
					authService.loginConfirmed(data);
				}).error(function(data,status,headers,config){
					$rootScope.authenticationError = true;
					Session.invalidate();
				});
		},
		isAuthroized:function(authroizedRoles){
			if(!angular.isArray(authroizedRoles)){
				if(authroizedRoles === '*'){
					return true;
				}
			}
			var isAuthorized = false;
			angular.forEach(authroizedRoles, function(authroizedRole){
				var authroized = (!!Session.login && Session.userRoles.indexOf(authroizedRole) !== -1);
				if(authorized || authorizedRole == '*'){
					isAuthorized = true;
				}
			});
			return isAuthorized;
		},
		getAccount: function(){
			$rootScope.loadingAccount= true;
			$http.get('security/account')
				.then(function(response){
					authService.loginConfirmed(response.data);
				})
		}
		
	}
});
	
