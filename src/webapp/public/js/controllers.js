angular
	.module('FomoControllers',['FomoSecurity'])
	.controller('navigation',function($rootScope){
		
	})
	.controller('home',function($http){
		var self=this;
		self.greeting={};
		$http.get('security/account')
		.then(function(response){
			self.greeting.id=response.data.email;
		})
	});